package com.holisticladies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.holisticladies.R;
import com.holisticladies.database.DatabaseHandler;
import com.holisticladies.model.Favourite;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> implements Filterable {

    private Context context;
    private List<Favourite> favouriteList;
    private List<Favourite> filtered_favouriteList;

    public FavouriteAdapter(Context context, List<Favourite> favouriteList) {
        this.context = context;
        this.favouriteList = favouriteList;
        filtered_favouriteList = new ArrayList<>(favouriteList);
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite,parent,false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, final int position) {
        final Favourite favourite = favouriteList.get(position);

        Glide.with(context)
                .load(favourite.getImage())
                .into(holder.image);
        String currency = "Kshs." + favourite.getPrice();
        holder.price.setText(currency);

    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    @Override
    public Filter getFilter(){
        return favouritesFilter;
    }

    private Filter favouritesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Favourite> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(filtered_favouriteList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Favourite favourite : filtered_favouriteList){
                    if (favourite.getName().toLowerCase().contains(filterPattern) || favourite.getPrice().toLowerCase().contains(filterPattern)){
                        filteredList.add(favourite);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            favouriteList.clear();
            favouriteList.addAll((List) results.values );
            notifyDataSetChanged();
        }
    };

     class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image ,delete_btn;
        private TextView price;

        FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            price = itemView.findViewById(R.id.price);

            delete_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();
            Favourite favourite = favouriteList.get(i);

            if (v == delete_btn) {
                DatabaseHandler db = new DatabaseHandler(context);

                db.deleteFavouriteItem(favourite);
                favouriteList.remove(i);
                notifyItemRemoved(i);
                notifyDataSetChanged();
                notifyItemRangeChanged(i,favouriteList.size());
                Toast.makeText(context, "Product removed from favourites!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
