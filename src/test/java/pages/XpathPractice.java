package pages;

import Utilities.Listener;
import Utilities.retryAnalyzer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;
import check.dropDown;

import javax.swing.text.Utilities;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners(Listener.class)
public class XpathPractice implements ITestListener {

    public XpathPractice() throws IOException {
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


    }

    public static Properties prop;
    public static WebDriver driver;
    public static EdgeOptions edgeOptions;
    public static JavascriptExecutor js;
    public static  WebElement element;

    @Test
    public static void testXpath()
    {
        //driver = new EdgeDriver(edgeOptions);
        driver.get(prop.getProperty("dropDown"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        dropDown.dropDown(driver, "course", "java");
        dropDown.dropDown(driver, "ide", "vs");
    }

    @Test(retryAnalyzer = retryAnalyzer.class)
    public static void  framesTest()
    {

        driver.get(prop.getProperty("frames"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        element = driver.findElement(By.xpath("(//h3[contains(text(),'Frame2:')])[1]"));
        js = (JavascriptExecutor) driver;
        // js.executeScript("arguments[0].scrollIntoView(true)", element);
        js.executeScript("window.scrollBy(0,700)");
        Wait<WebDriver> w = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchFrameException.class);
        w.until(driver -> driver.findElement(By.xpath("//iframe[@id='frm1']")));
        //w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("//iframe[@id='frm1']"));
        driver.switchTo().frame("frm1");
        dropDown.dropDown(driver,"course","python");



    }



    @AfterMethod
    public void closeBrowser(ITestResult result) {
        if ((ITestResult.FAILURE == result.getStatus())) {
            File path = new File(System.getProperty("user.dir") + "\\Screenshots\\AfterMethod"+result.getMethod().getMethodName()+".jpg");
            TakesScreenshot t = (TakesScreenshot) driver;
            File f = t.getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(f, path);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        driver.close();
    }
    @BeforeMethod
    public void setDriver(ITestContext context){
        driver = new EdgeDriver(edgeOptions);
        context.setAttribute("WebDriver", driver);
    }
}
