package com.app.pandastock;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button crear, ingresar;
    EditText email,password;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();

        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPassword);

        crear = findViewById(R.id.btnCrearCuentaa);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        ingresar = findViewById(R.id.btnIngresar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails, passwords;
                emails = email.getText().toString().trim();
                passwords = password.getText().toString().trim();

                if (TextUtils.isEmpty(emails) || TextUtils.isEmpty(passwords)) {
                    Toast.makeText(LoginActivity.this, "Los campos Email o Contraseña están vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emails, passwords)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Inicio de Sesión Exitoso", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error al Iniciar Sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
