package steps;

import java.util.List;

import org.testng.Assert;

import com.dummyjson.testng.program.model.ResponseModel.UpdateACartResponse;

import context.TestContext;
import io.cucumber.java.en.And;
import io.restassured.response.Response;

public class UpdateACartSteps {
    private final TestContext context;
    public static Response response;
    public static String token;
    public static List <Integer> productIds, updatedQty;
    public static Integer productQty1, productQty2;

    public UpdateACartSteps(TestContext context) {
        this.context = context;
    }

    @And("^Cart id in the response must be \\{cartId\\}$")
    public void assert_cart_id(){
        Integer cartId = GetAllCartsSteps.cartId;
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        Assert.assertEquals(updateACartResponse.getId(),  cartId, "Expected cart id " + cartId + " but got " + updateACartResponse.getId());
    }

    @And("Product id in the response must be the same as first and second product id")
    public void assert_product_ids(){
        productIds = GetAllCartsSteps.productIds;
        Integer productId1 = productIds.get(0);
        Integer productId2 = productIds.get(1);
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        Assert.assertEquals(updateACartResponse.getProducts().get(0).getId(), productId1, "Expected first product id " + productIds.get(0) + " but got " + updateACartResponse.getProducts().get(0).getId());
        Assert.assertEquals(updateACartResponse.getProducts().get(1).getId(), productId2, "Expected second product id " + productIds.get(1) + " but got " + updateACartResponse.getProducts().get(1).getId());
    }

    @And("Product titles of the first product in the response must not be changed")
    public void assert_product_titles(){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);
        System.out.println(context.getResponse().asPrettyString());

        Assert.assertEquals(updateACartResponse.getProducts().get(0).getTitle(), GetAllCartsSteps.productTitles.get(0), "Expected title of the first product " + GetAllCartsSteps.productTitles.get(0) + " but got " + updateACartResponse.getProducts().get(0).getTitle());
        Assert.assertEquals(updateACartResponse.getProducts().get(1).getTitle(), GetAllCartsSteps.productTitles.get(1), "Expected title of the second product " + GetAllCartsSteps.productTitles.get(1) + " but got " + updateACartResponse.getProducts().get(1).getTitle());
    }

    @And("Product prices in the response must not be changed")
    public void asser_product_prices(){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        Assert.assertEquals(updateACartResponse.getProducts().get(0).getPrice(), GetAllCartsSteps.productPrices.get(0), "Expected price of the first product " + GetAllCartsSteps.productPrices.get(0) + " but got " + updateACartResponse.getProducts().get(0).getPrice());
        Assert.assertEquals(updateACartResponse.getProducts().get(1).getPrice(), GetAllCartsSteps.productPrices.get(1), "Expected price of the second product " + GetAllCartsSteps.productPrices.get(1) + " but got " + updateACartResponse.getProducts().get(1).getPrice());
    }

    @And("Product quantities in the response must be the same as first and second product quantities")
    public void assert_each_product_quantity(){
        updatedQty = GetAllCartsSteps.productQty;

        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        productQty1 = GetAllCartsSteps.productQty.get(0) + 5;
        productQty2 = GetAllCartsSteps.productQty.get(1) + 10;

        Assert.assertEquals(updateACartResponse.getProducts().get(0).getQuantity(), productQty1, "Expected quantity of the first product " + productQty1 + " but got " + updateACartResponse.getProducts().get(0).getQuantity());
        Assert.assertEquals(updateACartResponse.getProducts().get(1).getQuantity(), productQty2, "Expected quantity of the second product " + productQty2 + " but got " + updateACartResponse.getProducts().get(1).getQuantity());
    }

    @And("Total of each product must be equal to price multiplied by quantity")
    public void assert_total_of_each_product(){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        Double expectedSubTotal1 = GetAllCartsSteps.productPrices.get(0) * productQty1;
        Double expectedSubTotal2 = GetAllCartsSteps.productPrices.get(1) * productQty2;
        Assert.assertEquals(updateACartResponse.getProducts().get(0).getTotal(), expectedSubTotal1, "Expected total of the first product " + expectedSubTotal1 + " but got " + updateACartResponse.getProducts().get(0).getTotal());
        Assert.assertEquals(updateACartResponse.getProducts().get(1).getTotal(), expectedSubTotal2, "Expected total of the second product " + expectedSubTotal2 + " but got " + updateACartResponse.getProducts().get(1).getTotal());
    }

    @And("Discount percentage of each product must be equal to the calculation between the discounted price and the product sub-total, if present")
    public void assert_discount_percentage(){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        List<UpdateACartResponse.Product> products = updateACartResponse.getProducts();

        for (int i = 0; i < products.size(); i++) {
            UpdateACartResponse.Product product = products.get(i);
            Double total = product.getTotal();
            Integer discountedPrice = product.getDiscountedPrice();
            Double actualDiscount = product.getDiscountPercentage();

            if (total == null || discountedPrice == null || actualDiscount == null) {
                System.out.println("Skipping product index " + i + " due to null values.");
                continue;
            }

            double expectedDiscount = ((total - discountedPrice) / total) * 100;
            expectedDiscount = Math.round(expectedDiscount * 100.0) / 100.0; 

            Assert.assertEquals(actualDiscount, expectedDiscount, "Expected discount for product index " + i + " is " + expectedDiscount + " but got " + actualDiscount);
        }
    }

    @And("Discounted price of each product must be equal to calculated discounted total")
    public void assert_product_discounted_prices(){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        List<UpdateACartResponse.Product> products = updateACartResponse.getProducts();

        for (int i = 0; i < products.size(); i++) {
            UpdateACartResponse.Product product = products.get(i);

            Double total = product.getTotal();
            Double discountPercentage = product.getDiscountPercentage();
            Integer actualDiscounted = product.getDiscountedPrice();

            if (total == null || discountPercentage == null || actualDiscounted == null) {
                System.out.println("Skipping product index " + i + " due to null values.");
                continue;
            }

            long expectedDiscounted = Math.round(total - (total * discountPercentage / 100));
            long actualDiscountedRounded = Math.round(actualDiscounted);

            Assert.assertEquals(actualDiscountedRounded, expectedDiscounted, "Expected discounted price for product index " + i + " is " + expectedDiscounted + " but got " + actualDiscountedRounded);
        }
    }

    @And("Total number of products in the response must be {int}")
    public void assert_total_number_of_products(Integer expectedTotalProduct){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        Assert.assertEquals(updateACartResponse.getTotalProducts(), expectedTotalProduct, "Expected total number of products " + expectedTotalProduct + " but got " +  updateACartResponse.getTotalProducts());
    }

    @And("Total quantity in the response must be equal to the sum of all product quantities")
    public void assert_total_quantity_after_updated_cart(){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        Integer expectedTotalQuantity = updateACartResponse.getProducts().stream()
            .mapToInt(UpdateACartResponse.Product::getQuantity)
            .sum();

        Assert.assertEquals(updateACartResponse.getTotalQuantity(), expectedTotalQuantity, "Expected total quantity " + expectedTotalQuantity + " but got " + updateACartResponse.getTotalQuantity());
    }

    @And("The Total in the response must be equal to the sum of all product subtotals")
    public void assert_total(){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        Double expectedTotal = updateACartResponse.getProducts().stream()
            .mapToDouble(UpdateACartResponse.Product::getTotal)
            .sum();

        Assert.assertEquals(updateACartResponse.getTotal(), expectedTotal, "Expected total " + expectedTotal + " but got " + updateACartResponse.getTotal());
    }

    @And("Total discounted price in the response must be equal to the sum of all product discounted prices")
    public void assert_total_discounted_price(){
        UpdateACartResponse updateACartResponse = context.getResponse().as(UpdateACartResponse.class);

        Double expectedTotalDiscountedPrice = updateACartResponse.getProducts().stream()
            .mapToDouble(UpdateACartResponse.Product::getDiscountedPrice)
            .sum();

        long expectedRounded = Math.round(expectedTotalDiscountedPrice);
        long actualRounded = Math.round(updateACartResponse.getDiscountedTotal());

        Assert.assertEquals(actualRounded, expectedRounded, "Expected total discounted price " + expectedRounded + " but got " + actualRounded);
    }
}
