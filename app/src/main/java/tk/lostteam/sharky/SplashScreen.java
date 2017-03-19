package tk.lostteam.sharky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * Created by MC on 19.3.2017.
 */
public class SplashScreen extends Activity {

    Handler handler;
    private static int SPLASH_TIME = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };

        handler.postDelayed(r, 1000);


    }
}
