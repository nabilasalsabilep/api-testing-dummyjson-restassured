package com.dummyjson.testng.program.model.ResponseModel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AddNewCartResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("products")
    private List<CartProduct> products;

    @Data
    public static class CartProduct{
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("price")
        private Double price;

        @JsonProperty("quantity")
        private Integer quantity;

        @JsonProperty("total")
        private Double total;

        @JsonProperty("discountPercentage")
        private Double discountPercentage;

        @JsonProperty("discountedPrice")
        private Integer discountedPrice;

        @JsonProperty("thumbnail")
        private String thumbnail;
    }

    @JsonProperty("total")
    private Double total;

    @JsonProperty("discountedTotal")
    private Integer discountedTotal;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("totalProducts")
    private Integer totalProducts;

    @JsonProperty("totalQuantity")
    private Integer totalQuantity;
}
