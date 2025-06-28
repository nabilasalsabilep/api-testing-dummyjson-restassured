package runners;

import org.testng.annotations.AfterSuite;

import helper.GenerateReport;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "steps",
    plugin = {"pretty",                                   
                "html:target/cucumber-reports.html",          
                "json:target/cucumber.json",         
                "junit:target/cucumber-reports.xml"           
            },
    monochrome = true
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
    @AfterSuite
    public void after_suite() {
        GenerateReport.generateReport();
        System.out.println("generate report");
    }
}