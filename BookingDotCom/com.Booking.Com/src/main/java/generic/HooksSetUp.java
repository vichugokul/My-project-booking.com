package generic;

import commons.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HooksSetUp extends BaseTest {

    private static final Logger log = LogManager.getLogger(HooksSetUp.class);
    public WebDriver driver;


    @Before
    public void launchBrowser() throws IOException {
        log.info("Test Start");
//        setReport();
        this.driver = initializeDriver();
    }

//    public void setReport() throws IOException {
//        String file = "D:\\BookingDotCom\\com.Booking.Com\\src\\test\\resources\\extent.properties";
//        String key = "extent.reporter.spark.out";
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yy_hh_mm_ss");
//        String value = "test-output/TestReport/TestReport"+format.format(cal.getTime())+".html";
//        PropertyReader.setPropertyValue(file,key,value);
//    }

    public WebDriver getDriver(){
        return driver;
    }

    @After
    public void  tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/jpeg", "screenshot");
        }

        if (driver != null) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/jpeg", "screenshot");
            driver.quit();
        }
    }
}
