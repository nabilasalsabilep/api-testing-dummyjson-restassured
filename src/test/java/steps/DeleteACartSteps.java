package steps;

import org.testng.Assert;

import com.dummyjson.testng.program.model.ResponseModel.DeleteACartResponse;

import context.TestContext;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class DeleteACartSteps {
    private final TestContext context;
    public static Response response;
    public static String token;

    public DeleteACartSteps(TestContext context) {
        this.context = context;
    }
    @Then("The \"isDeleted\" field in the response must be true")
    public void assert_is_deleted_true() {
        DeleteACartResponse deleteACartResponse = context.getResponse().as(DeleteACartResponse.class);        
        Assert.assertTrue(deleteACartResponse.isDeleted(), "Expected isDeleted to be true but got false");
    }

    @Then("The \"deletedOn\" field in the response must not be null")
    public void assert_deleted_on_not_null() {
        DeleteACartResponse response = context.getResponse().as(DeleteACartResponse.class);
        Assert.assertNotNull(response.getDeletedOn(), "Expected deletedOn to be present but it was null");
        System.out.println("Cart with id " + GetAllCartsSteps.cartId + " has been successfully deleted");
    }

}
