package com.holisticladies.model;

public class ListenerModel {

    public interface RefreshListener {
        void Refresh();
    }

    public interface FavouriteListener {
        void Refresh();
    }

    private static ListenerModel instance;
    private RefreshListener refreshListener;
    private FavouriteListener favouriteListener;
    private boolean mRefresh;
    private boolean favourite;

    public static ListenerModel getInstance() {
        if (instance == null){
            instance = new ListenerModel();
        }
        return instance;
    }

    /**
     * cartFragment methods
     * @param refreshListener to be used in any activity
     */

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void changeRefresh(boolean refresh) {
        if (refreshListener != null) {
            mRefresh = refresh;
            notifyChange();
        }
    }

    public boolean getRefresh() {
        return mRefresh;
    }

    /**
     * FavouriteFragment methods
     */
    public void setFavouriteRefreshListener(FavouriteListener favouriteRefreshListener) {
        this.favouriteListener = favouriteRefreshListener;
    }

    public void changeFavouriteRefresh(boolean refresh) {
        if (favouriteListener != null) {
            favourite = refresh;
            notifyChange();
        }
    }

    public boolean isFavourite() {
        return favourite;
    }

    /**
     * method to notify all listeners that there is change in state
     */
    private void notifyChange() {
        refreshListener.Refresh();
        favouriteListener.Refresh();
    }
}
