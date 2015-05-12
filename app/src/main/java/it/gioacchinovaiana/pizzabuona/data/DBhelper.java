package it.gioacchinovaiana.pizzabuona.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gioacchino on 23/03/2015.
 */
public class DBhelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 3;

    static final String DBNAME = "pizzabuona.db";


    public DBhelper(Context context) {

        super(context, DBNAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String SQL_CREATE_PIZZERIE_TABLE = "CREATE TABLE " + PizzaBuonaContratc.PizzerieEntry.TABLE_NAME + " ( " +
                PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PizzaBuonaContratc.PizzerieEntry.NOME_PIZZERIA + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.TITOLARE + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.PIZZAIOLO + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.INDIRIZZO + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.CAP + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.CITTA + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.PROV + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.COORD1+ " REAL NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.COORD2 + " REAL NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.TEL1 + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.TEL2 + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.EMAIL + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.CHIUSURA + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.FORNO + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.CELIACI + " INTEGER NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.NUMIMP + " INTEGER NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.LIEVMADR + " INTEGER NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.BIRREART + " INTEGER NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.FARINEPIETRA + " INTEGER NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.FILONE + " INTEGER NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.BOCCONE + " INTEGER NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.CONTO + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.DATA_RECENSIONE + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.DATA_RIVALUTAZIONE + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.LINK_ARTICOLO + " TEXT NOT NULL, " +
                PizzaBuonaContratc.PizzerieEntry.VALUTAZIONE + " INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_PIZZERIE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + PizzaBuonaContratc.PizzerieEntry.TABLE_NAME);

        onCreate(db);
    }
}