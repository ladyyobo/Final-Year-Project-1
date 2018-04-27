package io.healthe.model;

import java.util.List;

/**
 * Created by Serwaa on 26-Apr-18.
 */

public class ImageResponse {
    private Product product;
    private List<NutritionValues> values;
    private List<Product> recommendations;

    public ImageResponse(Product product, List<NutritionValues> values, List<Product> recommendations) {
        this.product = product;
        this.values = values;
        this.recommendations = recommendations;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<NutritionValues> getValues() {
        return values;
    }

    public void setValues(List<NutritionValues> values) {
        this.values = values;
    }

    public List<Product> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Product> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "product=" + product +
                ", values=" + values +
                ", recommendations=" + recommendations +
                '}';
    }
}
