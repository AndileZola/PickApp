package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pickapp.HomeActivty;
import com.pickapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backend.DriverModel;
import fragment.ChooseDriverFragment;

/**
 * Created by Andile on 21/03/2017.
 */
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    List<DriverModel> items = Collections.emptyList();
    Context context;
    public DriverAdapter(Context context, List<DriverModel> items)
    {
        inflater= LayoutInflater.from(context);
        this.items=items;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.driver_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position)
    {
        final DriverModel current=items.get(position);
        holder.Name.setText(current.getFirstName()+" "+current.getLastName());
        //holder.Cell.setText(current.getCellNumber());
        //holder.Registration.setText(current.getRegistration());
        holder.row.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HomeActivty homeActivty = (HomeActivty)context;
                homeActivty.getAcceptedFragment();
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView Name,Cell,Registration;
        LinearLayout row;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            Name = (TextView)itemView.findViewById(R.id.firstname);
            Cell = (TextView)itemView.findViewById(R.id.cellnumber);
            Registration = (TextView)itemView.findViewById(R.id.regnumber);
            row = (LinearLayout) itemView.findViewById(R.id.driver_row);
            //date = (TextView)itemView.findViewById(R.id.date_id);
            //itemView.setOnClickListener(this);
        }
    }
    public void setFilter(List<DriverModel> driverList)
    {
        Log.d("filter",String.valueOf(driverList.size()));
        items= new ArrayList<>();
        items.addAll(driverList);
        notifyDataSetChanged();
    }
    public void getAcceptedFragment()
    {
        //HomeActivty homeActivty = (HomeActivty)context;
        //homeActivty.startActivity(new Intent(homeActivty, AcceptedActivity.class));
        //homeActivty.getAcceptedFragment();
    }
}
