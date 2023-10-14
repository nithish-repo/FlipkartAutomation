package Utilities;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features/xyrfeature.feature",monochrome = true, dryRun = false, glue = "stepDefinition", plugin = {"pretty","html:output/report.html"}, publish = true)
public class xyrRunner extends AbstractTestNGCucumberTests {





}
