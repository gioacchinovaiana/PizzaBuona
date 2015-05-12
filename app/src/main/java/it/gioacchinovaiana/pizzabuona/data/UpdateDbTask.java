package it.gioacchinovaiana.pizzabuona.data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateDbTask extends AsyncTask<Void, Void, String>
{
    private Activity activity;
    private ProgressDialog pd;
    Integer f_update;
    Integer n_row;
    private final static String TEXT_DATA_KEY = "updateDate";
    private final static String TEXT_DATA_DB_KEY = "updateDbDate";

    //String address="http://www.gioacchinovaiana.it/artusi/pizzerie.php";
    String address="http://www.cucinartusi.it/martusi/pizzerie.json";
    String addressLastUpdate="http://www.cucinartusi.it/martusi/pizzerie_aggiornamento.json";

    public UpdateDbTask(Activity activity,Integer force_update,Integer num_row) {
        this.activity = activity;
        f_update = force_update;
        n_row =num_row;
        pd = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.setMessage("Wait...");
        pd.setCancelable(false);
        if(!pd.isShowing())pd.show();
    }

    @Override
    protected String doInBackground(Void... arg0)
    {
        DownloadElencoPizzerie downloader = new DownloadElencoPizzerie();
        try {
            String data_aggiornamento_db =downloader.download_data_aggirnamento(addressLastUpdate);
            try {
                //data_aggiornamento_db="{'ultimo_aggiornamento':'17-04-2015'}";//for debug
                JSONObject array_data_agg = new JSONObject(data_aggiornamento_db);
                //JSONObject data_update= array_data_agg.getJSONObject(0);
                String db_data_update = array_data_agg.getString("ultimo_aggiornamento");
                String my_db_data_update = getLastUpdateDbDate();
                SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
                Date dateLastUpdate = null;
                Date dateLastDbUpdate = null;
                try {
                    dateLastUpdate = curFormater.parse(db_data_update);
                    dateLastDbUpdate = curFormater.parse(my_db_data_update);
                    if(dateLastUpdate.after(dateLastDbUpdate)||(f_update==1)||(n_row==0)){
                        return downloader.download_elenco(address);
                    }else{
                        saveUpdateDate();
                        return "error";
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    return "error";
                }
            }catch(Exception e){return "Unable to retrieve web page. URL may be invalid.";}
        } catch (IOException e) {return "Unable to retrieve web page. URL may be invalid.";
        }

       // return "error";
    }


    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        try{
            pd.dismiss();
            if(result!=null&&result!="error"){
                Log.d("miojson",result);
                JSONArray array_pizzerie = new JSONArray(result);
                JSONObject dati_pizzeria;
                ContentResolver resolver_pizzeria= activity.getContentResolver();

                if(array_pizzerie.length()>0){
                    resolver_pizzeria.delete(PizzaBuonaContratc.PizzerieEntry.CONTENT_URI,null,null);
                    final String [] projection = {
                    };
                    for(int i=0;i<array_pizzerie.length();i++){
                        dati_pizzeria = array_pizzerie.getJSONObject(i);
                        ContentValues values = new ContentValues();
                        values.put(PizzaBuonaContratc.PizzerieEntry.NOME_PIZZERIA, dati_pizzeria.getString("nome"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.TITOLARE, dati_pizzeria.getString("titolare"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.INDIRIZZO, dati_pizzeria.getString("indirizzo"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.CAP, dati_pizzeria.getString("cap"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.CITTA, dati_pizzeria.getString("citta"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.PROV, dati_pizzeria.getString("provincia"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.COORD1, dati_pizzeria.getString("coordinate1"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.COORD2, dati_pizzeria.getString("coordinate2"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.TEL1, dati_pizzeria.getString("telefono1"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.TEL2, dati_pizzeria.getString("telefono2"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.EMAIL, dati_pizzeria.getString("email"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.FORNO, dati_pizzeria.getString("tipo_forno"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.CHIUSURA, dati_pizzeria.getString("giorno_chiusura"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.CELIACI,dati_pizzeria.getString("celiaci"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.NUMIMP, dati_pizzeria.getString("nro_impasti"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.LIEVMADR, dati_pizzeria.getString("lievito_madre"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.BIRREART, dati_pizzeria.getString("birre_artigianali"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.FARINEPIETRA, dati_pizzeria.getString("farine_a_pietra"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.FILONE, dati_pizzeria.getString("mozzarella_filone"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.BOCCONE, dati_pizzeria.getString("mozzarella_boccone"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.PIZZAIOLO, dati_pizzeria.getString("pizzaiolo"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.CONTO, dati_pizzeria.getString("conto"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.DATA_RECENSIONE, dati_pizzeria.getString("data_recensione"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.DATA_RIVALUTAZIONE, dati_pizzeria.getString("data_rivalutazione"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.LINK_ARTICOLO, dati_pizzeria.getString("link_recensione"));
                        values.put(PizzaBuonaContratc.PizzerieEntry.VALUTAZIONE, dati_pizzeria.getString("valutazione"));
                        resolver_pizzeria.insert(PizzaBuonaContratc.PizzerieEntry.CONTENT_URI,values);
                    }
                    saveUpdateDate();
                    saveUpdateDbDate();
                }
            }

        }catch(Exception e){
            Log.d("error",e.getMessage());
        }
        //updateLayout(result);
    }

    private void saveUpdateDate() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.activity);
        SharedPreferences.Editor editor = pref.edit();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        editor.putString(TEXT_DATA_KEY, formattedDate);
        editor.commit();
    }

    private void saveUpdateDbDate() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.activity);
        SharedPreferences.Editor editor = pref.edit();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        editor.putString(TEXT_DATA_DB_KEY, formattedDate);
        editor.commit();
    }

    private String getLastUpdateDbDate(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.activity);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        String string_date = pref.getString(TEXT_DATA_DB_KEY,formattedDate);
        return string_date;
    }


}