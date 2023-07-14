package com.example.project.Crypto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CryptoCurrencyActivity extends AppCompatActivity {
    EditText cryptoEditText;
    RecyclerView cryptoRecyclerView;
    ProgressBar progressBar;
    private ArrayList<CryptoModels> cyrptoArrayList;
    private CryptoCurrencyAdapter cryptoCurrencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_currency);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("Cryptocurrency");

        cryptoRecyclerView = findViewById(R.id.recyclerView);
        cryptoEditText = findViewById(R.id.cryptoSearch);
        progressBar = findViewById(R.id.progressBarLoading);
        cyrptoArrayList = new ArrayList<>();
        cryptoCurrencyAdapter = new CryptoCurrencyAdapter(cyrptoArrayList,this);
        cryptoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cryptoRecyclerView.setAdapter(cryptoCurrencyAdapter);
        getData();
        cryptoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchCrypto(editable.toString());

            }
        });
    }

    private void searchCrypto(String currency){
        ArrayList<CryptoModels> searchedList = new ArrayList<>();
        for(CryptoModels item : cyrptoArrayList){
            if (item.getName().toLowerCase().contains(currency.toLowerCase())){
                searchedList.add(item);
            }
        }
        if (searchedList.isEmpty()){

        }else {
            cryptoCurrencyAdapter.searchList(searchedList);
        }

    }

    private void getData(){
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for(int i=0; i<dataArray.length(); i++){
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        cyrptoArrayList.add(new CryptoModels(name,symbol,price));
                    }
                    cryptoCurrencyAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(CryptoCurrencyActivity.this, "Failed to extract json data...", Toast.LENGTH_SHORT).show();
                }







            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CryptoCurrencyActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","f7a0a8a2-5476-402c-88d5-55b84c9f424f");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }
}