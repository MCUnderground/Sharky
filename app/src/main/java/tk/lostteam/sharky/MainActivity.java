package tk.lostteam.sharky;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.*;
import android.widget.*;


public class MainActivity extends Activity {

    WebView webview;

    Button searchBtn;

    ImageView backBtn;
    ImageView menuBtn;

    EditText urlView;

    private SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webview);

        searchBtn = (Button) findViewById(R.id.goBtn);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        menuBtn = (ImageView) findViewById(R.id.popupMenuBtn);

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


            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadUrl("file:///android_asset/error.html");
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                view.loadUrl("file:///android_asset/error.html");
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoBack()){
                    webview.goBack();
                } else {

                }
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, menuBtn);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_menu:
                                webview.loadUrl("http://google.com");
                                return true;
                            case R.id.settings_menu:
                                Intent i_menu = new Intent(MainActivity.this, SettingsActivity.class);
                                startActivity(i_menu);
                                return true;
                            case R.id.exit_menu:
                                finish();
                                return true;

                        }
                        return true;
                    }
                });
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