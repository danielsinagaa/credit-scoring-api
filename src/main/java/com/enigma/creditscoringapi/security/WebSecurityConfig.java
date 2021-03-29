package com.enigma.creditscoringapi.security;

import com.enigma.creditscoringapi.security.jwt.AuthEntryPointJwt;
import com.enigma.creditscoringapi.security.service.UserDetailsServiceImpl;
import com.enigma.creditscoringapi.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RoleService roleService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] allRoles = new String[roleService.allRoleName().size()];
        allRoles = roleService.allRoleName().toArray(allRoles);

        String[] readAllCustomer = new String[roleService.findAllInputCustomer().size()];
        readAllCustomer = roleService.findAllReadAllCustomer().toArray(readAllCustomer);

        String[] inputCustomer = new String[roleService.findAllInputCustomer().size()];
        inputCustomer = roleService.findAllInputCustomer().toArray(inputCustomer);

        String[] inputTransaction = new String[roleService.findAllInputTransaction().size()];
        inputTransaction = roleService.findAllInputTransaction().toArray(inputTransaction);

        String[] readAllTransaction = new String[roleService.findAllInputTransaction().size()];
        readAllTransaction = roleService.findReadAllTransaction().toArray(readAllTransaction);

        String[] approveTransaction = new String[roleService.findAllApproveTransaction().size()];
        approveTransaction = roleService.findAllApproveTransaction().toArray(approveTransaction);

        int first = roleService.findAllInputTransaction().size();
        int second = roleService.findAllApproveTransaction().size();

        String[] readAllApproval = new String[first + second];

        System.arraycopy(readAllTransaction, 0, readAllApproval, 0, first);
        System.arraycopy(approveTransaction, 0, readAllApproval, first, second);

        String[] readAllReport = new String[roleService.findAllReadAllReport().size()];
        readAllReport = roleService.findAllReadAllReport().toArray(readAllReport);

        String[] readAllReportBySubmitter = new String[roleService.findAllReadAllReportByTransaction().size()];
        readAllReportBySubmitter = roleService.findAllReadAllReportByTransaction().toArray(readAllReportBySubmitter);

        int fLength = roleService.findAllReadAllReport().size();
        int sLength = roleService.findAllReadAllReportByTransaction().size();

        String[] readAllReports = new String[fLength + sLength];

        System.arraycopy(readAllReport, 0, readAllReports, 0, fLength);
        System.arraycopy(readAllReportBySubmitter, 0, readAllReports, fLength, sLength);

        String[] master = new String[roleService.findAllMaster().size()];
        master = roleService.findAllMaster().toArray(master);

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/customer").hasAnyAuthority(readAllCustomer)
                .antMatchers(HttpMethod.GET,"/customer/contract").hasAnyAuthority(readAllCustomer)
                .antMatchers(HttpMethod.GET,"/customer/regular").hasAnyAuthority(readAllCustomer)
                .antMatchers(HttpMethod.GET,"/customer/non").hasAnyAuthority(readAllCustomer)
                .antMatchers(HttpMethod.GET,"/customer/principal").hasAnyAuthority(inputCustomer)
                .antMatchers(HttpMethod.GET,"/customer/contract/principal").hasAnyAuthority(inputCustomer)
                .antMatchers(HttpMethod.GET,"/customer/regular/principal").hasAnyAuthority(inputCustomer)
                .antMatchers(HttpMethod.GET,"/customer/non/principal").hasAnyAuthority(inputCustomer)
                .antMatchers("/customer/**").hasAnyAuthority(inputCustomer)
                .antMatchers(HttpMethod.POST,"/transaction").hasAnyAuthority(inputTransaction)
                .antMatchers(HttpMethod.GET,"/approval").hasAnyAuthority(readAllApproval)
                .antMatchers(HttpMethod.GET,"/approval/waiting").hasAnyAuthority(readAllApproval)
                .antMatchers(HttpMethod.GET,"/approval/approved").hasAnyAuthority(readAllApproval)
                .antMatchers(HttpMethod.GET,"/approval/rejected").hasAnyAuthority(readAllApproval)
                .antMatchers(HttpMethod.GET,"/approval/principal/**").hasAnyAuthority(inputTransaction)
                .antMatchers(HttpMethod.GET,"/approval/**").hasAnyAuthority(approveTransaction)
                .antMatchers(HttpMethod.PATCH,"/approval/**").hasAnyAuthority(approveTransaction)
                .antMatchers(HttpMethod.GET, "/need").hasAnyAuthority(allRoles)
                .antMatchers(HttpMethod.PATCH, "/users/**").hasAnyAuthority(allRoles)
                .antMatchers(HttpMethod.GET, "/users/email/**").hasAnyAuthority(allRoles)
                .antMatchers(HttpMethod.GET, "/users/password/**").hasAnyAuthority(allRoles)
                .antMatchers(HttpMethod.GET, "/report").hasAnyAuthority(readAllReport)
                .antMatchers(HttpMethod.GET, "/report/principal").hasAnyAuthority(readAllReportBySubmitter)
                .antMatchers(HttpMethod.GET, "/report/**").hasAnyAuthority(readAllReports)
                .anyRequest().hasAnyAuthority(master);

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
