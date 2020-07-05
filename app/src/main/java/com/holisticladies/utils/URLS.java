package com.holisticladies.utils;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class URLS {

    /*public static String address(){

        class ip extends AsyncTask<String, InetAddress, String>{

            @Override
            protected String doInBackground(String... strings) {

                try {
                    InetAddress address = null;
                    address = InetAddress.getLocalHost();
                    System.out.println(address);
                    Log.i("address" ,"" + address);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                assert address != null;
                return address.toString();
            }
        }

        new ip().execute();

        return null;
    }*/

    public static String ip_address(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String address = null;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
            System.out.println(address);
            Log.i("address" ,"" + address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assert address != null;
        return address;
    }

    private static final String URL_ROOT = "http://192.168.43.192/holisticladies/Operations.php?action=";
    public static final String URL_REGISTER = URL_ROOT + "signUp";//used in SignUp activity
    public static final String URL_LOGIN = URL_ROOT + "login";// used in LogIn activity
    public static final String URL_PRODUCTS = URL_ROOT + "products";//used in products fragment
    public static final String URL_REQUEST = URL_ROOT + "place_order";//used in CheckOutActivity
    public static final String URL_FEEDBACK = URL_ROOT + "feedback";//used in profile fragment
}
