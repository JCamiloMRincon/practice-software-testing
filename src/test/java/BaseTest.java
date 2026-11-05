import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected Wait<WebDriver> wait;

    @BeforeEach
    public void setUp() throws IOException {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        URL gridUrl = new URL("http://localhost:4444");

        switch(browser) {
            case "firefox":
                driver = new RemoteWebDriver(gridUrl, new FirefoxOptions());
                break;
            case "edge":
                driver = new RemoteWebDriver(gridUrl, new EdgeOptions());
                break;
            default:
                ChromeOptions chromeOptions = new ChromeOptions();

                // Create temp profile dir
                Path tempProfile = Files.createTempDirectory("chrome-profile");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.6478.57 Safari/537.36");

                chromeOptions.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath().toString());

                // Headless (optional)
                chromeOptions.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");

                driver = new ChromeDriver(chromeOptions);
                break;
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
