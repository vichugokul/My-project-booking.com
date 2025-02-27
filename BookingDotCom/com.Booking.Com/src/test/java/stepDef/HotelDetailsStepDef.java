package stepDef;

import commons.BaseTest;
import generic.HooksSetUp;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.HotelDetailsPage;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.HashMap;

public class HotelDetailsStepDef extends BaseTest {

    WebDriver driver;
    HomePage hp;
    HotelDetailsPage hdp;
    HooksSetUp hook;
    ExcelUtils utils = new ExcelUtils();
    HashMap<String, String> data = new HashMap<>();
    private static final Logger log = LogManager.getLogger(HotelDetailsStepDef.class);

    public HotelDetailsStepDef(HooksSetUp hook) throws IOException {
        this.hook = hook;
        this.driver = hook.getDriver();
        this.hp = new HomePage(driver);
        this.hdp = new HotelDetailsPage(driver);
        data = utils.getData("TestData","TestData",2,data);
    }

    @Given("User searchs for hotel, check-in, check-out")
    public void user_searches_hotel() throws Exception{
        hp.enterDestinationDetails(data.get("dest"))
                .addCheckInCheckOut(data.get("checkIn"),data.get("checkOut"))
                .addGuests(data.get("adultsCount"),false,data.get("childrenCount"),data.get("roomCount"))
                .search();
    }


    @Then("Verify the hotel name")
    public void verify_hotel_name() throws IOException {
        hdp.selectHotel(data.get("dest"))
           .verifyHotel(data.get("dest"));
    }

    @And("Verify the hotel desc in search results")
    public void verify_hotel_desc(){
        hdp.verifyDesc();
    }

    @And("Verify the hotel amenities and room Type")
    public void verify_hotel_amenities_room_type(){
        hdp.verifyRoomType()
           .verifyAminities();
    }

    @When("User select no. of rooms for booking {string} and click on reserve")
    public void select_no_rooms_click_reserve(String no){
        hdp.selectRooms(no).reserve();
    }

    @When("User verifies the booking navigation")
    public void click_on_reserve(){
        hdp.verifyNavigation();
    }






}
