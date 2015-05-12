package it.gioacchinovaiana.pizzabuona;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import it.gioacchinovaiana.pizzabuona.data.PizzaBuonaContratc;

/**
 * Created by Gioacchino on 26/03/2015.
 */
public class PizzerieCursorAdapter extends CursorAdapter {

    public PizzerieCursorAdapter(Context context, Cursor c,int flags)
    {
        super(context, c,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate( R.layout.activity_elemento_lista_pizzerie, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

        ((TextView) view.findViewById(R.id.nome_pizzeria)).setText(
                cursor.getString(cursor.getColumnIndex(PizzaBuonaContratc.PizzerieEntry.NOME_PIZZERIA)));
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_pizzeria_lista);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ratingBar.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(PizzaBuonaContratc.PizzerieEntry.VALUTAZIONE))));

        ((TextView) view.findViewById(R.id.indirizzo_pizzeria)).setText(
                cursor.getString(cursor.getColumnIndex(PizzaBuonaContratc.PizzerieEntry.CITTA))+", "+cursor.getString(cursor.getColumnIndex(PizzaBuonaContratc.PizzerieEntry.INDIRIZZO)));
    }
}
