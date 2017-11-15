package com.example.rick.agileitticket.android;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.example.rick.agileitticket.R;
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
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import javax.net.ssl.HttpsURLConnection;

import static android.provider.Settings.System.getString;


public class Global {
    public static Boolean presence;
    public static String ticket;
}