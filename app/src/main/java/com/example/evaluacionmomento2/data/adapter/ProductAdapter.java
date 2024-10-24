package com.example.evaluacionmomento2.data.adapter;

// Importaciones necesarias para el funcionamiento del adaptador
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaluacionmomento2.R;
import com.example.evaluacionmomento2.data.model.Product;

import java.util.List;

// Adaptador para mostrar una lista de productos en un RecyclerView
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    // Lista de productos que se mostrará
    private List<Product> productList;
    // Interfaz para manejar los clics en los productos
    private OnProductClickListener listener;

    // Interfaz para definir el comportamiento al hacer clic en un producto
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    // Constructor del adaptador que recibe la lista de productos y el listener
    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    // Crea nuevas vistas (invocado por el layout manager)
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el diseño del item del producto
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        // Devuelve un nuevo objeto ViewHolder con la vista inflada
        return new ProductViewHolder(itemView);
    }

    // Reemplaza el contenido de una vista (invocado por el layout manager)
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Obtiene el producto en la posición dada
        Product product = productList.get(position);
        // Llama al método bind para vincular el producto al ViewHolder
        holder.bind(product, listener);
    }

    // Devuelve el tamaño de la lista de productos
    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Método para actualizar la lista de productos y notificar al adaptador
    public void setProducts(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    // Clase interna que representa el ViewHolder para cada producto
    class ProductViewHolder extends RecyclerView.ViewHolder {

        // Referencias a los TextViews para mostrar el nombre y el precio
        TextView textViewNombre, textViewPrecio;

        // Constructor del ViewHolder que recibe la vista del item
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa los TextViews utilizando findViewById
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewPrecio = itemView.findViewById(R.id.textViewPrecio);
        }

        // Método para vincular los datos del producto a la vista
        public void bind(final Product product, final OnProductClickListener listener) {
            // Establece el nombre y precio del producto en los TextViews
            textViewNombre.setText(product.getNombre());
            textViewPrecio.setText(product.getPrecio());

            // Configura un listener para el clic en el item
            itemView.setOnClickListener(v -> {
                listener.onProductClick(product); // Llama al método de la interfaz para manejar el clic
            });
        }
    }
}
