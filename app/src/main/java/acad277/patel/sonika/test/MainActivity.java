package acad277.patel.sonika.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import acad277.patel.sonika.test.model.Spot;

import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static acad277.patel.sonika.test.R.id.imageView;
import static acad277.patel.sonika.test.R.id.listNoteTitle;
import static acad277.patel.sonika.test.R.id.querythis;
import static acad277.patel.sonika.test.R.id.sonika;
import static acad277.patel.sonika.test.R.id.text;

import static acad277.patel.sonika.test.R.id.type;
import static android.R.string.yes;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static java.lang.System.in;


public class MainActivity extends AppCompatActivity {
    private ListView list;
    private FirebaseDatabase database;
    private DatabaseReference dbRefNotes;
    private DatabaseReference thing;
    FirebaseListAdapter mAdapter;
    private ArrayList<Spot> spots;
    private SpotAdapter spotAdapter;
    public static final String EXTRA_TYPE ="EXTRA_TYPE";
    public static final String EXTRA_SPOTNAME = "EXTRA_DRINK";

    //todo database references

    private String spotName;
    private String spotType;
    private String wifi;
    private String outlets;
    private String address;
    private String imageURL;
    private String startTime;
    private String closeTime;
    private String total;
    private TextView textChoice;
    Context context;
    int i = 1;
    int b =2;
    private Toolbar toolbar;

    public static final String SPOTS = "spots";
    String imgURl;

    public static final String EXTRA_URL = "https://finalproject-1c092.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView mylist = (ListView) findViewById(R.id.mylist);
        database = FirebaseDatabase.getInstance();
        textChoice = (TextView) findViewById(R.id.textChoice);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        // TODO: Get Support ActionBar and if not null, set the title.
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Results");
// set adapter for each spot name and details
        spots = new ArrayList<>();
        spotAdapter = new SpotAdapter(spots);
        mylist.setAdapter(spotAdapter);
        //todo get database reference paths
        dbRefNotes = database.getReference();
        thing = dbRefNotes.child("spots");
        list = (ListView) findViewById(R.id.notefragment);


        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){


                Spot f = spots.get(position);
                Intent i = new Intent(getApplicationContext(), EachDetail.class);
                //sends the spot object to each detail page
                i.putExtra(EXTRA_SPOTNAME, f);
                startActivityForResult(i,0);


            }
        });
        Intent i = getIntent();
       String typePlace =  i.getStringExtra(EXTRA_TYPE);
// query the results by choice of spot type
        Query query=thing.orderByChild("spot_type");

        if(typePlace.equals("Rooftop")){
            query = thing.orderByChild("spot_type").equalTo("Rooftop");
        }
        else if (typePlace.equals("Library")){
            query = thing.orderByChild("spot_type").equalTo("Library");

        }
        else if (typePlace.equals("Coffee Shop")){
            query = thing.orderByChild("spot_type").equalTo("Coffee Shop");

        }
        else if (typePlace.equals("Patio")){
            query = thing.orderByChild("spot_type").equalTo("Outside Patio");

        }
        else if (typePlace.equals("Park")){
            query = thing.orderByChild("spot_type").equalTo("Park");

        }
        else if (typePlace.equals("Study Room")){
            query = thing.orderByChild("spot_type").equalTo("Apt Study Rooms");

        }

        query.addValueEventListener(new ValueEventListener() {
            private int i=0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                     spotName = (String) messageSnapshot.child("spotName").getValue();
                    spotType = (String) messageSnapshot.child("spot_type").getValue();
                    wifi = (String) messageSnapshot.child("Wifi").getValue();
                    outlets = (String) messageSnapshot.child("Outlets").getValue();
                    address = (String) messageSnapshot.child("Address").getValue();
                    imageURL = (String) messageSnapshot.child("imageURL").getValue();
                    startTime = (String) messageSnapshot.child("start_time").getValue();
                    total = String.valueOf(dataSnapshot.getChildrenCount());
                    TextView wha = (TextView)findViewById(R.id.querythis);
                    closeTime = (String)messageSnapshot.child("closing_time").getValue();
                    Log.d(MainActivity.this.toString(), spotName);
                    Spot s = new Spot();
                    s.setSpotName(spotName);
                    s.setSpot_type(spotType);
                    s.setWifi(wifi);
                    s.setOutlets(outlets);
                    s.setAddress(address);
                    s.setImageURL(imageURL);
                    s.setStart_time(startTime);
                    s.setClosing_time(closeTime);
                    wha.setText(total);
                    spots.add(s);
                }





                    //to redraw the screen
                    spotAdapter.notifyDataSetChanged();
//
//                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }




    private class SpotAdapter extends ArrayAdapter<Spot>{

        //build an arraylist of SPOTS
        ArrayList<Spot> spots = new ArrayList<>();

        public SpotAdapter(ArrayList<Spot> spots ){
            super(getApplicationContext(), 0, spots);
            this.spots = spots;
        }

        //the job of getView is to create  ONE row of our listView
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_note_item,null);
            }
             final ImageView imageSpot;
            Spot f = spots.get(position);

            //references to each view within the list row
            TextView textName = (TextView) convertView.findViewById(R.id.listSpotType);
            TextView textLocation = (TextView) convertView.findViewById(R.id.listSpotName);
            TextView textAddress = (TextView)convertView.findViewById(R.id.listAddress);
            imageSpot = (ImageView)convertView.findViewById(R.id.imageView);
//

            // loads the data from the object into the view
            textName.setText(f.getSpot_type());
            textLocation.setText(f.getSpotName());
            textAddress.setText(f.getAddress());


            imgURl = f.getImageURL();

            //dynamic images CC
            Picasso.with(getApplicationContext()) //Context
                    .load(imgURl) //URL/FILE
                    .into(imageSpot);



            return convertView;
        }
    }

//toolbar link icons
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