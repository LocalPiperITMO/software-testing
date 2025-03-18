package itmo.localpiper;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadOriginalCatalogsTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Map<String, Object> vars;

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String waitForNewWindow(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            System.out.print("oh");
        }
        Set<String> currentWindows = driver.getWindowHandles();
        Set<String> previousWindows = (Set<String>) vars.get("window_handles");

        if (currentWindows.size() > previousWindows.size()) {
            currentWindows.removeAll(previousWindows);
            return currentWindows.iterator().next();
        }
        return null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome"})
    void readOriginalCatalogs(String browser) throws InterruptedException {
        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        vars = new HashMap<>();
        driver.manage().window().maximize();
        driver.get("https://exist.ru/");

        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/header/section[1]/div/div[1]/div/a[1]")));
        popup.click();

        driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div[2]/div/div/span")).click();

        // Click on "Оригинальные каталоги" (Original Catalogs)
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Оригинальные каталоги')]"))).click();

        // Save current windows before clicking
        vars.put("window_handles", driver.getWindowHandles());

        // Click on "Каталог оригинальных запчастей для автомобилей AUDI (Elcats.ru)" instantly
        driver.findElement(By.xpath("//a[contains(text(), 'Каталог оригинальных запчастей для автомобилей AUDI')]")).click();

        // Wait for the new window to open
        String newWindowHandle = waitForNewWindow(2000);
        if (newWindowHandle != null) {
            driver.switchTo().window(newWindowHandle);
        }

        // Verify that the new page contains "Audi"
        assertTrue(driver.getTitle().contains("Автозапчасти AUDI"), "New window should contain Audi catalog.");
    }
}
