package com.dummyjson.testng.program.model.ResponseModel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetAllProductsResponse {
    @JsonProperty("products")
    private List<Product> products;

    @Data
    public static class Product {
        @JsonProperty("id")
        private int id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        @JsonProperty("category")
        private String category;

        @JsonProperty("price")
        private double price;

        @JsonProperty("discountPercentage")
        private double discountPercentage;

        @JsonProperty("rating")
        private double rating;

        @JsonProperty("stock")
        private int stock;

        @JsonProperty("tags")
        private List<String> tags;

        @JsonProperty("brand")
        private String brand;

        @JsonProperty("sku")
        private String sku;

        @JsonProperty("weight")
        private double weight;

        @JsonProperty("dimensions")
        private Dimensions dimensions;

        @Data
        public static class Dimensions{
            @JsonProperty("width")
            private double width;

            @JsonProperty("height")
            private double height;

            @JsonProperty("depth")
            private double depth;
        }

        @JsonProperty("warrantyInformation")
        private String warrantyInformation;

        @JsonProperty("shippingInformation")
        private String shippingInformation;

        @JsonProperty("availabilityStatus")
        private String availabilityStatus;

        @JsonProperty("reviews")
        private List<Review> reviews;

        @Data
        public static class Review {
            @JsonProperty("rating")
            private int rating;

            @JsonProperty("comment")
            private String comment;

            @JsonProperty("date")
            private String date;

            @JsonProperty("reviewerName")
            private String reviewerName;

            @JsonProperty("reviewerEmail")
            private String reviewerEmail;
        }

        @JsonProperty("returnPolicy")
        private String returnPolicy;

        @JsonProperty("minimumOrderQuantity")
        private int minimumOrderQuantity;

        @JsonProperty("meta")
        private Meta meta;

        @Data
        public static class Meta {
            @JsonProperty("createdAt")
            private String createdAt;

            @JsonProperty("updatedAt")
            private String updatedAt;

            @JsonProperty("barcode")
            private String barcode;

            @JsonProperty("qrCode")
            private String qrCode;
        }


        @JsonProperty("thumbnail")
        private String thumbnail;

        @JsonProperty("images")
        private List<String> images;
    }

    @JsonProperty("total")
    private int total;

    @JsonProperty("skip")
    private int skip;

    @JsonProperty("limit")
    private int limit;
}