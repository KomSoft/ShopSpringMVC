package com.komsoft.shopspringmvc.model;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NamedQuery(name = "Product.getAll", query = "SELECT p FROM ProductModel p")
@NamedQuery(name = "Product.getByCategory", query = "SELECT p FROM ProductModel p WHERE p.category.id = :categoryId")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
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
