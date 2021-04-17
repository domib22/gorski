package com.example.gorski.domain.products.model;

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

    @Column(columnDefinition = "enum('UNISEX','MALE','FEMALE')")
    private String productGender;

    @Column(columnDefinition = "enum('YEAR_ROUND','WINTER','SUMMER','SPRING_AUTUMN')")
    private String season;

    private String category;

    private String pictureName;
    private String link;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Review> reviews = new ArrayList<>();

    public Product(String name, BigDecimal price, String productGender, String season, String category, String pictureName, String link) {
        this.name = name;
        this.price = price;
        this.productGender = productGender;
        this.season = season;
        this.category = category;
        this.pictureName = pictureName;
        this.link = link;
    }
}