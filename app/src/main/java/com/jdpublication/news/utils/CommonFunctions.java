package com.jdpublication.news.utils;

import static com.jdpublication.news.utils.Config.API_KEY;
import static com.jdpublication.news.utils.Config.BASE_URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CommonFunctions {

    public static String getRequestURL(String country, String category) {
        return BASE_URL + "?country=" + country + "&category=" + category + "&apiKey=" + API_KEY;
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }

    public static void loadImage(Context context, ImageView imageView, String uri, int placeHolder) {
        try {
            Glide.with(context).load(uri).placeholder(placeHolder).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
