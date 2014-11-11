package uasudios.newlocation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service  implements LocationListener {
    public MyService() {
    }

    float lat;
    float log;

    private LocationManager locationManager;
    private String provider;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void getLocatoin(){
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");

            onLocationChanged(location);
          float lati=Latitude();
           float logi=Longitude();
           Toast.makeText(this, "location 2 : " + String.valueOf(lati)+" "+String.valueOf(logi),
                   Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "location not available", Toast.LENGTH_LONG).show();

        }
    }

    String phoneNo="03343956704";
    String sms;

    public void sendSMS(){
        try {
            sms="Latitude:"+String.valueOf(Latitude())+ "Longitude"+ String.valueOf(Longitude());
            Toast.makeText(this, "location sms:  "+sms, Toast.LENGTH_LONG).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate() {

        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();

    }
/*
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */

  /*  protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }
*/
    @Override
    public void onLocationChanged(Location location) {
         lat = (float) (location.getLatitude());
         log = (float) (location.getLongitude());

    }

    public float Latitude(){

        return lat;
    }
    public float Longitude(){

        return log;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }






   public void onStart(Intent intent, int startId) {



       final Handler handler = new Handler();
       Timer    timer = new Timer();
       TimerTask doAsynchronousTask = new TimerTask() {
           @Override
           public void run() {
               handler.post(new Runnable() {
                   @SuppressWarnings("unchecked")
                   public void run() {
                       try {
                           getLocatoin();
                           sendSMS();
                       }
                       catch (Exception e) {
                           // TODO Auto-generated catch block
                       }
                   }
               });
           }
       };
      timer.schedule(doAsynchronousTask, 0,100*1000);
//timer.schedule(doAsynchronousTask,20);


       Toast.makeText(this, "Service started ", Toast.LENGTH_LONG).show();

  }
    /*
    protected void onResume() {
         super.onResume();
         locationManager.requestLocationUpdates(provider, 400, 1, this);
         }

    // Remove the locationlistener updates when Activity is paused */
/*
           protected void onPause() {
              super.onPause();
            locationManager.removeUpdates(this);
         }
*/
 //   @Override
  public void onDestroy() {
      Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
      sendBroadcast(new Intent("YouWillNeverKillMe"));
 }
}
