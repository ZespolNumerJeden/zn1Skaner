package com.example.rick.agileitticket;

//import android.icu.text.SimpleDateFormat;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.example.rick.agileitticket.android.IntentIntegrator;
import com.example.rick.agileitticket.android.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.util.JsonReader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import io.fabric.sdk.android.Fabric;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity implements OnClickListener {

    private Button scanBtn, cancelBtn, approveBtn;
    private TextView scanTxt, personTxt, companyTxt, panelTxt, wasInPastTxt, timeTxt, allRightsTxt;
    private View afterScanLayout, logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        setContentView(R.layout.activity_main);
        afterScanLayout = (View) findViewById(R.id.afterScan_layout);
        View root = afterScanLayout.getRootView();
        root.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        logo = (View) findViewById(R.id.logotyp_layout);
        logo.setBackgroundColor(getResources().getColor(R.color.backgroundDefault));

        scanBtn = (Button)findViewById(R.id.scan_button);
        scanBtn.setTextColor(getResources().getColor(R.color.buttonTextDefault));
        //scanBtn.setBackgroundColor(getResources().getColor(R.color.buttonDefault));
        scanBtn.setOnClickListener(this);

        cancelBtn = (Button)findViewById(R.id.cancel_button);
        cancelBtn.setTextColor(getResources().getColor(R.color.buttonTextBlack));
        //cancelBtn.setBackgroundColor(getResours().getColor(R.color.buttonDefault));
        cancelBtn.setOnClickListener(this);

        approveBtn = (Button)findViewById(R.id.approve_button);
        approveBtn.setTextColor(getResources().getColor(R.color.buttonTextDefault));
        //approveBtn.setBackgroundColor(getResources().getColor(R.color.buttonDefault));
        approveBtn.setOnClickListener(this);

        scanTxt = (TextView)findViewById(R.id.user_data_label);
        scanTxt.setText(getString(R.string.user_data_label_scan));
        scanTxt.setTextColor(getResources().getColor(R.color.buttonTextDefault));

        personTxt = (TextView)findViewById(R.id.scan_person);
        personTxt.setTextColor(getResources().getColor(R.color.textDefault));
        personTxt.setText("");

        companyTxt = (TextView)findViewById(R.id.scan_company);
        companyTxt.setTextColor(getResources().getColor(R.color.textDefault));
        companyTxt.setText("");

        panelTxt = (TextView)findViewById(R.id.scan_event);
        panelTxt.setTextColor(getResources().getColor(R.color.textDefault));
        panelTxt.setText("");

        wasInPastTxt = (TextView)findViewById(R.id.scan_was_in_past);
        wasInPastTxt.setTextColor(getResources().getColor(R.color.textDefault));
        wasInPastTxt.setText("");

        timeTxt = (TextView)findViewById(R.id.scan_time);
        timeTxt.setTextColor(getResources().getColor(R.color.textDefault));
        timeTxt.setText("");

        allRightsTxt = (TextView)findViewById(R.id.footer);
        allRightsTxt.setTextColor(getResources().getColor(R.color.textDefault));
        allRightsTxt.setText(getString(R.string.allRights));
    }



    public void onClick(View v){
//respond to clicks
        if(v.getId()==R.id.scan_button) {
//scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            scanBtn.setVisibility(View.INVISIBLE);
        }

        if(v.getId()==R.id.cancel_button){
            scanTxt.setText(getString(R.string.user_data_label_scan));
            scanTxt.setTextColor(getResources().getColor(R.color.buttonTextDefault));

            personTxt.setText("");
            companyTxt.setText("");
            panelTxt.setText("");
            wasInPastTxt.setText("");
            timeTxt.setText("");

            afterScanLayout.setVisibility(View.INVISIBLE);
            scanBtn.setVisibility(View.VISIBLE);
        }

        if(v.getId()==R.id.approve_button){
            /*//get is present from globals
            if (is_present == "true"){
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.codeAlreadyPresent), Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                //send is present = true here
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Create URL
                        URL apiEndpoint = new URL(getString(R.urls.api_url));
                        //set cache
                        HttpResponseCache myCache = HttpResponseCache.install(getCacheDir(), 100000L);
                        // Create connection
                        HttpsURLConnection getDataConnection = (HttpsURLConnection) apiEndpoint.openConnection();
                        // Set headers
                        getDataConnection.setRequestProperty("someheader", scanContent);
                        getDataConnection.setRequestProperty("is_present", "true");
                        // Check response
                        if (getDataConnection.getResponseCode() == 200) {
                            //display proper toast message
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    getString(R.string.codeAccepted), Toast.LENGTH_SHORT);
                            toast.show();
                            scanTxt.setText(getString(R.string.user_data_label_scan));
                            scanTxt.setText("");
                            personTxt.setText("");
                            companyTxt.setText("");
                            panelTxt.setText("");
                            wasInPastTxt.setText("");
                            timeTxt.setText("");

                            afterScanLayout.setVisibility(View.INVISIBLE);
                            scanBtn.setVisibility(View.VISIBLE);
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    getString(R.string.apiError), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        getDataConnection.disconnect();
                    }
                });
            }
*/

            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.codeAccepted), Toast.LENGTH_SHORT);
            toast.show();

            scanTxt.setText(getString(R.string.user_data_label_scan));
            scanTxt.setTextColor(getResources().getColor(R.color.buttonTextDefault));

            personTxt.setText("");
            companyTxt.setText("");
            panelTxt.setText("");
            wasInPastTxt.setText("");
            timeTxt.setText("");

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
/*

            //API CALLS HERE
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    // Create URL
                    URL apiEndpoint = new URL(getString(R.urls.api_url));
                    //set cache
                    HttpResponseCache myCache = HttpResponseCache.install(getCacheDir(), 100000L);
                    // Create connection
                    HttpsURLConnection getDataConnection = (HttpsURLConnection) apiEndpoint.openConnection();
                    // Set headers
                    getDataConnection.setRequestProperty("User-Agent", scanContent);
                    // Check response
                    if (getDataConnection.getResponseCode() == 200) {
                        // On success
                        //Get response json
                        InputStream responseBody = getDataConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");
                        //Analyze response
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        //Save values to global variables
                        jsonReader.beginObject(); // Start processing the JSON object
                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key
                            if (key.equals("first_name")) { // Check if desired key
                                // Fetch the value as a String
                                String first_name = jsonReader.nextString();
                                //assign to global
                            }
                            if (key.equals("last_name")) { // Check if desired key
                                // Fetch the value as a String
                                String last_name = jsonReader.nextString();
                                //assign to global
                            }
                            if (key.equals("company_name")) { // Check if desired key
                                // Fetch the value as a String
                                String company_name = jsonReader.nextString();
                                //assign to global
                            }
                            if (key.equals("event_name")) { // Check if desired key
                                // Fetch the value as a String
                                String event_name = jsonReader.nextString();
                                //assign to global
                            }
                            if (key.equals("event_date")) { // Check if desired key
                                // Fetch the value as a String
                                String event_date = jsonReader.nextString();
                                //assign to global
                            }
                            if (key.equals("event_time")) { // Check if desired key
                                // Fetch the value as a String
                                String event_time = jsonReader.nextString();
                                //assign to global
                            }
                            if (key.equals("is_present")) { // Check if desired key
                                // Fetch the value as a String
                                String is_present = jsonReader.nextString();
                                //assign to global
                            }
                            if (key.equals("was_in_past")) { // Check if desired key
                                // Fetch the value as a String
                                String was_in_past = jsonReader.nextString();
                                //assign to global
                            }
                            else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                        jsonReader.close();
                        scanTxt.setText(getString(R.string.user_data_label));
                        scanTxt.setTextColor(getResources().getColor(R.color.textDefault));
                        personTxt.setText("Uczestnik: " + first_name + " " + last_name);
                        companyTxt.setText("Firma: " + company_name);
                        panelTxt.setText("Wydarzenie: " + event_name);
                        timeTxt.setText("Czas: " + event_date + ", " + event_time);
                        if (was_in_past != null) {
                            wasInPastTxt.setText(getString(R.string.welcomeBack));
                        }
                        afterScanLayout.setVisibility(View.VISIBLE);

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                getString(R.string.apiError), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    getDataConnection.disconnect();
                }
            });
*/

            scanTxt.setText(getString(R.string.user_data_label));  //temporary for test
            scanTxt.setTextColor(getResources().getColor(R.color.textDefault));//temporary for test

            personTxt.setText("Uczestnik: Jan Kowalski");  //temporary for test
            companyTxt.setText("Firma: WSB");  //temporary for test
            panelTxt.setText("Panel: Wykład Profesora Mrożka");  //temporary for test
            wasInPastTxt.setText("Witaj ponownie!");  //temporary for test
            Calendar c = Calendar.getInstance(); //temporary for test
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa"); //temporary for test
            String datetime = dateformat.format(c.getTime()); //temporary for test
            timeTxt.setText("Czas: " + datetime);//temporary for test

            afterScanLayout.setVisibility(View.VISIBLE);   //temporary for test
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.codeError), Toast.LENGTH_SHORT);
            toast.show();

            scanTxt.setText(getString(R.string.user_data_label));  //temporary for test
            scanTxt.setTextColor(getResources().getColor(R.color.textDefault));//temporary for test

            personTxt.setText("Uczestnik: Jan Kowalski");  //temporary for test
            companyTxt.setText("Firma: WSB");  //temporary for test
            panelTxt.setText("Panel: Wykład Profesora Mrożka");  //temporary for test
            wasInPastTxt.setText("");  //temporary for test
            Calendar c = Calendar.getInstance(); //temporary for test
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa"); //temporary for test
            String datetime = dateformat.format(c.getTime()); //temporary for test
            timeTxt.setText("Czas: " + datetime);//temporary for test

            afterScanLayout.setVisibility(View.VISIBLE);   //temporary for test

            //FOR API PART
            ////scanTxt.setText(getString(R.string.user_data_label_scan));
            //personTxt.setText("");
            //companyTxt.setText("");
            //panelTxt.setText("");
            //wasInPastTxt.setText("");
            //timeTxt.setText("");

            //afterScanLayout.setVisibility(View.INVISIBLE);
            //scanBtn.setVisibility(View.VISIBLE);
        }
    }

}