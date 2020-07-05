package com.holisticladies.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.holisticladies.R;
import com.holisticladies.adapters.FavouriteAdapter;
import com.holisticladies.database.DatabaseHandler;
import com.holisticladies.model.Favourite;
import com.holisticladies.model.ListenerModel;
import com.holisticladies.utils.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements ListenerModel.FavouriteListener {

    private Context context;
    private RecyclerView recyclerView;
    private List<Favourite> favouriteList;
    private FavouriteAdapter favouriteAdapter;
    private DatabaseHandler db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favourite_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        ListenerModel.getInstance().setFavouriteRefreshListener(this);

        recyclerView = view.findViewById(R.id.favourite_recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(context, LinearLayoutManager.VERTICAL,16));

        favouriteList = new ArrayList<>();

        getFavourites();

    }

    private void getFavourites() {
        @SuppressLint("StaticFieldLeak")
        class GetFavourites extends AsyncTask<Void, Void, List<Favourite>> {
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected List<Favourite> doInBackground(Void... voids){
                db = new DatabaseHandler(context);
                favouriteList = db.getSQLiteFavourite();
                return favouriteList;
            }

            @Override
            protected void onPostExecute(List<Favourite> favouriteList){
                favouriteAdapter = new FavouriteAdapter(context, favouriteList);
                recyclerView.setAdapter(favouriteAdapter);
                super.onPostExecute(favouriteList);
            }
        }

        GetFavourites getFavourites = new GetFavourites();
        getFavourites.execute();
    }

    @Override
    public void Refresh() {
        favouriteList.clear();
        getFavourites();
    }
}
