package com.example.umair.shopgrocery.Model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;
public class Category {
    private String categorySubTitle;
    private String categoryTitle;
    private List<SubCategory> subCategories;
    private String categoryImageUrl;

    public Category() {
    }


    public String getCategorySubTitle() {
        return categorySubTitle;
    }

    public void setCategorySubTitle(String categorySubTitle) {
        this.categorySubTitle = categorySubTitle;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categorySubTitle='" + categorySubTitle + '\'' +
                ", categoryTitle='" + categoryTitle + '\'' +
                ", subCategories=" + subCategories.toString() +
                ", categoryImageUrl='" + categoryImageUrl + '\'' +
                '}';
    }
}
