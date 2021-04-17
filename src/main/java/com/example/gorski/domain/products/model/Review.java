package com.example.gorski.domain.products.model;

import com.example.gorski.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "REVIEWS")
public class Review extends AbstractEntity {

    private Integer stars_amount;
    private String opinion;

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "products_id")
    private Product product;

}
