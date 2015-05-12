package it.gioacchinovaiana.pizzabuona;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by Gioacchino on 25/04/2015.
 */
public class FilterActivity  extends ActionBarActivity {
    private boolean propagation;

    private boolean click_all_filter_pizza;
    private CheckBox check_all_filter_pizza;
    private CheckBox check_farine_pietra;
    private CheckBox check_lievito_madre;
    private CheckBox check_boccone;
    private CheckBox check_filone;

    private boolean click_all_forno;
    private CheckBox check_all_filter_forno;
    private CheckBox check_forno_elettrico;
    private CheckBox check_forno_gas;
    private CheckBox check_forno_legna;

    private CheckBox check_birre_art;
    private CheckBox check_celiaci;

    private boolean click_all_prov;
    private CheckBox check_all_prov;
    private CheckBox check_palermo;
    private CheckBox check_catania;
    private CheckBox check_messina;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        propagation = true;
        //per non fare propagare i gli eventi vengono settati a true e dopo loadFilter vengono messi a false
        click_all_filter_pizza= true;
        click_all_forno = true;
        click_all_prov = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtri);
        check_all_filter_pizza =(CheckBox) findViewById(R.id.checkBoxAllFilter);
        check_farine_pietra =(CheckBox) findViewById(R.id.checkBoxFarinePietra);
        check_lievito_madre =(CheckBox) findViewById(R.id.checkBoxLievitoMadre);
        check_boccone =(CheckBox) findViewById(R.id.checkBoxBoccone);
        check_filone =(CheckBox) findViewById(R.id.checkBoxFilone);
        check_all_filter_forno = (CheckBox) findViewById(R.id.checkBoxAllForno);
        check_forno_elettrico = (CheckBox) findViewById(R.id.checkBoxFornoElettrico);
        check_forno_gas = (CheckBox) findViewById(R.id.checkBoxFornoGas);
        check_forno_legna = (CheckBox) findViewById(R.id.checkBoxFornoLegna);
        check_birre_art =(CheckBox) findViewById(R.id.checkBoxBirre);
        check_celiaci =(CheckBox) findViewById(R.id.checkBoxCeliaci);
        check_all_prov =(CheckBox) findViewById(R.id.checkBoxAllProv);
        check_palermo =(CheckBox) findViewById(R.id.checkBoxPalermo);
        check_catania =(CheckBox) findViewById(R.id.checkBoxCatania);
        check_messina =(CheckBox) findViewById(R.id.checkBoxMessina);
        setCheckbox();
        loadFilter();
        verifyAllForno();
        verifyAllProv();
        verifyAllFilterPizza();
    }

    protected void setCheckbox() {
        check_farine_pietra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_FARINE_KEY,isChecked);
                if(!click_all_filter_pizza){verifyAllFilterPizza();}
            }
        });
        check_lievito_madre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_LIEVITOM_KEY,isChecked);
                if(!click_all_filter_pizza){verifyAllFilterPizza();}
            }
        });
        check_boccone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_BOCCONE_KEY,isChecked);
                if(!click_all_filter_pizza){verifyAllFilterPizza();}
            }
        });
        check_filone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_FILONE_KEY,isChecked);
                if(!click_all_filter_pizza){verifyAllFilterPizza();}
            }
        });

        check_all_filter_pizza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                click_all_filter_pizza=true;
                if(propagation){
                    if (isChecked){
                        check_farine_pietra.setChecked(true);
                        check_lievito_madre.setChecked(true);
                        check_boccone.setChecked(true);
                        check_filone.setChecked(true);}
                    else{
                        check_farine_pietra.setChecked(false);
                        check_lievito_madre.setChecked(false);
                        check_boccone.setChecked(false);
                        check_filone.setChecked(false);
                    }
                }else{
                    propagation =true;
                }
                click_all_filter_pizza=false;
            }
        });


        check_forno_elettrico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_FORNOELETTRICO_KEY,isChecked);
                if(!click_all_forno){verifyAllForno();}
            }
        });

        check_forno_gas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_FORNOGAS_KEY,isChecked);
                if(!click_all_forno){verifyAllForno();}
            }
        });
        check_forno_legna.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_FORNOLEGNA_KEY,isChecked);
                if(!click_all_forno){verifyAllForno();}
            }
        });
        check_all_filter_forno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                click_all_forno=true;
                if(propagation) {
                    if (isChecked) {
                        check_forno_elettrico.setChecked(true);
                        check_forno_gas.setChecked(true);
                        check_forno_legna.setChecked(true);
                    } else {
                        check_forno_elettrico.setChecked(false);
                        check_forno_gas.setChecked(false);
                        check_forno_legna.setChecked(false);
                    }
                }else{
                    propagation =true;
                }
                click_all_forno=false;
            }
        });
        check_celiaci.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_CELIACI_KEY,isChecked);
            }
        });

        check_birre_art.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(FILTER_BIRREART_KEY,isChecked);
            }
        });

        check_palermo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(PROV_PALERMO_KEY,isChecked);
                if(!click_all_prov){
                    verifyAllProv();
                }
            }
        });
        check_catania.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(PROV_CATANIA_KEY,isChecked);
                if(!click_all_prov){
                    verifyAllProv();
                }
            }
        });
        check_messina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                saveFilter(PROV_MESSINA_KEY,isChecked);
                if(!click_all_prov){
                    verifyAllProv();
                }
            }
        });
        check_all_prov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                click_all_prov=true;
                if(propagation) {
                    if (isChecked) {
                        check_catania.setChecked(true);
                        check_palermo.setChecked(true);
                        check_messina.setChecked(true);
                    } else {
                        check_catania.setChecked(false);
                        check_palermo.setChecked(false);
                        check_messina.setChecked(false);
                    }
                }else{
                    propagation =true;
                }
                click_all_prov=false;
            }
        });
    }

    private void saveFilter(String checkname, boolean isChecked) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        String isCheckedString;
        if(isChecked){isCheckedString="1";}else{isCheckedString="0";}
        editor.putString(checkname, isCheckedString);
        editor.commit();
    }

    private void verifyAllProv(){
        boolean allProv=true;
        if(!check_palermo.isChecked()){allProv=false;}
        if(!check_catania.isChecked()){allProv=false;}
        if(!check_messina.isChecked()){allProv=false;}
        if (allProv){
            if(!check_all_prov.isChecked()) {
                propagation=false;
                check_all_prov.setChecked(true);
            }
        }else {
            if (check_all_prov.isChecked()) {
                propagation=false;
                check_all_prov.setChecked(false);
            }
        }
    }

    private void verifyAllForno(){
        boolean allForno=true;
        if(!check_forno_elettrico.isChecked()){allForno=false;}
        if(!check_forno_gas.isChecked()){allForno=false;}
        if(!check_forno_legna.isChecked()){allForno=false;}
        if (allForno){//se tutti i tipi di forno sono selezionati
            if(!check_all_filter_forno.isChecked()) {//se non è selezionato all forno lo segno
                propagation=false;
                check_all_filter_forno.setChecked(true);
            }
        }else{//se non tutti i tipi di forno sono selezionati
            if (check_all_filter_forno.isChecked()) {
                propagation=false;
                check_all_filter_forno.setChecked(false);
            }
        }
    }

    private void verifyAllFilterPizza(){
        boolean allFilter=true;
        if(!check_filone.isChecked()){allFilter=false;}
        if(!check_farine_pietra.isChecked()){allFilter=false;}
        if(!check_boccone.isChecked()){allFilter=false;}
        if(!check_lievito_madre.isChecked()){allFilter=false;}
        if (allFilter){
            if(!check_all_filter_pizza.isChecked()) {
                propagation = false;
                check_all_filter_pizza.setChecked(true);
            }
        }else {
            if (check_all_filter_pizza.isChecked()) {
                propagation = false;
                check_all_filter_pizza.setChecked(false);
            }
        }
    }

    private void loadFilter(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String farine_pietra = pref.getString(FILTER_FARINE_KEY,"0");
        if (farine_pietra.equals("1")){check_farine_pietra.setChecked(true);}else{check_farine_pietra.setChecked(false);}
        String lievitom = pref.getString(FILTER_LIEVITOM_KEY,"0");
        if (lievitom.equals("1")){check_lievito_madre.setChecked(true);}else{check_lievito_madre.setChecked(false);}
        String boccone = pref.getString(FILTER_BOCCONE_KEY,"0");
        if (boccone.equals("1")){check_boccone.setChecked(true);}else{check_boccone.setChecked(false);}
        String filone = pref.getString(FILTER_FILONE_KEY,"0");
        if (filone.equals("1")){check_filone.setChecked(true);}else{check_filone.setChecked(false);}

        String fornoelettrico = pref.getString(FILTER_FORNOELETTRICO_KEY,"0");
        if (fornoelettrico.equals("1")){check_forno_elettrico.setChecked(true);}else{check_forno_elettrico.setChecked(false);}
        String fornoelgas = pref.getString(FILTER_FORNOGAS_KEY,"0");
        if (fornoelgas.equals("1")){check_forno_gas.setChecked(true);}else{check_forno_gas.setChecked(false);}
        String fornolegna = pref.getString(FILTER_FORNOLEGNA_KEY,"0");
        if (fornolegna.equals("1")){check_forno_legna.setChecked(true);}else{check_forno_legna.setChecked(false);}

        String birreart = pref.getString(FILTER_BIRREART_KEY,"0");
        if (birreart.equals("1")){check_birre_art.setChecked(true);}else{check_birre_art.setChecked(false);}
        String celiaci = pref.getString(FILTER_CELIACI_KEY,"0");
        if (celiaci.equals("1")){check_celiaci.setChecked(true);}else{check_celiaci.setChecked(false);}

        String palermo = pref.getString(PROV_PALERMO_KEY,"0");
        if (palermo.equals("1")){check_palermo.setChecked(true);}else{check_palermo.setChecked(false);}
        String catania = pref.getString(PROV_CATANIA_KEY,"0");
        if (catania.equals("1")){check_catania.setChecked(true);}else{check_catania.setChecked(false);}
        String messina = pref.getString(PROV_MESSINA_KEY,"0");
        if (messina.equals("1")){check_messina.setChecked(true);}else{check_messina.setChecked(false);}
        click_all_filter_pizza= false;
        click_all_forno = false;
        click_all_prov = false;
    }
}
