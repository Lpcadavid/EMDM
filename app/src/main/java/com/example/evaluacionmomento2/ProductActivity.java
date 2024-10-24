package com.example.evaluacionmomento2;

// Importaciones necesarias para la actividad
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Importaciones para la interfaz de usuario y la gestión de la actividad
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Importaciones para el adaptador, el DAO y el modelo de datos
import com.example.evaluacionmomento2.data.adapter.ProductAdapter;
import com.example.evaluacionmomento2.data.dao.ProductDao;
import com.example.evaluacionmomento2.data.model.Product;

// Importaciones para Firebase Firestore
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

// Importaciones para la gestión de listas
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private Button btnCrearProducto, btnLeerProductos, btnActualizarProducto, btnEliminarProducto;
    private RecyclerView recyclerViewPro;
    private ProductDao productDao;
    private ProductAdapter productAdapter;
    private EditText etNombre, etPrecio;
    private Product selectedProduct; // Producto seleccionado para actualizar o eliminar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        btnCrearProducto = findViewById(R.id.btnCrearProducto);
        btnLeerProductos = findViewById(R.id.btnLeerProductos);
        btnActualizarProducto = findViewById(R.id.btnActualizarProducto);
        btnEliminarProducto = findViewById(R.id.btnEliminarProducto);
        recyclerViewPro = findViewById(R.id.recyclerViewPro);
        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        productDao = new ProductDao(db);

        recyclerViewPro.setLayoutManager(new LinearLayoutManager(this));

        // Configurar el adaptador con un listener para seleccionar el producto
        productAdapter = new ProductAdapter(new ArrayList<>(), product -> {
            // Cuando se selecciona un producto, se carga en los EditText
            selectedProduct = product;
            etNombre.setText(product.getNombre());
            etPrecio.setText(product.getPrecio());
        });
        recyclerViewPro.setAdapter(productAdapter);

        // Crear Producto
        btnCrearProducto.setOnClickListener(view -> {
            Product product = new Product();
            product.setNombre(etNombre.getText().toString());
            product.setPrecio(etPrecio.getText().toString());

            // Crear el producto en Firestore
            productDao.createProduct(product, documentReference -> {
                // Asignar el ID generado por Firestore al producto
                product.setId(documentReference.getId());

                // Mostrar mensaje de éxito
                Toast.makeText(ProductActivity.this, "Producto creado", Toast.LENGTH_SHORT).show();

                // Limpiar los campos
                limpiarCampos();

                // Actualizar la lista de productos
                refreshProductList();
            }, e -> {
                Toast.makeText(ProductActivity.this, "Error al crear producto", Toast.LENGTH_SHORT).show();
            });
        });

        // Leer Productos
        btnLeerProductos.setOnClickListener(view -> refreshProductList());

        // Actualizar Producto
        btnActualizarProducto.setOnClickListener(view -> {
            if (selectedProduct != null && selectedProduct.getId() != null && !selectedProduct.getId().isEmpty()) {
                // Actualizar los valores del producto seleccionado
                selectedProduct.setNombre(etNombre.getText().toString());
                selectedProduct.setPrecio(etPrecio.getText().toString());

                productDao.updateProduct(selectedProduct, aVoid -> {
                    Toast.makeText(ProductActivity.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    refreshProductList();
                }, e -> {
                    Toast.makeText(ProductActivity.this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(ProductActivity.this, "Selecciona un producto válido para actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        // Eliminar Producto
        btnEliminarProducto.setOnClickListener(view -> {
            if (selectedProduct != null && selectedProduct.getId() != null && !selectedProduct.getId().isEmpty()) {
                productDao.deleteProduct(selectedProduct.getId(), aVoid -> {
                    Toast.makeText(ProductActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    refreshProductList();
                }, e -> {
                    Toast.makeText(ProductActivity.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(ProductActivity.this, "Selecciona un producto válido para eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Refrescar la lista de productos
    private void refreshProductList() {
        productDao.readProducts(queryDocumentSnapshots -> {
            List<Product> products = queryDocumentSnapshots.toObjects(Product.class);
            productAdapter.setProducts(products);
        }, e -> {
            Toast.makeText(ProductActivity.this, "Error al leer productos", Toast.LENGTH_SHORT).show();
        });
    }

    // Limpiar los campos después de una operación CRUD
    private void limpiarCampos() {
        etNombre.setText("");
        etPrecio.setText("");
        selectedProduct = null; // Limpiar el producto seleccionado
    }
}
