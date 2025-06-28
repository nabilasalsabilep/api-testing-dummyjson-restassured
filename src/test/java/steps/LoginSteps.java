package steps;

import org.testng.Assert;

import com.dummyjson.testng.program.model.ResponseModel.LoginResponse;

import apiengine.Endpoints;
import context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginSteps extends Endpoints{
    private final TestContext context;
    public static Response response;
    public static String token;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @When("Send a http {string} request to {string} with body:")
    public void send_request_http(String method, String url, String body) {
        response = sendRequest(method, url, body);
        context.setResponse(response);
    }

    @Then("The response status must be {int}")
    public void send_request_http(int statusCode) {
        Assert.assertEquals(response.statusCode(), statusCode, "Error, due to actual status code is " + response.statusCode());
    }

    @And("The response schema should be match with schema {string}")
    public void schema_validation(String schemaPath) {
        context.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    @And("Save the token from the response to local storage")
    public void save_the_token() {
        String token = context.getResponse().jsonPath().getString("accessToken");
        context.set("token", token);
        Assert.assertNotNull(token, "Token should not be null");

        System.out.println("Saved token: " + token);
    }

    @Given("Make sure token in local storage not empty")
    public void assert_token_in_variable() {
        String savedToken = context.get("token", String.class);
        Assert.assertNotNull(savedToken, "Token in context null");
    }

    @And("Username in the response must be {string}")
    public void assert_username(String username) throws Exception {
        LoginResponse loginResponse = context.getResponse().as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getUsername(), username, "Expected username " + username + " but got " + loginResponse.getUsername());
    }

    @And("Email in the response must be {string}")
    public void assert_email(String email) throws Exception {
        LoginResponse loginResponse = context.getResponse().as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getEmail(), email, "Expected email " + email + " but got " + loginResponse.getEmail());
    }

    @And("First name in the response must be {string}")
    public void assert_firstName(String firstName) throws Exception {
        LoginResponse loginResponse = context.getResponse().as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getFirstName(), firstName, "Expected first name " + firstName + " but got " + loginResponse.getFirstName());
    }

    @And("Last name in the response must be {string}")
    public void assert_lastName(String lastName) throws Exception {
        LoginResponse loginResponse = context.getResponse().as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getLastName(), lastName, "Expected last name " + lastName + " but got " + loginResponse.getLastName());
    }

    @And("Gender in the response must be {string}")
    public void assert_gender(String gender) throws Exception {
        LoginResponse loginResponse = context.getResponse().as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getGender(), gender, "Expected gender " + gender + " but got " + loginResponse.getGender());
    }

    @And("Image url in the response must be {string}")
    public void assert_imageUrl(String imageUrl) throws Exception {
        LoginResponse loginResponse = context.getResponse().as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getImage(), imageUrl, "Expected image url " + imageUrl + " but got " + loginResponse.getImage());
    }
    
}
