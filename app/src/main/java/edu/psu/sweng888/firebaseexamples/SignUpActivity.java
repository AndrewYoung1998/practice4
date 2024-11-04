package edu.psu.sweng888.firebaseexamples;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText, nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        nameEditText = findViewById(R.id.name);
        Button signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String name = nameEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    user.updateProfile(profileUpdates);
                }
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(SignUpActivity.this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
