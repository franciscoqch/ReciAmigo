package com.example.tiposdementes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Contacto extends AppCompatActivity {

    private EditText etAsunto, etContenido;
    private Boolean contacto = false;
    private ImageView volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        etAsunto = findViewById(R.id.etAsunto);
        etContenido = findViewById(R.id.etContenido);
        volver = findViewById(R.id.btnVolver);

        volver.setOnClickListener(v -> finish());
    }

    public void enviar(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.tituloalert));

        if (etContenido.getText().toString().equals("") || etAsunto.getText().toString().equals(""))
            builder.setMessage(getResources().getString(R.string.mensajealert));
        else
            builder.setMessage(getResources().getString(R.string.confirmacionalert));

        builder.setPositiveButton(getResources().getString(R.string.btnaceptar), (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.correodestino)});
            intent.putExtra(Intent.EXTRA_SUBJECT, etAsunto.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, etContenido.getText().toString());
            contacto = true;
            etContenido.clearFocus();
            startActivity(intent);
        });
        builder.setNegativeButton(getResources().getString(R.string.btncancelar), (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (contacto) {
            etContenido.setText("");
            etAsunto.setText("");
        }
    }
}