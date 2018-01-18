package com.rossana.android.a261117ebookreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by rui_c on 12/01/2018.
 */

public class AmFavoritosDB extends SQLiteOpenHelper {
    private final static String FAVORITOS_DB_NAME_FILE = "FAVORITOS_DB_NAME.DB";
    private final static int FAVORITOS_DB_NAME_VERSION = 1;
    private final static String FAVORITOS_DB_NAME = "FAVORITOS";

    private final static String COL_ID = "ID";
    private final static String ISBN = "ISBN";

    private final static String CREATE_TABLE_FAVORITOS =
            "CREATE TABLE IF NOT EXISTS " + FAVORITOS_DB_NAME + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ISBN + " TEXT NOT NULL);";

    private final static String DROP_TABLE_FAVORITOS = "DROP TABLE IF EXISTS " + FAVORITOS_DB_NAME +";";

    public AmFavoritosDB(Context pContext){
        super(pContext, FAVORITOS_DB_NAME_FILE,null, FAVORITOS_DB_NAME_VERSION);
    }

    public void installDB(SQLiteDatabase pDB){
        try {
            pDB.execSQL(CREATE_TABLE_FAVORITOS);
        }
        catch(Exception e){
            Log.e (this.getClass().getName(), e.toString() + " FAILED@installDB\n" + CREATE_TABLE_FAVORITOS);
        }
    }//installDB

    public void reinstallDB(SQLiteDatabase pDB){
        try{
            pDB.execSQL(DROP_TABLE_FAVORITOS);
        }
        catch(Exception e){
            Log.e (this.getClass().getName(), e.toString()+ " FAILED@reinstallDB\n" + DROP_TABLE_FAVORITOS);
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

    public HashMap<Integer, String> getFavoritos() {
        HashMap ret = new HashMap();
        try {
                SQLiteDatabase db = this.getReadableDatabase();
                String q = "SELECT * FROM " + FAVORITOS_DB_NAME;

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

    public boolean inserirFavorito(String ISBN) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues pares = new ContentValues();
            pares.put(null, COL_ID);
            pares.put(ISBN, this.ISBN);
            db.insert(FAVORITOS_DB_NAME,null, pares);

            db.close();
            return true;
        }
        catch (Exception e){ }
        return false;
    } //inserirFavorito

    public boolean apagarFavorito(String ISBN) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String filtro = ISBN + "= ?";
            String[] valor = {ISBN};

            db.delete(FAVORITOS_DB_NAME, filtro, valor);

            db.close();
            return true;
        }
        catch (Exception e){ }
        return false;
    } //apagarFavorito
} //AmFavoritosDB