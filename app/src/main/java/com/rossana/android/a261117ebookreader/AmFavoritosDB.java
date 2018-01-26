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
    private SQLiteDatabase db;
    private final static String FAVORITOS_DB_NAME_FILE = "FAVORITOS_DB_NAME.DB";
    private final static int FAVORITOS_DB_NAME_VERSION = 2;
    private final static String FAVORITOS_DB_NAME = "FAVORITOS";

    private final static String COL_ID = "ID";
    private final static String ISBN = "ISBN";

    private final static String CREATE_TABLE_FAVORITOS ="CREATE TABLE IF NOT EXISTS " +
            FAVORITOS_DB_NAME + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    ISBN + " TEXT NOT NULL\n" +
            ");";

    private final static String DROP_TABLE_FAVORITOS = "DROP TABLE IF EXISTS " + FAVORITOS_DB_NAME +";";

    public AmFavoritosDB(Context pContext){
        super(pContext, FAVORITOS_DB_NAME_FILE,null, FAVORITOS_DB_NAME_VERSION);
        db = this.getWritableDatabase();
        //dropDB(db);
        installDB(db);
    }

    public void installDB(SQLiteDatabase pDB){
        try {
            pDB.execSQL(CREATE_TABLE_FAVORITOS);
        }
        catch(Exception e){
            Log.e (this.getClass().getName(), e.toString() + " FAILED@installDB\n" + CREATE_TABLE_FAVORITOS);
        }
    }//installDB

    public void dropDB(SQLiteDatabase pDB){
        try{
            pDB.execSQL(DROP_TABLE_FAVORITOS);
        }
        catch(Exception e){
            Log.e (this.getClass().getName(), e.toString()+ " FAILED@dropDB\n" + DROP_TABLE_FAVORITOS);
        }
        installDB(pDB);
    } //dropDB

    @Override
    public void onCreate(SQLiteDatabase db) {
        installDB(db);
    } //onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropDB(db);
    } //onUpgrade

    public HashMap<Integer, String> getFavoritos() {
        HashMap ret = new HashMap();
        try {
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
    public boolean validarFavoritos(String ISBN) {
        try {
            String q = "SELECT * FROM " + FAVORITOS_DB_NAME + " where " + this.ISBN +"='"+ ISBN + "'";
            String[] straMyFilters = null;

            Cursor filteringSelectResults = db.rawQuery(q, straMyFilters);
            db.close();
            //creio que o problema está, não é bem ver se é o primeiro... Mas ver apenas se o livro já se encontra na BD
            if (filteringSelectResults.isFirst()) return true;
            else
                return false;

        }//try
        catch (Exception e){
            Log.e("Rossana feia", e.getMessage());
            return false;
        }//catch
    } //getFavoritos

    public boolean inserirFavorito(String ISBN) {
        try {
            db = this.getWritableDatabase();

            ContentValues pares = new ContentValues();
            //pares.put(COL_ID, null);
            pares.put(this.ISBN, ISBN);
            long correuTudoBem = db.insert(FAVORITOS_DB_NAME,null, pares);

            db.close();
            Log.e("Insert", "MACOAS");
        }
        catch (Exception e){
            Log.e("Insert", e.getMessage());
            return false;
        }
return true;
    } //inserirFavorito

    public boolean apagarFavorito(String ISBN) {
        try {
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