package acad277.patel.sonika.test;
import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import acad277.patel.sonika.test.model.Spot;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import static acad277.patel.sonika.test.R.id.getDirect;
import static android.R.attr.key;
import static android.R.id.input;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class EachDetail extends AppCompatActivity {
    String addy;
    Button getDir;
    private String URL;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView textJSON;
    private TextView theAddy;
    private Toolbar toolbar;
    double lat;
    double lon;
    Geocoder geocoder;
    List<Address> addresses;
    String position1;
    String thefullAddy;
    private TextView dist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_detail);
        TextView spotName = (TextView)findViewById(R.id.title);
        TextView spotType = (TextView)findViewById(R.id.type);
        TextView address = (TextView)findViewById(R.id.address);
        TextView wifi = (TextView)findViewById(R.id.wifilist);
        TextView outlets = (TextView)findViewById(R.id.outletslist);
        TextView startime = (TextView)findViewById(R.id.openTime);
        TextView closetime = (TextView)findViewById(R.id.closeTime);
        ImageView theImage = (ImageView)findViewById(R.id.profilePic);
        ImageView wifiImg = (ImageView)findViewById(R.id.wifiImage);
        ImageView outletImg = (ImageView)findViewById(R.id.outletImg);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

//toolbar setup
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Spot Details");


        dist = (TextView) findViewById(R.id.distance);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(this, Locale.getDefault());






        getDir = (Button)findViewById(getDirect);

        Intent i = getIntent();
        Spot s = (Spot) i.getSerializableExtra(MainActivity.EXTRA_SPOTNAME);
        spotName.setText(s.getSpotName());
        spotType.setText(s.getSpot_type());
         addy = s.getAddress();

        //get directions to spot on Google Maps
        getDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String map = "http://maps.google.co.in/maps?q=" + addy;

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(map));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                startActivity(intent);
            }
        });

        //indicate wifi/nowifi, outlets/no outlets, set img url and times
        address.setText(s.getAddress());
        String wif = s.getWifi();
        if(wif.equals("1")){
            wifiImg.setImageResource(R.drawable.wifi);
            wifi.setVisibility(View.GONE);
        }else{
            wifi.setText("No Wifi");
            wifiImg.setVisibility(View.GONE);
        }

        String outy = s.getOutlets();
        if(outy.equals("1")){
           outletImg.setImageResource(R.drawable.outlet);
            outlets.setVisibility(View.GONE);
        }else{
            outlets.setText("No Outlets");
            outletImg.setVisibility(View.GONE);
        }
       startime.setText(s.getStart_time());
        closetime.setText(s.getClosing_time());

        //converts imageURL into an imageView
        Picasso.with(getApplicationContext()) //Context
                .load(s.getImageURL()) //URL/FILE
                .into(theImage);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();


            //gets current location in latitude and longitude
                try {
                    addresses= geocoder.getFromLocation(lat, lon, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses.size() > 0)
                {

                    //finds current location and converts it into an address to parse into the URL
                    String yourAddress = addresses.get(0).getAddressLine(0);
                    String yourCity = addresses.get(0).getAddressLine(1);
                    String yourCountry = addresses.get(0).getAddressLine(2);
                    thefullAddy = yourAddress + ","+ yourCity + "," + yourCountry;
                    URL= "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + thefullAddy +  "&destinations= " +addy+ "&key=AIzaSyA4MgaFSkRfLjy9t7dPIwTfciWHBITQ3UM";

                    URL = URL.replace(" ", "");
                    new getDistance().execute(URL);


                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            } else {
                configureButton();
            }
        }

        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        Intent data = getIntent();
        //Any time we are retrieiving an object from an intent, we use seriable exra
        position1 = data.getStringExtra("EXTRA_LANGUAGE");

//


        geocoder = new Geocoder(this, Locale.getDefault());

    }

    //get distance from user to the spot from google maps API!

    class getDistance extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params){

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
//  PARSE DAT DATA
                JSONObject object = new JSONObject(s);

                JSONArray rowObject =  object.getJSONArray("rows");

                JSONObject rowFirstObject = (JSONObject) rowObject.get(0);
                Log.d("",rowFirstObject.toString());


                JSONArray elementsObject =  rowFirstObject.getJSONArray("elements");


                JSONObject elFirstObject = (JSONObject) elementsObject.get(0);

                JSONObject distanceObject =  elFirstObject.getJSONObject("distance");

                String distance= distanceObject.getString("text");
               dist.setText("You are "+distance + " from this study spot!");


            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    private void configureButton() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 10:
                if(grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_DENIED )
                    configureButton();
                return;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //one simple line
        getMenuInflater().inflate(R.menu.toolbar_icons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //step1 find the unique ID of which menu item was clicked
        //step2 take action
        int id = item.getItemId();

        switch (id) {
            case R.id.search:
                Intent i = new Intent(getApplicationContext(), SearchForm.class);

                startActivityForResult(i,1);
                break;
            case R.id.account:
                Intent b = new Intent(getApplicationContext(), AccountActivity.class);

                startActivityForResult(b,2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
