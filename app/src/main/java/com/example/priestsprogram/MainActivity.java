package com.example.priestsprogram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;
import java.util.Vector;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    Button btnAdd, btnRead, btnClear;
    EditText etData, etEmail;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener((View.OnClickListener) this);

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener((View.OnClickListener) this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener((View.OnClickListener) this);

        etData = findViewById(R.id.etData);
        etData.setOnClickListener((View.OnClickListener) this);

        etEmail = findViewById(R.id.etEmail);
        etEmail.setOnClickListener((View.OnClickListener) this);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = new DatabaseHelper(this);

    }



    public void onClick(View v) {

        String name = etData.getText().toString();
        String email = etEmail.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {
            case R.id.btnAdd:
                contentValues.put(DatabaseHelper.KEY_NAME, name);
                contentValues.put(DatabaseHelper.KEY_MAIL, email);

                database.insert(DatabaseHelper.TABLE_CONTACTS, null, contentValues);
                break;

            case R.id.btnRead:
                Cursor cursor = database.query(DatabaseHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if(cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DatabaseHelper.KEY_MAIL);
                        do {
                            Log.d("mLog", "ID = "+ cursor.getInt(idIndex)+
                                    ", name = " + cursor.getString(nameIndex) +
                                    ", email = " + cursor.getString(emailIndex));
                        } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");

                cursor.close();;
                break;

            case R.id.btnClear:
                database.delete(DatabaseHelper.TABLE_CONTACTS, null, null);
                break;
        }
        dbHelper.close();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


}
