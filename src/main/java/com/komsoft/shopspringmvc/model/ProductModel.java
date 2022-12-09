package com.komsoft.shopspringmvc.model;

public class ProductModel {
    private long id;
    private String name;
    private String description;
    private double price;
    private CategoryModel category;

    public ProductModel setId(long id) {
        this.id = id;
        return this;
    }

    public ProductModel setName(String name) {
        this.name = name;
        return this;
    }

    public ProductModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductModel setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductModel setCategory(CategoryModel category) {
        this.category = category;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

     public double getPrice() {
        return price;
    }

    public CategoryModel getCategory() {
        return category;
    }

}
