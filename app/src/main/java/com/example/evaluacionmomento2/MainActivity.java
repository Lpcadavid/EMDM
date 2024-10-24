package com.example.evaluacionmomento2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene el texto de los EditText
                String correo = usernameEditText.getText().toString().trim();
                String contrasena = passwordEditText.getText().toString().trim();


                if (esValido(correo, contrasena)) {

                    if (correo.equals("santaella") && contrasena.equals("123")) {

                        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(MainActivity.this, "Por favor, introduce un correo y una contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean esValido(String correo, String contrasena) {
        return !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contrasena);
    }
}
