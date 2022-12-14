package com.komsoft.shopspringmvc.model;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
