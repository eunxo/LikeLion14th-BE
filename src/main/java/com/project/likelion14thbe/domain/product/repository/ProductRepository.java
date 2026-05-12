package com.project.likelion14thbe.domain.product.repository;

import com.project.likelion14thbe.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT m FROM Product m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Product> findByAndNotDeleted(@Param("id") Long id);
}
