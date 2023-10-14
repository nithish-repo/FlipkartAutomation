package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;

public class Listener implements ITestListener, ISuiteListener {

    public WebDriver driver;
    public ExtentSparkReporter htmlReporter;
    public ExtentReports reports;
    public ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        htmlReporter =new ExtentSparkReporter("./Reports/"+context.getName()+".html");
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setReportName("FlipkartAutomation");
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setReportName("Automation Results");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);
        reports.setSystemInfo("Reporter Name", "Nithish");
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = reports.createTest(result.getTestClass().getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestContext context = result.getTestContext();
        driver = (WebDriver) context.getAttribute("WebDriver");
        File path = new File("C:\\Users\\Nithish\\IdeaProjects\\FlipkartAutomation\\Reports\\"+result.getMethod().getMethodName() + ".png");

        TakesScreenshot t = (TakesScreenshot) driver;
        File f =t.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(f, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pathString = path.getAbsolutePath();
        test.addScreenCaptureFromPath(result.getMethod().getMethodName() +".png");

        Markup m = MarkupHelper.createLabel(result.getMethod().getMethodName().toUpperCase(), ExtentColor.GREEN);
        test.log(Status.PASS, m);
        test.pass("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(result.getMethod().getMethodName() +".png").build());

    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestContext context = result.getTestContext();
        driver = (WebDriver)context.getAttribute("WebDriver");
        File path = new File("C:\\Users\\Nithish\\IdeaProjects\\FlipkartAutomation\\Reports\\"+result.getMethod().getMethodName() + ".png");
        TakesScreenshot t = (TakesScreenshot) driver;
        File f =t.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(f, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        test.addScreenCaptureFromPath(result.getMethod().getMethodName()+".png");
        Markup m = MarkupHelper.createLabel(result.getMethod().getMethodName().toUpperCase(), ExtentColor.RED);
        test.log(Status.FAIL, m);

    }
    @Override
    public void onFinish(ISuite suite) {

        reports.flush();
    }
}
