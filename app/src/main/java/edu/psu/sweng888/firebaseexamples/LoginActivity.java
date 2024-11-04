package edu.psu.sweng888.firebaseexamples;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.email_edit_text );
        passwordEditText = findViewById(R.id.password_login);
        Button loginButton = findViewById(R.id.login_button);
        Button signUpButton = findViewById(R.id.sign_up_button);

        loginButton.setOnClickListener(v -> loginUser());
        signUpButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
