package com.example.rick.agileitticket;

import com.example.rick.agileitticket.models.ApiResponse;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.example.rick.agileitticket.android.Global;
import com.example.rick.agileitticket.android.IntentIntegrator;
import com.example.rick.agileitticket.android.IntentResult;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button scanBtn, cancelBtn, approveBtn;
    private TextView scanTxt, personTxt, companyTxt, panelTxt, wasInPastTxt, timeTxt, allRightsTxt;
    private View afterScanLayout, logo;
    APIInterface apiInterface;


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

        scanBtn = (Button) findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);

        cancelBtn = (Button) findViewById(R.id.cancel_button);
        cancelBtn.setTextColor(getResources().getColor(R.color.buttonTextBlack));
        cancelBtn.setOnClickListener(this);

        approveBtn = (Button) findViewById(R.id.approve_button);
        approveBtn.setOnClickListener(this);

        scanTxt = (TextView) findViewById(R.id.user_data_label);
        personTxt = (TextView) findViewById(R.id.scan_person);
        companyTxt = (TextView) findViewById(R.id.scan_company);
        panelTxt = (TextView) findViewById(R.id.scan_event);
        wasInPastTxt = (TextView) findViewById(R.id.scan_was_in_past);
        timeTxt = (TextView) findViewById(R.id.scan_time);
        allRightsTxt = (TextView) findViewById(R.id.footer);

        scanTxt.setText(getString(R.string.user_data_label_scan));
        personTxt.setText("");
        companyTxt.setText("");
        panelTxt.setText("");
        wasInPastTxt.setText("");
        timeTxt.setText("");
        allRightsTxt.setText(getString(R.string.allRights));

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void onClick(View v) {
//respond to clicks
        if (v.getId() == R.id.agenda_button) {
            Intent intent = new Intent(getApplicationContext(), AgendaActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.scan_button) {
//scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();

            personTxt.setText("");
            companyTxt.setText("");
            panelTxt.setText("");
            wasInPastTxt.setText("");
            timeTxt.setText("");

            afterScanLayout.setVisibility(View.INVISIBLE);
            scanBtn.setVisibility(View.VISIBLE);
        }

        if (v.getId() == R.id.cancel_button) {
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

        if (v.getId() == R.id.approve_button) {
            //get is present from globals
            Boolean is_present = Global.presence;
            Boolean was_in_past = true;
            String ticket = Global.ticket;

            if (is_present == true) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.codeAlreadyPresent), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //API CALLS HERE
                Call<ApiResponse> call = apiInterface.setIsPresent(ticket, is_present);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        ApiResponse resource = response.body();
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

                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        call.cancel();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                getString(R.string.apiError), Toast.LENGTH_SHORT);
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
                    }

                });
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

            //API CALLS HERE
            Call<ApiResponse> call = apiInterface.getId(scanContent);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    ApiResponse resource = response.body();
                    String mResponseObj = response.body().toString();
                    if (mResponseObj.contains("Message")) {
                        call.cancel();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                mResponseObj, Toast.LENGTH_SHORT);
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
                    }
                    else {
                        String first_name = resource.getFirstName();
                        String last_name = resource.getLastName();
                        String company_name = resource.getCompanyName();
                        String event_name = resource.getEventName();
                        String event_date = resource.getEventDate();
                        String event_time = resource.getEventTime();
                        Boolean was_in_past = resource.isWasInPast();
                        Boolean is_present = resource.isIsPresent();
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
                        scanBtn.setVisibility(View.INVISIBLE);

                        call.cancel();
                    }

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    call.cancel();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getString(R.string.apiError), Toast.LENGTH_SHORT);
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

            });
        } else {
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
}
