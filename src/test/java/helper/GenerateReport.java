package helper;

import java.io.File;
import java.util.Collections;

import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Configuration;

public class GenerateReport {
    public static void generateReport() {
        File reportOutputDirectory = new File("target/cucumber-reports");
        File jsonFile = new File("target/cucumber.json");

        Configuration config = new Configuration(reportOutputDirectory, "CucumberProject");
        config.addClassifications("Platform", "Backend");
        config.addClassifications("Type", "Rest");

        ReportBuilder reportBuilder = new ReportBuilder(
                Collections.singletonList(jsonFile.getAbsolutePath()), config);
        reportBuilder.generateReports();
    }
}
