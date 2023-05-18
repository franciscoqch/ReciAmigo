package com.example.tiposdementes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tiposdementes.databinding.ActivityJuegosBinding;

public class Juegos extends AppCompatActivity  {

    private ActivityJuegosBinding binding;
    ImageButton bV,bAz,bAm,bN;
    ImageButton bo1,bo2,bo3,bo4;
    ImageButton bp1,bp2,bp3,bp4;
    ImageButton br1,br2,br3,br4;
    ImageButton bnr1,bnr2,bnr3,bnr4;
    ImageView imaLives;
    int lives = 3;
    int target = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJuegosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //referenciamos imagenbuttons con las variables
        //botes
        bV= (ImageButton)findViewById(R.id.boteVerde);
        bAz= (ImageButton)findViewById(R.id.boteAzul);
        bAm= (ImageButton)findViewById(R.id.boteAmarillo);
        bN= (ImageButton)findViewById(R.id.boteNegro);
        //basura organica
        bo1=(ImageButton)findViewById(R.id.o1);
        bo2=(ImageButton)findViewById(R.id.o2);
        bo3=(ImageButton)findViewById(R.id.o3);
        bo4=(ImageButton)findViewById(R.id.o4);
        //basura plasticos
        bp1=(ImageButton)findViewById(R.id.p1);
        bp2=(ImageButton)findViewById(R.id.p2);
        bp3=(ImageButton)findViewById(R.id.p3);
        bp4=(ImageButton)findViewById(R.id.p4);
        //basura reciclable
        br1=(ImageButton)findViewById(R.id.r1);
        br2=(ImageButton)findViewById(R.id.r2);
        br3=(ImageButton)findViewById(R.id.r3);
        br4=(ImageButton)findViewById(R.id.r4);
        //basura no reciclable
        bnr1=(ImageButton)findViewById(R.id.nr1);
        bnr2=(ImageButton)findViewById(R.id.nr2);
        bnr3=(ImageButton)findViewById(R.id.nr3);
        bnr4=(ImageButton)findViewById(R.id.nr4);

        //eventos dragListener (los que vamos a recibir)
        bV.setOnDragListener(dragListener);
        bAz.setOnDragListener(dragListener);
        bAm.setOnDragListener(dragListener);
        bN.setOnDragListener(dragListener);
        //eventos onLong (los que vamos a arrastrar)
        //basura organica
        bo1.setOnLongClickListener(longClickListener);
        bo2.setOnLongClickListener(longClickListener);
        bo3.setOnLongClickListener(longClickListener);
        bo4.setOnLongClickListener(longClickListener);
        //basura plasticos
        bp1.setOnLongClickListener(longClickListener);
        bp2.setOnLongClickListener(longClickListener);
        bp3.setOnLongClickListener(longClickListener);
        bp4.setOnLongClickListener(longClickListener);
        //basura reciclable
        br1.setOnLongClickListener(longClickListener);
        br2.setOnLongClickListener(longClickListener);
        br3.setOnLongClickListener(longClickListener);
        br4.setOnLongClickListener(longClickListener);
        //basura no reciclable
        bnr1.setOnLongClickListener(longClickListener);
        bnr2.setOnLongClickListener(longClickListener);
        bnr3.setOnLongClickListener(longClickListener);
        bnr4.setOnLongClickListener(longClickListener);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(Singleton.getInstance().getResponse()) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        else { finish(); }

    }

    View help_layout;

    public void onClickHelp(View v) {
        help_layout = this.getLayoutInflater().inflate(R.layout.help_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guia")
                .setView(help_layout)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    View.OnLongClickListener longClickListener= new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
            v.startDrag(data,myShadow,v,0);
            return true;
        }
    };

    private void ActionFail()
    {
        imaLives = findViewById(R.id.imageViewLives);
        if(lives == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("HAS PERDIDO!!!!")
                    .setView(help_layout)
                    .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utils.intentTrans(Juegos.class, Juegos.this);
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        else {
            lives--;
            if(lives == 2) { imaLives.setImageResource(R.drawable.lives2); }
            if(lives == 1) { imaLives.setImageResource(R.drawable.lives1); }
            if(lives == 0) { imaLives.setImageResource(R.drawable.lives0); }
            MediaPlayer mediaf = MediaPlayer.create(Juegos.this, R.raw.fail);
            mediaf.start();
        }
    }

    private void ActionSuccess(){
        target++;
        MediaPlayer media = MediaPlayer.create(Juegos.this, R.raw.trash);
        media.start();
        if(target == 16) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("HAS GANADO!!!!")
                    .setView(help_layout)
                    .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utils.intentTrans(MenuPrincipal.class, Juegos.this);
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }


    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            switch (dragEvent){
               /* case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case  DragEvent.ACTION_DRAG_EXITED:
                    break;*/
                case DragEvent.ACTION_DROP:
                    //ORGANICA
                    if(view.getId()==R.id.o1&&v.getId()==R.id.boteVerde){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.o2&&v.getId()==R.id.boteVerde){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.o3&&v.getId()==R.id.boteVerde){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.o4&&v.getId()==R.id.boteVerde){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    //fails
                    if(view.getId()==R.id.o1&&v.getId()!=R.id.boteVerde) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.o2&&v.getId()!=R.id.boteVerde) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.o3&&v.getId()!=R.id.boteVerde) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.o4&&v.getId()!=R.id.boteVerde) {
                        ActionFail();
                    }
                    //PLASTICOS
                    if(view.getId()==R.id.p1&&v.getId()==R.id.boteAzul){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.p2&&v.getId()==R.id.boteAzul){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.p3&&v.getId()==R.id.boteAzul){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }

                    if(view.getId()==R.id.p4&&v.getId()==R.id.boteAzul){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    //fails
                    if(view.getId()==R.id.p1&&v.getId()!=R.id.boteAzul) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.p2&&v.getId()!=R.id.boteAzul) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.p3&&v.getId()!=R.id.boteAzul) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.p4&&v.getId()!=R.id.boteAzul) {
                        ActionFail();
                    }
                    //Reciclable
                    if(view.getId()==R.id.r1&&v.getId()==R.id.boteAmarillo){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.r2&&v.getId()==R.id.boteAmarillo){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.r3&&v.getId()==R.id.boteAmarillo){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }

                    if(view.getId()==R.id.r4&&v.getId()==R.id.boteAmarillo){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    //fails
                    if(view.getId()==R.id.r1&&v.getId()!=R.id.boteAmarillo) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.r2&&v.getId()!=R.id.boteAmarillo) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.r3&&v.getId()!=R.id.boteAmarillo) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.r4&&v.getId()!=R.id.boteAmarillo) {
                        ActionFail();
                    }
                    //No Reciclable
                    if(view.getId()==R.id.nr1&&v.getId()==R.id.boteNegro){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.nr2&&v.getId()==R.id.boteNegro){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    if(view.getId()==R.id.nr3&&v.getId()==R.id.boteNegro){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }

                    if(view.getId()==R.id.nr4&&v.getId()==R.id.boteNegro){
                        LinearLayout oldParent = (LinearLayout)view.getParent();
                        oldParent.removeView(view);
                        ActionSuccess();
                    }
                    //fails
                    if(view.getId()==R.id.nr1&&v.getId()!=R.id.boteNegro) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.nr2&&v.getId()!=R.id.boteNegro) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.nr3&&v.getId()!=R.id.boteNegro) {
                        ActionFail();
                    }
                    if(view.getId()==R.id.nr4&&v.getId()!=R.id.boteNegro) {
                        ActionFail();
                    }

                    break;
            }
            return true;
        }
    };
}