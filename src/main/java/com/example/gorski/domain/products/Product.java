package com.example.gorski.domain.products;

import com.example.gorski.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product extends AbstractEntity {

    private String name;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ProductGender productGender;

    @Enumerated(EnumType.STRING)
    private Season season;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private String pictureName;
    private String link;

    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private List<Review> reviews = new ArrayList<>();

    public Product(String name, BigDecimal price, ProductGender productGender, Season season, ProductCategory category, String pictureName, String link) {
        this.name = name;
        this.price = price;
        this.productGender = productGender;
        this.season = season;
        this.category = category;
        this.pictureName = pictureName;
        this.link = link;
    }
}