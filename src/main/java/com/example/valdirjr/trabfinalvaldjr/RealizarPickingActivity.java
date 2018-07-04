package com.example.valdirjr.trabfinalvaldjr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class RealizarPickingActivity extends Activity {

    private ZXingScannerView scan;
    private Button leitor;
    private EditText numVendaPicking;
    private BancoVenda banco;
    final Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_picking);
        esconderStatusBar();

        banco = new BancoVenda(context);

        leitor = (Button) findViewById(R.id.leitor);
        numVendaPicking = (EditText) findViewById(R.id.numVendaPicking);

        leitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanear(v);
            }
        });

    }

    public void scanear(View view){
        scan = new ZXingScannerView(this);
        String numVen = numVendaPicking.getText().toString();
        if(!numVen.equals("")) {
            Venda ven = banco.obter(Integer.parseInt(numVen));
            if(ven.getConfirmado().toString().equals("1")){
                Toast.makeText(context, "Esse pedido já está confirmado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, RealizarPickingActivity.class);
                startActivity(intent);
                finish();
            } else {
                scan.setResultHandler(new zxingscanner(ven.getId(),ven.getCodigo().toString(), context));
            }
        }else {
            scan.setResultHandler(new zxingscanner());
        }
        setContentView(scan);
        scan.startCamera();
    }

    class zxingscanner implements ZXingScannerView.ResultHandler {

        private int idVenda;
        private String codigoVenda;
        private BancoVenda banco;

        @Override
        public void handleResult(Result result) {
            String codLido = result.getText();
            scan.stopCamera();
            setContentView(R.layout.activity_realizar_picking);
            if(codigoVenda.equals(codLido)) {
                Venda ven = new Venda(idVenda,codigoVenda, "1");
                if (banco.salvar(ven)) {
                    Toast.makeText(RealizarPickingActivity.this, "Código encontrado com sucesso, venda confirmada!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }else if(codigoVenda.equals("")){
                Toast.makeText(RealizarPickingActivity.this, "Código: "+codLido, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, RealizarPickingActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RealizarPickingActivity.this, "Código não encontrado para essa venda!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, RealizarPickingActivity.class);
                startActivity(intent);
                finish();
            }

        }

        public zxingscanner(int idV, String codigoV, Activity ctx){
            idVenda = idV;
            codigoVenda = codigoV;
            banco = new BancoVenda(ctx);
        }

        public zxingscanner(){
            codigoVenda = "";
        }

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
