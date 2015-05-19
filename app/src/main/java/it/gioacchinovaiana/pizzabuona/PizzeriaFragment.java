package it.gioacchinovaiana.pizzabuona;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.ShareActionProvider;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import it.gioacchinovaiana.pizzabuona.data.PizzaBuonaContratc;

/**
 * Created by Gioacchino on 04/04/2015.
 */
public class PizzeriaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

        private static final String LOG_TAG = PizzeriaFragment.class.getSimpleName();

        private static final String FORECAST_SHARE_HASHTAG = " #PizzaBuonaApp";

        private ShareActionProvider mShareActionProvider;
        private String mForecast;

        private static final int DETAIL_LOADER = 1;

        private static final String[] DETAIL_COLUMNS = {
                PizzaBuonaContratc.PizzerieEntry.TABLE_NAME + "." + PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE,
                //PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE,
                PizzaBuonaContratc.PizzerieEntry.NOME_PIZZERIA,
                PizzaBuonaContratc.PizzerieEntry.TITOLARE,
                PizzaBuonaContratc.PizzerieEntry.INDIRIZZO,
                PizzaBuonaContratc.PizzerieEntry.CAP,
                PizzaBuonaContratc.PizzerieEntry.CITTA,
                PizzaBuonaContratc.PizzerieEntry.PROV,
                PizzaBuonaContratc.PizzerieEntry.COORD1,
                PizzaBuonaContratc.PizzerieEntry.COORD2,
                PizzaBuonaContratc.PizzerieEntry.TEL1,
                PizzaBuonaContratc.PizzerieEntry.TEL2,
                PizzaBuonaContratc.PizzerieEntry.EMAIL,
                PizzaBuonaContratc.PizzerieEntry.FORNO,
                PizzaBuonaContratc.PizzerieEntry.CHIUSURA,
                PizzaBuonaContratc.PizzerieEntry.CELIACI,
                PizzaBuonaContratc.PizzerieEntry.NUMIMP,
                PizzaBuonaContratc.PizzerieEntry.LIEVMADR,
                PizzaBuonaContratc.PizzerieEntry.BIRREART,
                PizzaBuonaContratc.PizzerieEntry.FARINEPIETRA,
                PizzaBuonaContratc.PizzerieEntry.FILONE,
                PizzaBuonaContratc.PizzerieEntry.BOCCONE,
                PizzaBuonaContratc.PizzerieEntry.PIZZAIOLO,
                PizzaBuonaContratc.PizzerieEntry.CONTO,
                PizzaBuonaContratc.PizzerieEntry.DATA_RECENSIONE,
                PizzaBuonaContratc.PizzerieEntry.DATA_RIVALUTAZIONE,
                PizzaBuonaContratc.PizzerieEntry.LINK_ARTICOLO,
                PizzaBuonaContratc.PizzerieEntry.VALUTAZIONE
        };

        // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
        // must change.
        public static final int COL_ID_PIZZERIE =0;
        public static final int COL_NOME_PIZZERIA = 1;
        public static final int COL_TITOLARE = 2;
        public static final int COL_INDIRIZZO = 3;
        public static final int COL_CAP = 4;
        public static final int COL_CITTA = 5;
        public static final int COL_PROV = 6;
        public static final int COL_COORD1 = 7;
        public static final int COL_COORD2 = 8;
        public static final int COL_TEL1 = 9;
        public static final int COL_TEL2 = 10;
        public static final int COL_EMAIL = 11;//
        public static final int COL_FORNO = 12;
        public static final int COL_CHIUSURA = 13;
        public static final int COL_CELIACI = 14;
        public static final int COL_NUMIMP = 15;
        public static final int COL_LIEVMADR = 16;
        public static final int COL_BIRREART = 17;
        public static final int COL_FARINEPIETRA = 18;
        public static final int COL_FILONE = 19;
        public static final int COL_BOCCONE = 20;
        public static final int COL_PIZZAIOLO = 21;
        public static final int COL_CONTO = 22;
        public static final int COL_DATA_RECENSIONE = 23;
        public static final int COL_DATA_RIVALUTAZIONE = 24;
        public static final int COL_LINK_ARTICOLO = 25;
        public static final int COL_VALUTAZIONE = 26;


        private TextView textView_nome_pizzeria;
        private TextView textView_indirizzo_pizzeria;
        private TextView textView_t1;
        private TextView textView_t2;
        private RatingBar ratingBar;
        private LayerDrawable stars;
        private TextView textView_pizzaiolo;
        private TextView textView_proprietario;
        private TextView textView_forno;
        private CheckBox check_celiaci;
        private CheckBox check_filone;
        private CheckBox check_farine_pietra;
        private CheckBox check_boccone;
        private CheckBox check_lievito_madre;
        private CheckBox check_birre_art;
        private TextView textView_num_impasti;
        private TextView textView_chiusura;
        private TextView textView_conto;
        private TextView textView_recensione;
        private TextView textView_rivalutazione;
        private Button button_chiama_tel1;
        private Button button_chiama_tel2;
        private ImageView image_recensione;
        private ImageView image_map;

        public PizzeriaFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dettaglio_pizzeria, container, false);

            textView_nome_pizzeria= (TextView)rootView.findViewById(R.id.textView_nome_pizzeria);
            textView_indirizzo_pizzeria= (TextView)rootView.findViewById(R.id.textView_indirizzo);
            textView_t1 = (TextView)rootView.findViewById(R.id.textView_tel1);
            textView_t2 = (TextView)rootView.findViewById(R.id.textView_tel2);

            ratingBar = (RatingBar) rootView.findViewById(R.id.rating_pizzeria);
            stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(0).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            textView_pizzaiolo= (TextView)rootView.findViewById(R.id.text_pizzaiolo);
            textView_proprietario = (TextView)rootView.findViewById(R.id.text_proprietario);
            textView_forno= (TextView)rootView.findViewById(R.id.text_forno);
            check_celiaci =(CheckBox) rootView.findViewById(R.id.checkBox_celiaci);
            check_filone =(CheckBox) rootView.findViewById(R.id.checkBox_filone);
            check_farine_pietra =(CheckBox) rootView.findViewById(R.id.checkBox_farine_pietra);
            check_boccone =(CheckBox) rootView.findViewById(R.id.checkBox_boccone);
            check_lievito_madre =(CheckBox) rootView.findViewById(R.id.checkBox_lievito_madre);
            check_birre_art = (CheckBox) rootView.findViewById(R.id.checkBox_birre_art);
            textView_num_impasti= (TextView)rootView.findViewById(R.id.textView_num_impasti);
            textView_chiusura= (TextView)rootView.findViewById(R.id.textView_chiusura);
            textView_conto= (TextView)rootView.findViewById(R.id.textView_conto);
            textView_recensione= (TextView)rootView.findViewById(R.id.textView_recensione);
            textView_rivalutazione= (TextView)rootView.findViewById(R.id.textView_rivalutazione);
            button_chiama_tel1 = (Button)rootView.findViewById(R.id.textView_tel1);
            button_chiama_tel2 = (Button)rootView.findViewById(R.id.textView_tel2);
            image_recensione = (ImageView)rootView.findViewById(R.id.imageViewSite);
            image_map = (ImageView)rootView.findViewById(R.id.imageViewMap);
            return rootView;
        }

        private Intent createShareForecastIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mForecast + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            //Log.v(LOG_TAG, "In onCreateLoader");
            Intent intent = getActivity().getIntent();
            if (intent == null || intent.getData() == null) {
                return null;
            }

            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            CursorLoader myCursor = new CursorLoader(
                    getActivity(),
                    intent.getData(),
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
            return myCursor;
        }

        public void updateView(Uri uri){
            CursorLoader myCursor = new CursorLoader(
                    getActivity(),
                    uri,
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
            onLoadFinished(myCursor,null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {

            if (data != null && data.moveToFirst()) {
                // Read date from cursor and update views
                String indirizzo = data.getString(COL_INDIRIZZO)+", "+data.getString(COL_CITTA)+", "+data.getString(COL_PROV);

                setDataPizzeria(data.getString(COL_NOME_PIZZERIA),indirizzo,data.getString(COL_TEL1),data.getString(COL_TEL2),data.getFloat(COL_VALUTAZIONE),data.getString(COL_PIZZAIOLO),
                        data.getString(COL_FORNO),data.getString(COL_CELIACI),data.getString(COL_FILONE),data.getString(COL_FARINEPIETRA),data.getString(COL_BOCCONE),data.getString(COL_LIEVMADR),data.getString(COL_BIRREART),
                        data.getString(COL_NUMIMP),data.getString(COL_CHIUSURA),data.getString(COL_CONTO),data.getString(COL_DATA_RECENSIONE),data.getString(COL_DATA_RIVALUTAZIONE), data.getString(COL_LINK_ARTICOLO),
                        data.getString(COL_TITOLARE),data.getString(COL_COORD1),data.getString(COL_COORD2));
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) { }

        public void setDataPizzeria(final String NomePizzeria, String Indirizzo, final String T1, final String T2, Float valutazione, String pizzaiolo, String forno, String celiaci, String filone,
                            String farinepietra, String boccone, String lievitomadre, String birreart, String numimp, String chiusura, String conto, String recensione, String rivalutazione,
                            final String link, String proprietario, final String coord1, final String coord2){
            textView_nome_pizzeria.setText(NomePizzeria);
            textView_indirizzo_pizzeria.setText(Indirizzo);
            ratingBar.setRating(valutazione);

            if(pizzaiolo.length()>0){textView_pizzaiolo.setText("Pizzaiolo: "+pizzaiolo);}else{textView_pizzaiolo.setText("");}
            if(proprietario.length()>0){textView_proprietario.setText("Titolare: "+proprietario);}else{textView_proprietario.setText("");}
            String nomeGiorno = nomeGiornoSettimana(chiusura);
            if(nomeGiorno.length()>0){textView_chiusura.setText("Chiusura: "+nomeGiorno);}else{textView_chiusura.setText("");}

            textView_t1.setText(T1);
            textView_t2.setText(T2);

            if (celiaci.equals("Si")){ check_celiaci.setChecked(true);}else{check_celiaci.setChecked(false);}
            if (filone.equals("Si")){check_filone.setChecked(true);}else{check_filone.setChecked(false);}
            if (farinepietra.equals("Si")){check_farine_pietra.setChecked(true);}else{check_farine_pietra.setChecked(false);}
            if (boccone.equals("Si")){check_boccone.setChecked(true);}else{check_boccone.setChecked(false);}
            if (lievitomadre.equals("Si")) {check_lievito_madre.setChecked(true);}else{check_lievito_madre.setChecked(false);}
            if (birreart.equals("Si")) {check_birre_art.setChecked(true);}else{check_birre_art.setChecked(false);}


            if(forno.length()>0){textView_forno.setText("Forno: "+forno);}else{textView_forno.setText("");}
            if(numimp.length()>0){textView_num_impasti.setText("Numero impasti: "+numimp);}else{textView_num_impasti.setText("");}
            if(conto.length()>0){textView_conto.setText("Conto: € "+conto);}else{textView_conto.setText("");}
            if(recensione.length()>0){textView_recensione.setText("Recensione del "+recensione);}else{textView_recensione.setText("");}
            if(rivalutazione.length()>0){textView_rivalutazione.setText("Rivalutazione del " + rivalutazione);}else{textView_rivalutazione.setText("");}

 
            if(link.length()>0){
                image_recensione.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View arg0) {
                        String url = link;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
            }

            image_map.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0){
                    // Creates an Intent that will load a map
                    Uri gmmIntentUri = Uri.parse("geo:" + coord1 +","+coord2+"?q="+ coord1 +","+coord2+"("+NomePizzeria+")");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    startActivity(mapIntent);
                }
            });



        }

        private String nomeGiornoSettimana(String giorno){
            String nomeGiorno="";
            switch (giorno){
                case "Lun": nomeGiorno="Lunedi";
                    break;
                case "Mar": nomeGiorno="Martedi";
                    break;
                case "Mer": nomeGiorno="Mercoledi";
                    break;
                case "Gio": nomeGiorno="Giovedi";
                    break;
                case "Ven": nomeGiorno="Venerdi";
                    break;
                case "Sab": nomeGiorno="Sabato";
                    break;
                case "Dom": nomeGiorno="Domenica";
                    break;
            }
            return nomeGiorno;
        }
    }
