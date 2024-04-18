package utd.cs.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.*;

public class ChooseCountry extends AppCompatActivity {

    Spinner countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);

        countryList = (Spinner) findViewById(R.id.spinner);
        // call the method
        GetCountryList();

        //addListenerOnSpinnerItemSelection();
        Button button = findViewById(R.id.btnMap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass the selected country to a new activity
                Intent intent = new Intent(ChooseCountry.this, Navigate.class);
                intent.putExtra("countryName", String.valueOf(countryList.getSelectedItem()));
                startActivity(intent);
            }
        });
    }

    /**
     * This method gets a list of countries and populates the spinner
     */
    public void GetCountryList() {
        System.out.println("In GetCountyList Function");

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        // get Java inbuild Locale object, this is implemented without using API
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        // sort the list of countries using collection
        Collections.sort(countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, countries);
        countryList.setAdapter(adapter);

    }


}