package com.db.roomproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private EditText etLoginId;
    private EditText etPassword;
    private Button btnLoginOrSignUp;
    UserDao userDao;
    List<User> userList;
    TextView tvSignUpOrLogin;
    boolean loginOrSignUpBtn = false;
    TextView tvName;
    TextView tvEmail;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        btnLoginOrSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String name = etLoginId.getText().toString().trim();
                        String email = etEmail.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();

                        if (loginOrSignUpBtn == false) {
                            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                                User user = new User();
                                user.setPassword(password);
                                user.setFullName(name);
                                user.setEmail(email);
                                userDao.insert(user);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "created", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("email", user.getEmail());
                                startActivity(intent);
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Все поля должны быть заполнеными", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            if(!email.isEmpty() && !password.isEmpty()){
                                User user = userDao.getUserByLoginId(email);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(user != null){
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("email", user.getEmail());
                                            startActivity(intent);
                                            Toast.makeText(LoginActivity.this, "Hello, " + user.getFullName(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }
    public void init() {
        userDao = Room.databaseBuilder(this, AppDatabase.class, DbConfig.ROOM_DB_NAME)
                .fallbackToDestructiveMigration()
                .build().userDao();
        //.addMigrations(AppDatabase.MIGRATION_1_2)
        etLoginId = findViewById(R.id.logInEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnLoginOrSignUp = findViewById(R.id.loginOSignUpBtn);
        tvSignUpOrLogin = findViewById(R.id.loginOrSignupText);
        tvName = findViewById(R.id.visibleName);
        tvEmail = findViewById(R.id.visibleEmail);
        etEmail = findViewById(R.id.emailEditText);
    }
    public void loginOrSignUpText(View view) {
        if (loginOrSignUpBtn) {
            loginOrSignUpBtn = false;
            btnLoginOrSignUp.setText("Sign up");
            tvSignUpOrLogin.setText("Or log in");

            etLoginId.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.VISIBLE);
        } else {
            loginOrSignUpBtn = true;
            btnLoginOrSignUp.setText("Log in");
            tvSignUpOrLogin.setText("Or, sign up");

            etLoginId.setVisibility(View.GONE);
            tvName.setVisibility(View.GONE);
        }
    }
}
