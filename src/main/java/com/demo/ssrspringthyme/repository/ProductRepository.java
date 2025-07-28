package com.demo.ssrspringthyme.repository;

import com.demo.ssrspringthyme.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
