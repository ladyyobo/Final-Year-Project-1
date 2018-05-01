package io.healthe.model;

/**
 * Models a nutritionValue data source
 */

public class NutritionalValue {
    private String nutrient;
    private String percentage;

    public NutritionalValue() {
    }

    public NutritionalValue(String nutrient, String percentage) {
        this.nutrient = nutrient;
        this.percentage = percentage;
    }

    public String getNutrient() {
        return nutrient;
    }

    public void setNutrient(String nutrient) {
        this.nutrient = nutrient;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
