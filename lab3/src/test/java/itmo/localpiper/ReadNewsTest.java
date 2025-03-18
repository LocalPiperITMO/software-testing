package itmo.localpiper;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadNewsTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    void readNews(String browser) {
        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.get("https://exist.ru/");

        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/header/section[1]/div/div[1]/div/a[1]")));
        popup.click();

        // Scroll down to the news section
        js.executeScript("window.scrollBy(0, 2200)");

        // Click on the second news category (e.g., "Новости")
        WebElement newsCategory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='newsHeader']/li[2]/a")));
        newsCategory.click();

        // Click on the second news article
        WebElement secondNewsArticle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class, 'newsdiv')])[2]//span")));
        secondNewsArticle.click();
    }
}
