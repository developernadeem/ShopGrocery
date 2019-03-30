package com.example.umair.shopgrocery.Model;

public class SubCategory {
    private String subCategoryName ;
    private String subCategoryImageUrl ;

    public SubCategory() {
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryImageUrl() {
        return subCategoryImageUrl;
    }

    public void setSubCategoryImageUrl(String subCategoryImageUrl) {
        this.subCategoryImageUrl = subCategoryImageUrl;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "subCategoryName='" + subCategoryName + '\'' +
                ", subCategoryImageUrl='" + subCategoryImageUrl + '\'' +
                '}';
    }
}
