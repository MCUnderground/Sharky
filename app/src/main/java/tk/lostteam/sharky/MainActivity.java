package tk.lostteam.sharky;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    WebView webview;
    Button searchBtn;
    EditText urlView;
    private SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webview);
        searchBtn = (Button) findViewById(R.id.goBtn);
        urlView = (EditText) findViewById(R.id.urlView);

        urlView.setFocusable(true);
        urlView.setSelectAllOnFocus(true);

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                urlView.setText(url);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onReceivedError(WebView viewer, WebResourceRequest request, WebResourceError error) {
                viewer.loadUrl("file:///android_asset/error.html");
            }

        });



        webview.getSettings().getBuiltInZoomControls();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().getCacheMode();
        webview.getSettings().getAllowFileAccess();
        webview.getSettings().setSaveFormData(true);

        webview.loadUrl("http://google.com");

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlView.getText().toString().equals("http://sharky:settings") || urlView.getText().toString().equals("sharky:settings")) {
                    Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(i);
                } else if (!urlView.getText().toString().contains("http://") || !urlView.getText().toString().contains("https://")) {
                    webview.loadUrl("http://" + urlView.getText().toString());
                } else if (urlView.getText().toString().equals("google") || urlView.getText().toString().equals("http://google")) {
                    webview.loadUrl("http://google.com");
                } else if (urlView.getText().toString().contains("http://") || urlView.getText().toString().contains("https://")) {
                    webview.loadUrl(urlView.getText().toString());
                }
            }

        });

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipeLayout.setColorSchemeResources(android.R.color.holo_green_dark);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webview.reload();
                swipeLayout.setRefreshing(true);
            }
        });

    }

    private void urlSync(){
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                urlView.setText(webview.getUrl());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}