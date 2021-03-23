package com.enigma.creditscoringapi;

import com.enigma.creditscoringapi.entity.enums.ERole;

public class PasswordGenerator {
    public static void main(String[] args) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("password123"));
//
//        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        StringBuilder salt = new StringBuilder();
//        Random rnd = new Random();
//        while (salt.length() < 10) { // length of the random string.
//            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//            salt.append(SALTCHARS.charAt(index));
//        }
//        String saltStr = salt.toString().toLowerCase();
//        System.out.println(saltStr);

        ERole eRole = ERole.MASTER;

        System.out.println(eRole.toString());

    }

    
}
