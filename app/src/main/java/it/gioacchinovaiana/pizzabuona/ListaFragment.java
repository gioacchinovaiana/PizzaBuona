package it.gioacchinovaiana.pizzabuona;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import it.gioacchinovaiana.pizzabuona.data.PizzaBuonaContratc;

/**
 * Created by Gioacchino on 03/04/2015.
 */
public class ListaFragment  extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    //private static Cursor oldCursor;
    private static final int FORECAST_LOADER = 0;
    private String selectionFilter;
    private String[] selectionFilterArgs;
    String[] tempSelectionArgs = new String[10];
    int temp_index_sel_args=0;

    private final static String FILTER_FARINE_KEY = "filterFarine";
    private final static String FILTER_LIEVITOM_KEY = "filterLievitoM";
    private final static String FILTER_BOCCONE_KEY = "filterBoccone";
    private final static String FILTER_FILONE_KEY = "filterFilone";
    private final static String FILTER_FORNOELETTRICO_KEY = "filterFornoElettrico";
    private final static String FILTER_FORNOGAS_KEY = "filterFornoGas";
    private final static String FILTER_FORNOLEGNA_KEY = "filterFornoLegna";
    private final static String FILTER_BIRREART_KEY = "filterBirreart";
    private final static String FILTER_CELIACI_KEY = "filterCeliaci";
    private final static String PROV_PALERMO_KEY = "provinciaPalermo";
    private final static String PROV_CATANIA_KEY = "provinciaCatania";
    private final static String PROV_MESSINA_KEY = "provinciaMessina";

    private static final String[] FORECAST_COLUMNS  = {
            PizzaBuonaContratc.PizzerieEntry.TABLE_NAME + "." + PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE,
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
    private RatingBar ratingBar;
    private LayerDrawable stars;
    private boolean mDualPane;

    private PizzerieCursorAdapter mForecastAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
       // setDataFragment(oldCursor);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
 //       inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The CursorAdapter will take data from our cursor and populate the ListView.
        mForecastAdapter = new PizzerieCursorAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_lista, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);

        // We'll call our MainActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    if(mDualPane){
                        setDataFragment(cursor);
                       /* PizzeriaFragment details = (PizzeriaFragment) getFragmentManager().findFragmentById(R.id.pizzabuona_detail_container);
                        details.setDataPizzeria(cursor.getString(COL_NOME_PIZZERIA),cursor.getString(COL_INDIRIZZO),cursor.getString(COL_TEL1),cursor.getString(COL_TEL2),cursor.getString(COL_EMAIL),cursor.getFloat(COL_VALUTAZIONE),cursor.getString(COL_PIZZAIOLO),
                                cursor.getString(COL_FORNO),cursor.getString(COL_CELIACI),cursor.getString(COL_FILONE),cursor.getString(COL_FARINEPIETRA),cursor.getString(COL_BOCCONE),cursor.getString(COL_LIEVMADR),cursor.getString(COL_BIRREART),
                                cursor.getString(COL_NUMIMP),cursor.getString(COL_CHIUSURA),cursor.getString(COL_CONTO),cursor.getString(COL_DATA_RECENSIONE),cursor.getString(COL_LINK_ARTICOLO));
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.pizzabuona_detail_container, details);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();*/
                    }else {
                        //Uri myury = PizzaBuonaContratc.PizzerieEntry.buildUri(cursor.getLong(COL_ID_PIZZERIE));
                        Intent intent = new Intent(getActivity(), PizzeriaActivity.class)
                                .setData(PizzaBuonaContratc.PizzerieEntry.buildUri(cursor.getLong(COL_ID_PIZZERIE)));
                        startActivity(intent);
                    }
                }
            }
        });
        return rootView;
    }

    private void setDataFragment(Cursor cursor){
        PizzeriaFragment details = (PizzeriaFragment) getFragmentManager().findFragmentById(R.id.pizzabuona_detail_container);
        String indirizzo = cursor.getString(COL_INDIRIZZO)+", "+cursor.getString(COL_CITTA)+", "+cursor.getString(COL_PROV);
        details.setDataPizzeria(cursor.getString(COL_NOME_PIZZERIA),indirizzo,cursor.getString(COL_TEL1),cursor.getString(COL_TEL2),cursor.getFloat(COL_VALUTAZIONE),cursor.getString(COL_PIZZAIOLO),
                cursor.getString(COL_FORNO),cursor.getString(COL_CELIACI),cursor.getString(COL_FILONE),cursor.getString(COL_FARINEPIETRA),cursor.getString(COL_BOCCONE),cursor.getString(COL_LIEVMADR),cursor.getString(COL_BIRREART),
                cursor.getString(COL_NUMIMP),cursor.getString(COL_CHIUSURA),cursor.getString(COL_CONTO),cursor.getString(COL_DATA_RECENSIONE),cursor.getString(COL_DATA_RIVALUTAZIONE),cursor.getString(COL_LINK_ARTICOLO),
                cursor.getString(COL_TITOLARE),cursor.getString(COL_COORD1),cursor.getString(COL_COORD2));
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.pizzabuona_detail_container, details);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(FORECAST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
        View detailsFrame = getActivity().findViewById(R.id.pizzabuona_detail_container);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        selectionFilters();
        String sortOrder =PizzaBuonaContratc.PizzerieEntry.NOME_PIZZERIA + " ASC";
        Uri pizzerieUri = PizzaBuonaContratc.PizzerieEntry.buildElencoPizzerieUri();
        CursorLoader  cursorLoader = new CursorLoader(getActivity(),
                pizzerieUri,
                FORECAST_COLUMNS,
                selectionFilter,
                selectionFilterArgs,
                sortOrder);
        //cursorLoader.deliverResult(oldCursor);
        return cursorLoader;
    }

    private String selectionProv(){
        String selectionProv="";
        boolean first_prov=true;
        temp_index_sel_args = 0;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(super.getActivity());
        String palermo = pref.getString(PROV_PALERMO_KEY,"0");
        if (palermo.equals("1")){
            if(first_prov){
                selectionProv+="(";
                first_prov=false;
            }
            selectionProv += PizzaBuonaContratc.PizzerieEntry.PROV+"=? ";
            tempSelectionArgs[temp_index_sel_args]= "PA";
            temp_index_sel_args++;
        }
        String catania = pref.getString(PROV_CATANIA_KEY,"0");
        if (catania.equals("1")){
            if(first_prov){
                selectionProv+="(";
                first_prov=false;
            }else{
                selectionProv +=" OR ";
            }
            selectionProv += PizzaBuonaContratc.PizzerieEntry.PROV+"=? ";
            tempSelectionArgs[temp_index_sel_args]="CT";
            temp_index_sel_args++;
        }
        String messina = pref.getString(PROV_MESSINA_KEY,"0");
        if (messina.equals("1")){
            if(first_prov){
                selectionProv+="(";
                first_prov=false;
            }else{
                selectionProv +=" OR ";
            }
            selectionProv += PizzaBuonaContratc.PizzerieEntry.PROV+"=? ";
            tempSelectionArgs[temp_index_sel_args]="ME";
            temp_index_sel_args++;
        }
        if(!first_prov){selectionProv+=") ";}
        return selectionProv;
    }

    private String selectionForno(){
        String selectionForno="";
        boolean first_forno=true;
        temp_index_sel_args = 0;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(super.getActivity());
        String elettrico = pref.getString(FILTER_FORNOELETTRICO_KEY,"0");
        if (elettrico.equals("1")){
            if(first_forno){
                selectionForno+="(";
                first_forno=false;
            }
            selectionForno += PizzaBuonaContratc.PizzerieEntry.FORNO+"=? ";
            tempSelectionArgs[temp_index_sel_args]= "Elettrico";
            temp_index_sel_args++;
        }
        String gas = pref.getString(FILTER_FORNOGAS_KEY,"0");
        if (gas.equals("1")){
            if(first_forno){
                selectionForno+="(";
                first_forno=false;
            }else{
                selectionForno +=" OR ";
            }
            selectionForno += PizzaBuonaContratc.PizzerieEntry.FORNO+"=? ";
            tempSelectionArgs[temp_index_sel_args]="Gas";
            temp_index_sel_args++;
        }
        String legna = pref.getString(FILTER_FORNOLEGNA_KEY,"0");
        if (legna.equals("1")){
            if(first_forno){
                selectionForno+="(";
                first_forno=false;
            }else{
                selectionForno +=" OR ";
            }
            selectionForno += PizzaBuonaContratc.PizzerieEntry.FORNO+"=? ";
            tempSelectionArgs[temp_index_sel_args]="Legna";
            temp_index_sel_args++;
        }
        if(!first_forno){selectionForno+=") ";}
        return selectionForno;
    }

    private String selectionPizza(){
        String selectionPizza="";
        boolean first_filter_pizza=true;
        temp_index_sel_args = 0;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(super.getActivity());
        String farine = pref.getString(FILTER_FARINE_KEY,"0");
        if (farine.equals("1")){
            if(first_filter_pizza){
                selectionPizza+="(";
                first_filter_pizza=false;
            }
            selectionPizza += PizzaBuonaContratc.PizzerieEntry.FARINEPIETRA+"=? ";
            tempSelectionArgs[temp_index_sel_args]= "Si";
            temp_index_sel_args++;
        }

        String lievito_madre = pref.getString(FILTER_LIEVITOM_KEY,"0");
        if (lievito_madre.equals("1")){
            if(first_filter_pizza){
                selectionPizza+="(";
                first_filter_pizza=false;
            }else{
                selectionPizza +=" OR ";
            }
            selectionPizza += PizzaBuonaContratc.PizzerieEntry.LIEVMADR+"=? ";
            tempSelectionArgs[temp_index_sel_args]="Si";
            temp_index_sel_args++;
        }

        String boccone = pref.getString(FILTER_BOCCONE_KEY,"0");
        if (boccone.equals("1")){
            if(first_filter_pizza){
                selectionPizza+="(";
                first_filter_pizza=false;
            }else{
                selectionPizza +=" OR ";
            }
            selectionPizza += PizzaBuonaContratc.PizzerieEntry.BOCCONE+"=? ";
            tempSelectionArgs[temp_index_sel_args]="Si";
            temp_index_sel_args++;
        }

        String filone = pref.getString(FILTER_FILONE_KEY,"0");
        if (filone.equals("1")){
            if(first_filter_pizza){
                selectionPizza+="(";
                first_filter_pizza=false;
            }else{
                selectionPizza +=" OR ";
            }
            selectionPizza += PizzaBuonaContratc.PizzerieEntry.FILONE+"=? ";
            tempSelectionArgs[temp_index_sel_args]="Si";
            temp_index_sel_args++;
        }
        if(!first_filter_pizza){selectionPizza+=") ";}
        return selectionPizza;
    }

    private void selectionFilters(){
        selectionFilter="";
        boolean first_block= true;
        String tempSelection="";
        String[] tempSelectionArgsTotal = new String[20];
        int total_index=0;
        int old_total_index=0;

        tempSelection=selectionProv();
        if(tempSelection.length()>0) {
            first_block=false;
            selectionFilter += tempSelection;
            int temp_index_sel_prov = temp_index_sel_args;
            old_total_index = total_index;
            total_index += temp_index_sel_prov;
            for (int i = old_total_index, j = 0; i < total_index; i++, j++) {
                tempSelectionArgsTotal[i] = tempSelectionArgs[j];
            }
        }

        tempSelection=selectionForno();
        if(tempSelection.length()>0) {
            if(!first_block){selectionFilter+=" AND ";}
            first_block=false;
            selectionFilter += tempSelection;
            int temp_index_sel_forno = temp_index_sel_args;
            old_total_index = total_index;
            total_index += temp_index_sel_forno;
            for (int i = old_total_index, j = 0; i < total_index; i++, j++) {
                tempSelectionArgsTotal[i] = tempSelectionArgs[j];
            }
        }
        tempSelection = selectionPizza();
        if(tempSelection.length()>0) {
            if (!first_block) {selectionFilter += " AND ";}
            first_block = false;
            selectionFilter += tempSelection;
            int temp_index_sel_pizza = temp_index_sel_args;
            old_total_index = total_index;
            total_index += temp_index_sel_pizza;
            for (int i = old_total_index, j = 0; i < total_index; i++, j++) {
                tempSelectionArgsTotal[i] = tempSelectionArgs[j];
            }
        }


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(super.getActivity());

        String celiaci = pref.getString(FILTER_CELIACI_KEY,"0");
        if(celiaci.equals("1")){
            if (!first_block) {selectionFilter += " AND ";}
            first_block = false;
            selectionFilter +=  "(" + PizzaBuonaContratc.PizzerieEntry.CELIACI + "=? )";
            tempSelectionArgsTotal[total_index]="Si";
            total_index++;
        }

        String birreart = pref.getString(FILTER_BIRREART_KEY,"0");
        if(birreart.equals("1")){
            if (!first_block) {selectionFilter += " AND ";}
            first_block = false;
            selectionFilter +=  "(" + PizzaBuonaContratc.PizzerieEntry.BIRREART + "=? )";
            tempSelectionArgsTotal[total_index]="Si";
            total_index++;
        }
        selectionFilterArgs = new String[total_index];
        for(int i =0; i<total_index;i++){
            selectionFilterArgs[i]=tempSelectionArgsTotal[i];
        }


    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mForecastAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mForecastAdapter.swapCursor(null);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //oldCursor = mForecastAdapter.swapCursor(null);
    }


}


