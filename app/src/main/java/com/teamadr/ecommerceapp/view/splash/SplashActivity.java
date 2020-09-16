package com.teamadr.ecommerceapp.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.Tada)
                .duration(2000)
                .repeat(1)
                .playOn(imgLogo);

        /* New Handler to start the Login-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                progressBar.setVisibility(View.GONE);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
