package com.example.tiposdementes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRegistrar;
    private EditText etCodigoLogin, etContrasena;
    private Button btnLogin;
    private ImageView imgBack;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    boolean existCode = false;
    private String cod = "", contrasenaFirebase = "", correo = "", nombre = "", carrera = "", paises = "", universidades = "", identidad = "", edad = "", estado = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegistrar = findViewById(R.id.tvRegistrar);
        btnLogin = findViewById(R.id.btnLogin);
        etCodigoLogin = findViewById(R.id.etCodigoLogin);
        etContrasena = findViewById(R.id.contrasena);
        imgBack = findViewById(R.id.arrowBack);
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);

        tvRegistrar.setOnClickListener(v -> Utils.intentTrans(Register.class, LoginActivity.this));
        imgBack.setOnClickListener(v -> Utils.intentTrans(MenuPrincipal.class, LoginActivity.this));

        btnLogin.setOnClickListener(v -> {
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);

            existCode = false;
            String user = etCodigoLogin.getText().toString();
            String contrasena = etContrasena.getText().toString();
            db.collection(getResources().getString(R.string.fbusers)).whereEqualTo(getResources().getString(R.string.fbuserscodigo), user).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            //numero de identidad como usuario para login
                            cod = doc.getString(getResources().getString(R.string.fbuserscodigo));
                            //carrera como contrasena para login
                            contrasenaFirebase = doc.getString(getResources().getString(R.string.fbuserscarrera));
                            nombre = doc.getString(getResources().getString(R.string.fbusersnombre));
                            correo = doc.getString(getResources().getString(R.string.fbuserscorreo));
                            identidad = doc.getString(getResources().getString(R.string.fbusersidentidad));

                            if (user.equals(cod) && contrasena.equals(contrasenaFirebase)) {
                                existCode = true;
                            } else {
                                existCode = false;
                                Toast.makeText(LoginActivity.this, R.string.usernoregistrado, Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (existCode) {
                            Utils.intentTrans(Reciclador.class, LoginActivity.this);
                        } else
                            Toast.makeText(LoginActivity.this, R.string.usernoregis, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
        });
    }
}