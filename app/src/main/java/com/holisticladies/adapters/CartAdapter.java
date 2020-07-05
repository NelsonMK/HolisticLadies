package com.holisticladies.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.holisticladies.R;
import com.holisticladies.database.DatabaseHandler;
import com.holisticladies.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements Filterable {

    private Context context;
    private List<Cart> cartList;
    private List<Cart> filtered_cartList;

    public CartAdapter(Context context, List<Cart> cartList){
        this.context = context;
        this.cartList = cartList;
        filtered_cartList = new ArrayList<>(cartList);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.cart_list_layout, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, int position) {
        final Cart cart = cartList.get(position);

        final DatabaseHandler db = new DatabaseHandler(context);

        String price = "Kshs " + cart.getPrice();
        holder.product_name.setText(cart.getName());
        Glide.with(context)
                .load(cart.getImage())
                .into(holder.product_image);
        holder.product_price.setText(price);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = cart.getQuantity();
                quantity ++;

                int maximum = cart.getStock();
                if (quantity > maximum){
                    Toast.makeText(context, "Quantity cannot be more than the available stock (" + cart.getStock() + ")!", Toast.LENGTH_SHORT).show();
                } else {
                    cart.setQuantity(quantity);
                }


                final Handler handler = new Handler();

                Runnable setQuantity = new Runnable() {
                    @Override
                    public void run() {
                        String string_quantity = String.valueOf(cart.getQuantity());
                        holder.product_quantity.setText(string_quantity);
                        handler.postDelayed(this, 1);
                    }
                };

                handler.post(setQuantity);

                db.updateCart(cart);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cart.getQuantity();
                quantity --;

                if (quantity < 1){
                    Toast.makeText(context, "Quantity cannot be less that one!", Toast.LENGTH_SHORT).show();
                } else {
                    cart.setQuantity(quantity);
                }

                final Handler handler = new Handler();

                Runnable setQuantity = new Runnable() {
                    @Override
                    public void run() {
                        String string_quantity = String.valueOf(cart.getQuantity());
                        holder.product_quantity.setText(string_quantity);
                        handler.postDelayed(this, 1);
                    }
                };

                handler.post(setQuantity);

                db.updateCart(cart);
            }
        });

        String string_quantity = String.valueOf(cart.getQuantity());
        holder.product_quantity.setText(string_quantity);

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    @Override
    public Filter getFilter(){
        return cartFilter;
    }

    private Filter cartFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cart> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(filtered_cartList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Cart cart : filtered_cartList){
                    if (cart.getName().toLowerCase().contains(filterPattern) || cart.getPrice().toLowerCase().contains(filterPattern)){
                        filteredList.add(cart);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cartList.clear();
            cartList.addAll((List) results.values );
            notifyDataSetChanged();
        }
    };

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView product_name, product_price, product_description, product_quantity;
        ImageView product_image, delete;
        FrameLayout add, minus;

        CartViewHolder(View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_description = itemView.findViewById(R.id.product_description);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            product_image = itemView.findViewById(R.id.product_image);
            delete = itemView.findViewById(R.id.layout_delete);
            add = itemView.findViewById(R.id.layout_addQuantity);
            minus = itemView.findViewById(R.id.layout_reduceQuantity);

            delete.setOnClickListener(this);

        }
        @Override
        public void onClick(View v)
        {
            int i = getAdapterPosition();
            Cart cart = cartList.get(i);
            if (v == delete){
                DatabaseHandler db = new DatabaseHandler(context);

                db.deleteCartItem(cart);
                cartList.remove(i);
                notifyItemRemoved(i);
                notifyDataSetChanged();
                notifyItemRangeChanged(i,cartList.size());
                Toast.makeText(context, "Product removed from cart!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
