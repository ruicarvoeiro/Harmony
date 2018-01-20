package com.rossana.android.a261117ebookreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.HashMap;

/**
 * Created by some bitch on 13/01/2018.
 */

public class AmRecentesDB extends SQLiteOpenHelper {
    private final static String RECENTES_DB_NAME_FILE = "RECENTES_DB_NAME.DB";
    private final static int RECENTES_DB_NAME_VERSION = 1;
    private final static String RECENTES_DB_NAME = "RECENTES";

    private final static String COL_ID = "ID";
    private final static String ISBN = "ISBN";

    private final static String CREATE_TABLE_RECENTES =
            "CREATE TABLE IF NOT EXISTS " + RECENTES_DB_NAME + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ISBN + " TEXT NOT NULL);";

    private final static String DROP_TABLE_RECENTES = "DROP TABLE IF EXISTS " + RECENTES_DB_NAME +";";

    public AmRecentesDB(Context pContext){
        super(pContext, RECENTES_DB_NAME_FILE,null, RECENTES_DB_NAME_VERSION);
    }

    public void installDB(SQLiteDatabase pDB){
        try {
            pDB.execSQL(CREATE_TABLE_RECENTES);
        }
        catch(Exception e){
            Log.e (this.getClass().getName(), e.toString() + " FAILED@installDB\n" + CREATE_TABLE_RECENTES);
        }
    }//installDB

    public void reinstallDB(SQLiteDatabase pDB){
        try{
            pDB.execSQL(DROP_TABLE_RECENTES);
        }
        catch(Exception e){
            Log.e (this.getClass().getName(), e.toString()+ " FAILED@reinstallDB\n" + DROP_TABLE_RECENTES);
        }
        installDB(pDB);
    } //reinstallDB

    @Override
    public void onCreate(SQLiteDatabase db) {
        installDB(db);
    } //onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        reinstallDB(db);
    } //onUpgrade

    public HashMap<Integer, String> getRecentes() {
        HashMap ret = new HashMap();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String q = "SELECT * FROM " + RECENTES_DB_NAME;

            String[] straMyFilters = null;

            Cursor filteringSelectResults = db.rawQuery(q, straMyFilters);
            filteringSelectResults.moveToFirst();

            while (!filteringSelectResults.isAfterLast()){
                int _ID = Integer.parseInt(filteringSelectResults.getString(0));
                String ISBN = filteringSelectResults.getString(1);
                ret.put(_ID, ISBN);
                filteringSelectResults.moveToNext();
            }//while
            db.close();
        }//try
        catch (Exception e){
            Log.e("@selectAllCalls...", e.toString());
        }//catch
        return ret;
    } //getFavoritos

    public boolean inserirRecente(String ISBN) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues pares = new ContentValues();
            pares.put(null, COL_ID);
            pares.put(ISBN, this.ISBN);
            db.insert(RECENTES_DB_NAME,null, pares);

            db.close();
            return true;
        }
        catch (Exception e){ }
        return false;
    } //inserirFavorito

    public boolean apagarRecente(String ISBN) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String filtro = ISBN + "= ?";
            String[] valor = {ISBN};

            db.delete(RECENTES_DB_NAME, filtro, valor);

            db.close();
            return true;
        }
        catch (Exception e){ }// catch
        return false;
    } //apagarFavorito
} //AmFavoritosDB