package com.example.valdirjr.trabfinalvaldjr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Login extends Activity {

    private ZXingScannerView scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        esconderStatusBar();

        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanear(v);
            }
        });
    }

    public void scanear(View view){
        scan = new ZXingScannerView(this);
        scan.setResultHandler(new zxingscannerLogin());
        setContentView(scan);
        scan.startCamera();
    }

    class zxingscannerLogin implements ZXingScannerView.ResultHandler {

        private String codigoVenda;

        @Override
        public void handleResult(Result result) {
            String codLido = result.getText();
            scan.stopCamera();
            if(codLido.equals("1@v5jsEfB002Oax7GQK/Mo8J64CfX55sKn4utrq8Y6ul3M31IGaOmN9uYl,9vhv3d+0bsFcZD5bXeTKvYPyiA00Nq+FopLEqe6V/wM=,npzbM1LYgEaTEYncRZ6w+g==")) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_main);
            } else {
                Toast.makeText(Login.this, "Login inválido!", Toast.LENGTH_SHORT).show();
            }
            setContentView(R.layout.activity_login);
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
