package utd.cs.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnConvert = findViewById(R.id.btnConvert);
        btnConvert.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConvertCurrency.class);
                //intent.putExtra("message", "Hello World");
                startActivity(intent);
            }
        });

        Button btnCountry = findViewById(R.id.btnCountry);
        btnCountry.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseCountry.class);
                //intent.putExtra("message", "Hello World");
                startActivity(intent);
            }
        });

    }


}