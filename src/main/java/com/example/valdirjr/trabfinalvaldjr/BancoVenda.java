package com.example.valdirjr.trabfinalvaldjr;

/**
 * Created by Valdir Jr on 02/07/2018.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BancoVenda {

    protected Banco banco;
    protected SQLiteDatabase db;
    protected SQLiteStatement stmt;
    protected Cursor cursor;
    protected String query;
    protected Context context;

    protected BancoVenda(Context context) {
        try {
            this.context = context;
            this.banco = new Banco(context);
            this.db = banco.getWritableDatabase();
            this.cursor = null;
            this.stmt = null;
            this.query = "";
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void closeConnection() {
        if ((cursor != null) && (! cursor.isClosed())) {
            cursor.close();
        }

        if (db != null) {
            db.close();
        }

        if (banco != null) {
            banco.close();
        }
    }

    public boolean salvar(Object obj) {
        Venda venda = (Venda) obj;
        boolean isSaved = true;

        try {
            if (venda.getId() > 0) {
                query = "UPDATE venda SET codigo = ?, confirmado = ? WHERE id = ?";
            } else {
                query = "INSERT INTO venda (codigo, confirmado) VALUES (?, ?)";
            }

            db.beginTransaction();
            stmt = db.compileStatement(query);
            stmt.clearBindings();
            stmt.bindString(1, venda.getCodigo());
            stmt.bindString(2, venda.getConfirmado());
            if(venda.getId() > 0){
                stmt.bindLong(3, venda.getId());
                stmt.executeUpdateDelete();
            }else {
                stmt.executeInsert();
            }

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            isSaved = false;
            db.endTransaction();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            System.out.println(e);
        }

        return isSaved;
    }

    public boolean excluir(int id) {
        boolean isDeleted = true;

        try {
            banco = new Banco(context);
            db.beginTransaction();
            db.delete("venda","id = ?", new String[]{ String.valueOf(id) });
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            isDeleted = false;
            db.endTransaction();
            System.out.println(e);
        }

        return isDeleted;
    }

    public ArrayList<Object> listarTodos() {
        ArrayList<Object> vendas = new ArrayList<>();

        try{
            cursor = db.query("venda",null,null,null,null,null,"id DESC");

            while(cursor.moveToNext()) {
                Venda venda = new Venda();

                venda.setId(cursor.getInt(cursor.getColumnIndex("id")));
                venda.setCodigo(cursor.getString(cursor.getColumnIndex("codigo")));
                venda.setConfirmado(cursor.getString(cursor.getColumnIndex("confirmado")));

                vendas.add(venda);
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return vendas;
    }

    public void close(){
        this.closeConnection();
    }

    public Venda obterUltimo() {
        Venda venda = new Venda();
        try {
            cursor = db.query("venda", null, null, null, null, null, "id DESC");

            cursor.moveToNext();
            venda.setId(cursor.getInt(cursor.getColumnIndex("id")));
            venda.setCodigo(cursor.getString(cursor.getColumnIndex("codigo")));
            venda.setConfirmado(cursor.getString(cursor.getColumnIndex("confirmado")));

        }catch (Exception e){
            System.out.println(e);
        }

        return venda;
    }

    public Venda obter(int id) {
        Venda venda = new Venda();
        try {
            cursor = db.query("venda", null, "id = "+id, null, null, null, null);

            cursor.moveToNext();
            venda.setId(cursor.getInt(cursor.getColumnIndex("id")));
            venda.setCodigo(cursor.getString(cursor.getColumnIndex("codigo")));
            venda.setConfirmado(cursor.getString(cursor.getColumnIndex("confirmado")));

        }catch (Exception e){
            System.out.println(e);
        }

        return venda;
    }
}
