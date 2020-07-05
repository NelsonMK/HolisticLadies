package com.holisticladies.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.holisticladies.CheckOutActivity;
import com.holisticladies.R;
import com.holisticladies.adapters.CartAdapter;
import com.holisticladies.database.DatabaseHandler;
import com.holisticladies.model.Cart;
import com.holisticladies.model.ListenerModel;
import com.holisticladies.utils.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements ListenerModel.RefreshListener {

    private Context context;
    private RecyclerView recyclerView;
    private List<Cart> cartList;
    private Button checkOUt;
    private DatabaseHandler db;
    private CartAdapter cartAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        ListenerModel.getInstance().setRefreshListener(this);
        boolean state = ListenerModel.getInstance().getRefresh();
        Log.i("Previous state", "" + state);

        recyclerView = view.findViewById(R.id.cartRecycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(context, LinearLayoutManager.VERTICAL,16));

        checkOUt = view.findViewById(R.id.checkOut);

        cartList = new ArrayList<>();

        getCart();

        checkOut();

    }

    private void getCart() {
        @SuppressLint("StaticFieldLeak")
        class GetCart extends AsyncTask<Void, Void, List<Cart>> {
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected List<Cart> doInBackground(Void... voids){
                db = new DatabaseHandler(context);
                cartList = db.getSQLiteCart();
                return cartList;
            }

            @Override
            protected void onPostExecute(List<Cart> cartList){
                cartAdapter = new CartAdapter(context, cartList);
                recyclerView.setAdapter(cartAdapter);
                super.onPostExecute(cartList);
            }
        }

        GetCart getCart = new GetCart();
        getCart.execute();
    }

    private void checkOut() {
        checkOUt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckOutActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void Refresh() {
        boolean state = ListenerModel.getInstance().getRefresh();
        Log.i("Present state", "" + state);
        cartList.clear();
        getCart();
    }

}
