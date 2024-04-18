package utd.cs.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class ConvertCurrency extends AppCompatActivity {

    private List<String> availableCurrencies;
    private ArrayAdapter<String> currencyAdapter;
    Spinner FromCurrency;
    Spinner ToCurrency;
    TextView xtAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_convert_currency);
        FromCurrency = (Spinner) findViewById(R.id.spnsrc);
        ToCurrency = (Spinner) findViewById(R.id.spndest);
        EditText Amount = findViewById(R.id.txtamount);
        xtAmt = (TextView) findViewById(R.id.txtResult);

        Button convert = findViewById(R.id.button3);
        availableCurrencies = new ArrayList<>();

        // call API
        try {
            getCountryCurrencies();
        } catch (IOException e) {
            e.printStackTrace();
        }

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Amount.getText().toString().isEmpty()) {
                    String frmCurr = FromCurrency.getSelectedItem().toString();
                    String toCurr = ToCurrency.getSelectedItem().toString();
                    double Value = Double.valueOf(Amount.getText().toString());

                    try {
                        convertCurrency(frmCurr, toCurr, Value);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Amount is Empty");
                }
            }
        });

    }

    public void getCountryCurrencies() throws IOException {
        List<String> arrCurrencies = new ArrayList<>();
        String URL = "https://api.apilayer.com/currency_data/list";
        String API_KEY = "fBKVxlRrGzERSYLDATsg5cReiphZHkk2";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .header("Content-Type", "application/json")
                .header("apikey", API_KEY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String responseBody = response.body().string();

                try {
                    // Parse the JSON response to get the currencies
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONObject currencies = jsonObject.getJSONObject("currencies");
                    Iterator<String> keys = currencies.keys();

                    // printing the elements of LinkedHashMap
                    for (Iterator<String> it = keys; it.hasNext(); ) {
                        String key = it.next();
                        System.out.println(key + " -- " + currencies.get(key));
                        arrCurrencies.add(key);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // populate the both from and to spinner with currency code
                            currencyAdapter = new ArrayAdapter<>(ConvertCurrency.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, arrCurrencies);
                            FromCurrency.setAdapter(currencyAdapter);
                            ToCurrency.setAdapter(currencyAdapter);
                        }
                    });

                    // Show the result in the UI (use runOnUiThread if needed)
                    //String result = String.format(Locale.getDefault(), "%.2f %s = %.2f %s", Value, "EUR", convertedValue, Curr);
                    // showToast(result); // Implement a method to show a Toast message with the result
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
/*
The JSON response is parsed to extract the "result" field,
 which contains the converted amount of money.
 The result is then displayed on the UI by setting the text of the xtAmt (presumably a TextView)
 to the converted amount (eAmt) using runOnUiThread. The runOnUiThread is used to ensure that the UI updates
  are performed on the main UI thread.
 */
    public void convertCurrency(final String fCurr, final String tCurr, final Double dAmt) throws IOException {

        String URL = "https://api.apilayer.com/currency_data/convert" + "?from=" + fCurr + "&to=" + tCurr + "&amount=" + dAmt;
        String API_KEY = "fBKVxlRrGzERSYLDATsg5cReiphZHkk2";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .header("Content-Type", "application/json")
                .header("apikey", API_KEY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String responseBody = response.body().string();

                try {
                    // Parse the JSON response to get the exchange rates
                    JSONObject jsonObject = new JSONObject(responseBody);
                    System.out.println(jsonObject.toString());
                    String result = jsonObject.getString("result");
                    System.out.println(result);
                    String eAmt = result.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            xtAmt.setText(eAmt);
                        }
                    });

                    // Show the result in the UI (use runOnUiThread if needed)
                    //String logMsg = String.format("Output>>>>", "%.2f %s = %.2f %s", dAmt, fCurr, eAmt, tCurr);
                    //showToast(logMsg); // Implement a method to show a Toast message with the result
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ConvertCurrency.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

