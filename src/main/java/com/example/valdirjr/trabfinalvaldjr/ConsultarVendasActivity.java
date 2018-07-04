package com.example.valdirjr.trabfinalvaldjr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConsultarVendasActivity extends Activity {

    ArrayList<Object> vendas;
    public Button excluir;
    public EditText numeroVenda;
    public BancoVenda banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_vendas);
        esconderStatusBar();

        excluir = (Button) findViewById(R.id.excluirVenda);
        numeroVenda = (EditText) findViewById(R.id.numeroVenda);

        final Activity context = this;
        ListView listarVendas = (ListView) findViewById(R.id.listarVendas);

        banco = new BancoVenda(context);
        this.vendas = banco.listarTodos();

        ArrayAdapter<Object> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, this.vendas);
        listarVendas.setAdapter(adapter);

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (banco.excluir(Integer.parseInt(numeroVenda.getText().toString()))) {
                    Toast.makeText(context, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ConsultarVendasActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void esconderStatusBar(){
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
}
