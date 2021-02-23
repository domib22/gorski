package com.example.gorski.domain.users;

import com.example.gorski.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ROLES")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleName name;

    public RoleName getName() {
        return name;
    }

}
