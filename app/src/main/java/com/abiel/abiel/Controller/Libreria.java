package com.abiel.abiel.Controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Libreria {
    public  static  boolean isConneted(Context _context){
        try {
            ConnectivityManager cManager =  (ConnectivityManager) _context.getSystemService(_context.CONNECTIVITY_SERVICE);
            NetworkInfo _info = cManager.getActiveNetworkInfo();
            return  _info != null && _info.isAvailable() &&  _info.isConnected();
        }
        catch (Exception ex){
            return  false;
        }
    }
}
