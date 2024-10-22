package com.example.licenseactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

//Nu imi merge sa salvez si numarul de telefon prin intermediul firebase, firestore !

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private EditText signup_email, signup_password, signup_phone;
    private Button signup_button;
    private TextView login_redirect_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Inițializare Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Inițializare EditText-uri și Button-uri
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_phone = findViewById(R.id.signup_phone);
        signup_button = findViewById(R.id.signup_button);
        login_redirect_text = findViewById(R.id.login_text);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signup_email.getText().toString().trim();
                String pass = signup_password.getText().toString().trim();
                String phone = signup_phone.getText().toString().trim();

                if (user.isEmpty()) {
                    signup_email.setError("Email cannot be empty");
                } else if (pass.isEmpty()) {
                    signup_password.setError("Password cannot be empty");
                } else if (phone.isEmpty()) {
                    signup_phone.setError("Phone number cannot be empty");
                } else {
                    // Verificăm dacă email-ul este deja folosit
                    auth.fetchSignInMethodsForEmail(user).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.isSuccessful()) {
                                boolean emailExists = !task.getResult().getSignInMethods().isEmpty();
                                if (emailExists) {
                                    Toast.makeText(SignUpActivity.this, "Email already in use. Please login.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Email-ul nu există, creăm un nou cont
                                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Salvăm datele utilizatorului (email + telefon) în Firestore
                                                String userId = auth.getCurrentUser().getUid();
                                                User userObject = new User(user, phone);  // Cream un obiect user
                                                db.collection("users").document(userId)
                                                        .set(userObject)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(SignUpActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(SignUpActivity.this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(SignUpActivity.this, "SignUp Failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(SignUpActivity.this, "Error checking email: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Redirect către LoginActivity dacă utilizatorul vrea să se logheze
        login_redirect_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}