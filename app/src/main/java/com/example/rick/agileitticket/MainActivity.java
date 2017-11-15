package com.example.rick.agileitticket;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.example.rick.agileitticket.android.Global;
import com.example.rick.agileitticket.android.IntentIntegrator;
import com.example.rick.agileitticket.android.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import io.fabric.sdk.android.Fabric;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button scanBtn, cancelBtn, approveBtn;
    private TextView scanTxt, personTxt, companyTxt, panelTxt, wasInPastTxt, timeTxt, allRightsTxt;
    private View afterScanLayout, logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        setContentView(R.layout.activity_main);
        afterScanLayout = (View) findViewById(R.id.afterScan_layout);
        View root = afterScanLayout.getRootView();
        root.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        scanBtn = (Button)findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);

        cancelBtn = (Button)findViewById(R.id.cancel_button);
        cancelBtn.setTextColor(getResources().getColor(R.color.buttonTextBlack));
        cancelBtn.setOnClickListener(this);

        approveBtn = (Button)findViewById(R.id.approve_button);
        approveBtn.setOnClickListener(this);

        scanTxt = (TextView)findViewById(R.id.user_data_label);
        personTxt = (TextView)findViewById(R.id.scan_person);
        companyTxt = (TextView)findViewById(R.id.scan_company);
        panelTxt = (TextView)findViewById(R.id.scan_event);
        wasInPastTxt = (TextView)findViewById(R.id.scan_was_in_past);
        timeTxt = (TextView)findViewById(R.id.scan_time);
        allRightsTxt = (TextView)findViewById(R.id.footer);

        scanTxt.setText(getString(R.string.user_data_label_scan));
        personTxt.setText("");
        companyTxt.setText("");
        panelTxt.setText("");
        wasInPastTxt.setText("");
        timeTxt.setText("");
        allRightsTxt.setText(getString(R.string.allRights));
    }

    public void onClick(View v){
//respond to clicks
        if (v.getId()==R.id.agenda_button){
            Intent intent = new Intent(getApplicationContext(), AgendaActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.scan_button) {
//scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            scanBtn.setVisibility(View.INVISIBLE);
        }

        if(v.getId()==R.id.cancel_button){
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.codeCanceled), Toast.LENGTH_SHORT);
            toast.show();

            scanTxt.setText(getString(R.string.user_data_label_scan));

            personTxt.setText("");
            companyTxt.setText("");
            panelTxt.setText("");
            wasInPastTxt.setText("");
            timeTxt.setText("");

            afterScanLayout.setVisibility(View.INVISIBLE);
            scanBtn.setVisibility(View.VISIBLE);
        }

        if(v.getId()==R.id.approve_button){
            //get is present from globals
            Boolean is_present = Global.presence;

            if (is_present == true) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.codeAlreadyPresent), Toast.LENGTH_SHORT);
                toast.show();
            }
            else {


                            //display proper toast message for 200 response
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

                            Global.presence = null;
                            Global.ticket = null;
                        //or any other response
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    getString(R.string.apiError), Toast.LENGTH_SHORT);
                            toast.show();

            }

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            final String scanContent = scanningResult.getContents();
            Global.ticket = scanContent;
            final String api_url = getString(R.string.api_url);
            final String url = api_url + scanContent;


            //API CALLS HERE
            ApiInterface retrofit = ApiInterface.retrofit.create(ApiInterface.class);

            ApiClientResponse apiCall = retrofit.create(ApiClientResponse.class);
           // Call<ApiClient> call = api.getId("");
            apiCall.enqueue(new Callback<ApiClientResponse>() {
                @Override
                public void onResponse(Call<ApiClientResponse> call, Response<ApiClientResponse> response) {
                    response.body();
                    String first_name = response.body().getFirstName();
                    String last_name = response.body().getLastName();
                    String company_name = response.body().getCompanyName();
                    String event_name = response.body().getEventName();
                    String event_date = response.body().getEventDate();
                    String event_time = response.body().getEventTime();
                    Boolean was_in_past = response.body().getWasInPast();
                    Boolean is_present = response.body().getIsPresent();
                    Global.presence = is_present;

                    scanTxt.setText(getString(R.string.user_data_label));
                    personTxt.setText("Uczestnik: " + first_name + " " + last_name);
                    companyTxt.setText("Firma: " + company_name);
                    panelTxt.setText("Wydarzenie: " + event_name);
                    timeTxt.setText("Czas: " + event_date + ", " + event_time);
                    if (was_in_past == true) {
                        wasInPastTxt.setText(getString(R.string.welcomeBack));
                    }
                    afterScanLayout.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFailure(Call<ApiClientResponse> call, Throwable t) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            getString(R.string.apiError), Toast.LENGTH_SHORT);
                    toast.show();

                    Global.presence = null;
                    Global.ticket = null;
                }

            });
    }





 else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.codeError), Toast.LENGTH_SHORT);
                toast.show();

                scanTxt.setText(getString(R.string.user_data_label_scan));
                personTxt.setText("");
                companyTxt.setText("");
                panelTxt.setText("");
                wasInPastTxt.setText("");
                timeTxt.setText("");

                afterScanLayout.setVisibility(View.INVISIBLE);
                scanBtn.setVisibility(View.VISIBLE);

                Global.presence = null;
                Global.ticket = null;

        }

}