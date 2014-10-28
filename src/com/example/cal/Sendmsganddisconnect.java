package com.example.cal;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.android.internal.telephony.ITelephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

public class Sendmsganddisconnect extends BroadcastReceiver{
Database datab;
private ITelephony telephonyService;

@Override
public void onReceive(Context context, Intent intent) {
						// TODO Auto-generated method stub
		if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
				TelephonyManager.EXTRA_STATE_RINGING)) {
			try {
				sendmsganddisconnect(context, intent);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// silent(context);

		} else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
				TelephonyManager.EXTRA_STATE_IDLE)
				|| (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
						.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
			//Toast.makeText(context, "call hangup", Toast.LENGTH_LONG).show();
		}
	
}


	private void sendmsganddisconnect(Context context, Intent intent) throws ParseException {

		// TODO Auto-generated method stub
		String incomingnumber = intent
				.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		String starttime;
		String endtime;
		String Getdata;
		Getdata = "";
		endtime="";
		starttime="";
		datab = new Database(context);
		datab.open();
		Cursor C = datab.returndata();
		if (C.moveToFirst()) {
			do {
				starttime=C.getString(0);
				endtime=C.getString(1);
				Getdata = C.getString(2);
			} while (C.moveToNext());
		}
		datab.close();
		//Toast.makeText(context, "data read", Toast.LENGTH_LONG).show();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd, HH:mm:ss");
		String strDate = sdf.format(c.getTime());
		
		
		Date one = sdf.parse(starttime);
		Date two = sdf.parse(endtime);
		Date three=sdf.parse(strDate);
		
		if (three.after(one) && three.before(two)) {

			// Toast.makeText(context, "call from " +
			// incomingnumber,Toast.LENGTH_LONG).show();

			//TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			TelephonyManager telephony = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			try {
				Class cls = Class.forName(telephony.getClass().getName());
				Method m = cls.getDeclaredMethod("getITelephony");
				m.setAccessible(true);
				telephonyService = (ITelephony) m.invoke(telephony);
				// telephonyService.silenceRinger();
				telephonyService.endCall();
			} catch (Exception e) {
				e.printStackTrace();
			}

			SmsManager manager = SmsManager.getDefault();
			manager.sendTextMessage(incomingnumber, null, Getdata, null, null);
			
			}
            
      

			
		}
		
		

		

	}

