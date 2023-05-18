package com.example.tiposdementes;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Utils extends AppCompatActivity {

    private final FirebaseFirestore db;
    private final Context context;
    public static SharedPreferences prefe;
    public static Map<String, Object> map,map2;
    private String nombreuser = "", carrerauser = "", correouser = "", codigouser = "";

    public Utils(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        prefe = context.getSharedPreferences(context.getString(R.string.shnombrefile), Context.MODE_PRIVATE);
        map = new HashMap<>();
        map2 = new HashMap<>();
    }

    public void registrarUserFirebase(String user, String ciudad, String empresa, String contrasena) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        map.put(context.getString(R.string.fbuserscodigo), user);
        map.put(context.getString(R.string.fbuserscarrera), contrasena);
        map.put(context.getString(R.string.fbusersnombre), ciudad);
        map.put(context.getString(R.string.fbuserscorreo), empresa);
        map.put(context.getString(R.string.fbusersestado), "true");
        map.put(context.getString(R.string.fbusersfecha), fecha);
        db.collection(context.getString(R.string.fbusers)).document(user).set(map);
    }

    public void registrarValidador() {
        SharedPreferences.Editor editor = prefe.edit();
        editor.putString(context.getString(R.string.shinstalada), context.getString(R.string.shtrue));
        editor.putString(context.getString(R.string.nameversion), context.getString(R.string.version));
        editor.apply();
    }

    public static void intentTrans(Class<?> cls, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }
}
