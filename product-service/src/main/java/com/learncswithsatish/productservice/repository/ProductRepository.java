package com.learncswithsatish.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learncswithsatish.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
