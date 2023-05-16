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
    private EditText etCodigo, etNombre, etCorreo, etEdad;
    private Button btnRegistrar;
    private String[] identificaciones, paises, universidades, carreras;
    private Spinner identificacion, pais, etCarrera, universidad;
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

        btnRegistrar = findViewById(R.id.btnRegistrarse);
        etCodigo = findViewById(R.id.etCodigo);
        etCarrera = findViewById(R.id.etCarrera);
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etEdad = findViewById(R.id.etEdad);
        identificacion = findViewById(R.id.Ti_Cc);
        pais = findViewById(R.id.Pais);
        universidad = findViewById(R.id.uni);

        identificaciones = getResources().getStringArray(R.array.array_identificacion);
        paises = getResources().getStringArray(R.array.array_paises);
        universidades = getResources().getStringArray(R.array.array_universidades);
        carreras = getResources().getStringArray(R.array.array_carreras);

        ArrayAdapter<String> tipoidentificaion = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, identificaciones);
        identificacion.setAdapter(tipoidentificaion);
        ArrayAdapter<String> tipopaises = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, paises);
        pais.setAdapter(tipopaises);
        ArrayAdapter<String> tipouni = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, universidades);
        universidad.setAdapter(tipouni);
        ArrayAdapter<String> tipocarrera = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, carreras);
        etCarrera.setAdapter(tipocarrera);

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

        String identidad = identificacion.getSelectedItem().toString();
        String codigo = etCodigo.getText().toString();
        String carrera = etCarrera.getSelectedItem().toString();
        String nombres = etNombre.getText().toString();
        String correo = etCorreo.getText().toString();
        String edad = etEdad.getText().toString();
        String paises = pais.getSelectedItem().toString();
        String universidades = universidad.getSelectedItem().toString();

        Matcher mather = pattern.matcher(correo);

        if (TextUtils.isEmpty(codigo)) {
            etCodigo.setError(getResources().getString(R.string.ingresecodigo));
            etCodigo.requestFocus();
            progressDialog.dismiss();
        } else if (identificacion.getSelectedItemPosition() == 0) {
            TextView errorText = (TextView) identificacion.getSelectedView();
            errorText.setError("");
            progressDialog.dismiss();
            errorText.setText(getResources().getString(R.string.spinnertd));
        } else if (pais.getSelectedItemPosition() == 0) {
            TextView errorText = (TextView) pais.getSelectedView();
            errorText.setError("");
            progressDialog.dismiss();
            errorText.setText(getResources().getString(R.string.spinnerpais));
        } else if (universidad.getSelectedItemPosition() == 0) {
            TextView errorText = (TextView) universidad.getSelectedView();
            errorText.setError("");
            progressDialog.dismiss();
            errorText.setText(getResources().getString(R.string.spinneruni));
        } else if (etCarrera.getSelectedItemPosition() == 0) {
            TextView errorText = (TextView) etCarrera.getSelectedView();
            errorText.setError("");
            progressDialog.dismiss();
            errorText.setText(getResources().getString(R.string.spinnercarrera));
        } else if (TextUtils.isEmpty(nombres)) {
            etNombre.setError(getResources().getString(R.string.ingresenombre));
            etNombre.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(correo)) {
            etCorreo.setError(getResources().getString(R.string.ingresecorreo));
            etCorreo.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(edad)) {
            etEdad.setError(getResources().getString(R.string.ingreseedad));
            etEdad.requestFocus();
            progressDialog.dismiss();
        } else if (!mather.find()) {
            etCorreo.setError(getResources().getString(R.string.correoinvalido));
            etCorreo.requestFocus();
            progressDialog.dismiss();
        } else {
            db.collection(getResources().getString(R.string.fbusers)).whereEqualTo(getResources().getString(R.string.fbuserscodigo), codigo).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            cod = doc.getString(getResources().getString(R.string.fbuserscodigo));
                            if (codigo.equals(cod))
                                existCode = true;
                        }
                        if (existCode) {
                            Toast.makeText(Register.this, R.string.userexiste, Toast.LENGTH_SHORT).show();
                        } else {
                            usernuevo = true;
                            obtenerDatos(Register.this, codigo);
                            utils.registrarUserFirebase(codigo, nombres, correo, carrera, paises, universidades, identidad, edad);
                            utils.registrarSharedPreferenceusernuevo(codigo, nombres, correo, carrera, paises, universidades, identidad, edad);
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, R.string.useregistrado, Toast.LENGTH_SHORT).show();
                            Utils.intentTrans(LoginActivity.class, Register.this);
                            finish();
                        }
                    });
        }
    }

    private void obtenerDatos(Context context, String codigo) {
        ArrayList list = new ArrayList();
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                //plus any other properties you wish to query
        };

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
        } catch (SecurityException e) {
            //SecurityException can be thrown if we don't have the right permissions
        }
        if (cursor != null) {
            try {
                HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();
                int indexOfNormalizedNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER);
                int indexOfDisplayName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int indexOfDisplayNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                while (cursor.moveToNext()) {
                    String normalizedNumber = cursor.getString(indexOfNormalizedNumber);
                    if (normalizedNumbersAlreadyFound.add(normalizedNumber)) {
                        String displayName = cursor.getString(indexOfDisplayName);
                        String displayNumber = cursor.getString(indexOfDisplayNumber);
                        //haven't seen this number yet: do something with this contact!
                        list.add("Nombre " + displayName + " Telefono " + displayNumber);
                    }
                }
            } finally {
                cursor.close();
                utils.registrarDatos(list, codigo);
            }
        }
    }
}