package itmo.localpiper;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GetPartsTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    void getParts(String browser) {
        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://exist.ru/");

        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/header/section[1]/div/div[1]/div/a[1]")));
        popup.click();

        // Click on "Каталог"
        driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div[2]/div/div")).click();

        // Click on "Запчасти для ТО"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/header/div/div/div[2]/div/div/div/div/div[1]/div/a[4]"))).click();

        // Click on "Acura"
        driver.findElement(By.xpath("/html/body/div/div/div[1]/form/span/div[1]/div[1]/div/div[1]/ul/li[1]/a")).click();

        // Select specific car model
        driver.findElement(By.xpath("/html/body/div/div/div[1]/form/div[3]/div[2]/a[6]")).click();

        // Select car modification
        driver.findElement(By.xpath("/html/body/div/div/div[1]/form/div[5]/div/table/tbody/tr[2]/td[1]/a")).click();

        // Click on "Цены"
        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/form/div[5]/div/div/div[1]/div[1]/div[2]/a")).click();

        // Move price range slider (example, may need adjusting)
        WebElement upperSlider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[1]/form/div[4]/div[2]/div[1]/div[3]/div[1]/div/div[3]/div")));
        new Actions(driver).clickAndHold(upperSlider).moveByOffset(-10, 0).release().perform();

        WebElement lowerSlider = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/form/div[4]/div[2]/div[1]/div[3]/div[1]/div/div[2]/div"));
        new Actions(driver).clickAndHold(lowerSlider).moveByOffset(20, 0).release().perform();

        // Select first brand filter
        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/form/div[4]/div[2]/div[1]/div[4]/div[3]/div[1]/label")).click();

        // Open sorting dropdown
        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/form/div[4]/div[2]/div[1]/div[2]/div/div[1]")).click();

        // Select sorting option (Price Ascending)
        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/form/div[4]/div[2]/div[1]/div[2]/div/div[2]/ul/li[4]/a")).click();

        // Click "Add to Cart"
        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/form/div[4]/div[3]/div[3]/div[2]/div[2]/div[1]/div[1]/div[2]")).click();

        // Click "Cart"
        driver.findElement(By.xpath("/html/body/div[1]/header/section[2]/div[3]/noindex[1]/a")).click();

        // Check if cart is not empty (as validation)
        WebElement cartElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//div[contains(@class, 'prc-table__basket-office')]")));

        assertTrue(cartElement.isDisplayed(), "Cart should not be empty");

        // Click "Proceed to Checkout"
        driver.findElement(By.xpath("/html/body/div[1]/header/section[2]/div[3]/noindex[1]/a")).click();



    }
}
