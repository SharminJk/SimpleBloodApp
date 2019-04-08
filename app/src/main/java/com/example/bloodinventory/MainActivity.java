package com.example.bloodinventory;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"Firebase Connection Successfull",Toast.LENGTH_LONG).show();
        Button save = (Button) findViewById(R.id.SaveButton);
        final EditText name =(EditText) findViewById(R.id.Name);
        final EditText age =(EditText)findViewById(R.id.Age);
        final EditText mobile = (EditText)findViewById(R.id.mobileNumber);
        final RadioGroup bloodRadioGroup = (RadioGroup)findViewById(R.id.radioBlood);
        final EditText email = (EditText)findViewById(R.id.Email);
        final Button SearchButton = (Button) findViewById(R.id.SearchBlood);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchBlood.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String donorsname = name.getText().toString();
                int donorsAge = Integer.valueOf(age.getText().toString());
                int selectedId = bloodRadioGroup.getCheckedRadioButtonId();
                int donorsMobileNumber = Integer.valueOf(mobile.getText().toString());
                RadioButton selectedButton = (RadioButton)findViewById(selectedId);
                String donorsbloodgroup = selectedButton.getText().toString();
                String donorsEmail = email.getText().toString();
                FirebaseApp.initializeApp(getApplicationContext());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Donors");
                String donorsId = myRef.push().getKey();
                Donor donor = new Donor();
                donor.setName(donorsname);
                donor.setAge(donorsAge);
                donor.setBloodGroup(donorsbloodgroup);
                donor.setEmail(donorsEmail);
                donor.setMobileNumber(Integer.toString(donorsMobileNumber));
                donor.setId(donorsId);
                myRef.child(donorsId).setValue(donor, new DatabaseReference.CompletionListener() {
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        System.out.println("Value was set. Error = "+error);
                    }
                });
                Toast.makeText(getApplicationContext(),donorsname,Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this,donorsAge,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),donorsbloodgroup,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),donorsEmail,Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this,donorsMobileNumber,Toast.LENGTH_LONG).show();
            }

        });
    }
}
