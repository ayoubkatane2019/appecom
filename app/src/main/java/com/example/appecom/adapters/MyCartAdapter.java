package com.example.appecom.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appecom.R;
import com.example.appecom.activities.CartActivity;
import com.example.appecom.models.MyCartModel;

import java.util.List;
import java.util.Locale;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.Viewholder> {




    Context context;
    List<MyCartModel>list;
    int totalAmount = 0;

    
    public  MyCartAdapter(Context context , List<MyCartModel>list){
        
        this.context=context;
        this.list=list;
    }
    


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder (
                LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice()+"$");
        holder.name.setText(list.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());




       /* totalAmount = Integer.parseInt(totalAmount+(String.valueOf(list.get(position).getTotalPrice())));

        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("TotalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/

//send data to other page
        totalAmount = Integer.parseInt(list.get(position).getTotalPrice());

        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("TotalAmount","120");

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class Viewholder extends RecyclerView.ViewHolder{

        TextView name,price,date,time,totalQuantity,totalPrice,overAllAmount ;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            overAllAmount = itemView.findViewById(R.id.textView3);






        }
    }
}
