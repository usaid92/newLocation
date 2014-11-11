package uasudios.newlocation;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity  {

    private TextView latituteField;
    private TextView longitudeField;
    float lat,log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  stopService(new Intent(this, MyService.class));
        MyService service=new MyService();
        setContentView(R.layout.activity_my);
        startService(new Intent(this, MyService.class));
       latituteField = (TextView) findViewById(R.id.TextView02);
       longitudeField = (TextView) findViewById(R.id.TextView04);
      lat=service.Latitude();
        log=service.Longitude();

        Toast.makeText(this, "location 3 : " + String.valueOf(lat)+" "+String.valueOf(log),
                Toast.LENGTH_SHORT).show();


        latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(log));


    }


}


