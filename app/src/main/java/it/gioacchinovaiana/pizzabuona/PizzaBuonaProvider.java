package it.gioacchinovaiana.pizzabuona;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import it.gioacchinovaiana.pizzabuona.data.DBhelper;
import it.gioacchinovaiana.pizzabuona.data.PizzaBuonaContratc;

/**
 * Created by Gioacchino on 27/03/2015.
 */
public class PizzaBuonaProvider extends ContentProvider {
    private DBhelper dbhelper=null;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int PIZZERIE = 1;
    private static final int PIZZERIA_ID = 2;
    //private static final int ELENCO_PIZZERIE = 3;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PizzaBuonaContratc.CONTENT_AUTHORITY;

        matcher.addURI(authority, PizzaBuonaContratc.BASE_PATH, PIZZERIE);
        matcher.addURI(authority, PizzaBuonaContratc.BASE_PATH + "/#", PIZZERIA_ID);
        //matcher.addURI(authority, PizzaBuonaContratc.BASE_PATH + "/elenco", ELENCO_PIZZERIE);

        return matcher;
    }


    @Override
    public boolean onCreate()
    {
        dbhelper=new DBhelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder)
    {
        Cursor crs=null;
        switch (sUriMatcher.match(uri)){
            case PIZZERIE:
                crs=dbhelper.getReadableDatabase().query(
                        PizzaBuonaContratc.PizzerieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case PIZZERIA_ID:
                long id = PizzaBuonaContratc.PizzerieEntry.getIdFromUri(uri);
                String selArgs [] = {""+Long.toString(id)};
                crs=dbhelper.getReadableDatabase().query(
                        PizzaBuonaContratc.PizzerieEntry.TABLE_NAME,
                        projection,
                        PizzaBuonaContratc.PizzerieEntry.ID_PIZZERIE + " = ?",
                        selArgs,
                        null,
                        null,
                        null
                );
                //crs=pizza_db.seleziona_dettaglio_pizzeria(Long.parseLong(selectionArgs[0]));
                break;
   /*         case ELENCO_PIZZERIE:
                crs=dbhelper.getReadableDatabase().query(
                        PizzaBuonaContratc.PizzerieEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                              break;*/
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        crs.setNotificationUri(getContext().getContentResolver(), uri);

        return crs;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PIZZERIE:
                return PizzaBuonaContratc.PizzerieEntry.CONTENT_TYPE;
            case PIZZERIA_ID:
                return PizzaBuonaContratc.PizzerieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final SQLiteDatabase db = dbhelper.getWritableDatabase();
        final Uri returnUri;

        long _id = db.insert(PizzaBuonaContratc.PizzerieEntry.TABLE_NAME, null, contentValues);

        if ( _id > 0 )
            returnUri = PizzaBuonaContratc.PizzerieEntry.buildUri(_id);
        else
            throw new android.database.SQLException("Failed to insert row into " + uri);

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = dbhelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowDeleted;

        switch(match){

            case PIZZERIE:
                rowDeleted = db.delete(
                        PizzaBuonaContratc.PizzerieEntry.TABLE_NAME,
                        null,
                        null
                );
                break;

            case PIZZERIA_ID:
                rowDeleted = db.delete(
                        PizzaBuonaContratc.PizzerieEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;

            default:
                throw new UnsupportedOperationException("Failed to delete row into " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbhelper.getWritableDatabase();
        int rowsUpdated;

        rowsUpdated = db.update(
                PizzaBuonaContratc.PizzerieEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        return rowsUpdated;
    }

}
