package com.example.valdirjr.trabfinalvaldjr;

/**
 * Created by Valdir Jr on 19/06/2018.
 */

public class Venda {

    private int id;
    private String codigo;
    private String confirmado;

    public Venda(int id, String item, String confirmado) {
        this.id = id;
        this.codigo = item;
        this.confirmado = confirmado;
    }

    public Venda(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String item) {
        this.codigo = item;
    }

    public String getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(String confirmado) {
        this.confirmado = confirmado;
    }

    @Override
    public String toString() {
        String conf = "";
        if(this.confirmado.equals("1")) {
            conf = "Sim";
        } else {
            conf = "Não";
        }
        return "Venda: "+this.id+" \nCódigo: "+this.codigo.toString()+" \nConfirmado: "+conf;
    }
}
