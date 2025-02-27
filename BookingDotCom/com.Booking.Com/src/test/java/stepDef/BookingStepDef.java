package stepDef;

import commons.BaseTest;
import generic.HooksSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.BookingPage;
import pages.HomePage;
import pages.HotelDetailsPage;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.HashMap;

public class BookingStepDef extends BaseTest {

    WebDriver driver;
    HomePage hp;
    HotelDetailsPage hdp;
    BookingPage bp;
    HooksSetUp hook;
    ExcelUtils utils = new ExcelUtils();
    HashMap<String, String> data = new HashMap<>();
    private static final Logger log = LogManager.getLogger(HotelDetailsStepDef.class);

    public BookingStepDef(HooksSetUp hook) throws IOException {
        this.hook = hook;
        this.driver = hook.getDriver();
        this.hp = new HomePage(driver);
        this.hdp = new HotelDetailsPage(driver);
        this.bp = new BookingPage(driver);
        data = utils.getData("TestData","TestData",3,data);
    }

    @Given("User searchs for hotel for booking by specifying check-in, check-out")
    public void user_searches_hotel() throws Exception{
        hp.enterDestinationDetails(data.get("dest"))
                .addCheckInCheckOut(data.get("checkIn"),data.get("checkOut"))
                .addGuests(data.get("adultsCount"),false,data.get("childrenCount"),data.get("roomCount"))
                .search();
    }

    @When("User selects hotel for booking")
    public void verify_hotel_name() throws IOException {
        hdp.selectHotel(data.get("dest"))
                .verifyHotel(data.get("dest"));
    }

    @When("User select no. of rooms {string} and click on reserve")
    public void select_no_rooms_click_reserve(String no){
        hdp.selectRooms(no).reserve();
    }

    @When("User enters booking information for booking")
    public void user_enter_bookinginfo(){
        bp.enterGuestDetails(data.get("firstName"),data.get("lastName"),data.get("emailAddr"),data.get("mobileNo"));
    }

    @When("User completes booking for booking")
    public void complete_booking(){
        bp.clickBook().completeBooking();
    }

    @Then("User verify the confirmation No for booking")
    public void click_on_reserve(){
        bp.verifyConfirmationNo(data);
    }


}
