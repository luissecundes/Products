package com.product.springboot.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.springboot.models.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    List<ProductModel> findByNameContainingIgnoreCase(String name);

}
