package acad277.patel.sonika.test;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class AccountActivity extends AppCompatActivity {

    private Button mLogOutBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView namet;
    private TextView emailt;
    private ImageView userPhoto;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        namet = (TextView)findViewById(R.id.name);
        emailt = (TextView)findViewById(R.id.email);
        userPhoto = (ImageView)findViewById(R.id.userPic);
        mLogOutBtn = (Button) findViewById(R.id.logOutBtn);
        mAuth = FirebaseAuth.getInstance();

        //add toolbar
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        // TODO: Get Support ActionBar and if not null, set the title.
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Account Details");


//launches login activity after logging out
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(AccountActivity.this, Login.class));
                }
            }
        };
        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            namet.setText(name);
            emailt.setText(email);
            Picasso.with(getApplicationContext()) //Context
                    .load(photoUrl) //URL/FILE
                    .into(userPhoto);

            String uid = user.getUid();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

//toolbar intents - connecting icons to dif pages
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
