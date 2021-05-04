package com.example.gorski.messages.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductAdd {
    private String name;
    private String price;
    private String productGender;
    private String season;
    private String category;
    private String pictureName;
    private String link;
    private String description;
}
