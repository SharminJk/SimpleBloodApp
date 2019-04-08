package com.example.bloodinventory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchBlood extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<Donor> list;
    DonorsAdapter adapter;
    Donor donor;
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        searchButton = (Button) findViewById(R.id.searchButton);
        listView = (ListView)findViewById(R.id.donorlist);
        final Button phone  = (Button)findViewById(R.id.donorPhoneinfo);
        final RadioGroup bloodgroup = (RadioGroup) findViewById(R.id.bloodgroup);
        database = FirebaseDatabase.getInstance();
        list = new ArrayList<Donor>();
        donor = new Donor();
        adapter = new DonorsAdapter(this,list);
        listView.setAdapter(adapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedid = bloodgroup.getCheckedRadioButtonId();
                RadioButton selectedbutton = (RadioButton) findViewById(selectedid);
                String blood = selectedbutton.getText().toString();
                final Query ref = database.getReference("Donors").orderByChild("bloodGroup").equalTo(blood);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds:dataSnapshot.getChildren() ){
                            donor = ds.getValue(Donor.class);
                            list.add(donor);
                        }
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Donor donor =(Donor)  parent.getItemAtPosition(position);

                        // Display the selected item text on TextView
                        //tv.setText("Your favorite : " + selectedItem);
                        Toast.makeText(getApplicationContext(),donor.name.toString(),Toast.LENGTH_LONG);
                        String phone = donor.mobileNumber.toString();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
                });
            }
        });

    }

}
