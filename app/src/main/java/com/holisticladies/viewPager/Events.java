package com.holisticladies.viewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.holisticladies.R;
import com.holisticladies.utils.URLS;
import com.holisticladies.adapters.ProductAdapter;
import com.holisticladies.model.Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Events extends Fragment {

    private Context context;
    private RecyclerView recyclerView;

    private ProductAdapter productAdapter;
    private List<Products> productsList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.events, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        recyclerView = view.findViewById(R.id.events_recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        productsList = new ArrayList<>();

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                productsList.clear();
                loadProducts();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        loadProducts();

    }

    private void loadProducts(){
        @SuppressLint("StaticFieldLeak")
        class LoadProducts extends AsyncTask<Void, Void, List<com.holisticladies.model.Products>> {
            @Override
            protected void onPreExecute(){
                //progressDialog = ProgressDialog.show(context,"Fetching data",null,true,false);
                super.onPreExecute();
            }

            @Override
            protected List<com.holisticladies.model.Products> doInBackground(Void... voids){
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_PRODUCTS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Products", "response: " + response);
                                try {

                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject products = array.getJSONObject(i);

                                        productsList.add(new com.holisticladies.model.Products(
                                                products.getInt("item_id"),
                                                products.getString("item_name"),
                                                products.getString("item_description"),
                                                products.getString("item_price"),
                                                products.getString("item_image"),
                                                products.getInt("item_maker_id"),
                                                67
                                        ));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                Volley.newRequestQueue(context).add(stringRequest);

                return productsList;
            }

            @Override
            protected void onPostExecute(List<com.holisticladies.model.Products> productsList){
                productAdapter = new ProductAdapter(context, productsList);
                recyclerView.setAdapter(productAdapter);
                //progressDialog.dismiss();
                super.onPostExecute(productsList);
            }
        }

        LoadProducts loadProducts = new LoadProducts();
        loadProducts.execute();
    }
}
