package com.example.valdirjr.trabfinalvaldjr;

/**
 * Created by Valdir Jr on 02/07/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    private static String NOME = "iPicking.db";
    private static int VERSAO = 1;

    public Banco(Context context) {
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE venda ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "codigo VARCHAR(255) NOT NULL, " +
                "confirmado VARCHAR(255) NOT NULL);");
        db.execSQL("INSERT INTO venda (id, codigo, confirmado) VALUES (1,'012345678905', '0');");
        db.execSQL("INSERT INTO venda (id, codigo, confirmado) VALUES (2,'314159265359', '1');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}