package com.example.newsservice2.category.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "category")
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    public void update(CategoryEntity newEntity) {
        if (newEntity == null) {
            return;
        }
        if (StringUtils.hasText(newEntity.getName())) {
            this.name = newEntity.getName();
        }
    }
}
