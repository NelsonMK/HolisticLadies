package com.holisticladies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.holisticladies.utils.RequestHandler;
import com.holisticladies.utils.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText editFirst, editLast, editPhone, editEmail, editPassword;
    RadioGroup radioGender, radioUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

    }

    void init(){

        editFirst = findViewById(R.id.editTextFirstname);
        editLast = findViewById(R.id.editTextLastname);
        radioGender = findViewById(R.id.radioGender);
        editPhone = findViewById(R.id.editTextPhone);
        editEmail = findViewById(R.id.editTextEmail);
        radioUserType = findViewById(R.id.radioUserType);
        editPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignUp.this, LogIn.class));
            }
        });
    }

    private void registerUser() {
        final String first_name = editFirst.getText().toString().trim();
        final String last_name = editLast.getText().toString().trim();
        final String gender = ((RadioButton) findViewById(radioGender.getCheckedRadioButtonId())).getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String user_type = ((RadioButton) findViewById(radioUserType.getCheckedRadioButtonId())).getText().toString().trim();
        String class1 = null;
        final String password = editPassword.getText().toString().trim();

        switch (user_type){
            case "Customer": class1="1";
                break;
            case "Employee": class1="2";
                break;
        }

        //validations
        String mobile_pattern = "^07[0-9]{8}$";
        String email_pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "gmail.com$";


        if (TextUtils.isEmpty(first_name)) {
            editFirst.setError("Please enter your first name!");
            editFirst.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(last_name)) {
            editLast.setError("Please enter your last name!");
            editLast.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            editPhone.setError("Please enter your phone number!");
            editPhone.requestFocus();
            return;
        }
        if (editPhone.length()<10){
            editPhone.setError("Phone number cannot be less than 10 digits");
            editPhone.requestFocus();
            return;
        }
        if (!editPhone.getText().toString().matches(mobile_pattern)){
            editPhone.setError("Enter a valid phone number");
            editPhone.requestFocus();
            return;
        }
        if (!android.util.Patterns.PHONE.matcher(phone).matches()){
            editPhone.setError("Enter a valid phone number");
            editPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Please enter your email address!");
            editEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Enter a valid email address!");
            editEmail.requestFocus();
            return;
        }
        if (!editEmail.getText().toString().matches(email_pattern)){
            editEmail.setError("Enter a valid email address!");
            editEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Enter your password!");
            editPassword.requestFocus();
            return;
        }

        //if it passes all the validations
        //executing the async task
        RegisterUser registerUser = new RegisterUser(first_name,last_name,gender,phone,email,class1,password);
        registerUser.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class RegisterUser extends AsyncTask<Void, Void, String> {
        private ProgressBar progressBar;
        private String first_name,last_name,gender,phone,email,class1,password;
        RegisterUser(String first_name,String last_name, String gender, String phone, String email, String class1, String password){
            this.first_name = first_name;
            this.last_name = last_name;
            this.gender = gender;
            this.phone = phone;
            this.password = password;
            this.class1 = class1;
            this.email = email;
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
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("gender", gender);
            params.put("phone", phone);
            params.put("email", email);
            params.put("class", class1);
            params.put("password", password);

            //returning the response
            return requestHandler.sendPostRequest(URLS.URL_REGISTER, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("SignUp","signUp : "+s);
            //hiding the progressbar after completion
            progressBar.setVisibility(View.GONE);
            try {
                //converting response to json object
                JSONObject obj = new JSONObject(s);
                //if no error in response
                if (!obj.getBoolean("error")) {
                    //take the user to log in screen
                    startActivity(new Intent(SignUp.this, LogIn.class));
                    finish();

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
