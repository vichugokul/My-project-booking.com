package commons;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.PropertyReader;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

public class BaseTest {

    public WebDriver driver;
    public String GlobalFilePath;
    public String Browser;
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    public WebDriver initializeDriver() throws IOException {

        GlobalFilePath = PropertyReader.getPropertyValue("src/test/resources/Config/PropertyFiles.properties","Global");
        String browser = PropertyReader.getPropertyValue(GlobalFilePath,"browser");
        String URL = PropertyReader.getPropertyValue(GlobalFilePath,"URL");
        switch (browser){
            case "chrome":
                ChromeOptions co = new ChromeOptions();
                co.setAcceptInsecureCerts(true);
                co.addArguments("--incognito");
                driver = new ChromeDriver(co);
                break;

            case "edge":
                EdgeOptions eo = new EdgeOptions();
                eo.setAcceptInsecureCerts(true);
                driver = new EdgeDriver(eo);
                break;

            case "firefox":
                FirefoxOptions fo = new FirefoxOptions();
                fo.setAcceptInsecureCerts(true);
                driver = new FirefoxDriver(fo);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get(URL);
        log.info("Successfully navigated to application");
        return driver;
    }


    public String getScreenshot() throws IOException {

        Random rand = new Random();
        String path = System.getProperty("user.dir")+"//Reports"+"//Screenshots//Screenshot"+rand.nextInt()+".JPEG";
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(path);
        FileUtils.copyFile(source,file);

        return path;
    }


}
