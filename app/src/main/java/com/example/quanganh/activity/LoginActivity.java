package com.example.quanganh.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanganh.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private int TIME_OUT=1000;
    TextView tvRegister;
    private FirebaseAuth mAuth;
    private EditText edtEmaill;
    private EditText edtPassword;
    String email,password;
    private Button btnRegister;
    private static final String TAG = "EmailPassword";
    private CheckBox cbRemember;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkFieldsForEmptyValues();

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private  void checkFieldsForEmptyValues(){
        btnRegister= findViewById(R.id.btnRegister);


       email = edtEmaill.getText().toString();
       password = edtPassword.getText().toString();

        if(email.equals("") && password.equals(""))
        {
            btnRegister.setEnabled(false);
        }

        else if(!email.equals("")&&password.equals("")){
            btnRegister.setEnabled(false);
        }

        else if(!password.equals("")&&email.equals(""))
        {
            btnRegister.setEnabled(false);
        }

        else
        {
            btnRegister.setEnabled(true);
        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        edtEmaill= findViewById(R.id.edtEmail);
        edtPassword= findViewById(R.id.edtPassword);
        edtEmaill.addTextChangedListener(textWatcher);
        edtPassword.addTextChangedListener(textWatcher);
        checkFieldsForEmptyValues();
        cbRemember = findViewById(R.id.ckRemeber);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            edtEmaill.setText(loginPreferences.getString("username", ""));
            edtPassword.setText(loginPreferences.getString("password", ""));
            cbRemember.setChecked(true);
        }





        tvRegister= findViewById(R.id.tvSignup);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });




        btnRegister= findViewById(R.id.btnRegister);




        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login(email,password);
                checkFieldsForEmptyValues();
                if (view == btnRegister) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtEmaill.getWindowToken(), 0);



                    if (cbRemember.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", email);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }


                }



            }
        });

    }

    private void Login(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(LoginActivity.this, "Login success.",
                                    Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent= new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },TIME_OUT);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {


    }

}
