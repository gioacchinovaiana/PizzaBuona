package it.gioacchinovaiana.pizzabuona.data;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;


/**
 * Created by Gioacchino on 26/03/2015.
 */


public class PizzaBuonaContratc {
    public static final String CONTENT_AUTHORITY = "it.gioacchinovaiana.pizzabuona";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String BASE_PATH = "pizzerie";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    public static final class PizzerieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(BASE_PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + BASE_PATH;
        public static final String CONTENT_ITEM_TYPE =ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + BASE_PATH;

        public static final String TABLE_NAME = "pizzerie";
        public static final String ID_PIZZERIE = "_id";
        public static final String NOME_PIZZERIA = "nome_pizzeria";
        public static final String TITOLARE = "titolare";
        public static final String INDIRIZZO = "indirizzo";
        public static final String CAP = "cap";
        public static final String CITTA = "citta";
        public static final String PROV = "prov";
        public static final String COORD1 = "coord1";
        public static final String COORD2 = "coord2";
        public static final String TEL1 = "tel1";
        public static final String TEL2 = "tel2";
        public static final String EMAIL = "email";
        public static final String FORNO = "forno";
        public static final String CHIUSURA = "chiusura";
        public static final String CELIACI = "celiaci";
        public static final String NUMIMP = "num_imp";
        public static final String LIEVMADR = "lievito_madre";
        public static final String BIRREART = "birre_artigianali";
        public static final String FARINEPIETRA = "farine_pietra";
        public static final String FILONE = "filone";
        public static final String BOCCONE = "boccone";
        public static final String PIZZAIOLO = "pizzaiolo";
        public static final String CONTO = "conto";
        public static final String DATA_RECENSIONE = "data_recensione";
        public static final String DATA_RIVALUTAZIONE = "data_rivalutazione";
        public static final String LINK_ARTICOLO = "link_articolo";
        public static final String VALUTAZIONE = "valutazione";

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPizzeriaById(long id) {
            return CONTENT_URI.buildUpon().appendQueryParameter(ID_PIZZERIE, Long.toString(id)).build();
        }

        public static Uri buildElencoPizzerieUri() {
            return CONTENT_URI;
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }


}
