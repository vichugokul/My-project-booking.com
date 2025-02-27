package pages;

import commons.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;

import java.io.IOException;
import java.util.HashMap;

public class BookingPage extends BasePage {

    WebDriver driver;

    String objRepoFile;

    private By Booking_firstName;
    private By Booking_lastName;
    private By Booking_email;
    private By Booking_mobile;
    private By Booking_Next;
    private By Booking_Confirm;
    private By Booking_ConfirmationNo;

    public BookingPage(WebDriver driver) throws IOException {
        super(driver);
        objRepoFile = PropertyReader.getPropertyValue("src/test/resources/Config/PropertyFiles.properties","Booking");
        initElem();
    }

    public void initElem() throws IOException {

        Booking_firstName = PropertyReader.getLocator(objRepoFile,"Booking_firstName");
        Booking_lastName = PropertyReader.getLocator(objRepoFile,"Booking_lastName");
        Booking_email = PropertyReader.getLocator(objRepoFile,"Booking_email");
        Booking_mobile = PropertyReader.getLocator(objRepoFile,"Booking_mobile");
        Booking_Next = PropertyReader.getLocator(objRepoFile,"Booking_Next");
        Booking_Confirm = PropertyReader.getLocator(objRepoFile,"Booking_Confirm");
        Booking_ConfirmationNo = PropertyReader.getLocator(objRepoFile,"Booking_ConfirmationNo");


    }

    public BookingPage enterGuestDetails(String firstName, String lastName, String email, String mobile){
        enterKeys(Booking_firstName,firstName+ Keys.TAB);
        enterKeys(Booking_lastName,lastName+ Keys.TAB);
        enterKeys(Booking_email,email+ Keys.TAB);
        enterKeys(Booking_mobile,mobile+ Keys.TAB);
        return this;
    }

    public BookingPage clickBook(){
        scrollToView(Booking_Next);
        doubleClick(Booking_Next);
        return this;
    }

    public BookingPage completeBooking(){
        minWait();
        doubleClick(Booking_Next);
        doubleClick(Booking_Next);
        minWait();
        return this;
    }

    public BookingPage verifyConfirmationNo(HashMap<String, String> data){
        waitForElement(Booking_ConfirmationNo);
        verifyElement(Booking_ConfirmationNo);
        data.put("confirmationNo",getText(Booking_ConfirmationNo));
        return this;
    }



}
