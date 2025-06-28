package steps;

import java.util.stream.IntStream;

import org.testng.Assert;

import com.dummyjson.testng.program.model.ResponseModel.AddNewCartResponse;

import context.TestContext;
import io.cucumber.java.en.And;
import io.restassured.response.Response;

public class AddNewCartSteps {
    private final TestContext context;
    public static Response response;
    public static String token;

    public AddNewCartSteps(TestContext context) {
        this.context = context;
    }

    @And("The user id in the response must be {int}")
    public void assert_user_id(Integer userId){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getUserId(), userId, "Expected user id " + userId + " but got " + addNewCartResponse.getUserId());
    }

    @And("Cart id in the response must be created")
    public void verify_cart_id_not_null(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertNotNull(addNewCartResponse.getId(), "Cart ID should not be null");
    }

    @And("^First product id in the response must be \\{id1\\}$")
    public void assert_product_id_1(){
        Integer id1 = GetAllProductsSteps.ids.get(0);
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getProducts().get(0).getId(), id1, "Expected product id " + id1 + " but got " + addNewCartResponse.getProducts().get(0).getId());
    }

    @And("Title of the first product in the response must be the same as the title of the first product in the list")
    public void assert_product_title_1(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getProducts().get(0).getTitle(), GetAllProductsSteps.titles.get(0), "Expected product title " + GetAllProductsSteps.titles.get(0) + " but got " + addNewCartResponse.getProducts().get(0).getTitle());
    }

    @And("Price of the first product in the response must be the same as the price of the first product in the list")
    public void assert_product_price_1(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getProducts().get(0).getPrice(), GetAllProductsSteps.prices.get(0), "Expected product price " + GetAllProductsSteps.prices.get(0) + " but got " + addNewCartResponse.getProducts().get(0).getPrice());
    }

    @And("^Quantity of the first product in the response must be \\{qty1\\}$")
    public void assert_quantity_1(){
        Integer qty1 = GetAllProductsSteps.minQuantities.get(0) + 1;
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getProducts().get(0).getQuantity(), qty1, "Expected quantity " + qty1 + " but got " + addNewCartResponse.getProducts().get(0).getQuantity());
    }

    @And("Sub total of the first product must be equal to price of the first product that calculated with quantity of the first product")
    public void assert_sub_total_1(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Double actualSubTotal1 = (addNewCartResponse.getProducts().get(0).getPrice()) * (addNewCartResponse.getProducts().get(0).getQuantity());
        Double expectedSubTotal1 = (GetAllProductsSteps.prices.get(0)) * (GetAllProductsSteps.minQuantities.get(0) + 1);

        Assert.assertEquals(actualSubTotal1, expectedSubTotal1, "Expected sub total of first product " + expectedSubTotal1 + " but got " + actualSubTotal1);
    }

    @And("Discount percentage of the first product in the response must be the equal as the discount percentage of the first product in the list, if present")
    public void assert_discount_percentage_1() {
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Double actualDiscount = addNewCartResponse.getProducts().get(0).getDiscountPercentage();
        Double expectedDiscount = GetAllProductsSteps.discountedPercentage.get(0);

        if (actualDiscount == null || expectedDiscount == null) {
            System.out.println("Discount percentage is null in response or expected, skipping assertion.");
            return; 
        }

        Assert.assertEquals(actualDiscount, expectedDiscount, "Expected discount " + expectedDiscount + " but got " + actualDiscount);
    }

    @And("Discounted price of the first product in the response must be equal to the calculated discounted price")
    public void assert_discounted_price_1() {
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Double total = addNewCartResponse.getProducts().get(0).getTotal();
        Double discountPercentage = addNewCartResponse.getProducts().get(0).getDiscountPercentage();
        Integer actualDiscounted = addNewCartResponse.getProducts().get(0).getDiscountedPrice();

        if (discountPercentage == null) {
            System.out.println("Discount percentage is null in response, skipping discounted price assertion.");
            return; 
        }

        long expectedDiscounted = Math.round(total - (total * discountPercentage / 100));
        long actualDiscountedRounded = Math.round(actualDiscounted);

        Assert.assertEquals(actualDiscountedRounded, expectedDiscounted, "Expected discounted price " + expectedDiscounted + " but got " + actualDiscountedRounded);
    }


    @And("^Second product id in the response must be \\{id2\\}$")
    public void assert_product_id_2(){
        Integer id2 = GetAllProductsSteps.ids.get(1);
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getProducts().get(1).getId(), id2, "Expected product id " + id2 + " but got " + addNewCartResponse.getProducts().get(1).getId());
    }

    @And("Title of the second product in the response must be the same as the title of the second product in the list")
    public void assert_product_title_2(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getProducts().get(1).getTitle(), GetAllProductsSteps.titles.get(1), "Expected product title " + GetAllProductsSteps.titles.get(1) + " but got " + addNewCartResponse.getProducts().get(1).getTitle());
    }

    @And("Price of the second product in the response must be the same as the price of the second product in the list")
    public void assert_product_price_2(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getProducts().get(1).getPrice(), GetAllProductsSteps.prices.get(1), "Expected product price " + GetAllProductsSteps.prices.get(1) + " but got " + addNewCartResponse.getProducts().get(1).getPrice());
    }

    @And("^Quantity of the second product in the response must be \\{qty2\\}$")
    public void assert_quantity_2(){
        Integer qty2 = GetAllProductsSteps.minQuantities.get(1) + 1;
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getProducts().get(1).getQuantity(), qty2, "Expected quantity " + qty2 + " but got " + addNewCartResponse.getProducts().get(1).getQuantity());
    }

    @And("Sub total of the second product must be equal to price of the second product that calculated with quantity of the second product")
    public void assert_sub_total_2(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Double actualSubTotal2 = (addNewCartResponse.getProducts().get(1).getPrice()) * (addNewCartResponse.getProducts().get(1).getQuantity());
        Double expectedSubTotal2 = (GetAllProductsSteps.prices.get(1)) * (GetAllProductsSteps.minQuantities.get(1) + 1);

        Assert.assertEquals(actualSubTotal2, expectedSubTotal2, "Expected sub total of first product " + expectedSubTotal2 + " but got " + actualSubTotal2);
    }

    @And("Discount percentage of the second product in the response must be the equal as the discount percentage of the second product in the list, if present")
    public void assert_discount_percentage_2() {
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Double actualDiscount = addNewCartResponse.getProducts().get(1).getDiscountPercentage();
        Double expectedDiscount = GetAllProductsSteps.discountedPercentage.get(1);

        if (actualDiscount == null || expectedDiscount == null) {
            System.out.println("Discount percentage is null in response or expected, skipping assertion.");
            return; 
        }

        Assert.assertEquals(actualDiscount, expectedDiscount, "Expected discount " + expectedDiscount + " but got " + actualDiscount);
    }

    @And("Discounted price of the second product in the response must be equal to the calculated discounted price")
    public void assert_discounted_price_2() {
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Double total = addNewCartResponse.getProducts().get(1).getTotal();
        Double discountPercentage = addNewCartResponse.getProducts().get(1).getDiscountPercentage();
        Integer actualDiscounted = addNewCartResponse.getProducts().get(1).getDiscountedPrice();

        if (discountPercentage == null) {
            System.out.println("Discount percentage is null in response, skipping discounted price assertion.");
            return; 
        }

        long expectedDiscounted = Math.round(total - (total * discountPercentage / 100));
        long actualDiscountedRounded = Math.round(actualDiscounted);

        Assert.assertEquals(actualDiscountedRounded, expectedDiscounted, "Expected discounted price " + expectedDiscounted + " but got " + actualDiscountedRounded);
    }

    @And("Total product in the response must be {int}")
    public void assert_total_product(Integer totalProduct){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Assert.assertEquals(addNewCartResponse.getTotalProducts(), totalProduct, "Expected total product " + totalProduct + " but got " + addNewCartResponse.getTotalProducts());
    }

    @And("Total discounted price in the response must be equal to the sum of the two discounted prices")
    public void assert_total_discounted_price(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Integer discountedPrice1 = addNewCartResponse.getProducts().get(0).getDiscountedPrice();
        Integer discountedPrice2 = addNewCartResponse.getProducts().get(1).getDiscountedPrice();

        if (discountedPrice1 == null || discountedPrice2 == null) {
            System.out.println("Skipped assert_total_discounted_price() because one of the discountedPrice is null");
            return;
        }
    
        Integer expectedTotalDiscounted = discountedPrice1 + discountedPrice2;
        Integer actualTotalDiscounted = addNewCartResponse.getDiscountedTotal();
    
        Assert.assertEquals(actualTotalDiscounted, expectedTotalDiscounted, "Expected total discounted price " + expectedTotalDiscounted + " but got " + actualTotalDiscounted);
    }

    @And("Total quantity in the response must be equal to the sum of the quantities")
    public void assert_total_quantity(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Integer totalQuantity = (GetAllProductsSteps.minQuantities.get(0) + 1) + (GetAllProductsSteps.minQuantities.get(1) + 1);

        Assert.assertEquals(addNewCartResponse.getTotalQuantity(), totalQuantity, "Expected total quantity " + totalQuantity + " but got " + addNewCartResponse.getTotalQuantity());
    }

    @And("The Total in the response must be equal to the sum of the two product prices that calculated with quantity")
    public void assert_total(){
        AddNewCartResponse addNewCartResponse = context.getResponse().as(AddNewCartResponse.class);

        Double total = IntStream.range(0, 2)
            .mapToDouble(i -> GetAllProductsSteps.prices.get(i) * (GetAllProductsSteps.minQuantities.get(i) + 1))
            .sum();

        Assert.assertEquals(addNewCartResponse.getTotal(), total, "Expected total " + total + " but got " + addNewCartResponse.getTotal());
    }
}