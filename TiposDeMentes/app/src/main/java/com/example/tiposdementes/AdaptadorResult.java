package com.example.tiposdementes;

import static com.example.tiposdementes.Results.nombre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

public class AdaptadorResult extends RecyclerView.Adapter<AdaptadorResult.ViewHolder> {

    private final Context context;
    private final String[] arregloresultado;
    public String escala = "0 - 0", txtdominante = "", txtsubdominante = "", txtapoyo = "";
    private Boolean mentevalida = true;

    public AdaptadorResult(Context context, String[] arregloresultado) {
        this.context = context;
        this.arregloresultado = arregloresultado;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtDominante.setText(arregloresultado[0]);
        holder.txtSubDominate.setText(arregloresultado[1]);
        holder.txtApoyo.setText(arregloresultado[2]);
        holder.txtPrgress.setText(arregloresultado[3]);
        holder.progressBar.setProgress(Math.round(Float.parseFloat(arregloresultado[6])));
        holder.txtPorcenDominandte.setText(arregloresultado[3]);
        holder.txtPorcenSubdominante.setText(arregloresultado[4]);
        holder.txtPorcenApoyo.setText(arregloresultado[5]);
        txtdominante = arregloresultado[0];
        txtsubdominante = arregloresultado[1];
        txtapoyo = arregloresultado[2];
        holder.linearContenedor1.setVisibility(position != 0 ? View.GONE : View.VISIBLE);
        holder.linearContenedor2.setVisibility(position != 1 ? View.GONE : View.VISIBLE);

        if (position == 2) {
            holder.linearContenedor3.setVisibility(txtdominante.equals(context.getString(R.string.desproporcionado)) ? View.GONE : View.VISIBLE);
            holder.linearContenedor4.setVisibility(txtdominante.equals(context.getString(R.string.desproporcionado)) ? View.VISIBLE : View.GONE);
        }

        if (txtsubdominante.equals(context.getString(R.string.triangulo)) && txtapoyo.equals(context.getString(R.string.cuadrado))) {
            holder.cerebroResultSubDominante.setImageResource(R.drawable.braincentral);
            holder.cerebroResultSubDominante2.setImageResource(R.drawable.brainleft);
            holder.txtResultSubDominante.setText(context.getString(R.string.mensajesub1));
        } else if (txtsubdominante.equals(context.getString(R.string.cuadrado)) && txtapoyo.equals(context.getString(R.string.triangulo))) {
            holder.cerebroResultSubDominante.setImageResource(R.drawable.brainleft);
            holder.cerebroResultSubDominante2.setImageResource(R.drawable.braincentral);
            holder.txtResultSubDominante.setText(context.getString(R.string.mensajesub2));
        } else if (txtsubdominante.equals(context.getString(R.string.triangulo)) && txtapoyo.equals(context.getString(R.string.circulo))) {
            holder.cerebroResultSubDominante.setImageResource(R.drawable.braincentral);
            holder.cerebroResultSubDominante2.setImageResource(R.drawable.brainright);
            holder.txtResultSubDominante.setText(context.getString(R.string.mensajesub3));
        } else if (txtsubdominante.equals(context.getString(R.string.circulo)) && txtapoyo.equals(context.getString(R.string.triangulo))) {
            holder.cerebroResultSubDominante.setImageResource(R.drawable.brainright);
            holder.cerebroResultSubDominante2.setImageResource(R.drawable.braincentral);
            holder.txtResultSubDominante.setText(context.getString(R.string.mensajesub4));
        } else if (txtsubdominante.equals(context.getString(R.string.cuadrado)) && txtapoyo.equals(context.getString(R.string.circulo))) {
            holder.cerebroResultSubDominante.setImageResource(R.drawable.brainleft);
            holder.cerebroResultSubDominante2.setImageResource(R.drawable.brainright);
            holder.txtResultSubDominante.setText(context.getString(R.string.mensajesub5));
        } else if (txtsubdominante.equals(context.getString(R.string.circulo)) && txtapoyo.equals(context.getString(R.string.cuadrado))) {
            holder.cerebroResultSubDominante.setImageResource(R.drawable.brainright);
            holder.cerebroResultSubDominante2.setImageResource(R.drawable.brainleft);
            holder.txtResultSubDominante.setText(context.getString(R.string.mensajesub6));
        }

        if (txtdominante.equals(context.getString(R.string.desproporcionado))) {
            holder.txtxdescripciontecnica.setText(Html.fromHtml("<b>Tus resultados son</b><br><br> <b>Dominante:</b> " + arregloresultado[0] + " = " + arregloresultado[3] + "<br>" + "<b>Subdominante:</b> " + arregloresultado[1] + " = " + arregloresultado[4] + "<br>" + "<b>Apoyo:</b> " + arregloresultado[2] + " = " + arregloresultado[5]));
            holder.txtdanger.setVisibility(View.VISIBLE);
            holder.txtdanger.setText(context.getString(R.string.tecnicadesproporcionado));
            mentevalida = false;
        } else
            holder.txtxdescripciontecnica.setText(txtdominante.equals(context.getString(R.string.circulo)) ? context.getString(R.string.tecnicacirculo) : (txtdominante.equals(context.getString(R.string.cuadrado)) ? context.getString(R.string.tecnicacuadrado) : context.getString(R.string.tecnicatriangulo)));

        holder.desplazar1.setVisibility(mentevalida ? View.VISIBLE : View.GONE);
        holder.desplazar2.setVisibility(mentevalida ? View.VISIBLE : View.GONE);
        holder.danger1.setVisibility(mentevalida ? View.GONE : View.VISIBLE);
        holder.danger2.setVisibility(mentevalida ? View.GONE : View.VISIBLE);
        holder.tableEscala.setVisibility(mentevalida ? View.VISIBLE : View.GONE);
        holder.desproporcionado.setVisibility(mentevalida ? View.GONE : View.VISIBLE);
        holder.escala.setText(mentevalida ? escala : context.getString(R.string.shvalordefault));
        holder.txtdescripcion.setText(mensaje(txtdominante));
        holder.animationResultDominante.setAnimation(txtdominante.equals(context.getString(R.string.triangulo)) ? R.raw.moradotriangle : (txtdominante.equals(context.getString(R.string.cuadrado)) ? R.raw.resultsquare : R.raw.resultcircle));
        holder.cerebroResultDominante.setImageResource(txtdominante.equals(context.getString(R.string.triangulo)) ? R.drawable.braincentral : (txtdominante.equals(context.getString(R.string.cuadrado)) ? R.drawable.brainleft : R.drawable.brainright));
        holder.txtobservacion.setText(calcularEscala(Integer.parseInt(arregloresultado[7])));
        holder.ivSubdominante.setBackgroundResource(txtsubdominante.equals(context.getString(R.string.triangulo)) ? R.drawable.triangle : (txtsubdominante.equals(context.getString(R.string.cuadrado)) ? R.drawable.square : R.drawable.circle));
        holder.ivApoyo.setBackgroundResource(txtapoyo.equals(context.getString(R.string.triangulo)) ? R.drawable.triangle : (txtapoyo.equals(context.getString(R.string.cuadrado)) ? R.drawable.square : R.drawable.circle));
        holder.txtResultDominante.setText(txtdominante.equals(context.getString(R.string.triangulo)) ? context.getString(R.string.descripciontriangulo) : (txtdominante.equals(context.getString(R.string.cuadrado)) ? context.getString(R.string.descripcioncuadrado) : context.getString(R.string.descripcioncirculo)));
        holder.ivDominante.setBackgroundResource(txtdominante.equals(context.getString(R.string.circulo)) ? R.drawable.circle : (txtdominante.equals(context.getString(R.string.cuadrado)) ? R.drawable.square : (txtdominante.equals(context.getString(R.string.triangulo)) ? R.drawable.triangle : R.drawable.confused)));
    }

    public String calcularEscala(int puntaje) {
        if (puntaje >= 9 && puntaje <= 27) {
            escala = context.getString(R.string.escala1);
            return context.getString(R.string.escala1descrip);
        } else if (puntaje >= 28 && puntaje <= 35) {
            escala = context.getString(R.string.escala2);
            return context.getString(R.string.escala2descrip);
        } else if (puntaje >= 36 && puntaje < 45) {
            escala = context.getString(R.string.escala3);
            return context.getString(R.string.escala3descrip);
        } else {
            escala = context.getString(R.string.escala4);
            return context.getString(R.string.escala4descrip);
        }
    }

    public Spanned mensaje(String mente) {
        if (!mentevalida)
            return Html.fromHtml("Hola " + nombre + ",<b>te invitamos a resolver nuevamente el cuestionario ya que tu tipo de mente es desproporcional, y te explicamos el por que.</b>");
        else
            return Html.fromHtml("Hola " + nombre + ", felicitaciones obtuviste un puntaje de <b>" + arregloresultado[7] + "</b>, tu mente dominante es el <b>" + mente + "</b>, eres una persona con las siguientes caracteristicas.");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtDominante;
        private final TextView txtSubDominate;
        private final TextView txtApoyo;
        private final TextView txtPrgress;
        private final TextView txtPorcenDominandte;
        private final TextView txtPorcenSubdominante;
        private final TextView txtPorcenApoyo;
        private final TextView txtdescripcion;
        private final TextView txtxdescripciontecnica;
        private final TextView txtobservacion;
        private final TextView txtdanger;
        private final TextView escala;
        private final TextView txtResultDominante;
        private final TextView txtResultSubDominante;
        private final ImageView ivDominante;
        private final ImageView ivSubdominante;
        private final ImageView ivApoyo;
        private final ImageView cerebroResultDominante;
        private final ImageView cerebroResultSubDominante;
        private final ImageView cerebroResultSubDominante2;
        private final ProgressBar progressBar;
        private final LinearLayout linearContenedor1;
        private final LinearLayout linearContenedor2;
        private final LinearLayout linearContenedor3;
        private final LinearLayout linearContenedor4;
        private final LottieAnimationView desplazar1;
        private final LottieAnimationView desplazar2;
        private final LottieAnimationView danger1;
        private final LottieAnimationView danger2;
        private final LottieAnimationView desproporcionado;
        private final LottieAnimationView animationResultDominante;
        private final TableLayout tableEscala;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDominante = itemView.findViewById(R.id.txtDomiante);
            txtSubDominate = itemView.findViewById(R.id.txtSubDominante);
            txtApoyo = itemView.findViewById(R.id.txtApoyo);
            ivDominante = itemView.findViewById(R.id.iconDominante);
            ivSubdominante = itemView.findViewById(R.id.iconSubDominante);
            ivApoyo = itemView.findViewById(R.id.iconApoyo);
            progressBar = itemView.findViewById(R.id.progress_bar);
            txtPrgress = itemView.findViewById(R.id.txt_progress);
            txtPorcenDominandte = itemView.findViewById(R.id.txtPorcentajeDominante);
            txtPorcenSubdominante = itemView.findViewById(R.id.txtPorcentajeSubDominante);
            txtPorcenApoyo = itemView.findViewById(R.id.txtPorcentajeApoyo);
            linearContenedor1 = itemView.findViewById(R.id.linearContenedor1);
            linearContenedor2 = itemView.findViewById(R.id.linearcontenedor2);
            linearContenedor3 = itemView.findViewById(R.id.linearcontenedor3);
            linearContenedor4 = itemView.findViewById(R.id.linearcontenedor4);
            txtdescripcion = itemView.findViewById(R.id.txtdescripcion);
            txtxdescripciontecnica = itemView.findViewById(R.id.txtxdescripciontecnica);
            txtobservacion = itemView.findViewById(R.id.txtobservacion);
            desplazar1 = itemView.findViewById(R.id.lotieDesplazar1);
            desplazar2 = itemView.findViewById(R.id.lotieDesplazar2);
            danger1 = itemView.findViewById(R.id.lotieDanger1);
            danger2 = itemView.findViewById(R.id.lotieDanger2);
            tableEscala = itemView.findViewById(R.id.tableEscala);
            txtdanger = itemView.findViewById(R.id.txtdanger);
            desproporcionado = itemView.findViewById(R.id.lotiedesproporcionado);
            escala = itemView.findViewById(R.id.escala);
            animationResultDominante = itemView.findViewById(R.id.animationResultDominante);
            cerebroResultDominante = itemView.findViewById(R.id.cerebroResultDominante);
            txtResultDominante = itemView.findViewById(R.id.txtResultDominante);
            cerebroResultSubDominante = itemView.findViewById(R.id.cerebroResultSubDominante);
            txtResultSubDominante = itemView.findViewById(R.id.txtResultSubDominante);
            cerebroResultSubDominante2 = itemView.findViewById(R.id.cerebroResultSubDominante2);
        }
    }
}
