package steps;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;

import com.dummyjson.testng.program.model.ResponseModel.GetAllProductsResponse;
import com.dummyjson.testng.program.model.ResponseModel.GetAllProductsResponse.Product;

import context.TestContext;
import io.cucumber.java.en.And;
import io.restassured.response.Response;

public class GetAllProductsSteps {
    private final TestContext context;
    public static Response response;
    public static String token;
    public static List<Integer> ids = new ArrayList<>();
    public static List<String> titles = new ArrayList<>();
    public static List<Double> prices = new ArrayList<>();
    public static List<Integer> minQuantities = new ArrayList<>();
    public static List<Double> discountedPercentage = new ArrayList<>();

    public GetAllProductsSteps(TestContext context) {
        this.context = context;
    }

    @And("The total number of products must be more than 0")
    public void verify_total_number_of_products_more_than_zero(){
        GetAllProductsResponse getAllProductsResponse = context.getResponse().as(GetAllProductsResponse.class);

        Assert.assertNotNull(getAllProductsResponse.getTotal(), "Product must not be null.");
        Assert.assertTrue(getAllProductsResponse.getTotal() > 0, "Product is empty");
    }

    @And("Each product in the response must contain id, title, price, and minimumOrderQuantity")
    public void verify_product_contents(){
        GetAllProductsResponse getAllProductsResponse = context.getResponse().as(GetAllProductsResponse.class);

        List<GetAllProductsResponse.Product> products = getAllProductsResponse.getProducts();

        Assert.assertFalse(products.isEmpty(), "Product list should not be empty");

        for (int i = 0; i < products.size(); i++) {
            GetAllProductsResponse.Product product = products.get(i);

            Assert.assertNotNull(product.getId(), "Product at index " + i + " has null 'id'");
            Assert.assertNotNull(product.getTitle(), "Product at index " + i + " has null 'title'");
            Assert.assertNotNull(product.getPrice(), "Product at index " + i + " has null 'price'");
            Assert.assertNotNull(product.getMinimumOrderQuantity(), "Product at index " + i + " has null 'minimumOrderQuantity'");
        }
    }
    
    @And("Get first product & second product from the list of products")
    public void get_first_and_second_product_from_the_list(){
        GetAllProductsResponse getAllProductsResponse = context.getResponse().as(GetAllProductsResponse.class);

        List<Product> products = getAllProductsResponse.getProducts();

        ids = products.stream()
            .limit(2)
            .map(Product::getId)
            .collect(Collectors.toList());

        titles = products.stream()
            .limit(2)
            .map(Product::getTitle)
            .collect(Collectors.toList());

        prices = products.stream()
            .limit(2)
            .map(Product::getPrice)
            .collect(Collectors.toList());

        minQuantities = products.stream()
            .limit(2)
            .map(Product::getMinimumOrderQuantity)
            .collect(Collectors.toList());

        discountedPercentage = products.stream()
            .limit(2)
            .map(Product::getDiscountPercentage)
            .collect(Collectors.toList());
    }
}
