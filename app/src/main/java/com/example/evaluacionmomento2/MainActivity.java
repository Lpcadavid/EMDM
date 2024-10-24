package com.example.evaluacionmomento2;

import android.content.Intent; // Importa la clase Intent para manejar actividades
import android.os.Bundle; // Importa la clase Bundle para pasar datos entre actividades
import android.text.TextUtils; // Importa para verificar si el texto está vacío
import android.view.View; // Importa para manejar las vistas
import android.widget.Button; // Importa la clase Button para botones
import android.widget.EditText; // Importa la clase EditText para campos de texto
import android.widget.Toast; // Importa la clase Toast para mostrar mensajes cortos

import androidx.appcompat.app.AppCompatActivity; // Importa la clase base para actividades

// Clase principal de la aplicación que maneja la pantalla de inicio de sesión
public class MainActivity extends AppCompatActivity {

    // Declaración de variables para los elementos de la interfaz
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Llama al método de la clase base
        setContentView(R.layout.activity_main); // Establece el diseño de la actividad

        // Inicializa los elementos de la interfaz
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        // Configura el listener para el botón de inicio de sesión
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene el texto de los EditText y elimina espacios en blanco
                String correo = usernameEditText.getText().toString().trim();
                String contrasena = passwordEditText.getText().toString().trim();

                // Verifica si el correo y la contraseña son válidos
                if (esValido(correo, contrasena)) {
                    // Comprueba las credenciales de inicio de sesión
                    if (correo.equals("santaella") && contrasena.equals("123")) {
                        // Si las credenciales son correctas, inicia la ProductActivity
                        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                        startActivity(intent);
                    } else {
                        // Muestra un mensaje de error si las credenciales son incorrectas
                        Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Muestra un mensaje si los campos están vacíos
                    Toast.makeText(MainActivity.this, "Por favor, introduce un correo y una contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para validar que los campos no están vacíos
    private boolean esValido(String correo, String contrasena) {
        return !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contrasena); // Devuelve true si ambos campos tienen texto
    }
}
