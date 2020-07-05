package com.holisticladies.database;

public class DbDetails {
    // Database Version
    static final int DATABASE_VERSION = 1;
    // Database Name
    static final String DATABASE_NAME = "Holistic Ladies";
    // Table names
    static final String TABLE_CART = "cart";
    static final String TABLE_FAVOURITE = "favourite";
    static final String TABLE_PROFILE = "profile";
    //Table columns
    // Cart Table Column names
    private static final String ID = "id";
    static final String CART_ID = "cart_id";
    static final String CART_NAME = "name";
    static final String CART_IMAGE= "image";
    static final String CART_PRICE= "price";
    static final String CART_QUANTITY = "quantity";
    static final String CART_STOCK = "stock";
    static final String CART_DESCRIPTION = "description";

    // Favourite Table Column names
    private static final String FAV_ID = "id";
    static final String FAVOURITE_ID = "favourite_id";
    static final String FAVOURITE_NAME = "name";
    static final String FAVOURITE_IMAGE = "image";
    static final String FAVOURITE_PRICE = "price";
    static final String FAVOURITE_DESCRIPTION = "description";

    // Profile Table Columns names
    static final String USER_ID = "id";
    static final String USER_FIRST_NAME = "first_name";
    static final String USER_LAST_NAME = "last_name";
    static final String USER_PHONE = "phone";
    static final String USER_EMAIL = "email";
    static final String USER_IMAGE = "image";
    static final String USER_D_O_B = "date_of_birth";
    static final String USER_LOCATION = "location";
    static final String USER_CLASS = "class";
    static final String USER_STATUS = "status";
    static final String USER_ARCHIVE = "archive";

    /**
     * queries
     */
    static final String profile_sql = "CREATE TABLE " + TABLE_PROFILE
            +"(" + USER_ID + " INTEGER PRIMARY KEY, "
            + USER_FIRST_NAME + " TEXT, "
            + USER_LAST_NAME + " TEXT, "
            + USER_PHONE + " VARCHAR, "
            + USER_EMAIL + " VARCHAR, "
            + USER_IMAGE + " VARCHAR, "
            + USER_D_O_B + " VARCHAR, "
            + USER_LOCATION + " INTEGER, "
            + USER_CLASS + " INTEGER, "
            + USER_STATUS + " INTEGER, "
            + USER_ARCHIVE + " INTEGER " + ");";

    static final String cart_sql = "CREATE TABLE " + TABLE_CART
            + "(" + ID + " INTEGER PRIMARY KEY, "
            + CART_ID + " INTEGER, "
            + CART_NAME + " VARCHAR, "
            + CART_IMAGE + " VARCHAR, "
            + CART_PRICE + " VARCHAR, "
            + CART_QUANTITY + " INTEGER, "
            + CART_STOCK + " INTEGER, "
            + CART_DESCRIPTION + " VARCHAR " + ");";

    static final String favourite_sql = "CREATE TABLE " + TABLE_FAVOURITE
            + "(" + FAV_ID + " INTEGER PRIMARY KEY, "
            + FAVOURITE_ID + " INTEGER, "
            + FAVOURITE_NAME + " VARCHAR, "
            + FAVOURITE_IMAGE + " VARCHAR, "
            + FAVOURITE_PRICE + " VARCHAR, "
            + FAVOURITE_DESCRIPTION + " VARCHAR " + ");";
}
