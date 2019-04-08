package com.example.bloodinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class DonorsAdapter extends ArrayAdapter<Donor> {
    public DonorsAdapter(Context context, ArrayList<Donor> donors){
        super(context,0,donors);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Donor donor = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.donorview, parent, false);
        }
        // Lookup view for data population
        TextView donorName = (TextView) convertView.findViewById(R.id.donorNameinfo);
        TextView donorPhone = (TextView) convertView.findViewById(R.id.donorPhoneinfo);
        // Populate the data into the template view using the data object
        donorName.setText(donor.name);
        donorPhone.setText(donor.mobileNumber);
        // Return the completed view to render on screen
        return convertView;
    }

}
