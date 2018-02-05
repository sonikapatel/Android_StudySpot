package acad277.patel.sonika.test;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ImageView;

public class SearchForm extends AppCompatActivity {
    ImageButton hotel;
    ImageButton apt;
    ImageButton park;
    ImageButton coffee;
    ImageButton library;
    ImageButton patio;
    private Toolbar toolbar;
    public static final String INDEX = "INDEX";
    private int Index;

    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_form);

        apt = (ImageButton) findViewById(R.id.StudyRoom);
        park = (ImageButton)findViewById(R.id.Park);
        library = (ImageButton)findViewById(R.id.Library);
        coffee = (ImageButton)findViewById(R.id.coffee);
        hotel = (ImageButton)findViewById(R.id.Rooftop);
        patio = (ImageButton)findViewById(R.id.Patio);



        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        // TODO: Get Support ActionBar and if not null, set the title.
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Find a Spot");


//set each button to send the selected spot type to next page and launch next page

        apt.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(MainActivity.EXTRA_TYPE,"Study Room");

                startActivityForResult(i,1);



            }

        });

        park.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(MainActivity.EXTRA_TYPE,"Park");

                startActivityForResult(i,1);



            }

        });
        library.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(MainActivity.EXTRA_TYPE,"Library");

                startActivityForResult(i,1);
            }

        });
        coffee.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(MainActivity.EXTRA_TYPE,"Coffee Shop");

                startActivityForResult(i,1);
            }

        });
        hotel.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(MainActivity.EXTRA_TYPE,"Rooftop");

                startActivityForResult(i,1);
            }

        });
        patio.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(MainActivity.EXTRA_TYPE,"Patio");

                startActivityForResult(i,1);
            }

        });
    }

//toolbar - get icons to link to pages
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //one simple line
        getMenuInflater().inflate(R.menu.toolbar_icons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
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
