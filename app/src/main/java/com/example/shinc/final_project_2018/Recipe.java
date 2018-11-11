package com.example.shinc.final_project_2018;

public class Recipe {
    private String title;
    private String href;
    private String ingredients;
    private String thumnail;

    public Recipe(String title, String href, String ingredients, String thumnail) {
        this.title = title;
        this.href = href;
        this.ingredients = ingredients;
        this.thumnail = thumnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }
}
