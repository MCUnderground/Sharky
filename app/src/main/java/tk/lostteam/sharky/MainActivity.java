package tk.lostteam.sharky;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    WebView webview;
    Button searchBtn;
    EditText urlView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webview);
        searchBtn = (Button) findViewById(R.id.goBtn);
        urlView = (EditText) findViewById(R.id.urlView);

        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                urlView.setText(url);
            }
        });

        webview.getSettings().getBuiltInZoomControls();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().getCacheMode();
        webview.getSettings().getAllowFileAccess();

        webview.loadUrl("http://google.com");

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlView.getText().toString().contains("http://") || urlView.getText().toString().contains("https://")) {
                    webview.loadUrl(urlView.getText().toString());
                } else if (!urlView.getText().toString().contains("http://") || !urlView.getText().toString().contains("https://")) {
                    webview.loadUrl("http://" + urlView.getText().toString());
                } else if (urlView.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Enter url", Toast.LENGTH_LONG);
                }
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