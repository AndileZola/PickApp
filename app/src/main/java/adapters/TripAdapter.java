package adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pickapp.HomeActivty;
import com.pickapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backend.TripModel;
import fragment.ChooseDriverFragment;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    List<TripModel> items = Collections.emptyList();
    Context context;
    public TripAdapter(Context context, List<TripModel> items)
    {
        inflater= LayoutInflater.from(context);
        this.items=items;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.trip_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position)
    {
        //holder.rateDriver.setRating(2);
        final TripModel current=items.get(position);
        holder.dest.setText("To : "+current.getDestination());
        holder.GetTaxi.setText("Get Taxi");
        holder.ViewTrip.setText("View Trip");
        //holder.seats.setText("Seats :" + current.getSeats());
        //holder.fare.setText(current.getFare());
        holder.GetTaxi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getRequestFragment();
            }
        });
        holder.ViewTrip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getTripHistoryFragment();
            }
        });

        /*holder.rateDriver.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context,"Rate "+ current.getName(),Toast.LENGTH_SHORT).show();
                Rate(current.getName());
            }
        });*/
    }
    @Override
    public int getItemCount()
    {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView dest,seats,fare,date;
        TextView GetTaxi,ViewTrip;
        LinearLayout row;
        RatingBar rateDriver;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            dest = (TextView)itemView.findViewById(R.id.destination_id);
            GetTaxi = (TextView)itemView.findViewById(R.id.get_taxi);
            ViewTrip = (TextView)itemView.findViewById(R.id.get_history);
            rateDriver = (RatingBar)itemView.findViewById(R.id.ratingBar);
            //seats = (TextView)itemView.findViewById(R.id.seats_id);
            //fare = (TextView)itemView.findViewById(R.id.fare_id);
            row = (LinearLayout) itemView.findViewById(R.id.trip_row);
            //date = (TextView)itemView.findViewById(R.id.date_id);
            //itemView.setOnClickListener(this);
        }
    }
    public void setFilter(List<TripModel> tripList)
    {
        Log.d("filter",String.valueOf(tripList.size()));
        items= new ArrayList<>();
        items.addAll(tripList);
        notifyDataSetChanged();
    }
    public void getRequestFragment()
    {
        HomeActivty homeActivty = (HomeActivty)context;
        homeActivty.getRequestFragment();
    }
    public void getTripHistoryFragment()
    {
        HomeActivty homeActivty = (HomeActivty)context;
        homeActivty.getTripHistoryFragment();
    }
    public void Rate(final String name)
    {
        //final String ss=s;
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
        LayoutInflater factory=LayoutInflater.from(context);
        final View textEntryView = factory.inflate(R.layout.rate, null);
        alt_bld.setView(textEntryView).setPositiveButton("RATE",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                Toast.makeText(context,"Rating!",Toast.LENGTH_SHORT).show();
                // context.startActivity(new Intent(context,ChatActivity.class));
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.setTitle("Rate "+name+ "'s service");
        alert.setIcon(R.mipmap.applogo);
        alert.show();
    }
}
