package com.mindblowninnovation.ank;


import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ANUtils {
	
	public static final String ACTION_GET = "GET";
	public static final String ACTION_POST = "POST";
	public static final String ACTION_POST_JSON = "POST_JSON";
	
	
	Context context;
	public ANUtils(Context context){
		this.context = context;
	}
	
	public Boolean hasUsername(){
		SharedPreferences settings = context.getSharedPreferences("ANUtils",0);
		String device_id = settings.getString("username","-1");
		return !device_id.equals("-1");
	}
	public String getUsername(){
		SharedPreferences settings = context.getSharedPreferences("ANUtils",0);
		String id = settings.getString("username","-1");
		return id;
	}
	public String getPassword(){
		SharedPreferences settings = context.getSharedPreferences("ANUtils",0);
		String token = settings.getString("password","-1");
		return token;
	}
	public void setLogin(String username, String password){
		SharedPreferences settings = context.getSharedPreferences("ANUtils",0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("username", username);
		editor.putString("password", username);
		editor.commit();
	}
	
	public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
	public static String getVersionString(Context c){
    	String toReturn = "ANDROID v.";
    	toReturn += android.os.Build.VERSION.SDK_INT;
    	toReturn += " - Model:" + android.os.Build.MODEL;
    	toReturn += " by " + android.os.Build.MANUFACTURER;
    	toReturn += "("+android.os.Build.DISPLAY + ")";
    	try {
			toReturn += " - munchful v." + c.getPackageManager().getPackageInfo(c.getPackageName(), 0 ).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return toReturn;
    }
	public static String decodeParams(List<NameValuePair> params, String queryString)
	{
		if(params != null)
		{
			queryString = "?" + URLEncodedUtils.format(params, "UTF-8");
			Log.d("queryString", queryString);
		}
		else{
			queryString = "";
			Log.d("queryString", "empty query string");
		}
		return queryString;
	}
}
