package com.example.project.Crypto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CryptoCurrencyAdapter extends RecyclerView.Adapter<CryptoCurrencyAdapter.ViewHolder> {
    private ArrayList<CryptoModels> cyrptoArrayList;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#,##");

    public CryptoCurrencyAdapter(ArrayList<CryptoModels> cyrptoArrayList, Context context) {
        this.cyrptoArrayList = cyrptoArrayList;
        this.context = context;
    }



    public void searchList(ArrayList<CryptoModels> searchedList){
        cyrptoArrayList = searchedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CryptoCurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crypto_row_layout,parent,false);
        return new CryptoCurrencyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoCurrencyAdapter.ViewHolder holder, int position) {
        CryptoModels cryptoModels = cyrptoArrayList.get(position);
        holder.currencyName.setText(cryptoModels.getName());
        holder.symbol.setText(cryptoModels.getSymbol());
        holder.price.setText("$ "+df2.format(cryptoModels.getPrice()));

    }

    @Override
    public int getItemCount() {
        return cyrptoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView currencyName,symbol,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyName = itemView.findViewById(R.id.crypto_name);
            symbol = itemView.findViewById(R.id.crypto_symbol);
            price = itemView.findViewById(R.id.crypto_price);
        }
    }
}
