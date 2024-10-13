package com.example.electric_bill_management.Customer_RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electric_bill_management.R;

import java.util.ArrayList;

public class Customer_RecyclerViewAdapter extends RecyclerView.Adapter<Customer_RecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<CustomerModel> customerModels;

    public  Customer_RecyclerViewAdapter(Context context, ArrayList<CustomerModel> customerModels, RecyclerViewInterface recyclerViewInterface){
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.customerModels = customerModels;
    }

    @NonNull
    @Override
    public Customer_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Customer_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.customerName.setText(customerModels.get(position).getCustomerName());
        holder.customerAddress.setText(customerModels.get(position).getCustomerAddress());
        holder.imageView.setImageResource(customerModels.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return customerModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView customerName, customerAddress;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface){
            super(itemView);

            imageView = itemView.findViewById(R.id.customerAvt);
            customerName = itemView.findViewById(R.id.nameUser);
            customerAddress = itemView.findViewById(R.id.addressUser);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
