package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import backend.PassengerModel;

/**
 * Created by Andile on 25/03/2017.
 */
public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    List<PassengerModel> items = Collections.emptyList();
    Context context;
    public PassengerAdapter(Context context, List<PassengerModel> items)
    {
        inflater= LayoutInflater.from(context);
        this.items=items;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.passenger_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position)
    {
        final PassengerModel current=items.get(position);
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
        TextView Name,Origin,Dest,gender;
        LinearLayout row;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            Name    = (TextView)itemView.findViewById(R.id.firstname);
            Origin  = (TextView)itemView.findViewById(R.id.origin);
            Dest    = (TextView)itemView.findViewById(R.id.dest);
            gender  = (TextView) itemView.findViewById(R.id.gender);
            row     = (LinearLayout) itemView.findViewById(R.id.passenger_row);
            //date = (TextView)itemView.findViewById(R.id.date_id);
            //itemView.setOnClickListener(this);
        }
    }
    public void setFilter(List<PassengerModel> passengerList)
    {
        Log.d("filter",String.valueOf(passengerList.size()));
        items= new ArrayList<>();
        items.addAll(passengerList);
        notifyDataSetChanged();
    }
    public void getAcceptedFragment()
    {
        //HomeActivty homeActivty = (HomeActivty)context;
        //homeActivty.startActivity(new Intent(homeActivty, AcceptedActivity.class));
        //homeActivty.getAcceptedFragment();
    }
}
