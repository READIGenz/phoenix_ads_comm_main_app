package com.comm.main.app;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.MediaType;

@Configuration
@PropertySources({
        @PropertySource(value = {"classpath:urlconstants.properties"}, ignoreResourceNotFound = true),
        @PropertySource(value = {"file:config/urlconstants.properties"}, ignoreResourceNotFound = true)
})
@Getter
@Setter
@NoArgsConstructor
public class UrlConstants {

    // URL Constants
    public static final String HOME = "/";
    public static final String CSV_TO_DB = "/csv_to_db";
    public static final String ORG_DATA = "/org_data";
    public static final String GENERATE_FILES = "/generate_files";
    public static final String CONVERT_EXCEL_CSV = "/excel_to_csv";
    public static final String GET_MESSAGE = "/getMessage";
    public static final String UPLOAD = "/upload";
    public static final String DATA_CONVERSION = "/dataConversion";
    public static final String RUN_JAR = "/api/run-jar";
    public static final String RUN_JAR_BACKUP = "/api/run-jar-backup";
    public static final String GENERATE_REPORT = "/generateReport";
    public static final String HOME_DISPLAY = "/home";
    public static final String TUDF_EXCEL_MAP = "/tudf_to_excel";
    public static final String UPLOAD_EXCEL = "/upload-excel";
    public static final String FILE = "file";
    public static final String REDIRECT_SELECT_REPORT = "redirect:/select-report";
    public static final String  SELECT_REPORT_MAP = "/select-report";
    public static final String  BORROWER_REC_MAP = "/borrower_rec";
    public static final String  CREDIT_REC_MAP = "/credit_rec";
    public static final String  FIN_REP_MAP = "/financial_rec";
    public static final String  REL_GUAR_MAP = "/rel_guar_rec";
    public static final String  CALL_PROC_MAP = "/call-procedures";


    @Value("${page.index}")
    public String INDEX_PAGE;

    @Value("${page.csvToDb}")
    public String CSV_TO_DB_PAGE;

    @Value("${page.orgData}")
    public String ORG_DATA_PAGE;

    @Value("${page.generateFiles}")
    public String GENERATE_FILES_PAGE;

    @Value("${page.convert.csv}")
    public String CONVERT_EXCEL_CSV_PAGE;

    @Value("${page.dqiReport}")
    public String DQI_REPORT_PAGE;

    @Value("${attribute.message}")
    public String MESSAGE;

    @Value("${tudf.to.excel}")
    public String TUDF_TO_EXCEL;

    @Value("${report.borrower.record.analysis}")
    private String borrowerRecordAnalysisPage;

    @Value("${report.credit.record.analysis}")
    private String creditRecordAnalysisPage;

    @Value("${report.financial.summary}")
    private String financialSummaryPage;

    @Value("${report.relationship.guarantor}")
    private String relationshipGuarantorPage;

    // Media Type for Excel
    static final MediaType EXCEL_MEDIA_TYPE = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final String DOCUMENT = "document";
    public static final String TXT_INPUT = "textInput";

    // Error messages from application.properties
    @Value("${FILE_ERR}")
    public String fileError;

    @Value("${NAME_ERR}")
    public String nameError;

    @Value("${NO_NAME_ERR}")
    public String noNameError;

    @Value("${SERVER_ERR}")
    public String serverError;
}
