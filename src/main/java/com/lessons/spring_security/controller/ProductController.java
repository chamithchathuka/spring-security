package com.lessons.spring_security.controller;

import com.lessons.spring_security.dto.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController {


    @GetMapping("/foradmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> getProducts(){
        Product product = new Product();
        product.setProductName("admin Product");
        return ResponseEntity.ok(product);
    }


    @GetMapping("/forUser")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Product> getUserProduct(){
        Product product = new Product();
        product.setProductName("user Product");
        return ResponseEntity.ok(product);
    }

    @GetMapping("/forUserAndAdmin")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Product> getUserAndAdminProduct(){
        Product product = new Product();
        product.setProductName("User and Admin Product");
        return ResponseEntity.ok(product);
    }

    @GetMapping("/forAllAuthenticated")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Product> getForAllAuthenticatedProduct(){
        Product product = new Product();
        product.setProductName("for All Authenticated Product");
        return ResponseEntity.ok(product);
    }

    @GetMapping("/welcome")

    public ResponseEntity<String> getWelcome() {
        return ResponseEntity.ok("Welcome");
    }
}
