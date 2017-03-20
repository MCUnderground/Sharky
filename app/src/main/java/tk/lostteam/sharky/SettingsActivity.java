package tk.lostteam.sharky;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by MC on 19.3.2017.
 */
public class SettingsActivity extends MainActivity{

    Button clrHistoryBtn;
    Button clrCacheBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        clrHistoryBtn = (Button) findViewById(R.id.clearHistoryBtn);
        clrCacheBtn = (Button) findViewById(R.id.clearCacheBtn);

        clrHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.clearHistory();
            }
        });
        clrCacheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.clearCache(true);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
