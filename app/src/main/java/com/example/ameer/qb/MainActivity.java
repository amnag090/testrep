package com.example.ameer.qb;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSION_REQUEST = 200;
    private Button scan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity act=this;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scan_btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                IntentIntegrator integrator = new IntentIntegrator(act);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                
                integrator.setPrompt("Use Volume +/- button to use flash");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setOrientationLocked(false);
                integrator.setCaptureActivity(CaptureActivityPortrait.class);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents() == null){
                Toast.makeText(this,"you cancelled d scan",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
            }

        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }


    }
}
