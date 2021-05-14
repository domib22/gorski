package com.example.gorski.domain.products.model;

import com.example.gorski.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Integer starsAmount;
    private String opinion;
    @Column(length=40)
    private String userName;

    @JsonIgnore
    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "products_id")
    private Product product;

}
