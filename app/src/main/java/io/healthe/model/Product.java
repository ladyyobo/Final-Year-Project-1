package io.healthe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Product data model
 */
public class Product implements Parcelable {
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

    protected Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        cat_id = in.readString();
        manufacturer = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(cat_id);
        dest.writeString(manufacturer);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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
