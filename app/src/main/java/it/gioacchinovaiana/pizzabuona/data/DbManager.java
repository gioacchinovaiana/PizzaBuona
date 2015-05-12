package it.gioacchinovaiana.pizzabuona.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


/**
 * Created by Gioacchino on 24/03/2015.
 */
public class DbManager
{
    private DBhelper dbhelper;

    public DbManager(Context ctx)
    {
        dbhelper=new DBhelper(ctx);
    }

    public long save(String nome_pizzeria, String titolare, String pizzaiolo,String indirizzo, String cap, String citta, String prov, float coord1, float coord2, String tel1, String tel2,
                    String email, String chiusura, String forno, int celiaci, int num_imp, int liev_madre, int birre_art, int farine_pietra, int filone, int boccone, String conto,
                    String data_recensione, String data_rivalutazione, String link_articolo, String valutazione)
    {
        long id_pizzeria=0;

        ContentValues cv=new ContentValues();
        cv.put(PizzaBuonaContratc.PizzerieEntry.NOME_PIZZERIA , nome_pizzeria);
        cv.put(PizzaBuonaContratc.PizzerieEntry.TITOLARE , titolare);
        cv.put(PizzaBuonaContratc.PizzerieEntry.PIZZAIOLO , pizzaiolo);
        cv.put(PizzaBuonaContratc.PizzerieEntry.INDIRIZZO , indirizzo);
        cv.put(PizzaBuonaContratc.PizzerieEntry.CAP , cap);
        cv.put(PizzaBuonaContratc.PizzerieEntry.CITTA , citta);
        cv.put(PizzaBuonaContratc.PizzerieEntry.PROV , prov);
        cv.put(PizzaBuonaContratc.PizzerieEntry.COORD1 , coord1);
        cv.put(PizzaBuonaContratc.PizzerieEntry.COORD2 , coord2);
        cv.put(PizzaBuonaContratc.PizzerieEntry.TEL1 , tel1);
        cv.put(PizzaBuonaContratc.PizzerieEntry.TEL2 , tel2);
        cv.put(PizzaBuonaContratc.PizzerieEntry.EMAIL , email);
        cv.put(PizzaBuonaContratc.PizzerieEntry.CHIUSURA , chiusura);
        cv.put(PizzaBuonaContratc.PizzerieEntry.FORNO , forno);
        cv.put(PizzaBuonaContratc.PizzerieEntry.CELIACI , celiaci);
        cv.put(PizzaBuonaContratc.PizzerieEntry.NUMIMP , num_imp);
        cv.put(PizzaBuonaContratc.PizzerieEntry.LIEVMADR , liev_madre);
        cv.put(PizzaBuonaContratc.PizzerieEntry.BIRREART , birre_art);
        cv.put(PizzaBuonaContratc.PizzerieEntry.FARINEPIETRA , farine_pietra);
        cv.put(PizzaBuonaContratc.PizzerieEntry.FILONE , filone);
        cv.put(PizzaBuonaContratc.PizzerieEntry.BOCCONE , boccone);
        cv.put(PizzaBuonaContratc.PizzerieEntry.CONTO , conto);
        cv.put(PizzaBuonaContratc.PizzerieEntry.DATA_RECENSIONE , data_recensione);
        cv.put(PizzaBuonaContratc.PizzerieEntry.DATA_RIVALUTAZIONE , data_rivalutazione);
        cv.put(PizzaBuonaContratc.PizzerieEntry.LINK_ARTICOLO, link_articolo);
        cv.put(PizzaBuonaContratc.PizzerieEntry.VALUTAZIONE, valutazione);

        SQLiteDatabase db= dbhelper.getWritableDatabase();
        try
        {
            id_pizzeria = db.insert(PizzaBuonaContratc.PizzerieEntry.TABLE_NAME, null,cv);
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
        db.close();
        return id_pizzeria;
    }



    public boolean delete_pizzeria(long id)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try
        {
            if (db.delete(PizzaBuonaContratc.PizzerieEntry.TABLE_NAME, PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE+"=?", new String[]{Long.toString(id)})>0)
                return true;
            return false;
        }
        catch (SQLiteException sqle)
        {
            return false;
        }
    }


    public Cursor seleziona_pizzerie(String citta, String forno, int celiaci, int lievito_madre, int birre_art, int farine_pietra, int filone, int boccone, int valutazione)
    {
        boolean first_clausole = true;

        String [] tempSelectionArgs= new String[9];
        int position=0;
       String pizzerie_query = "SELECT piz."+PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE+" AS _id, "+PizzaBuonaContratc.PizzerieEntry.NOME_PIZZERIA+", "+PizzaBuonaContratc.PizzerieEntry.CITTA+", "+PizzaBuonaContratc.PizzerieEntry.INDIRIZZO+", "+
               PizzaBuonaContratc.PizzerieEntry.TEL1+", "+PizzaBuonaContratc.PizzerieEntry.VALUTAZIONE+" FROM "+PizzaBuonaContratc.PizzerieEntry.TABLE_NAME +" AS piz ";

        if(citta.length()>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.CITTA +" LIKE ? ";
            String mycitta=citta+"%";
            tempSelectionArgs[position]=mycitta;
            position++;
        }
        if(forno.length()>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.FORNO +" LIKE ? ";
            String myforno=forno+"%";
            tempSelectionArgs[position]=myforno;
            position++;
        }
        if(celiaci>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.CELIACI +" = ? ";
            tempSelectionArgs[position]= Integer.toString(celiaci);
            position++;
        }
        if(lievito_madre>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.LIEVMADR +" = ? ";
            tempSelectionArgs[position]= Integer.toString(lievito_madre);
            position++;
        }
        if(birre_art>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.BIRREART +" = ? ";
            tempSelectionArgs[position]= Integer.toString(birre_art);
            position++;
        }
        if(farine_pietra>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.FARINEPIETRA +" = ? ";
            tempSelectionArgs[position]= Integer.toString(farine_pietra);
            position++;
        }
        if(filone>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.FILONE +" = ? ";
            tempSelectionArgs[position]= Integer.toString(filone);
            position++;
        }
        if(boccone>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.BOCCONE +" = ? ";
            tempSelectionArgs[position]= Integer.toString(boccone);
            position++;
        }
        if(valutazione>0){
            if(first_clausole){
                pizzerie_query+=" WHERE ";
                first_clausole=false;
            }else{
                pizzerie_query+=" AND ";
            }
            pizzerie_query+= PizzaBuonaContratc.PizzerieEntry.CELIACI +" > ? ";
            tempSelectionArgs[position]= Integer.toString(valutazione);
            position++;
        }
        if(first_clausole){
            pizzerie_query+=" WHERE 1";}
        String [] selectionArgs;
        selectionArgs = new String[position];
        for(int i=0; i<position; i++){
            selectionArgs[i]=tempSelectionArgs[i];
        }


        Cursor cursor_pizzerie=null;
        try
        {
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            cursor_pizzerie=db.rawQuery(pizzerie_query,selectionArgs);
        }
        catch(SQLiteException sqle)
        {
            return null;
        }
        return cursor_pizzerie;
    }

    public Cursor seleziona_dettaglio_pizzeria(long id_pizzeria){
        String pizzeria_query = "SELECT piz."+PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE+" AS _id, "+PizzaBuonaContratc.PizzerieEntry.NOME_PIZZERIA+", "+PizzaBuonaContratc.PizzerieEntry.CITTA+
                ", "+PizzaBuonaContratc.PizzerieEntry.INDIRIZZO+", "+PizzaBuonaContratc.PizzerieEntry.CAP+", "+PizzaBuonaContratc.PizzerieEntry.PROV+", "+PizzaBuonaContratc.PizzerieEntry.TEL1+
                ", "+PizzaBuonaContratc.PizzerieEntry.TEL2 + ", " + PizzaBuonaContratc.PizzerieEntry.EMAIL+", "+PizzaBuonaContratc.PizzerieEntry.CHIUSURA+", "+ PizzaBuonaContratc.PizzerieEntry.CELIACI+
                ", "+ PizzaBuonaContratc.PizzerieEntry.NUMIMP+", "+ PizzaBuonaContratc.PizzerieEntry.LIEVMADR+", "+ PizzaBuonaContratc.PizzerieEntry.BIRREART+", "+ PizzaBuonaContratc.PizzerieEntry.FARINEPIETRA+
                ", "+ PizzaBuonaContratc.PizzerieEntry.FILONE+", "+ PizzaBuonaContratc.PizzerieEntry.BOCCONE+", " + PizzaBuonaContratc.PizzerieEntry.PIZZAIOLO+
                ", "+ PizzaBuonaContratc.PizzerieEntry.CONTO+", "+ PizzaBuonaContratc.PizzerieEntry.FORNO+
                ", "+PizzaBuonaContratc.PizzerieEntry.VALUTAZIONE+", "+ PizzaBuonaContratc.PizzerieEntry.DATA_RECENSIONE+", "+ PizzaBuonaContratc.PizzerieEntry.DATA_RIVALUTAZIONE+
                ", "+ PizzaBuonaContratc.PizzerieEntry.LINK_ARTICOLO+" FROM "+PizzaBuonaContratc.PizzerieEntry.TABLE_NAME +" AS piz WHERE piz."+ PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE+" = "+id_pizzeria;

        Cursor cursor_pizzeria=null;
       // String [] selectionArgs = new String[] { Long.toString(id_pizzeria) };
        String [] selectionArgs = new String[] {};
        try
        {
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            cursor_pizzeria=db.rawQuery(pizzeria_query,selectionArgs);
        }
        catch(SQLiteException sqle)
        {
            return null;
        }
        return cursor_pizzeria;
    }

}
