package com.example.gorski.messages.request;

import com.example.gorski.domain.products.ProductCategory;
import com.example.gorski.domain.products.ProductGender;
import com.example.gorski.domain.products.Season;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductAdd {
    private String name;
    private String price;
    private ProductGender productGender;
    private Season season;
    private ProductCategory category;
    private String pictureName;
    private String link;
}
