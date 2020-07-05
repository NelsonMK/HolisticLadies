package com.holisticladies.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.holisticladies.ProductDetails;
import com.holisticladies.R;
import com.holisticladies.model.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsViewHolder> implements Filterable {

    public static final String EXTRA_ITEM_ID = "id";
    public static final String EXTRA_ITEM_TITLE = "title";
    public static final String EXTRA_ITEM_DETAILS = "details";
    public static final String EXTRA_ITEM_IMAGE = "image";
    public static final String EXTRA_ITEM_PRICE = "price";
    public static final String EXTRA_ITEM_MAKER_ID = "maker_id";
    private Context mCtx;
    private List<Products> productsList;
    private List<Products> new_productList;
    private int lastPosition = -1;

    public ProductAdapter(Context mCtx, List<Products> productsList) {
        this.mCtx = mCtx;
        this.productsList = productsList;
        new_productList = new ArrayList<>(productsList);
    }
    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.product_layout, null);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Products products = productsList.get(position);

        holder.item_name.setTextColor(Color.BLACK);
        holder.item_description.setTextColor(Color.BLACK);
        holder.item_price.setTextColor(Color.BLACK);
        holder.item_name.setText(products.getItem_name());
        holder.item_description.setText(products.getItem_description());
        holder.item_description.setVisibility(View.GONE);
        holder.item_price.setText(products.getItem_price());
        Glide.with(mCtx)
                .load(products.getItem_image())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(holder.item_image);

        if (String.valueOf(products.getStock()).equals("0")){
            holder.item_stock.setTextColor(Color.RED);
            holder.item_stock.setText("Out of stock");
        } else {
            holder.item_stock.setTextColor(Color.BLACK);
            holder.item_stock.setText(String.valueOf(products.getStock()));
        }
        //setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mCtx, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public Filter getFilter(){
        return eventsFilter;
    }

    private Filter eventsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Products> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(new_productList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Products products : new_productList){
                    if (products.getItem_name().toLowerCase().contains(filterPattern) || products.getItem_description().toLowerCase().contains(filterPattern)
                            || products.getItem_price().toLowerCase().contains(filterPattern)){
                        filteredList.add(products);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productsList.clear();
            productsList.addAll((List) results.values );
            notifyDataSetChanged();
        }
    };

    class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView item_name, item_description, item_price, item_stock;
        ImageView item_image;

        ProductsViewHolder(View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            item_description = itemView.findViewById(R.id.item_description);
            item_price = itemView.findViewById(R.id.item_price);
            item_image = itemView.findViewById(R.id.item_image);
            item_stock = itemView.findViewById(R.id.stock);

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v)
        {
            int i = getAdapterPosition();
            Products products = productsList.get(i);

            Intent intent = new Intent(mCtx, ProductDetails.class);
            intent.putExtra(EXTRA_ITEM_ID, String.valueOf(products.getItem_id()));
            intent.putExtra(EXTRA_ITEM_TITLE, products.getItem_name());
            intent.putExtra(EXTRA_ITEM_DETAILS, products.getItem_description());
            intent.putExtra(EXTRA_ITEM_IMAGE, products.getItem_image());
            intent.putExtra(EXTRA_ITEM_PRICE, products.getItem_price());
            intent.putExtra(EXTRA_ITEM_MAKER_ID, String.valueOf(products.getItem_maker_id()));
            mCtx.startActivity(intent);

        }
    }

}
