package com.example.evaluacionmomento2.data.model;

public class Product {
    private String id;
    private String nombre;
    private String precio;

    public Product() {
        // Constructor vacío requerido por Firestore
    }

    public Product(String id, String nombre, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
