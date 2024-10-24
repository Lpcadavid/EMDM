package com.example.evaluacionmomento2.data.dao;

import com.example.evaluacionmomento2.data.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private FirebaseFirestore db;

    public ProductDao(FirebaseFirestore db) {
        this.db = db;
    }

    public void createProduct(Product product, OnSuccessListener<DocumentReference> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("productos").add(product)
                .addOnSuccessListener(documentReference -> {
                    // Obtener el ID generado por Firestore
                    product.setId(documentReference.getId());
                    // Luego de obtener el ID, podrías actualizar el producto con su ID si lo deseas
                    db.collection("productos").document(product.getId()).set(product);
                    onSuccessListener.onSuccess(documentReference);
                })
                .addOnFailureListener(onFailureListener);
    }


    public void readProducts(OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("productos").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Obtener la lista de productos y asignar el ID de Firestore a cada uno
                    List<Product> products = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Product product = document.toObject(Product.class);
                        if (product != null) {
                            product.setId(document.getId());  // Asignar el ID del documento
                            products.add(product);
                        }
                    }
                    // Pasar la lista de productos al listener de éxito
                    onSuccessListener.onSuccess(queryDocumentSnapshots);
                })
                .addOnFailureListener(onFailureListener);
    }



    public void updateProduct(Product product, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("productos").document(product.getId()).set(product)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void deleteProduct(String productId, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection("productos").document(productId).delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}
