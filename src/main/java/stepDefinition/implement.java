package stepDefinition;

import check.dropDown;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class implement {

    public static Properties prop;
    public static WebDriver driver;
    public static EdgeOptions edgeOptions;
    public static JavascriptExecutor js;
    public static WebElement element;

    @Given("Go to XYR tutorial dropdown page")
    public void go_to_xyr_tutorial_dropdown_page() {
        driver.get(prop.getProperty("dropDown"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @When("select coursename and ide name")
    public void select_coursename_and_ide_name() {
        dropDown.dropDown(driver, "course", "java");
        dropDown.dropDown(driver, "ide", "vs");
    }

    @Then("close  the browser")
    public void close_the_browser() {
        File path = new File(System.getProperty("user.dir") + "\\Screenshots\\dropDown.png");
        TakesScreenshot t = (TakesScreenshot) driver;
        File f = t.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(f, path);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Given("Start chrome browser")
    public void startChromeBrowser() throws IOException {

        WebDriverManager.edgedriver().setup();
        edgeOptions = new EdgeOptions();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setAcceptInsecureCerts(true);
        edgeOptions.setCapability("excludeSwitches", "disable-popup-blocking");
        edgeOptions.setCapability("excludeSwitches", "enable-automation");
        edgeOptions.setCapability("useAutomationExtension", false);
        edgeOptions.setCapability("personalization_data_consent_enabled", true);
        edgeOptions.merge(caps);
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\PropertyFile.properties";
        FileReader f = new FileReader(path);
        prop = new Properties();
        prop.load(f);
        driver = new EdgeDriver(edgeOptions);
    }

    @After
    public void attachScreenshot(io.cucumber.java.Scenario sc)
    {
        TakesScreenshot ts = (TakesScreenshot) driver;
        byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
     sc.attach(screenshot,"image/png","screenshotattached");
        driver.close();
    }
}
