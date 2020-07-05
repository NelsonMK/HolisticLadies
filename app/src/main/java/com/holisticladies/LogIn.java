package com.holisticladies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.holisticladies.database.DatabaseHandler;
import com.holisticladies.model.User;
import com.holisticladies.utils.RequestHandler;
import com.holisticladies.utils.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LogIn extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if (PrefManager.getInstance(this).isLoggedIn()) {
            switch (PrefManager.getInstance(this).classId()) {
                case 1:
                    Intent customer = new Intent(LogIn.this, MainActivity.class);
                    customer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(customer);
                    break;
                case 2:
                    Intent employee = new Intent(LogIn.this, Employee.class);
                    employee.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(employee);
                    break;
            }
        }

        init();
    }

    void transparentBar(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    void init(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        //if user presses on login calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();
            }
        });
        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                startActivity(new Intent(getApplicationContext(), SignUp.class));
                finish();
            }
        });
        //if the user clicks on forgot password

    }

    private void userLogin() {
        //first getting the values
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email!");
            editTextEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password!");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine
        UserLogin userLogin = new UserLogin(email,password);
        userLogin.execute();
    }

    @SuppressLint("StaticFieldLeak")
    class UserLogin extends AsyncTask<Void, Void, String> {
        ProgressBar progressBar;
        String email, password;
        UserLogin(String email,String password) {
            this.email = email;
            this.password = password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);

            //returning the response
            return requestHandler.sendPostRequest(URLS.URL_LOGIN, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);

                //if no error in response
                if (!obj.getBoolean("error")) {
                    //getting the user from the response
                    JSONObject userJson = obj.getJSONObject("user");
                    String class1 = userJson.getString("class");
                    String status = userJson.getString("status");

                    //creating a new user object
                    User user = new User(
                            userJson.getInt("id"),
                            userJson.getString("first_name"),
                            userJson.getString("last_name"),
                            userJson.getString("phone"),
                            userJson.getString("email"),
                            userJson.getString("gender"),
                            userJson.getString("date_of_birth"),
                            userJson.getString("location"),
                            userJson.getInt("class"),
                            userJson.getInt("status"),
                            userJson.getInt("archive")
                    );

                    if (status.equals("0")){
                        Toast.makeText(getApplicationContext(), "Account not confirmed!", Toast.LENGTH_SHORT).show();
                    } else {
                        //storing the log in session in shared preferences
                        PrefManager.getInstance(getApplicationContext()).setUserLogin(user);
                        //storing the user to SQLite database
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        db.addUser(user);
                        //starting the respective activities according to user types
                        switch (class1) {
                            case "1":
                                Intent customer = new Intent(LogIn.this, MainActivity.class);
                                customer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(customer);
                                break;
                            case "2":
                                Intent coach = new Intent(LogIn.this, Employee.class);
                                coach.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(coach);
                                break;
                        }
                    }
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
