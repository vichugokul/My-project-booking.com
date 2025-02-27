package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;


public abstract class BasePage {

    public WebDriver driver;
    int minWait = 2000;
    int maxWait = 8000;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    protected void minWait(){
        try {
            Thread.sleep(minWait);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    protected void maxWait(){
        try {
            Thread.sleep(maxWait);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected WebElement waitForElement(WebElement element) {

        WebElement elem = null;
        while(true) {
            try {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                elem = wait.until(ExpectedConditions.elementToBeClickable(element));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                break;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return elem;
    }

    protected WebElement waitForElement(By loc) {

        WebElement elem = null;
        while(true) {
            try {
                Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                elem = wait.until(ExpectedConditions.elementToBeClickable(loc));
                break;
            } catch (Exception e) {
                // TODO: handle exception
                Assert.fail();
            }
        }
        return elem;
    }

    protected void enterKeys(By by, Keys key){
        driver.findElement(by).sendKeys(key);
    }


    protected void enterKeys(By by, String text){

        try{
            WebElement elem = waitForElement(by);
            minWait();
            elem.sendKeys(text);

        }
        catch (ElementClickInterceptedException e){
            System.err.println(e);
            enterKeys(by, text);
        }

    }

    protected List<WebElement> elementList(By by){
        return driver.findElements(by);
    }

    protected void enterKeys(WebElement elem, String text){

        try{
            WebElement element = waitForElement(elem);
            minWait();
            element.clear();
            element.sendKeys(text);

        }
        catch (ElementClickInterceptedException e){
            System.err.println(e);
            enterKeys(elem, text);
        }

    }

    protected String getText(By by){
        return waitForElement(by).getText();
    }

    protected void clickJS(By path){

        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();",driver.findElement(path));
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    protected void webDriverClick(By path){

        try{
            WebElement elem = waitForElement(path);
            minWait();
            elem.click();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    protected String getTitle(){
        return driver.getTitle();
    }

    protected boolean verifyElement(By by){
        return driver.findElement(by).isDisplayed();
    }

    protected boolean verifyElementPresent(By by){

        boolean isElemPresent = false;
        try {
            driver.findElement(by).isDisplayed();
            isElemPresent = true;
        }
        catch (Exception e){
            System.err.println(e);
        }

        return isElemPresent;
    }

    protected boolean verifyElementPresent(WebElement elem){
        boolean isElemPresent = false;
        try {
            isElemPresent = waitForElement(elem).isDisplayed();
        }
        catch (Exception e){
            System.err.println(e);
        }

        return isElemPresent;
    }

    protected void inputText_JS(By by, String val){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='"+val+"';",by);

    }

    protected void scrollToView(By by){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(by));
    }

    protected void selByText(By by,String text){
        try{
            new Select(waitForElement(by)).selectByVisibleText(text);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    protected void selByValue(By by,String val){
        try{
            new Select(waitForElement(by)).selectByValue(val);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    protected void selByIndex(By by,int index){
        try{
            new Select(waitForElement(by)).selectByIndex(index);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    protected void doubleClick(By by){
        try {
            WebElement elem = waitForElement(by);
            new Actions(driver).moveToElement(elem).doubleClick().build().perform();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    protected void mouseHover(By by){
        try {
            WebElement elem = waitForElement(by);
            new Actions(driver).moveToElement(elem).build().perform();
        }
        catch (Exception e){
            System.err.println(e);
        }

    }


    protected void handleAlertAccept() {
        driver.switchTo().alert().accept();
    }

    protected void handleAlertDismiss() {
        driver.switchTo().alert().dismiss();
    }


    protected void switchToFrame(String name){
        try{
            new WebDriverWait(driver, Duration.ofSeconds(60))
                    .until(
                            ExpectedConditions.frameToBeAvailableAndSwitchToIt(name)
                    );
        }
        catch (NoSuchFrameException e){
            System.err.println(e);
        }
    }

    protected void switchToFrame(WebElement elem){
        try{
            new WebDriverWait(driver, Duration.ofSeconds(60))
                    .until(
                            ExpectedConditions.frameToBeAvailableAndSwitchToIt(elem)
                    );
        }
        catch (NoSuchFrameException e){
            System.err.println(e);
        }
    }

    protected void switchToSecondWindow(){
        String[] handles = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(handles[1]);
    }

    protected void switchToNthWindow(int n){
        String[] handles = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(handles[n-1]);
    }

    protected void switchToNthWindowAfterClosingFirstWin(int n){
        driver.close();
        String[] handles = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(handles[n-1]);
    }

    public void holdAndRelease(By by, int xoffset){
        Actions act = new Actions(driver);
        act.clickAndHold(waitForElement(by))
                .moveByOffset(xoffset,0)
                .release()
                .build()
                .perform();
    }






}