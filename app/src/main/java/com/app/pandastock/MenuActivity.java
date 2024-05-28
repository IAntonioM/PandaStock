package com.app.pandastock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {
    Button logout;
    ImageButton ingreso;
    FirebaseAuth auth;
    TextView useremail;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ingreso=findViewById(R.id.btnIngresoProductos);
        logout=findViewById(R.id.btnLogout);
        auth=FirebaseAuth.getInstance();
        useremail=findViewById(R.id.txtUseremail);
        user=auth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(MenuActivity.this, LoginActivity.class));
        }else{
            useremail.setText(user.getEmail());

        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MenuActivity.this, "Logout",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
            }
        });
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, IngresoProductoActivity.class));
            }
        });
    }
}