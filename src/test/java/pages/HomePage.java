package pages;

import Utilities.Excelcode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HomePage {

    public HomePage() throws IOException {
        WebDriverManager.edgedriver().setup();
        EdgeOptions edgeOptions = new EdgeOptions();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setAcceptInsecureCerts(true);
        edgeOptions.setCapability("excludeSwitches", "disable-popup-blocking");
        edgeOptions.setCapability("excludeSwitches", "enable-automation");
        edgeOptions.setCapability("useAutomationExtension", false);
        edgeOptions.setCapability("personalization_data_consent_enabled", true);
        edgeOptions.merge(caps);
        driver = new EdgeDriver(edgeOptions);
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\PropertyFile.properties";
        FileReader f = new FileReader(path);
        prop = new Properties();
        prop.load(f);
    }

    public static Properties prop;
    public static WebDriver driver;

    @Test
    public void login() throws IOException {
        driver.get(prop.get("WebsiteURL").toString());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span[text()='✕']")).click();
        driver.findElement(By.xpath("//span[text()='Mobiles']")).click();
        List<WebElement> brands = driver.findElements(By.xpath("//a//p"));
        for(WebElement brand: brands)
        {
            if (brand.getText().equalsIgnoreCase("Samsung"))
            {
                brand.click();
                break;
            }
        }
        List<WebElement> models = driver.findElements(By.xpath("//div[contains(@class,'_3pLy-c row')]/div/div[@class='_4rR01T']"));
        /*for (WebElement model: models)
        {
            System.out.println(model.getText());
        }*/

        for (int i=0; i<models.size(); i++)
        {
            System.out.println(models.get(i).getText());
            Excelcode.setCellData(i+1,0, models.get(i).getText());
        }

    }

    @AfterTest
    public void browerClose()
    {
        driver.quit();
    }

}
