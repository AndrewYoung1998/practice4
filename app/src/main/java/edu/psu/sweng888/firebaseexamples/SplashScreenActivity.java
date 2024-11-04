package edu.psu.sweng888.firebaseexamples;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            Intent intent = (auth.getCurrentUser() != null) ?
                    new Intent(SplashScreenActivity.this, MainActivity.class) :
                    new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000); // 3 seconds delay
    }
}
