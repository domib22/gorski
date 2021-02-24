package com.example.gorski.domain.products;

import com.example.gorski.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "LINKS")
public class Link extends AbstractEntity {

    private String content;

    @JsonIgnore
    @OneToOne(mappedBy = "link", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Product product;

}
