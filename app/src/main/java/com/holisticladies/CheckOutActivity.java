package com.holisticladies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.holisticladies.adapters.CheckOutAdapter;
import com.holisticladies.database.DatabaseHandler;
import com.holisticladies.model.Cart;
import com.holisticladies.utils.RequestHandler;
import com.holisticladies.utils.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Cart> cartList;
    EditText code;
    ProgressDialog progressDialog;
    Button checkOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        recyclerView = findViewById(R.id.checkOutRecycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        cartList = new ArrayList<>();

        loadCheckOutItems();

        setUpPrices();


        checkOut = findViewById(R.id.checkOut);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkOut();

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void loadCheckOutItems(){
        DatabaseHandler db = new DatabaseHandler(CheckOutActivity.this);
        cartList.addAll(db.getSQLiteCart());
        CheckOutAdapter checkOutAdapter = new CheckOutAdapter(this, cartList);
        recyclerView.setAdapter(checkOutAdapter);

    }

    private void checkOut(){
        DatabaseHandler db = new DatabaseHandler(this);
        code = findViewById(R.id.mpesaCode);
        String m_pesa = code.getText().toString().trim();
        String total_price = String.valueOf(getTotalPrice(cartList));
        final String purchase = db.composeCartJSONFromSQLite();

        String code_pattern = "^[A-Z0-9]+$";

        if (TextUtils.isEmpty(m_pesa)){
            code.setError("Please input your MPESA CODE!");
            code.requestFocus();
            return;
        }
        if (m_pesa.length() < 10){
            code.setError("MPESA CODE cannot be less than 10 characters!");
            code.requestFocus();
            return;
        }
        if (!code.getText().toString().trim().matches(code_pattern)){
            code.setError("Please input a valid MPESA CODE!");
            code.requestFocus();
            return;
        }

        purchase purchase1 = new purchase(purchase, m_pesa, total_price);
        purchase1.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class purchase extends AsyncTask<Void, Void, String> {

        private String purchaseRequest, code, total_price;

        purchase(String purchaseRequest, String code, String total_price){
            this.purchaseRequest = purchaseRequest;
            this.code = code;
            this.total_price = total_price;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CheckOutActivity.this,"Sending order",null,false, false);
        }
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("purchase", purchaseRequest);
            params.put("code", code);
            params.put("total_price", total_price);

            return requestHandler.sendPostRequest(URLS.URL_REQUEST, params);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("CheckOut","CheckOut : "+s);
            progressDialog.dismiss();
            try {
                JSONObject obj = new JSONObject(s);
                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpPrices(){
        double subTotal1 = getTotalPrice(cartList);
        double shippingCost = 300;
        String sub_Total = String.valueOf(subTotal1);
        TextView subTotal = findViewById(R.id.subTotal);
        subTotal.setText(sub_Total);
        TextView shipping = findViewById(R.id.shippingCost);
        String string_shipping = String.valueOf(shippingCost);
        shipping.setText(string_shipping);
        TextView textView_total = findViewById(R.id.TotalCost);
        double total = subTotal1 + shippingCost;
        String string_total = String.valueOf(total);
        textView_total.setText(string_total);
    }

    private double getTotalPrice(List<Cart> carts){
        double totalCost = 0;
        for(int i = 0; i < carts.size(); i++){
            Cart cart_new = carts.get(i);
            totalCost = totalCost + Double.parseDouble(cart_new.getPrice()) * cart_new.getQuantity();
        }
        return totalCost;
    }
}
