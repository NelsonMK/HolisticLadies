package com.holisticladies.database;

class DatabaseDetails {
    // Database Version
    static final int DATABASE_VERSION = 1;
    // Database Name
    static final String DATABASE_NAME = "Holistic Ladies";
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

    // Favourite Table Column names
    private static String FAV_ID = "id";
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
    private static final String USER_IMAGE = "image";
    private static final String USER_D_O_B = "date_of_birth";
    private static final String USER_LOCATION = "location";
    private static final String USER_CLASS = "class";
    private static final String USER_STATUS = "status";
    private static final String USER_ARCHIVE = "archive";

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
