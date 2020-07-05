package com.holisticladies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holisticladies.PrefManager;
import com.holisticladies.model.Cart;
import com.holisticladies.model.Favourite;
import com.holisticladies.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Holistic Ladies";
    // Table names
    private static final String TABLE_CART = "cart";
    private static final String TABLE_FAVOURITE = "favourite";
    private static final String TABLE_PROFILE = "profile";
    //Table columns
    // Cart Table Column names
    private static final String ID = "id";
    private static final String CART_ID = "cart_id";
    private static final String CART_NAME = "name";
    private static final String CART_IMAGE= "image";
    private static final String CART_PRICE= "price";
    private static final String CART_QUANTITY = "quantity";
    private static final String CART_STOCK = "stock";
    private static final String CART_DESCRIPTION = "description";
    private static final String CART_ITEM_MAKER_ID = "item_maker_id";

    // Favourite Table Column names
    private static String FAVOURITE_ID = "favourite_id";
    private static String FAVOURITE_NAME = "name";
    private static String FAVOURITE_IMAGE = "image";
    private static String FAVOURITE_PRICE = "price";
    private static String FAVOURITE_DESCRIPTION = "description";

    // Profile Table Columns names
    private static final String USER_ID = "id";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_PHONE = "phone";
    private static final String USER_EMAIL = "email";
    private static final String USER_GENDER = "gender";
    private static final String USER_D_O_B = "date_of_birth";
    private static final String USER_LOCATION = "location";
    private static final String USER_CLASS = "class";
    private static final String USER_STATUS = "status";
    private static final String USER_ARCHIVE = "archive";


    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String profile_sql = "CREATE TABLE " + TABLE_PROFILE
                +"(" + USER_ID + " INTEGER PRIMARY KEY, "
                + USER_FIRST_NAME + " TEXT, "
                + USER_LAST_NAME + " TEXT, "
                + USER_PHONE + " VARCHAR, "
                + USER_EMAIL + " VARCHAR, "
                + USER_GENDER + " VARCHAR, "
                + USER_D_O_B + " VARCHAR, "
                + USER_LOCATION + " INTEGER, "
                + USER_CLASS + " INTEGER, "
                + USER_STATUS + " INTEGER, "
                + USER_ARCHIVE + " INTEGER " + ");";

        db.execSQL(profile_sql);

        String cart_sql = "CREATE TABLE " + TABLE_CART
                + "(" + ID + " INTEGER PRIMARY KEY, "
                + CART_ID + " INTEGER, "
                + CART_NAME + " VARCHAR, "
                + CART_IMAGE + " VARCHAR, "
                + CART_PRICE + " VARCHAR, "
                + CART_QUANTITY + " INTEGER, "
                + CART_STOCK + " INTEGER, "
                + CART_DESCRIPTION + " VARCHAR, "
                + CART_ITEM_MAKER_ID + " VARCHAR " + ");";
        db.execSQL(cart_sql);

        String FAV_ID = "id";
        String favourite_sql = "CREATE TABLE " + TABLE_FAVOURITE
                + "(" + FAV_ID + " INTEGER PRIMARY KEY, "
                + FAVOURITE_ID + " INTEGER, "
                + FAVOURITE_NAME + " VARCHAR, "
                + FAVOURITE_IMAGE + " VARCHAR, "
                + FAVOURITE_PRICE + " VARCHAR, "
                + FAVOURITE_DESCRIPTION + " VARCHAR " + ");";
        db.execSQL(favourite_sql);
    }

    // Upgrading Tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        // Create tables again
        onCreate(db);
    }

    /**
     * start of profile operations
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getId());
        values.put(USER_FIRST_NAME, user.getFirst_name());
        values.put(USER_LAST_NAME, user.getLast_name());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_GENDER, user.getGender());
        values.put(USER_D_O_B, user.getDate_of_birth());
        values.put(USER_LOCATION, user.getLocation());
        values.put(USER_CLASS, user.getClass_id());
        values.put(USER_STATUS, user.getStatus());
        values.put(USER_ARCHIVE, user.getArchive());
        // Inserting Row
        db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_PROFILE + " WHERE " + USER_ID + " = '"+ id + "'";
        Cursor cursor = db.rawQuery(sql, null);

        if ( cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        User user = new User(
                cursor.getInt(cursor.getColumnIndex(USER_ID)),
                cursor.getString(cursor.getColumnIndex(USER_FIRST_NAME)),
                cursor.getString(cursor.getColumnIndex(USER_LAST_NAME)),
                cursor.getString(cursor.getColumnIndex(USER_PHONE)),
                cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                cursor.getString(cursor.getColumnIndex(USER_GENDER)),
                cursor.getString(cursor.getColumnIndex(USER_D_O_B)),
                cursor.getString(cursor.getColumnIndex(USER_LOCATION)),
                cursor.getInt(cursor.getColumnIndex(USER_CLASS)),
                cursor.getInt(cursor.getColumnIndex(USER_STATUS)),
                cursor.getInt(cursor.getColumnIndex(USER_ARCHIVE))
        );

        cursor.close();
        db.close();

        return user;

    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_FIRST_NAME, user.getFirst_name());
        values.put(USER_LAST_NAME, user.getLast_name());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_GENDER, user.getGender());
        values.put(USER_D_O_B, user.getDate_of_birth());
        values.put(USER_LOCATION, user.getLocation());

        db.update(TABLE_PROFILE, values, USER_ID + " = ? ", new String[]{String.valueOf(user.getId())});

        db.close();
    }
    /**
     * start of cart operations
     */
    public void addToCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_ID, cart.getId());
        values.put(CART_NAME, cart.getName());
        values.put(CART_IMAGE, cart.getImage());
        values.put(CART_PRICE, cart.getPrice());
        values.put(CART_QUANTITY, cart.getQuantity());
        values.put(CART_STOCK, cart.getStock());
        values.put(CART_DESCRIPTION, cart.getDescription());
        values.put(CART_ITEM_MAKER_ID, cart.getMaker_id());
        // Inserting Row
        db.insert(TABLE_CART, null, values);
        db.close(); // Closing database connection

    }

    public void updateCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CART_QUANTITY, cart.getQuantity());

        db.update(TABLE_CART, values, CART_ID + " = ? ", new String[]{String.valueOf(cart.getId())});

        db.close();
    }

    public boolean checkIfProductExists(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String SqlQuery = "SELECT " + CART_ID + " FROM " + TABLE_CART + " WHERE " + CART_ID + " = '"+ id + "'";
        cursor = db.rawQuery(SqlQuery, null);

        boolean exists = false;
        if (cursor.moveToFirst()){
            exists = true;
        }
        cursor.close();
        db.close();
        return exists;
    }

    public List<Cart> getSQLiteCart() {
        List<Cart> cartList = new ArrayList<>();

        String sql = " SELECT * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setId(cursor.getString(cursor.getColumnIndex(CART_ID)));
                cart.setName(cursor.getString(cursor.getColumnIndex(CART_NAME)));
                cart.setImage(cursor.getString(cursor.getColumnIndex(CART_IMAGE)));
                cart.setPrice(cursor.getString(cursor.getColumnIndex(CART_PRICE)));
                cart.setQuantity(cursor.getInt(cursor.getColumnIndex(CART_QUANTITY)));
                cart.setStock(cursor.getInt(cursor.getColumnIndex(CART_STOCK)));
                cart.setDescription(cursor.getString(cursor.getColumnIndex(CART_DESCRIPTION)));
                cart.setMaker_id(cursor.getString(cursor.getColumnIndex(CART_ITEM_MAKER_ID)));
                cartList.add(cart);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cartList;

    }

    public int getCartCount() {
        String query = " SELECT * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    public void deleteCartItem(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CART, CART_ID + " = ? ", new String[]{String.valueOf(cart.getId())});
        db.close();
    }

    private int item_price(int quantity, int price){
        return quantity * price;
    }
    /**
     * Compose JSON out of SQLite records from cart table
     * @return the array that is converted to JSON
     */
    public String composeCartJSONFromSQLite() {
        ArrayList<HashMap<String, String>> cartList;
        cartList = new ArrayList<>();
        Random Number = new Random();
        int Random_number = Number.nextInt(100);
        int new_random = Number.nextInt(100);
        int third_random = Number.nextInt(1000);
        int cartId = Integer.parseInt(String.valueOf(PrefManager.getInstance(context).userId()) + (Random_number * new_random + third_random));

        String selectQuery = "SELECT * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> params = new HashMap<>();
                params.put("product_id", cursor.getString(cursor.getColumnIndex(CART_ID)));
                params.put("user_id", String.valueOf(PrefManager.getInstance(context).userId()));
                params.put("cart_id", String.valueOf(cartId));
                params.put("item_maker_id", cursor.getString(cursor.getColumnIndex(CART_ITEM_MAKER_ID)));
                params.put("quantity", String.valueOf(cursor.getInt(cursor.getColumnIndex(CART_QUANTITY))));
                params.put("item_price", String.valueOf(item_price(cursor.getInt(cursor.getColumnIndex(CART_QUANTITY)),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(CART_PRICE))))));
                cartList.add(params);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(cartList);
    }
    // end of cart operations
    /**
     * start of favourite operations
     */
    public void addToFavourite(Favourite favourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVOURITE_ID, favourite.getId());
        values.put(FAVOURITE_NAME, favourite.getName());
        values.put(FAVOURITE_IMAGE, favourite.getImage());
        values.put(FAVOURITE_PRICE, favourite.getPrice());
        values.put(FAVOURITE_DESCRIPTION, favourite.getDescription());
        // Inserting Row
        db.insert(TABLE_FAVOURITE, null, values);
        db.close(); // Closing database connection
    }

    public boolean checkIfProductExistsInFavourite(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String SqlQuery = "SELECT " + FAVOURITE_ID + " FROM " + TABLE_FAVOURITE + " WHERE " + FAVOURITE_ID + " = '"+ id + "'";
        cursor = db.rawQuery(SqlQuery, null);

        boolean exists = false;
        if (cursor.moveToFirst()){
            exists = true;
        }
        cursor.close();
        db.close();
        return exists;
    }

    public List<Favourite> getSQLiteFavourite() {
        List<Favourite> favouriteList = new ArrayList<>();

        String sql = " SELECT * FROM " + TABLE_FAVOURITE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Favourite favourite = new Favourite();
                favourite.setId(cursor.getString(cursor.getColumnIndex(FAVOURITE_ID)));
                favourite.setName(cursor.getString(cursor.getColumnIndex(FAVOURITE_NAME)));
                favourite.setImage(cursor.getString(cursor.getColumnIndex(FAVOURITE_IMAGE)));
                favourite.setPrice(cursor.getString(cursor.getColumnIndex(FAVOURITE_PRICE)));
                favourite.setDescription(cursor.getString(cursor.getColumnIndex(FAVOURITE_DESCRIPTION)));
                favouriteList.add(favourite);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return favouriteList;
    }

    public void deleteFavouriteItem(Favourite favourite) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FAVOURITE, FAVOURITE_ID + " = ? ", new String[]{String.valueOf(favourite.getId())});
        db.close();
    }
    // end of favourite operations
    /**
     * Re create database
     * Delete all tables and create them again
     * */
    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROFILE, null, null);
        db.delete(TABLE_CART, null, null);
        db.delete(TABLE_FAVOURITE, null, null);
        db.close();
    }
}
