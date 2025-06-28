package steps;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;

import com.dummyjson.testng.program.model.ResponseModel.GetAllCartsResponse;
import com.dummyjson.testng.program.model.ResponseModel.GetAllCartsResponse.Cart;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import context.TestContext;
import io.cucumber.java.en.And;
import io.restassured.response.Response;

public class GetAllCartsSteps {
    private final TestContext context;
    public static Response response;
    public static String token;
    public static Integer cartId;
    public static List<Integer> productIds, productQty, totalProduct = new ArrayList<>();
    public static List<String> productTitles = new ArrayList<>();
    public static List<Double> productPrices, discountedPercentage, discountedTotal, subTotal = new ArrayList<>();

    public GetAllCartsSteps(TestContext context) {
        this.context = context;
    }

    @And("Total carts in the response should be more than or equal to 0")
    public void verify_total_carts_more_than_zero(){
        GetAllCartsResponse getAllCartsResponse = context.getResponse().as(GetAllCartsResponse.class);

        Assert.assertNotNull(getAllCartsResponse.getTotal(), "Cart should not be null. Make sure you have added products to the cart.");
        Assert.assertTrue(getAllCartsResponse.getTotal() > 0, "Empty cart. Make sure you have added products to the cart.");
    }

    @And("Each cart should contain userId, products, total, discountedTotal, and totalQuantity")
    public void verify_cart_contents(){
        GetAllCartsResponse getAllCartsResponse = context.getResponse().as(GetAllCartsResponse.class);

        getAllCartsResponse.getCarts().forEach(cart -> {
            Assert.assertNotNull(cart.getUserId(), "UserId should not be null");
            Assert.assertNotNull(cart.getProducts(), "Products list should not be null");
            Assert.assertFalse(cart.getProducts().isEmpty(), "Products list should not be empty");
    
            Assert.assertNotNull(cart.getDiscountedTotal(), "DiscountedTotal should not be null");
            Assert.assertTrue(cart.getDiscountedTotal() >= 0, "DiscountedTotal should be >= 0");
    
            Assert.assertNotNull(cart.getTotalQuantity(), "TotalQuantity should not be null");
            Assert.assertTrue(cart.getTotalQuantity() >= 0, "TotalQuantity should be >= 0");
    
            cart.getProducts().forEach(product -> {
                Assert.assertNotNull(product.getId(), "Product id should not be null");
                Assert.assertNotNull(product.getTitle(), "Product title should not be null");
                Assert.assertNotNull(product.getPrice(), "Product price should not be null");
                Assert.assertNotNull(product.getQuantity(), "Product quantity should not be null");
                Assert.assertNotNull(product.getTotal(), "Product total should not be null");
            });
        });
    }

    @And("Get first cart from the list of carts")
    public void get_first_cart(){
        GetAllCartsResponse getAllCartsResponse = context.getResponse().as(GetAllCartsResponse.class);
        List<Cart> carts = getAllCartsResponse.getCarts();

        cartId = carts.get(0).getId();

        List<Cart.Product> products = carts.stream()
            .flatMap(cart -> cart.getProducts().stream())
            .limit(2) 
            .collect(Collectors.toList());

        productIds = products.stream()
            .map(Cart.Product::getId)
            .collect(Collectors.toList());

        productTitles = products.stream()
            .limit(2)
            .map(Cart.Product::getTitle)
            .collect(Collectors.toList());

        productPrices = products.stream()
            .limit(2)
            .map(Cart.Product::getPrice)
            .collect(Collectors.toList());

        productQty = products.stream()
            .limit(2)
            .map(Cart.Product::getQuantity)
            .collect(Collectors.toList());

        discountedPercentage = products.stream()
            .limit(2)
            .map(Cart.Product::getDiscountPercentage)
            .collect(Collectors.toList());
            
        discountedTotal = products.stream()
            .limit(2)
            .map(Cart.Product::getDiscountedTotal)
            .collect(Collectors.toList());

        subTotal = products.stream()
            .limit(2)
            .map(Cart.Product::getTotal)
            .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            System.out.println(mapper.writeValueAsString(carts.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}