package com.lessons.spring_security.controller;

import com.lessons.spring_security.dto.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
public class ProductController {


    @GetMapping("/secure")
    public ResponseEntity<Product> getProducts(){
        Product product = new Product();
        product.setProductName("prodyc");
        return ResponseEntity.ok(product);
    }

    @GetMapping("/user")
    public ResponseEntity<Product> getUserProduct(){
        Product product = new Product();
        product.setProductName("user Product");
        return ResponseEntity.ok(product);
    }
        @GetMapping("/welcome")
        public ResponseEntity<String> getWelcome() {
            return ResponseEntity.ok("Welcome");
        }
}
