package com.holisticladies.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holisticladies.R;
import com.holisticladies.model.Events;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> implements Filterable {

    private Context context;
    private List<Events> eventsList;
    private List<Events> filtered_eventsList;

    public EventsAdapter(Context context, List<Events> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
        this.filtered_eventsList = new ArrayList<>(eventsList);
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.product_layout, null);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        Events events = eventsList.get(position);
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    @Override
    public Filter getFilter(){
        return eventsFilter;
    }

    private Filter eventsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Events> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(filteredList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Events events : filtered_eventsList){
                    if (events.getEvent_name().toLowerCase().contains(filterPattern)){
                        filteredList.add(events);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            eventsList.clear();
            eventsList.addAll((List) results.values );
            notifyDataSetChanged();
        }
    };

    class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView item_name, item_description, item_price, item_stock;
        ImageView item_image;

        EventsViewHolder(View itemView) {
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
            Events events = eventsList.get(i);

        }
    }
}
