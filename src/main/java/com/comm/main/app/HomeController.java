package com.comm.main.app;

import com.comm.tudftoexcel.Conversion;
import com.comm.ui.MessageController;
import com.commercial.backend.*;
import com.commercial.dqi.DQIWebPageTranslation;
import com.comm.reports.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.logging.Level;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UrlConstants urlConstants;

    // Autowired services for dependency injection
    @Autowired
    UploadExcelService uploadExcelService;
    @Autowired
    DataConversionService dataConversionService;
    @Autowired
    RunJarServiceImpl runJarServiceImpl;
    @Autowired
    RunJarBackUpDataService runJarBackUpDataService;
    @Autowired
    ReportGenerationService reportGenerationService;
    @Autowired
    DQIWebPageTranslation dqiWebPageTranslation;
    @Autowired
    Conversion conversion;
    @Autowired
    MessageController messageController;

    @Autowired
    ExcelToCsvService excelToCsvService;

    @Autowired
    CallProcedureController callProcedureController;
    @Autowired
    SelectReportController selectReportController;
    @Autowired
    BorrowerRecordController borrowerRecordController;
    @Autowired
    CreditRecordController creditRecordController;
    @Autowired
    FinancialRecordController financialRecordController;
    @Autowired
    RelationShipGuarantorController relationShipGuarantorController;


    // Handler for the home page
    @GetMapping(UrlConstants.HOME)
    public String home(Model model) {
        return urlConstants.INDEX_PAGE;
    }

    // Handler to show the upload form
    @GetMapping(UrlConstants.CSV_TO_DB)
    public String showUploadForm() {
        return urlConstants.CSV_TO_DB_PAGE;
    }

    // Handler to retrieve organization data
    @GetMapping(UrlConstants.ORG_DATA)
    public String getOrgData() {
        return urlConstants.ORG_DATA_PAGE;
    }

    // Handler to retrieve generated files
    @GetMapping(UrlConstants.GENERATE_FILES)
    public String getGenerateFiles() {
        return urlConstants.GENERATE_FILES_PAGE;
    }


    @GetMapping(UrlConstants.GET_MESSAGE)
    public ResponseEntity<String> getmessage(Model  model){
        String message = messageController.getMessage();
        return ResponseEntity.ok().body(message);
    }

    // Handler for file upload
    @PostMapping(UrlConstants.UPLOAD)
    public String handleFileUpload(@RequestParam("file") MultipartFile[] file, Model model) {
        // Call service to handle file upload
        long start = System.currentTimeMillis();
        String message = uploadExcelService.handleFileUpload(file, model);
        long end = System.currentTimeMillis();
        long durationInMillis = end - start;
        double durationInMinutes = durationInMillis / (1000.0 * 60);
        logger.info("Upload CSV File took {} minutes", durationInMinutes);
        model.addAttribute(urlConstants.MESSAGE, message);
        return urlConstants.CSV_TO_DB_PAGE; // Return the name of your HTML form file
    }

    // Handler for data conversion
    @PostMapping(UrlConstants.DATA_CONVERSION)
    public ResponseEntity<String> dataConversion(Model model) {
        // Call service to perform data conversion
        long start = System.currentTimeMillis();
        String message = dataConversionService.performDataConversion();
        long end = System.currentTimeMillis();
        long durationInMillis = end - start;
        double durationInMinutes = durationInMillis / (1000.0 * 60);
        logger.info("Multi Import took {} minutes", durationInMinutes);
        return ResponseEntity.ok().body(message);
    }

    // Handler to run a JAR file
    @GetMapping(UrlConstants.RUN_JAR)
    public ResponseEntity<String> runJar() throws SQLException, IOException {
        // Call service to run a JAR file
        long start = System.currentTimeMillis();
        String message = runJarServiceImpl.runJar();
        long end = System.currentTimeMillis();
        long durationInMillis = end - start;
        double durationInMinutes = durationInMillis / (1000.0 * 60);
        logger.info("Transactional Files took {} minutes", durationInMinutes);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(UrlConstants.RUN_JAR_BACKUP)
    public ResponseEntity<String> runJarBackup() throws SQLException, IOException {
        // Call service to run a JAR file
        long start = System.currentTimeMillis();
        String message = runJarBackUpDataService.runJar();
        long end = System.currentTimeMillis();
        long durationInMillis = end - start;
        double durationInMinutes = durationInMillis / (1000.0 * 60);
        logger.info("Complete Files took {} minutes", durationInMinutes);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Handler to generate a report
    @GetMapping(UrlConstants.GENERATE_REPORT)
    public ResponseEntity<byte[]> generateReport() throws IOException {
        // Call service to generate a report
        return reportGenerationService.generateReport();
    }


    @PostMapping(UrlConstants.HOME_DISPLAY)
    public String HomeDisplay(Model model) {
        // Call service to perform data conversion
        dqiWebPageTranslation.showStats(model);
        return urlConstants.DQI_REPORT_PAGE;
    }

    @PostMapping(UrlConstants.CALL_PROC_MAP)
    public String CallReportProcedure(Model model) {
        // Call service to perform data conversion
        callProcedureController.callProcedures(model);
        return urlConstants.REDIRECT_SELECT_REPORT;
    }

    @GetMapping(UrlConstants.SELECT_REPORT_MAP)
    public void generateReportPage(Model model) throws IOException, SQLException, ClassNotFoundException {
        // Call service to generate a report
        selectReportController.selectReport();

    }
    @GetMapping(UrlConstants.BORROWER_REC_MAP)
    public String generateReportModuleFirst(Model model) throws IOException, SQLException, ClassNotFoundException {
        // Call service to generate a report
        long start = System.currentTimeMillis();
        borrowerRecordController.showReport(model);
        long end = System.currentTimeMillis();
        long durationInMillis = end - start;
        double durationInMinutes = durationInMillis / (1000.0 * 60);
        logger.info("Borrower Record Analysis Report took {} minutes", durationInMinutes);
        return urlConstants.getBorrowerRecordAnalysisPage();
    }

    @GetMapping(UrlConstants.CREDIT_REC_MAP)
    public String generateReportModuleSecond(Model model) throws IOException, SQLException, ClassNotFoundException {
        // Call service to generate a report
        long start = System.currentTimeMillis();
        creditRecordController.showReport(model);
        long end = System.currentTimeMillis();
        long durationInMillis = end - start;
        double durationInMinutes = durationInMillis / (1000.0 * 60);
        logger.info("Credit Analysis Report took {} minutes", durationInMinutes);
        return urlConstants.getCreditRecordAnalysisPage();
    }

    @GetMapping(UrlConstants.FIN_REP_MAP)
    public String generateReportModuleThird(Model model) throws IOException, SQLException, ClassNotFoundException {
        // Call service to generate a report
        long start = System.currentTimeMillis();
        financialRecordController.showReport(model);
        long end = System.currentTimeMillis();
        long durationInMillis = end - start;
        double durationInMinutes = durationInMillis / (1000.0 * 60);
        logger.info("Financial Summary Report took {} minutes", durationInMinutes);
        return urlConstants.getFinancialSummaryPage();
    }
    @GetMapping(UrlConstants.REL_GUAR_MAP)
    public String generateReportModuleFourth(Model model) throws IOException, SQLException, ClassNotFoundException {
        // Call service to generate a report
        long start = System.currentTimeMillis();
        relationShipGuarantorController.showReport(model);
        long end = System.currentTimeMillis();
        long durationInMillis = end - start;
        double durationInMinutes = durationInMillis / (1000.0 * 60);
        logger.info("Relationship/Guarnator Report took {} minutes", durationInMinutes);
        return urlConstants.getRelationshipGuarantorPage();
    }
    @GetMapping(UrlConstants.TUDF_EXCEL_MAP)
    public String index() {
        return urlConstants.TUDF_TO_EXCEL; // Thymeleaf template name
    }

    @PostMapping(UrlConstants.UPLOAD_EXCEL)
    public ResponseEntity<Object> handleFileUpload(
            @RequestParam(UrlConstants.DOCUMENT) MultipartFile file,
            @RequestParam(UrlConstants.TXT_INPUT) String textInput,
            RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            logger.warn("File upload failed: Empty file received");
            return ResponseEntity.badRequest().body(urlConstants.fileError);
        }

        try {
            logger.info("Processing file upload. File name: {}, Size: {} bytes",
                    file.getOriginalFilename(), file.getSize());

            ByteArrayInputStream in;

            if (textInput == null || textInput.trim().isEmpty()) {
                logger.info("Text input is empty. Generating Excel without filter");
                in = conversion.generateExcel(file.getInputStream());
            } else {
                String normalizedInput = textInput.trim().toUpperCase();
                logger.info("Generating Excel with filter value: {}", normalizedInput);
                in = conversion.generateExcel(file.getInputStream(), normalizedInput);
            }

            if (in.available() == 0) {
                logger.error("Excel generation failed: No data found in output stream");
                return ResponseEntity.badRequest().body(urlConstants.noNameError);
            }

            logger.info("Excel generated successfully for file: {}", file.getOriginalFilename());

            return ResponseEntity.ok()
                    .contentType(UrlConstants.EXCEL_MEDIA_TYPE)
                    .body(new InputStreamResource(in));

        } catch (IOException e) {
            logger.error("Excel generation failed due to IOException. File: {}",
                    file.getOriginalFilename(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(urlConstants.serverError);
        } catch (Exception e) {
            logger.error("Unexpected error during file upload processing", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(urlConstants.serverError);
        }
    }





    @GetMapping(UrlConstants.CONVERT_EXCEL_CSV)
    public String getCSVConversion() {
        return urlConstants.CONVERT_EXCEL_CSV_PAGE;
    }



    @PostMapping("/exceltocsv")
    public ResponseEntity<byte[]> convertExcelToMultipleCsv(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded".getBytes(StandardCharsets.UTF_8));
        }

        String zipFileName = "converted_csvs.zip";
        try {
            byte[] zipBytes = excelToCsvService.convertExcelToZipOfCsv(file.getInputStream());
            String encodedName = URLEncoder.encode(zipFileName, StandardCharsets.UTF_8);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(zipBytes.length)
                    .body(zipBytes);

        } catch (IOException e) {
            String msg = "Failed to convert: " + e.getMessage();
            return ResponseEntity.status(500).body(msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}
