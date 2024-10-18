package com.example.electric_bill_management.Customer_RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electric_bill_management.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchAdapter extends RecyclerView.Adapter<CustomerSearchAdapter.MyViewHolder> {
    Context context;
    List<CustomerSearchModel> customerModels;
    List<CustomerSearchModel> customerFilter;

    public CustomerSearchAdapter(Context context, List<CustomerSearchModel> customerModels){
        this.context = context;
        this.customerModels = customerModels;
        this.customerFilter = new ArrayList<>(customerModels);
    }

    @NonNull
    @Override
    public CustomerSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_customer,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerSearchAdapter.MyViewHolder holder, int position) {
        CustomerSearchModel customer = customerFilter.get(position);
        holder.customerId.setText(customer.getId());
        holder.customerName.setText(customer.getName());
        holder.customerMonth.setText(customer.getMonth());
        holder.customerAddress.setText(customer.getAddress());
        holder.customerAmount.setText(customer.getAmount());
        holder.customerType.setText(customer.getUserType());
        holder.customerPrice.setText(customer.getPrice());

    }

    @Override
    public int getItemCount() {
        return customerFilter.size();
    }

    public void filter(String type, String query) {
        customerFilter.clear();
        if (query.isEmpty()) {
            customerFilter.addAll(customerModels);
        } else {
            for (CustomerSearchModel customer : customerModels) {
                if (type.equals("Name")) {
                    if (customer.getName().toLowerCase().contains(query.toLowerCase())) {
                        customerFilter.add(customer);
                    }
                } else {
                    if (customer.getAddress().toLowerCase().contains(query.toLowerCase())) {
                        customerFilter.add(customer);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView customerId, customerName, customerMonth, customerAddress, customerAmount, customerType, customerPrice;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            customerId = itemView.findViewById(R.id.id);
            customerName = itemView.findViewById(R.id.name);
            customerMonth = itemView.findViewById(R.id.month);
            customerAddress = itemView.findViewById(R.id.address);
            customerAmount = itemView.findViewById(R.id.electricUsedAmount);
            customerType = itemView.findViewById(R.id.userType);
            customerPrice = itemView.findViewById(R.id.price);
        }
    }
}
