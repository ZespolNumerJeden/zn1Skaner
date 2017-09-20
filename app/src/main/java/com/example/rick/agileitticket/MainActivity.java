package com.example.rick.agileitticket;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.example.rick.agileitticket.android.IntentIntegrator;
import com.example.rick.agileitticket.android.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import io.fabric.sdk.android.Fabric;
import java.util.Calendar;

public class MainActivity extends Activity implements OnClickListener {

    private Button scanBtn;
    private TextView formatTxt, contentTxt, timeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_main);
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        timeTxt = (TextView)findViewById(R.id.time_content);
        scanBtn.setOnClickListener(this);


    }

    public void onClick(View v){
//respond to clicks
        if(v.getId()==R.id.scan_button){
//scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("Uczestnik: " + scanFormat);
            contentTxt.setText("Panel: " + scanContent);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
            String datetime = dateformat.format(c.getTime());
            timeTxt.setText("Czas: " + datetime);

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Brak danych!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
