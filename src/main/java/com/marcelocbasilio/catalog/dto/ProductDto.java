package com.marcelocbasilio.catalog.dto;

import com.marcelocbasilio.catalog.entities.Category;
import com.marcelocbasilio.catalog.entities.Product;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link com.marcelocbasilio.catalog.entities.Product}
 */
public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;
    private final List<CategoryDto> categories = new ArrayList<>();

    public ProductDto() {
    }

    public ProductDto(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
        this.date = product.getDate();
    }

    public ProductDto(Product product, Set<Category> categories) {
        this(product);
        categories.forEach(category -> this.categories.add(new CategoryDto(category)));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto entity = (ProductDto) o;
        return Objects.equals(this.id, entity.id) &&
               Objects.equals(this.name, entity.name) &&
               Objects.equals(this.description, entity.description) &&
               Objects.equals(this.price, entity.price) &&
               Objects.equals(this.imgUrl, entity.imgUrl) &&
               Objects.equals(this.date, entity.date) &&
               Objects.equals(this.categories, entity.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, imgUrl, date, categories);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
               "id = " + id + ", " +
               "name = " + name + ", " +
               "description = " + description + ", " +
               "price = " + price + ", " +
               "imgUrl = " + imgUrl + ", " +
               "date = " + date + ", " +
               "categories = " + categories + ")";
    }
}