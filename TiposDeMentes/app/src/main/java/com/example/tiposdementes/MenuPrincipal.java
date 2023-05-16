package com.example.tiposdementes;

import static com.example.tiposdementes.LoginActivity.userdiferente;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.File;

public class MenuPrincipal extends AppCompatActivity {

    private TextView txtMentePrincipal, txtporcentajecirculo, txtCudadradoPorcentajetarjeta, txtTrianguloPorcentaje;
    private RelativeLayout rlInformacion, rlTest, rlPerfil, rlEstadisticas, rlAcercade, rlContacto;
    public static Boolean informacionstatus = false;
    private String dominanteprefe = "";
    private String subdominanteprefe = "";
    private String apoyoprefe = "";
    private String codigof = "";
    private String dominante = "";
    private String subdominante = "";
    private String apoyo = "";
    private String porcentajeD = "";
    private String porcentajeS = "";
    private String porcentajeA = "";
    private String puntaje = "";
    private String puntajescore = "";
    private FirebaseFirestore db;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        txtMentePrincipal = findViewById(R.id.txtMenteCirculo);
        rlInformacion = findViewById(R.id.relaInformacion);
        rlTest = findViewById(R.id.relaTest);
        rlPerfil = findViewById(R.id.relaPerfil);
        rlEstadisticas = findViewById(R.id.relaEstadisticas);
        rlAcercade = findViewById(R.id.relaAcercade);
        rlContacto = findViewById(R.id.relaContacto);
        txtporcentajecirculo = findViewById(R.id.txtCirculoPorcentajeTarjeta);
        txtCudadradoPorcentajetarjeta = findViewById(R.id.txtCudadradoPorcentajetarjeta);
        txtTrianguloPorcentaje = findViewById(R.id.txtTrianguloPorcentaje);
        db = FirebaseFirestore.getInstance();
        utils = new Utils(MenuPrincipal.this);

        File file = new File(getApplicationContext().getApplicationInfo().dataDir + getResources().getString(R.string.ruta) + getResources().getString(R.string.shnombrefile) + ".xml");
        SharedPreferences prefe = getSharedPreferences(getResources().getString(R.string.shnombrefile), Context.MODE_PRIVATE);

        codigof = prefe.getString(getResources().getString(R.string.shcodigo), getResources().getString(R.string.shvalordefault));

        if (userdiferente) {
            db.collection(getResources().getString(R.string.fbtest)).whereEqualTo(getResources().getString(R.string.fbtestcodigo), codigof).orderBy(getResources().getString(R.string.fbusersfecha), Query.Direction.ASCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
                if (queryDocumentSnapshots.size() != 0) {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        dominante = doc.getString(getResources().getString(R.string.fbtestmentedominante));
                        subdominante = doc.getString(getResources().getString(R.string.fbtestmentesubdominante));
                        apoyo = doc.getString(getResources().getString(R.string.fbtestmenteapoyo));
                        porcentajeD = doc.getString(getResources().getString(R.string.fbtestporcentajed));
                        porcentajeS = doc.getString(getResources().getString(R.string.fbtestporcentajes));
                        porcentajeA = doc.getString(getResources().getString(R.string.fbtestporcentajea));
                        puntajescore = doc.getString(getResources().getString(R.string.shscore));

                        if (!(dominante.isEmpty() || subdominante.isEmpty() || apoyo.isEmpty() || porcentajeA.isEmpty() || porcentajeD.isEmpty() || porcentajeS.isEmpty())) {
                            utils.registrarShMente(dominante, subdominante, apoyo, porcentajeD, porcentajeS, porcentajeA, true, puntajescore);
                        }
                    }
                    setearCampos(prefe, file);
                } else {
                    utils.registrarShMente(dominante, subdominante, apoyo, porcentajeD, porcentajeS, porcentajeA, false, puntajescore);
                    setearCamposDefault();
                }
            });
        }
        setearCampos(prefe, file);

        rlInformacion.setOnClickListener(v -> {
            informacionstatus = true;
            Utils.intentTrans(IntroSlider.class, MenuPrincipal.this);
        });

        rlTest.setOnClickListener(v -> Utils.intentTrans(Preguntas.class, MenuPrincipal.this));

        rlPerfil.setOnClickListener(v -> Utils.intentTrans(Perfil.class, MenuPrincipal.this));

        rlEstadisticas.setOnClickListener(v -> Utils.intentTrans(Estadistica.class, MenuPrincipal.this));

        rlAcercade.setOnClickListener(v -> Utils.intentTrans(AcercaDe.class, MenuPrincipal.this));

        rlContacto.setOnClickListener(v -> Utils.intentTrans(Contacto.class, MenuPrincipal.this));
    }

    @SuppressLint("SetTextI18n")
    public void setearCampos(SharedPreferences prefe, File file) {
        dominanteprefe = prefe.getString(getResources().getString(R.string.shdominante), getResources().getString(R.string.shvalordefault));
        subdominanteprefe = prefe.getString(getResources().getString(R.string.shsubdominante), getResources().getString(R.string.shvalordefault));
        apoyoprefe = prefe.getString(getResources().getString(R.string.shapoyo), getResources().getString(R.string.shvalordefault));
        puntaje = prefe.getString(getResources().getString(R.string.shscore), "null");

        if (file.exists() && !(dominanteprefe.equals("") || dominanteprefe.equals(" ") || dominanteprefe.equals("null") || subdominanteprefe.equals("") || dominanteprefe.equals(" ") || subdominanteprefe.equals("null") || apoyoprefe.equals("") || apoyoprefe.equals(" ") || apoyoprefe.equals("null"))) {
            if (dominanteprefe.equals(getResources().getString(R.string.desproporcionado))) {
                setearCamposDefault();
            } else {
                if (dominanteprefe.equals(getResources().getString(R.string.circulo))) {
                    txtporcentajecirculo.setText(prefe.getString(getResources().getString(R.string.shporcentajed), getResources().getString(R.string.shporcentajedefault)));
                    txtMentePrincipal.setText(getResources().getString(R.string.titulocircular) + "\n" + puntaje + " Puntos");
                } else if (dominanteprefe.equals(getResources().getString(R.string.cuadrado))) {
                    txtCudadradoPorcentajetarjeta.setText(prefe.getString(getResources().getString(R.string.shporcentajed), getResources().getString(R.string.shporcentajedefault)));
                    txtMentePrincipal.setText(getResources().getString(R.string.cuadrado) + "\n" + puntaje + " Puntos");
                } else {
                    txtTrianguloPorcentaje.setText(prefe.getString(getResources().getString(R.string.shporcentajed), getResources().getString(R.string.shporcentajedefault)));
                    txtMentePrincipal.setText(getResources().getString(R.string.titulotriangular) + "\n" + puntaje + " Puntos");
                }

                if (subdominanteprefe.equals(getResources().getString(R.string.circulo)))
                    txtporcentajecirculo.setText(prefe.getString(getResources().getString(R.string.shporcentajes), getResources().getString(R.string.shporcentajedefault)));
                else if (subdominanteprefe.equals(getResources().getString(R.string.cuadrado)))
                    txtCudadradoPorcentajetarjeta.setText(prefe.getString(getResources().getString(R.string.shporcentajes), getResources().getString(R.string.shporcentajedefault)));
                else
                    txtTrianguloPorcentaje.setText(prefe.getString(getResources().getString(R.string.shporcentajes), getResources().getString(R.string.shporcentajedefault)));

                if (apoyoprefe.equals(getResources().getString(R.string.circulo)))
                    txtporcentajecirculo.setText(prefe.getString(getResources().getString(R.string.shporcentajea), getResources().getString(R.string.shporcentajedefault)));
                else if (apoyoprefe.equals(getResources().getString(R.string.cuadrado)))
                    txtCudadradoPorcentajetarjeta.setText(prefe.getString(getResources().getString(R.string.shporcentajea), getResources().getString(R.string.shporcentajedefault)));
                else
                    txtTrianguloPorcentaje.setText(prefe.getString(getResources().getString(R.string.shporcentajea), getResources().getString(R.string.shporcentajedefault)));
            }
        } else {
            setearCamposDefault();
        }
    }

    public void setearCamposDefault() {
        txtporcentajecirculo.setText(R.string.shporcentajedefault);
        txtCudadradoPorcentajetarjeta.setText(R.string.shporcentajedefault);
        txtTrianguloPorcentaje.setText(R.string.shporcentajedefault);
        txtMentePrincipal.setText(getResources().getString(R.string.menumente));
    }

    public void toastSalir() {
        final RelativeLayout relativeLayout = findViewById(R.id.msgAccount);

        relativeLayout.setVisibility(View.VISIBLE);
        Button btnEntentido = findViewById(R.id.entendidoMsg);
        Button btnSalir = findViewById(R.id.noEntendidoMsg);
        TextView msg1 = findViewById(R.id.msg1Screen);
        msg1.setText(getResources().getString(R.string.msgmenu));

        relativeLayout.setOnClickListener(v -> {
            //nothing
        });
        btnEntentido.setOnClickListener(v -> relativeLayout.setVisibility(View.GONE));
        btnSalir.setOnClickListener(v -> {
            Utils.intentTrans(LoginActivity.class, MenuPrincipal.this);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        toastSalir();
    }
}