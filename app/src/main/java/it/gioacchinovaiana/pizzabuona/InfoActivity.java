package it.gioacchinovaiana.pizzabuona;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Gioacchino on 16/04/2015.
 */
public class InfoActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        TextView textViewIndirizzoArtusi= (TextView)findViewById(R.id.indirizzo_artusi);
        textViewIndirizzoArtusi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                String url = "http://www.cucinartusi.it/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        TextView textViewIndirizzoVaiana= (TextView)findViewById(R.id.indirizzo_vaiana);
        textViewIndirizzoVaiana.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                String url = "http://www.gioacchinovaiana.it/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

}