package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;
import utils.Logs;
import utils.WebDriverProvider;
import utils.listeners.SuiteListeners;
import utils.listeners.TestListeners;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Listeners({TestListeners.class, SuiteListeners.class})
public class BaseTest {
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected static SoftAssert softAssertl;
    protected static WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void mastersetUp() {
        softAssertl = new SoftAssert();
        Logs.debug("Inicializamos el Webdriver");
        driver = new ChromeDriver();

        Logs.debug("Ingresamos a la URL");
        driver.get("https://demoqa.com/automation-practice-form");

        Logs.debug("Maximizamos pantalla");
        driver.manage().window().maximize();

        Logs.debug("Borrando cookies");
        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        Logs.debug("asignamos el webdriver");
        new WebDriverProvider().set(driver);

    }

    @AfterMethod(alwaysRun = true)
    public void mastertearDown() {
        Logs.debug("Finalizamos el webDriver");
        driver.quit();
    }


    public static void scrollToElement(By component) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(component));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static List<String> getElementos(By component) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elementos = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(component));
        List<String> catalog = new ArrayList<>();
        for (WebElement elemento : elementos) {
            catalog.add(elemento.getText().trim());
        }
        return catalog;
    }

    public static void getOpctionMenu(String option, By component) throws InterruptedException {
        List<WebElement> cards = driver.findElements(component);
        for (WebElement card : cards) {
            String title = card.findElement(By.tagName("h5")).getText().trim();
            if (title.equalsIgnoreCase(option)) {
                card.click();
                break;
            }
        }
    }
}

