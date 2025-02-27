package pages;

import commons.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.PropertyReader;

import java.io.IOException;

public class HotelDetailsPage extends BasePage {

    WebDriver driver;

    String objRepoFile;

    private By Hotel_Details_Hotel_Desc,
               Hotel_Details_Hotel_Non_Smoking,
               Hotel_Details_Hotel_Room_Service,
               Hotel_Details_Hotel_Free_Wifi,
               Hotel_Details_Hotel_Family,
               Hotel_Details_24_hr_front_desk,
               Hotel_Details_Breakfast,
               Hotel_Details_RoomType_One,
               Hotel_Details_RoomType_Two,
               Hotel_Details_Select_No,
               Hotel_Details_Reserve;


    public HotelDetailsPage(WebDriver driver) throws IOException {
        super(driver);
        objRepoFile = PropertyReader.getPropertyValue("src/test/resources/Config/PropertyFiles.properties","HotelDetails");
        initElem();
    }

    public void initElem() throws IOException {

        Hotel_Details_Hotel_Desc = PropertyReader.getLocator(objRepoFile,"Hotel_Details_Hotel_Desc");
        Hotel_Details_Hotel_Non_Smoking = PropertyReader.getLocator(objRepoFile,"Hotel_Details_Hotel_Non_Smoking");
        Hotel_Details_Hotel_Room_Service = PropertyReader.getLocator(objRepoFile,"Hotel_Details_Hotel_Room_Service");
        Hotel_Details_Hotel_Free_Wifi = PropertyReader.getLocator(objRepoFile,"Hotel_Details_Hotel_Free_Wifi");
        Hotel_Details_Hotel_Family = PropertyReader.getLocator(objRepoFile,"Hotel_Details_Hotel_Family");
        Hotel_Details_24_hr_front_desk = PropertyReader.getLocator(objRepoFile,"Hotel_Details_24_hr_front_desk");
        Hotel_Details_Breakfast = PropertyReader.getLocator(objRepoFile,"Hotel_Details_Breakfast");
        Hotel_Details_RoomType_One = PropertyReader.getLocator(objRepoFile,"Hotel_Details_RoomType_One");
        Hotel_Details_RoomType_Two = PropertyReader.getLocator(objRepoFile,"Hotel_Details_RoomType_Two");
        Hotel_Details_Select_No = PropertyReader.getLocator(objRepoFile,"Hotel_Details_Select_No");
        Hotel_Details_Reserve = PropertyReader.getLocator(objRepoFile,"Hotel_Details_Reserve");

    }

    public HotelDetailsPage selectHotel(String hotelName){
        webDriverClick(By.xpath("//div[text()='"+hotelName+"']"));
        return this;
    }

    public HotelDetailsPage verifyHotel(String hotelName){
        switchToNthWindow(2);
        minWait();
        verifyElementPresent(By.xpath("//h2[text()='"+hotelName+"']"));
        return this;
    }

    public HotelDetailsPage verifyDesc(){
        verifyElementPresent(Hotel_Details_Hotel_Desc);
        return this;
    }

    public HotelDetailsPage verifyAminities(){
        verifyElementPresent(Hotel_Details_Hotel_Non_Smoking);
        verifyElementPresent(Hotel_Details_Hotel_Room_Service);
        verifyElementPresent(Hotel_Details_Hotel_Free_Wifi);
        verifyElementPresent(Hotel_Details_Hotel_Family);
        verifyElementPresent(Hotel_Details_24_hr_front_desk);
        verifyElementPresent(Hotel_Details_Breakfast);
        return this;
    }

    public HotelDetailsPage verifyRoomType(){
        scrollToView(Hotel_Details_RoomType_One);
        verifyElement(Hotel_Details_RoomType_One);
        verifyElement(Hotel_Details_RoomType_Two);
        return this;
    }

    public HotelDetailsPage selectRooms(String no){
        scrollToView(Hotel_Details_Select_No);
        selByValue(Hotel_Details_Select_No,no);
        minWait();
        return this;
    }

    public HotelDetailsPage reserve(){
        clickJS(Hotel_Details_Reserve);
        minWait();
        return this;
    }

    public HotelDetailsPage verifyNavigation(){
        String title = getTitle();
        Assert.assertEquals(title,"Booking.com: Your Details");
        return this;
    }









}
