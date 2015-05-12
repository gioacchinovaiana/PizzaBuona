package it.gioacchinovaiana.pizzabuona;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        Button button_elenco_pizzerie = (Button)findViewById(R.id.button_elenco);
        button_elenco_pizzerie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // definisco l'intenzione
                Intent openPageListaPizzerie = new Intent(MainActivity.this,ListaActivity.class);
                // passo all'attivazione dell'activity Pagina.java
                startActivity(openPageListaPizzerie);
            }
        });*/
        ImageView imagepizza = (ImageView) findViewById(R.id.imagepizza);
        imagepizza.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // definisco l'intenzione
                Intent openPageListaPizzerie = new Intent(MainActivity.this,ListaActivity.class);
                // passo all'attivazione dell'activity Pagina.java
                startActivity(openPageListaPizzerie);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.info) {
            startActivity(new Intent(MainActivity.this, InfoActivity.class));
            return true;
        }
        if (id == R.id.filtri) {
            startActivity(new Intent(MainActivity.this, FilterActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  /*  image_map.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View arg0){
            // Creates an Intent that will load a map
            Uri gmmIntentUri = Uri.parse("geo:" + coord1 +","+coord2+"?q="+ coord1 +","+coord2+"("+NomePizzeria+")");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            startActivity(mapIntent);
        }
    });*/

}
