import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Webform {
    WebDriver driver;
    @Before
    public void browserSetup () {
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("headed");
        driver = new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @Test
    public void writeOnTextBox() throws InterruptedException {

        driver.get("https://www.digitalunite.com/practice-webform-learners");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Accept cookies
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ccc-notify-accept"))).click();

        // Name
        wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-name")))
                .sendKeys("Abdur Rahim");

        // Phone number
        wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-number")))
                .sendKeys("123456780");

        // Date
        WebElement date = driver.findElement(By.id("edit-date"));
        date.sendKeys(Keys.CONTROL + "a");
        date.sendKeys(Keys.BACK_SPACE);
        date.sendKeys("05/08/1993", Keys.ENTER);

        // Email
        wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-email")))
                .sendKeys("user@gmail.com");


        // description
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("edit-tell-us-a-bit-about-yourself-")))
                .sendKeys("This is a demo description");

        // scroll the page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1500)");

        // File upload
        WebElement uploadElement = driver.findElement(By.id("edit-uploadocument-upload"));
        uploadElement.sendKeys("/Users/fatemaakhter/Downloads/Cypress-1.pdf");
        String text = driver.findElement(By.id("edit-uploadocument-upload")).getText();
        Thread.sleep(2000);
        //Assert.assertTrue(text.contains("Cypress-1.pdf"));


    // Checkbox
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("edit-age")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
        Thread.sleep(300);
        js.executeScript("arguments[0].click();", checkbox);
        System.out.println("Checkbox selected: " + checkbox.isSelected());

        // Manual reCAPTCHA solving
        System.out.println(" PAUSED: Please solve reCAPTCHA manually in the browser...");
        System.out.println("You have 60 seconds...");
        Thread.sleep(60000);  // Wait 60 seconds for manual solving


        // Submit
        driver.findElement(By.id("edit-submit")).click();

        // successful message show

        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );

        Assert.assertEquals("Thank you for your submission!", successMsg.getText());

    }
public void teardown(){
        driver.close();

    }

}
