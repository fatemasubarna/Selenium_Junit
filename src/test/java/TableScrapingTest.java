import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TableScrapingTest {
    WebDriver driver;
    @Before
    public void browserSetup () {
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--start-maximized");
        driver = new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void scrapeDataToCSV() {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");

        WebElement table = driver.findElement(By.className("shares-table"));
        List<WebElement> allRows = table.findElements(By.cssSelector("tbody tr"));

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "dse_stock_data_" + timestamp + ".csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write CSV header
            writer.write("SL NO,TRADING CODE,LTP,HIGH,LOW,CLOSEP,YCP,CHANGE,TRADE,VALUE (MN),VOLUME\n");

            for (WebElement row : allRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                // Print to console
                for (int i = 0; i < cells.size(); i++) {
                    String cellValue = cells.get(i).getText();
                    System.out.print(cellValue);

                    if (i < cells.size() - 1) {
                        System.out.print(" | ");
                        writer.write(cellValue + ",");
                    } else {
                        System.out.println();
                        writer.write(cellValue + "\n");
                    }
                }
            }

            System.out.println("\nData saved to: " + fileName);

        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // closes all browser windows and ends session
        }
    }
    }