package com.example.gorski.domain.users.model;

import com.example.gorski.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ROLES")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('ROLE_USER','ROLE_ADMIN')")
    @NaturalId
    private RoleName name;

    public RoleName getName() {
        return name;
    }

}
