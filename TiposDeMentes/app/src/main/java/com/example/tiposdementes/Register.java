package com.example.tiposdementes;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText etUser, etCiudad, etEmpresa, etContrasena;
    private Button btnRegistrar;
    private ImageView back;
    boolean existCode = false;
    public Utils utils;
    private String cod = "";
    public static Boolean usernuevo = false;
    Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private ProgressDialog progressDialog;
    public static Map<String, Object> map;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back = findViewById(R.id.arrowBack);

        back.setOnClickListener(v-> Utils.intentTrans(MenuPrincipal.class, Register.this));

        btnRegistrar = findViewById(R.id.btnRegistrarse);
        //el codigo es el nombre de usuario
        etUser = findViewById(R.id.etCodigo);
        // carrera en FB va hacer contrasena aca
        etContrasena = findViewById(R.id.etContrasena);
        //nombre en FB va hacer ciudad aca
        etCiudad = findViewById(R.id.etCiudad);
        //correo en FB va hacer empresa
        etEmpresa = findViewById(R.id.etEmpresa);

        db = FirebaseFirestore.getInstance();
        map = new HashMap<>();
        progressDialog = new ProgressDialog(Register.this);

        utils = new Utils(Register.this);
        btnRegistrar.setOnClickListener(v -> {
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);
            createUser();
        });
    }

    public void createUser() {

        String user = etUser.getText().toString();
        String contrasena = etContrasena.getText().toString();
        String ciudad = etCiudad.getText().toString();
        String empresa = etEmpresa.getText().toString();

        Matcher mather = pattern.matcher(empresa);

        if (TextUtils.isEmpty(user)) {
            etUser.setError("Ingrese un usuario");
            etUser.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(contrasena)) {
            etContrasena.setError("Ingrese una contraseña");
            etContrasena.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(ciudad)) {
            etCiudad.setError("Ingrese una ciudad");
            etCiudad.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(empresa)) {
            etEmpresa.setError("Ingrese una Empresa");
            etEmpresa.requestFocus();
            progressDialog.dismiss();
        } else {
            db.collection(getResources().getString(R.string.fbusers)).whereEqualTo(getResources().getString(R.string.fbuserscodigo), user).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            cod = doc.getString(getResources().getString(R.string.fbuserscodigo));
                            if (user.equals(cod))
                                existCode = true;
                        }
                        if (existCode) {
                            Toast.makeText(Register.this, R.string.userexiste, Toast.LENGTH_SHORT).show();
                        } else {
                            usernuevo = true;
                            utils.registrarUserFirebase(user, ciudad, empresa, contrasena);
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, R.string.useregistrado, Toast.LENGTH_SHORT).show();
                            Utils.intentTrans(LoginActivity.class, Register.this);
                            finish();
                        }
                    });
        }
    }

}