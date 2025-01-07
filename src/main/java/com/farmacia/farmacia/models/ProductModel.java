package com.farmacia.farmacia.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB-PRODUCTS")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_product;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private double value;
    private String description;
    private int quantity;

    public UUID getId_product() {
        return id_product;
    }

    public void setId_product(UUID id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
