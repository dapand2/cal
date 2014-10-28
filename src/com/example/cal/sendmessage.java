package com.example.cal;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

public class sendmessage extends BroadcastReceiver{
database datab;

@Override
public void onReceive(Context context, Intent intent) {
						// TODO Auto-generated method stub
	if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
						TelephonyManager.EXTRA_STATE_RINGING)) {
					try {
						sendmessage(context, intent);
						TelephonyManager telephonyManager =(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	                      
                        // Get the getITelephony() method                        
						Class<?> classTelephony;
						try {
							classTelephony = Class.forName(telephonyManager.getClass().getName());
							Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");
			                 
	                        // Ignore that the method is supposed to be private                          methodGetITelephony.setAccessible(true);
	                 
	                        // Invoke getITelephony() to get the ITelephony interface
	                        Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);
	                 
	                        // Get the endCall method from ITelephony
	                        Class<?> telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
	                        Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
	                 
	                        // Invoke endCall()
	                        methodEndCall.invoke(telephonyInterface);
						} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//silent(context);
					
					} else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
						TelephonyManager.EXTRA_STATE_IDLE)
						|| (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
								.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
					Toast.makeText(context, "call hangup", Toast.LENGTH_LONG).show();
					}
	
}


	private void sendmessage(Context context, Intent intent) throws ParseException {

		// TODO Auto-generated method stub
		String incomingnumber = intent
				.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		String starttime;
		String endtime;
		String Getdata;
		Getdata = "";
		endtime="";
		starttime="";
		datab = new database(context);
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
		Toast.makeText(context, "data read", Toast.LENGTH_LONG).show();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd, HH:mm:ss");
		String strDate = sdf.format(c.getTime());
		
		
		Date one = sdf.parse(starttime);
		Date two = sdf.parse(endtime);
		Date three=sdf.parse(strDate);
		
		if (three.after(one) && three.before(two)) {
			
			Toast.makeText(context, "call from " + incomingnumber,
					Toast.LENGTH_LONG).show();
			SmsManager manager = SmsManager.getDefault();
			manager.sendTextMessage(incomingnumber, null, Getdata, null, null);
			
		}
		
		

		

	}
}
