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

public class IssueRepairTest {
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
    void issueRepair(String browser) {
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

        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("/html/body/div[1]/header/section[1]/div/div[1]/div/a[1]")));
        popup.click();

        driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div[2]/ul/li[2]/a")).click();

        // Click on the "Repair" link
        driver.findElement(By.xpath("/html/body/div/div/div[1]/form/div[3]/div[1]/div[1]/h2/a")).click();

        // Wait for the city selection dropdown and click
        WebElement cityDropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[1]/form/div[4]/div/div[2]/div[2]/div[2]/div/div")));
        cityDropdown.click();

        // Wait for the city option and click
        WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"_78\"]")));
        cityOption.click();

        // Wait for the point type dropdown and click
        WebElement pointTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[1]/form/div[4]/div/div[2]/div[2]/div[3]/div[1]/div")));
        pointTypeDropdown.click();

        // please die thanks
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.mupGrayBackground')?.remove();");

        // Wait for "СТО" (Service Station) and click
        WebElement serviceStation = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), 'СТО')]")));
        serviceStation.click();
        // please die thanks
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.mupGrayBackground')?.remove();");
        
        // Wait for the car brand dropdown and click
        WebElement carBrandDropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[1]/form/div[4]/div/div[2]/div[2]/div[4]/div[1]/div")));
        carBrandDropdown.click();

        // Wait for "Audi" and click
        WebElement audiOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), 'Audi')]")));
        audiOption.click();

        // Select repair types
        WebElement repairType = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[1]/form/div[4]/div/div[2]/div[2]/div[5]/div[1]/div/div[2]")));
        repairType.click();

        // please die thanks
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.mupGrayBackground')?.remove();");

        // Wait for each feature checkbox to be clickable before clicking
        WebElement feature1 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[1]/form/div[4]/div/div[2]/div[2]/div[5]/div[2]/div/div[1]/label")));
        feature1.click();

        WebElement feature3 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[1]/form/div[4]/div/div[2]/div[2]/div[5]/div[2]/div/div[3]/label")));
        feature3.click();

        WebElement feature4 = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[1]/form/div[4]/div/div[2]/div[2]/div[5]/div[2]/div/div[4]/label")));
        feature4.click();

        // please die
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.dummy')?.remove();");
        ((JavascriptExecutor) driver).executeScript("document.querySelector('.mupGrayBackground')?.remove();");


        // Wait for the final button and click
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.print("oh");
        }
        WebElement finalButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[1]/form/div[4]/div/div[3]/div[3]/div[2]/a[1]")));
        finalButton.click();
    }
}
