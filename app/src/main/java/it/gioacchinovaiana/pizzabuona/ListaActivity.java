package it.gioacchinovaiana.pizzabuona;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.gioacchinovaiana.pizzabuona.data.PizzaBuonaContratc;
import it.gioacchinovaiana.pizzabuona.data.UpdateDbTask;


/**
 * Created by Gioacchino on 19/03/2015.
 */
public class ListaActivity extends ActionBarActivity {
    private final String LOG_TAG = ListaActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private BroadcastReceiver connectionReceiver;
    private boolean isConnected;

    // Costante relativa al nome della particolare preferenza
    private final static String TEXT_DATA_KEY = "updateDate";
    private int num_row;
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Cursor cursor = getContentResolver().query(
                PizzaBuonaContratc.PizzerieEntry.buildElencoPizzerieUri(),
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        num_row = cursor.getCount();

        updateData(0);
        if (findViewById(R.id.pizzabuona_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.pizzabuona_detail_container, new PizzeriaFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    private void saveUpdateDate() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        editor.putString(TEXT_DATA_KEY, formattedDate);
        editor.commit();
    }

    private String getLastUpdateDate(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        String string_date = pref.getString(TEXT_DATA_KEY,formattedDate);
//        string_date="14-04-2015";//for debug
        return string_date;
    }

    private void updateData(Integer force){
        //Date curDate = new Date();
        Calendar c = Calendar.getInstance();
        //Date curDate = c.getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
        String stringCurDate = curFormater.format(c.getTime());
        String lastUpdatedDate = getLastUpdateDate();

        Date dateLastUpdate = null;
        Date curDate = null;
        try {
            dateLastUpdate = curFormater.parse(lastUpdatedDate);
            Calendar mycal = Calendar.getInstance();
            mycal.setTime(dateLastUpdate);
            mycal.add(Calendar.DATE, 3);
            curFormater.setCalendar(mycal);
            String temp = curFormater.format(mycal.getTime());
            dateLastUpdate = curFormater.parse(temp);
            curDate = curFormater.parse(stringCurDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(curDate.after(dateLastUpdate)|| (num_row==0)||(force==1)){//se la data dell'ultimo aggiornameto è antecedente ad oggi
 /*           bReceiver = new UpdateBroadcastReceiver();
            IntentFilter inf = new IntentFilter("some.action");
            registerReceiver(bReceiver,inf);*/
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new UpdateDbTask(this,force,num_row).execute();
                saveUpdateDate();
            } else {
                // display error
            }

        }

    }

    private void manageConnection(){
        isConnected = checkConnection();
        connectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isConnected = checkConnection();
                invalidateOptionsMenu();
                //             NoConnectionDialog dialog =
                //                     (NoConnectionDialog)getFragmentManager().findFragmentByTag("no_connection");

                //             if(dialog!=null && isConnected){
                //                 dialog.dismiss();
                //             }

            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectionReceiver, filter);

    }

    private boolean checkConnection(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionReceiver);
        connectionReceiver = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        manageConnection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista, menu);
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
            startActivity(new Intent(this, InfoActivity.class));
            return true;
        }
        if (id == R.id.filtri) {
            startActivity(new Intent(this, FilterActivity.class));
            return true;
        }
        if (id == R.id.update) {
            this.updateData(1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
