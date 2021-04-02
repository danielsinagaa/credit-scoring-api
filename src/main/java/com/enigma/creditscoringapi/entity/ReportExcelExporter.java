package com.enigma.creditscoringapi.entity;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReportExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<TransactionReport> reports;

    public ReportExcelExporter(List<TransactionReport> reports) {
        this.reports = reports;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("TransactionReport");
    }

    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("No.");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Customer Name");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Customer Email");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Customer ID Number");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Customer Address");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Customer Type");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Submitter");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("Income");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("Outcome");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("Loan");
        cell.setCellStyle(style);

        cell = row.createCell(10);
        cell.setCellValue("Tenor");
        cell.setCellStyle(style);

        cell = row.createCell(11);
        cell.setCellValue("Interest Rate");
        cell.setCellStyle(style);

        cell = row.createCell(12);
        cell.setCellValue("Main Loan");
        cell.setCellStyle(style);

        cell = row.createCell(13);
        cell.setCellValue("Interest");
        cell.setCellStyle(style);

        cell = row.createCell(14);
        cell.setCellValue("Installment");
        cell.setCellStyle(style);

        cell = row.createCell(15);
        cell.setCellValue("Installment Total");
        cell.setCellStyle(style);

        cell = row.createCell(16);
        cell.setCellValue("Installment");
        cell.setCellStyle(style);

        cell = row.createCell(17);
        cell.setCellValue("Loan Purpose");
        cell.setCellStyle(style);

        cell = row.createCell(17);
        cell.setCellValue("Credit Ratio");
        cell.setCellStyle(style);

        cell = row.createCell(18);
        cell.setCellValue("Finance Criteria");
        cell.setCellStyle(style);

        cell = row.createCell(19);
        cell.setCellValue("Employee Criteria");
        cell.setCellStyle(style);

        cell = row.createCell(20);
        cell.setCellValue("Approval Status");
        cell.setCellStyle(style);

        cell = row.createCell(21);
        cell.setCellValue("Submit Date");
        cell.setCellStyle(style);

        cell = row.createCell(22);
        cell.setCellValue("Approval Date");
        cell.setCellStyle(style);

    }

    private void writeDataRows() {
        int rows = 1;
        CellStyle style = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        style.setDataFormat(creationHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        for (TransactionReport report : reports) {
            Row row = sheet.createRow(rows++);

            String financeCriteria = "NOT PASS";
            String employeeCriteria = "NOT PASS";
            String approvalStatus = "REJECTED";

            if (report.getApproval().getTransaction().getFinanceCriteria()) financeCriteria = "PASS";

            try {
                if (report.getApproval().getTransaction().getEmployeeCriteria()) employeeCriteria = "PASS";
            } catch (NullPointerException e) {
                employeeCriteria = "NON EMPLOYEE";
            }

            if (report.getApproval().getApprove()) approvalStatus = "APPROVED";

            Cell cell = row.createCell(0);
            cell.setCellValue(rows - 1);
            CellStyle noStyle = workbook.createCellStyle();
            noStyle.setAlignment(HorizontalAlignment.LEFT);
            cell.setCellStyle(noStyle);
            sheet.autoSizeColumn(0);

            cell = row.createCell(1);
            cell.setCellValue(report.getApproval().getTransaction().getCustomer().getName());
            sheet.autoSizeColumn(1);

            cell = row.createCell(2);
            cell.setCellValue(report.getApproval().getTransaction().getCustomer().getEmail());
            sheet.autoSizeColumn(2);

            cell = row.createCell(3);
            cell.setCellValue(report.getApproval().getTransaction().getCustomer().getIdNumber());
            sheet.autoSizeColumn(3);

            cell = row.createCell(4);
            cell.setCellValue(report.getApproval().getTransaction().getCustomer().getAddress());
            sheet.autoSizeColumn(4);

            cell = row.createCell(5);
            cell.setCellValue(String.valueOf(report.getApproval().getTransaction().getCustomer().getEmployeeType()));
            sheet.autoSizeColumn(5);

            cell = row.createCell(6);
            cell.setCellValue(report.getApproval().getTransaction().getCustomer().getSubmitter());
            sheet.autoSizeColumn(6);

            cell = row.createCell(7);
            cell.setCellValue(report.getApproval().getTransaction().getIncome());
            sheet.autoSizeColumn(7);

            cell = row.createCell(8);
            cell.setCellValue(report.getApproval().getTransaction().getOutcome());
            sheet.autoSizeColumn(8);

            cell = row.createCell(9);
            cell.setCellValue(report.getApproval().getTransaction().getLoan());
            sheet.autoSizeColumn(9);

            cell = row.createCell(10);
            cell.setCellValue(report.getApproval().getTransaction().getTenor() + " months");
            sheet.autoSizeColumn(10);

            cell = row.createCell(11);
            cell.setCellValue(report.getApproval().getTransaction().getInterestRate() + "%");
            sheet.autoSizeColumn(11);

            cell = row.createCell(12);
            cell.setCellValue(report.getApproval().getTransaction().getMainLoan());
            sheet.autoSizeColumn(12);

            cell = row.createCell(13);
            cell.setCellValue(report.getApproval().getTransaction().getInterest());
            sheet.autoSizeColumn(13);

            cell = row.createCell(14);
            cell.setCellValue(report.getApproval().getTransaction().getInstallment());
            sheet.autoSizeColumn(14);

            cell = row.createCell(15);
            cell.setCellValue(report.getApproval().getTransaction().getInstallmentTotal());
            sheet.autoSizeColumn(15);

            cell = row.createCell(16);
            cell.setCellValue(report.getApproval().getTransaction().getNeedType().getType());
            sheet.autoSizeColumn(16);

            cell = row.createCell(17);
            cell.setCellValue(report.getApproval().getTransaction().getCreditRatio() + "%");
            sheet.autoSizeColumn(17);

            cell = row.createCell(18);
            cell.setCellValue(financeCriteria);
            sheet.autoSizeColumn(18);

            cell = row.createCell(19);
            cell.setCellValue(employeeCriteria);
            sheet.autoSizeColumn(19);

            cell = row.createCell(20);
            cell.setCellValue(approvalStatus);
            sheet.autoSizeColumn(20);

            cell = row.createCell(21);
            sheet.autoSizeColumn(21);
            cell.setCellStyle(style);
            cell.setCellValue(report.getSubmitDate());

            cell = row.createCell(22);
            sheet.autoSizeColumn(22);
            cell.setCellStyle(style);
            cell.setCellValue(report.getApprovalDate());

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
