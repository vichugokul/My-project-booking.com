package pages;

import commons.BasePage;
import generic.HooksSetUp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import utils.PropertyReader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    private static final Logger log = LogManager.getLogger(BasePage.class);

    WebDriver driver;
    private By Home_Page_Banner_Close,
               Home_Page_Destination,
               Home_Page_CalendarDrpDwn,
               Home_Page_Occupancy,
               Home_Page_No_Of_Adults_Minus,
               Home_Page_No_Of_Adults_Add,
               Home_Page_No_Of_Children_Minus,
               Home_Page_No_Of_Children_Add,
               Home_Page_No_Of_Rooms_minus,
               Home_Page_No_Of_Rooms_Add,
               Home_Page_Search,
               Home_Page_Sort,
               Home_page_Sort_Price,
               Home_Page_Rating_Filter;
    ;

    String objRepoFile;

    public HomePage(WebDriver driver) throws IOException {
        super(driver);
        objRepoFile = PropertyReader.getPropertyValue("src/test/resources/Config/PropertyFiles.properties","HomePage");
        initElem();
    }

    public void initElem() throws IOException {

        Home_Page_Banner_Close= PropertyReader.getLocator(objRepoFile,"Home_Page_Banner_Close");
        Home_Page_Destination= PropertyReader.getLocator(objRepoFile,"Home_Page_Destination");
        Home_Page_CalendarDrpDwn= PropertyReader.getLocator(objRepoFile,"Home_Page_CalendarDrpDwn");
        Home_Page_Occupancy= PropertyReader.getLocator(objRepoFile,"Home_Page_Occupancy");
        Home_Page_No_Of_Adults_Minus= PropertyReader.getLocator(objRepoFile,"Home_Page_No_Of_Adults_Minus");
        Home_Page_No_Of_Adults_Add= PropertyReader.getLocator(objRepoFile,"Home_Page_No_Of_Adults_Add");
        Home_Page_No_Of_Children_Minus= PropertyReader.getLocator(objRepoFile,"Home_Page_No_Of_Children_Minus");
        Home_Page_No_Of_Children_Add= PropertyReader.getLocator(objRepoFile,"Home_Page_No_Of_Children_Add");
        Home_Page_No_Of_Rooms_minus= PropertyReader.getLocator(objRepoFile,"Home_Page_No_Of_Rooms_minus");
        Home_Page_No_Of_Rooms_Add= PropertyReader.getLocator(objRepoFile,"Home_Page_No_Of_Rooms_Add");
        Home_Page_Search = PropertyReader.getLocator(objRepoFile,"Home_Page_Search");
        Home_Page_Sort = PropertyReader.getLocator(objRepoFile,"Home_Page_Sort");
        Home_page_Sort_Price = PropertyReader.getLocator(objRepoFile,"Home_page_Sort_Price");
        Home_Page_Rating_Filter = PropertyReader.getLocator(objRepoFile,"Home_Page_Rating_Filter");

    }

    public void closeBanner(){
        if(verifyElementPresent(Home_Page_Banner_Close))
            webDriverClick(Home_Page_Banner_Close);
    }

    public HomePage enterDestinationDetails(String dest){
        closeBanner();
        enterKeys(Home_Page_Destination, dest);
        maxWait();
        enterKeys(Home_Page_Destination, Keys.ENTER);
        return this;

    }

    public HomePage addCheckInCheckOut(String dt1, String dt2){

        //webDriverClick(Home_Page_CalendarDrpDwn);
        minWait();
        selectDate(dt1);
        selectDate(dt2);
        return this;

    }

    public HomePage addGuests(String adultsCount, boolean hasChildren, String childrenCount, String roomCount){

        webDriverClick(Home_Page_Occupancy);
        minWait();
        addGuests(adultsCount, By.xpath("//label[contains(text(),'Adults')]/../following-sibling::div/span"),Home_Page_No_Of_Adults_Minus,Home_Page_No_Of_Adults_Add);
        minWait();
        if(hasChildren){
            addGuests(childrenCount, By.xpath("//label[contains(text(),'Children')]/../following-sibling::div/span"),Home_Page_No_Of_Children_Minus,Home_Page_No_Of_Children_Add);
        }
        minWait();
        addGuests(roomCount, By.xpath("//label[contains(text(),'Rooms')]/../following-sibling::div/span"), Home_Page_No_Of_Rooms_minus, Home_Page_No_Of_Rooms_Add);

        webDriverClick(By.xpath("//span[contains(text(),'Done')]"));
        return this;
    }

    public void addGuests(String Count, By byDefault, By minus, By add){
        if(Count.equals("1")){

            while (true){
                try {
                    String no = getText(byDefault);
                    if (no.equals("1"))
                        break;
                    else
                        webDriverClick(minus);
                    minWait();
                }

                catch (Exception e){
                    log.info(e);
                }
            }
        }

        else {
            for(int i =1; i<Integer.parseInt(Count); i++){
                if(getText(byDefault).equals(Count)){
                    break;
                }
                webDriverClick(add);
                minWait();
            }
        }
    }

    public void selectDate(String dt){

        dt = dt.substring(1);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, Integer.parseInt(dt));
        SimpleDateFormat formattedDt = new SimpleDateFormat("yyyy-MM-dd");
        By calDate = By.xpath("//span[@data-date='"+formattedDt.format(cal.getTime())+"']");
        webDriverClick(calDate);
    }

    public HomePage search(){
        webDriverClick(Home_Page_Search);
        return this;
    }


    public HomePage verifySearchResult(String dest){
        Assert.assertTrue(waitForElement(By.xpath("//h1[contains(text(),'"+dest+"')]")).isDisplayed());
        Assert.assertTrue(waitForElement(By.xpath("//span[@data-testid='property-card-deal']/span")).isDisplayed());
        return this;
    }

//    public HomePage filterSearchResults(){
//        holdAndRelease(By.xpath("(//div[@data-testid='filters-group-slider']/div[2]/div/input)[1]"),50);
//        return this;
//    }


    public HomePage sortByPrice(){
        minWait();
        webDriverClick(Home_Page_Sort);
        webDriverClick(Home_page_Sort_Price);
        minWait();
        return this;
    }

    public HomePage verifySortedList(){

        List<WebElement> piceElems = elementList(By.xpath("//span[@data-testid='price-and-discounted-price']"));
        List<Integer> priceList = new ArrayList<>();
        for (WebElement elem:piceElems){
            priceList.add(Integer.parseInt(elem.getText().replace("₹","").replace(",","").trim()));
        }
        System.out.println(priceList);
        List<Integer> sortedPriceList = priceList.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedPriceList);
        if (!priceList.equals(sortedPriceList))
            Assert.fail();

        return this;
    }

    public HomePage filterRating(String starRating){

        By rating = By.xpath("(//div[contains(text(),'"+starRating +" stars')]/../../../../../../span/span)[1]");
        scrollToView(rating);
        minWait();
        clickJS(rating);
        minWait();
        return this;

    }

    public HomePage verifySearchResults(String starRating){

        List<WebElement> ratingElems = elementList(Home_Page_Rating_Filter);

        for (WebElement elem:ratingElems){
            String strRating = elem.getAttribute("aria-label");
            if(!strRating.contains(starRating))
                Assert.fail();
        }

        return this;
    }

    public HomePage filterDistance(String distance){

        By dist = By.xpath("(//div[contains(text(),'Less than "+ distance +" km')]/../../../../span[2])[1]");
        scrollToView(dist);
        minWait();
        clickJS(dist);
        minWait();
        return this;
    }

    public HomePage verifyFilterDistance(String distance){
        By dist = By.xpath("//span[contains(text(),'Less than "+ distance +" km')]");
        Assert.assertTrue(verifyElementPresent(dist));
        return this;
    }




}
