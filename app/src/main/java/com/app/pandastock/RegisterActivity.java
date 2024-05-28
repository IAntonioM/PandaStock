package com.app.pandastock;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button concuenta,crearCuenta;
    EditText nombres,apellidos,email,password;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        nombres=findViewById(R.id.txtNombres);
        apellidos=findViewById(R.id.txtApellidos);
        email=findViewById(R.id.txtEmail1);
        password=findViewById(R.id.txtPassword1);

        concuenta = findViewById(R.id.btnYatienescuenta);
        crearCuenta=findViewById(R.id.btnCrearcuenta);

        concuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Ya tengo Cuenta",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombress, apellidoss, emails, passwords;

                nombress = nombres.getText().toString().trim();
                apellidoss = apellidos.getText().toString().trim();
                emails = email.getText().toString().trim();
                passwords = password.getText().toString().trim();

                if (TextUtils.isEmpty(nombress) || TextUtils.isEmpty(apellidoss)) {
                    Toast.makeText(RegisterActivity.this, "Los campos Nombres o Apellidos están vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(emails) || TextUtils.isEmpty(passwords)) {
                    Toast.makeText(RegisterActivity.this, "Los campos Email o Contraseña están vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emails, passwords)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Crear un objeto Map para almacenar los datos del usuario
                                        Map<String, Object> userData = new HashMap<>();
                                        userData.put("userId", user.getUid());
                                        userData.put("nombres", nombress);
                                        userData.put("apellidos", apellidoss);
                                        userData.put("email", emails);

                                        // Obtener una referencia a la base de datos Firestore
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                                        // Guardar los datos del usuario en Firestore
                                        db.collection("users").document(user.getUid())
                                                .set(userData)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(RegisterActivity.this, "Registro de Usuario exitoso.",
                                                                Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(RegisterActivity.this, "Error al guardar los datos del usuario.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registro de Usuario fallido",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}