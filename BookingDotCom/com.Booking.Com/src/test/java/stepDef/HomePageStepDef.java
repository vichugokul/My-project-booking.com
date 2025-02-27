package stepDef;


import commons.BaseTest;
import generic.HooksSetUp;
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

public class HomePageStepDef extends BaseTest {

    WebDriver driver;
    HomePage hp;
    HotelDetailsPage hdp;
    HooksSetUp hook;
    ExcelUtils utils = new ExcelUtils();
    HashMap<String, String> data = new HashMap<>();
    private static final Logger log = LogManager.getLogger(HomePageStepDef.class);

    public HomePageStepDef(HooksSetUp hook) throws IOException {
        this.hook = hook;
        this.driver = hook.getDriver();
        this.hp = new HomePage(driver);
        data = utils.getData("TestData","TestData",1,data);
    }

    @Given("User searchs for dest, check-in, check-out")
    public void user_searches_dest() throws Exception{
        hp.enterDestinationDetails(data.get("dest"))
          .addCheckInCheckOut(data.get("checkIn"),data.get("checkOut"))
          .addGuests(data.get("adultsCount"),false,data.get("childrenCount"),data.get("roomCount"))
          .search();
    }

    @Then("Verify search Results")
    public void verify_Search_Results() {
        hp.verifySearchResult(data.get("dest"));
    }

    @When("User sorts the result based on price")
    public void user_sorts_the_search_result_based_on_price() {
        hp.sortByPrice();
    }

    @Then("User verify the sorted list")
    public void verify_sorted_list() {
        hp.verifySortedList();
    }

    @When("User sorts the result based on rate {string}")
    public void user_sorts_the_search_result_based_on_rate(String rate) {
        hp.filterRating(rate);
    }

    @Then("User verify the filtered result based on rating {string}")
    public void verify_rating_filtered_search(String rate) {
        hp.verifySearchResults(rate);
    }

    @When("User sorts the result based on distance from downtown {string}")
    public void filter_based_on_downtown_result(String dist){
        hp.filterDistance(dist);
    }

    @Then("User verify the filtered result based on downtown distance {string}")
    public void verify_filter_based_on_downtown_result(String dist){
        hp.verifyFilterDistance(dist);
    }



}
