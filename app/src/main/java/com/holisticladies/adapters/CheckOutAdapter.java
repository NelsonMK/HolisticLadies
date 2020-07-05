package com.holisticladies.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holisticladies.R;
import com.holisticladies.model.Cart;

import java.util.List;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder>{
    private Context mCtx;
    private List<Cart> cartList;

    public CheckOutAdapter(Context context, List<Cart> cartList){
        this.mCtx = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CheckOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.check_out_items, null);
        return new CheckOutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        int totalPrice = cart.getQuantity() * Integer.parseInt(cart.getPrice());
        holder.product.setText(cart.getName());
        holder.quantity.setText(String.valueOf(cart.getQuantity()));
        holder.price.setText(String.valueOf(totalPrice));

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CheckOutViewHolder extends RecyclerView.ViewHolder {

        TextView product, quantity, price;

        CheckOutViewHolder(View itemView) {
            super(itemView);

            product = itemView.findViewById(R.id.product);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);

        }

    }
}
