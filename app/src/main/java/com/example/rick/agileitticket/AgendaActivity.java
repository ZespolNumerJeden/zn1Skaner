package com.example.rick.agileitticket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class AgendaActivity extends AppCompatActivity {

    private View webview;
    private TextView allRightsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.agenda_item_name));

        allRightsTxt = (TextView)findViewById(R.id.footer);
        allRightsTxt.setText(getString(R.string.allRights));

        webview = (View) findViewById(R.id.agenda_webview);
        View root = webview.getRootView();
        root.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        WebView myWebView = (WebView) findViewById(R.id.agenda_webview);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(getString(R.string.agenda_url));
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(getString(R.string.agenda_url))) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}