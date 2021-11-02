package com.nikolasnorth.productservice;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

  public List<Product> getProducts() {
    return List.of(
      new Product(1, "Product Name", "Product description...", 1000, 1, LocalDate.now(), LocalDate.now())
    );
  }
}
