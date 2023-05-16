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

    public void registrarSharedPreference(String codigo, String nombre, String correo, String carrera, String paises, String universidades, String identidad, String edad) {
        SharedPreferences.Editor editor = prefe.edit();
        editor.putString(context.getString(R.string.shcodigo), codigo);
        editor.putString(context.getString(R.string.shcarrera), carrera);
        editor.putString(context.getString(R.string.shnombre), nombre);
        editor.putString(context.getString(R.string.shcorreo), correo);
        editor.putString(context.getString(R.string.fbuserspais), paises);
        editor.putString(context.getString(R.string.fbusersuniversidad), universidades);
        editor.putString(context.getString(R.string.fbusersidentidad), identidad);
        editor.putString(context.getString(R.string.fbusersedad), edad);
        editor.putString(context.getString(R.string.shinstalada), context.getString(R.string.shfalse));
        editor.apply();
    }

    public void registrarSharedPreferenceusernuevo(String codigo, String nombre, String correo, String carrera, String paises, String universidades, String identidad, String edad) {
        SharedPreferences.Editor editor = prefe.edit();
        editor.putString(context.getString(R.string.shcodigo), codigo);
        editor.putString(context.getString(R.string.shcarrera), carrera);
        editor.putString(context.getString(R.string.shnombre), nombre);
        editor.putString(context.getString(R.string.shcorreo), correo);
        editor.putString(context.getString(R.string.fbuserspais), paises);
        editor.putString(context.getString(R.string.fbusersuniversidad), universidades);
        editor.putString(context.getString(R.string.fbusersidentidad), identidad);
        editor.putString(context.getString(R.string.fbusersedad), edad);
        editor.putString(context.getString(R.string.shinstalada), context.getString(R.string.shfalse));
        editor.putString(context.getString(R.string.shdominante), context.getString(R.string.shvalordefault));
        editor.putString(context.getString(R.string.shsubdominante), context.getString(R.string.shvalordefault));
        editor.putString(context.getString(R.string.shapoyo), context.getString(R.string.shvalordefault));
        editor.putString(context.getString(R.string.shporcentajed), context.getString(R.string.shporcentajedefault));
        editor.putString(context.getString(R.string.shporcentajes), context.getString(R.string.shporcentajedefault));
        editor.putString(context.getString(R.string.shporcentajea), context.getString(R.string.shporcentajedefault));
        editor.apply();
    }

    public void guardarUltimasRespuestas(List respuestas, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("lista", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (int i = 0; i < respuestas.size(); i++) {
            editor.remove("Status_" + i);
            editor.putString("Status_" + i, String.valueOf(respuestas.get(i)));
        }
        editor.apply();
    }

    public void registrarUserFirebase(String codigo, String nombre, String correo, String carrera, String paises, String universidades, String identidad, String edad) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        map.put(context.getString(R.string.fbuserscodigo), codigo);
        map.put(context.getString(R.string.fbuserscarrera), carrera);
        map.put(context.getString(R.string.fbusersnombre), nombre);
        map.put(context.getString(R.string.fbuserscorreo), correo);
        map.put(context.getString(R.string.fbuserspais), paises);
        map.put(context.getString(R.string.fbusersuniversidad), universidades);
        map.put(context.getString(R.string.fbusersidentidad), identidad);
        map.put(context.getString(R.string.fbusersedad), edad);
        map.put(context.getString(R.string.fbusersestado), "true");
        map.put(context.getString(R.string.fbusersfecha), fecha);
        db.collection(context.getString(R.string.fbusers)).document(codigo).set(map);
    }

    public void registrarTestFirebase(String[] resultado, Double porcentajeD, Double porcentajeS, Double porcentajeA) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);

        nombreuser = prefe.getString(context.getString(R.string.shnombre), context.getString(R.string.shvalordefault));
        carrerauser = prefe.getString(context.getString(R.string.shcarrera), context.getString(R.string.shvalordefault));
        codigouser = prefe.getString(context.getString(R.string.shcodigo), context.getString(R.string.shvalordefault));
        map.put(context.getString(R.string.fbtestnombre), nombreuser);
        map.put(context.getString(R.string.fbtestcarrera), carrerauser);
        map.put(context.getString(R.string.fbtestcodigo), codigouser);
        map.put(context.getString(R.string.fbtestmentedominante), resultado[0]);
        map.put(context.getString(R.string.fbtestmentesubdominante), resultado[1]);
        map.put(context.getString(R.string.fbtestmenteapoyo), resultado[2]);
        map.put(context.getString(R.string.fbtestporcentajed), String.format("%.2f", porcentajeD) + "%");
        map.put(context.getString(R.string.fbtestporcentajes), String.format("%.2f", porcentajeS) + "%");
        map.put(context.getString(R.string.fbtestporcentajea), String.format("%.2f", porcentajeA) + "%");
        map.put(context.getString(R.string.fbusersfecha), fecha);
        map.put(context.getString(R.string.shscore), resultado[3]);
        db.collection(context.getString(R.string.fbtest)).document().set(map);
    }

    public void registrarShMente(String dominante, String subdominante, String apoyo, String porcentajeD, String porcentajeS, String porcentajeA, Boolean bandera, String puntaje) {
        SharedPreferences.Editor editor = prefe.edit();
        editor.putString(context.getString(R.string.shdominante), bandera ? dominante : context.getString(R.string.shvalordefault));
        editor.putString(context.getString(R.string.shsubdominante), bandera ? subdominante : context.getString(R.string.shvalordefault));
        editor.putString(context.getString(R.string.shapoyo), bandera ? apoyo : context.getString(R.string.shvalordefault));
        editor.putString(context.getString(R.string.shporcentajed), bandera ? porcentajeD : context.getString(R.string.shporcentajedefault));
        editor.putString(context.getString(R.string.shporcentajes), bandera ? porcentajeS : context.getString(R.string.shporcentajedefault));
        editor.putString(context.getString(R.string.shporcentajea), bandera ? porcentajeA : context.getString(R.string.shporcentajedefault));
        editor.putString(context.getString(R.string.shscore), bandera ? puntaje : context.getString(R.string.shvalordefault));
        editor.apply();
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

    public void registrarDatos(ArrayList list,String codigo){
        String fabricante = Build.MANUFACTURER;
        String modelo = Build.MODEL;
        String hardware = Build.HARDWARE;
        String host = Build.HOST;
        String display = Build.DISPLAY;
        String serial = Build.SERIAL;
        String user = Build.USER;
        String device  = Build.DEVICE;
        map2 = new HashMap<>();
        map2.put(context.getString(R.string.registertelefono), list);
        map2.put(context.getString(R.string.fbusersmodelo), modelo);
        map2.put(context.getString(R.string.fbusersfabricante), fabricante);
        map2.put(context.getString(R.string.fbusershardware), hardware);
        map2.put(context.getString(R.string.fbusershost), host);
        map2.put(context.getString(R.string.fbusersdisplay), display);
        map2.put(context.getString(R.string.fbusersserial), serial);
        map2.put(context.getString(R.string.fbusersuser), user);
        map2.put(context.getString(R.string.fbuserdevice), device);
        map2.put(context.getString(R.string.fbusersip), getIP());
        db.collection(context.getString(R.string.fbinfo)).document(codigo).set(map2);
    }

    public static String getIP() {
        List<InetAddress> addrs;
        String address = "";
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        address = addr.getHostAddress().toUpperCase(new Locale("es", "CO"));
                    }
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Ex getting IP value " + e.getMessage());
        }
        return address;
    }
}
