package com.example.newsservice2.category.output.repository.specification;

import com.example.newsservice2.category.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
}
