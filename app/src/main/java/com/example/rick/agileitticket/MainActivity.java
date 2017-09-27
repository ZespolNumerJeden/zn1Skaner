package com.example.rick.agileitticket;

//import android.icu.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
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

    private Button scanBtn, cancelBtn, approveBtn;
    private TextView scanTxt, formatTxt, contentTxt, timeTxt;
    private View afterScanLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_main);
        afterScanLayout = (View) findViewById(R.id.afterScan_layout);
        scanBtn = (Button)findViewById(R.id.scan_button);
        cancelBtn = (Button)findViewById(R.id.cancel_button);
        approveBtn = (Button)findViewById(R.id.approve_button);
        scanTxt = (TextView)findViewById(R.id.user_data_label);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        timeTxt = (TextView)findViewById(R.id.time_content);
        scanTxt.setText(getString(R.string.user_data_label_scan));
        formatTxt.setText("");
        contentTxt.setText("");
        scanBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        approveBtn.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        timeTxt.setText("Czas: " + datetime);

    }

    public void onClick(View v){
//respond to clicks
        if(v.getId()==R.id.scan_button){
//scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            scanBtn.setVisibility(View.INVISIBLE);

            scanTxt.setText(getString(R.string.user_data_label));  //temporary for test
            formatTxt.setText("Uczestnik: Jan Kowalski");  //temporary for test
            contentTxt.setText("Panel: Wykład Profesora Mrożka");  //temporary for test
            afterScanLayout.setVisibility(View.VISIBLE); //temporary for test

        }

        if(v.getId()==R.id.cancel_button){
            scanTxt.setText(getString(R.string.user_data_label_scan));
            formatTxt.setText("");
            contentTxt.setText("");
            afterScanLayout.setVisibility(View.INVISIBLE);
            scanBtn.setVisibility(View.VISIBLE);
        }

        if(v.getId()==R.id.approve_button){
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.codeAccepted), Toast.LENGTH_SHORT);
            toast.show();
            scanTxt.setText(getString(R.string.user_data_label_scan));
            formatTxt.setText("");
            contentTxt.setText("");
            afterScanLayout.setVisibility(View.INVISIBLE);
            scanBtn.setVisibility(View.VISIBLE);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            scanTxt.setText(getString(R.string.user_data_label));
            formatTxt.setText("Uczestnik: " + scanFormat);
            contentTxt.setText("Panel: " + scanContent);
            afterScanLayout.setVisibility(View.VISIBLE);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.codeError), Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
