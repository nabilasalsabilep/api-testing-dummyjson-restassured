package apiengine;

import helper.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import steps.AddNewCartSteps;
import steps.DeleteACartSteps;
import steps.GetAllCartsSteps;
import steps.GetAllProductsSteps;
import steps.LoginSteps;
import steps.UpdateACartSteps;

public class Endpoints {

    public Endpoints() {
        // Set the base URI for the API
        String baseUrl = ConfigManager.getBaseUrl();
        RestAssured.baseURI = baseUrl;
    }

    public Response sendRequest(String method, String url, String body) {
        url = resolveDynamicUrl(url);
        body = prepareRequestBody(url, method, body);

        Response response = RestAssured
                .given()
                .contentType("application/json")
                .header("Authorization", getAuthorizationHeader())
                .body(body)
                .when()
                .request(method, RestAssured.baseURI + url);

        handleResponse(url, method, response);
        return response;
    }

    private String prepareRequestBody(String url, String method, String body) {
        String key = method;

        switch (key) {
            case "POST":
                if (url.equals("/carts/add")){
                    body = body
                        .replace("{id1}", String.valueOf(GetAllProductsSteps.ids.get(0)))
                        .replace("{qty1}", String.valueOf(GetAllProductsSteps.minQuantities.get(0) + 1))
                        .replace("{id2}", String.valueOf(GetAllProductsSteps.ids.get(1)))
                        .replace("{qty2}", String.valueOf(GetAllProductsSteps.minQuantities.get(1) + 1));
                    return body;
                }
    
            case "PUT":
                if (url.startsWith("/carts/")){
                    body = body
                        .replace("{productId1}", String.valueOf(GetAllCartsSteps.productIds.get(0)))
                        .replace("{productQty1}", String.valueOf(GetAllCartsSteps.productQty.get(0) + 5))
                        .replace("{productId2}", String.valueOf(GetAllCartsSteps.productIds.get(1)))
                        .replace("{productQty2}", String.valueOf(GetAllCartsSteps.productQty.get(1) + 10));
                    return body;
                }

            default:
                return body;
        }
    }
    

    private String resolveDynamicUrl(String url) {
        switch (url) {
            case "/carts/{cartId}":
                return "/carts/" + GetAllCartsSteps.cartId;
            default:
                return url;
        }
    }

    private String getAuthorizationHeader() {
        return LoginSteps.token != null ? "Bearer " + LoginSteps.token : "";
    }

    private void handleResponse(String url, String method, Response response) {
        String key = method;
    
        switch (key) {
            case "POST":
                if (url.equals("/auth/login")){
                    LoginSteps.response = response;
                } else if (url.equals("/carts/add")){
                    AddNewCartSteps.response = response;
                }
                break;
    
            case "GET":
                if(url.equals("/products")){
                    GetAllProductsSteps.response = response;
                } else if (url.equals("/carts")){
                    GetAllCartsSteps.response = response;
                }
                break;
    
            case "PUT":
                if (url.startsWith("/carts/"))
                    UpdateACartSteps.response = response;
                break;

            case "DELETE":
                if(url.startsWith("/carts/"))
                    DeleteACartSteps.response = response;
                break;
    
            default:
                System.out.println("No handler for " + key);
                break;
        }
    }
    
}