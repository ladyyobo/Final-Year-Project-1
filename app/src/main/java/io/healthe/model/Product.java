package io.healthe.model;

/**
 * Product data model
 */
public class Product {
    private String id;
    private String name;
    private String cat_id;
    private String manufacturer;
    private String image;

    public Product(String id, String name, String cat_id, String manufacturer, String image) {
        this.id = id;
        this.name = name;
        this.cat_id = cat_id;
        this.manufacturer = manufacturer;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
