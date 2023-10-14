package check;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class dropDown {


    public static void dropDown(WebDriver driver, String element, String value)
    {
        Select drop = new Select(driver.findElement(By.id(element)));
        drop.selectByValue(value);
    }
}
