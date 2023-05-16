package com.example.tiposdementes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRegistrar;
    private EditText etCodigoLogin;
    private Button btnLogin;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    boolean existCode = false;
    public static boolean userdiferente = false;
    public Utils utils;
    private String cod = "", correo = "", nombre = "", carrera = "", paises = "", universidades = "", identidad = "", edad = "", estado = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegistrar = findViewById(R.id.tvRegistrar);
        btnLogin = findViewById(R.id.btnLogin);
        etCodigoLogin = findViewById(R.id.etCodigoLogin);
        db = FirebaseFirestore.getInstance();
        utils = new Utils(LoginActivity.this);
        progressDialog = new ProgressDialog(LoginActivity.this);

        File file = new File(getApplicationContext().getApplicationInfo().dataDir + getResources().getString(R.string.ruta) + getResources().getString(R.string.shnombrefile) + ".xml");
        SharedPreferences preferencias = getSharedPreferences(getResources().getString(R.string.shnombrefile), Context.MODE_PRIVATE);

        tvRegistrar.setOnClickListener(v -> Utils.intentTrans(Register.class, LoginActivity.this));

        btnLogin.setOnClickListener(v -> {
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);

            existCode = false;
            String codigo = etCodigoLogin.getText().toString();
            db.collection(getResources().getString(R.string.fbusers)).whereEqualTo(getResources().getString(R.string.fbuserscodigo), codigo).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            cod = doc.getString(getResources().getString(R.string.fbuserscodigo));
                            nombre = doc.getString(getResources().getString(R.string.fbusersnombre));
                            correo = doc.getString(getResources().getString(R.string.fbuserscorreo));
                            carrera = doc.getString(getResources().getString(R.string.fbuserscarrera));
                            paises = doc.getString(getResources().getString(R.string.fbuserspais));
                            universidades = doc.getString(getResources().getString(R.string.fbusersuniversidad));
                            identidad = doc.getString(getResources().getString(R.string.fbusersidentidad));
                            edad = doc.getString(getResources().getString(R.string.fbusersedad));
                            estado = doc.getString(getResources().getString(R.string.fbuserestado));

                            if (codigo.equals(cod) && estado.equals(getResources().getString(R.string.shtrue))) {
                                if (file.exists()) {
                                    //esta fallando la validacion con usernuevo, revisar, por ahora se quita, problema al registrar user nuevo (&& !usernuevo)
                                    if (preferencias.getString(getResources().getString(R.string.shcodigo), "null").equals(codigo)) {
                                        existCode = true;
                                    } else {
                                        existCode = true;
                                        userdiferente = true;
                                        utils.registrarSharedPreference(cod, nombre, correo, carrera, paises, universidades, identidad, edad);
                                    }
                                } else {
                                    existCode = true;
                                    utils.registrarSharedPreference(cod, nombre, correo, carrera, paises, universidades, identidad, edad);
                                }
                            }
                        }
                        if (existCode) {
                            Utils.intentTrans(MenuPrincipal.class, LoginActivity.this);
                        } else
                            Toast.makeText(LoginActivity.this, R.string.usernoregis, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    });
        });
    }
}