package com.mindblowninnovation.ank;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public abstract class ANHandler extends Handler
{
	public void handleMessage(Message msg)
	{
    	Log.d("Munchful Handler", "Got message");
        super.handleMessage(msg);
        //What did that async task say?
        switch (msg.what) {
            case 1://OK
                Log.d("Handler", "Got OK result");
                resultOK(msg);
                break;       
            case 2://failed
            	Log.d("Handler", "Got failed result");
            	resultFailed(msg);
            	break;
            case 3:
            	Log.d("Handler", "Got no internet result");
            	resultFailed(msg);
            	break;
        }
    }
	public void resultNoInternet(Context context){
		Toast.makeText(context, "Sorry, no internet detected!", Toast.LENGTH_SHORT).show();
	}
	//abstract methods
	public abstract void resultOK(Message msg);
	public abstract void resultFailed(Message msg);
}
