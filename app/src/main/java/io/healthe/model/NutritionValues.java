package io.healthe.model;

/**
 * Models a nutritionValue data source
 */

public class NutritionValues {
    private String nutrients;
    private String percentage;

    public NutritionValues() {
    }

    public NutritionValues(String nutrients, String percentage) {
        this.nutrients = nutrients;
        this.percentage = percentage;
    }

    public String getNutrients() {
        return nutrients;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
