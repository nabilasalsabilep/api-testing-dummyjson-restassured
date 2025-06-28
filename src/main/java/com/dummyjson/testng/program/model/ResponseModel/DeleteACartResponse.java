package com.dummyjson.testng.program.model.ResponseModel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DeleteACartResponse {
    @JsonProperty("id")
    private int id;

    @JsonProperty("products")
    private List<Product> products;

    @Data
    public static class Product {
        @JsonProperty("id")
        private int id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("price")
        private double price;

        @JsonProperty("quantity")
        private int quantity;

        @JsonProperty("total")
        private double total;

        @JsonProperty("discountPercentage")
        private double discountPercentage;

        @JsonProperty("discountedTotal")
        private double discountedTotal;

        @JsonProperty("thumbnail")
        private String thumbnail;
    }

    @JsonProperty("total")
    private double total;

    @JsonProperty("discountedTotal")
    private double discountedTotal;

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("totalProducts")
    private int totalProducts;

    @JsonProperty("totalQuantity")
    private int totalQuantity;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    @JsonProperty("deletedOn")
    private String deletedOn;
}
