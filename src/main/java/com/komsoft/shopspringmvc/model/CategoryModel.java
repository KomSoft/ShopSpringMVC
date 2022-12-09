package com.komsoft.shopspringmvc.model;

public class CategoryModel {
    private long id;
    private String name;

    public CategoryModel() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryModel setId(long id) {
        this.id = id;
        return this;
    }

    public CategoryModel setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
