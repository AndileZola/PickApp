package fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pickapp.R;

import java.util.ArrayList;
import java.util.List;

import adapters.PassengerAdapter;
import backend.PassengerModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptedFragment extends Fragment
{
    private LinearLayout rateDriver;
    private TextView driverView;
    private RecyclerView recyclerView;
    private static PassengerAdapter adapter;
    private static List<PassengerModel> data;
    public AcceptedFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_accepted, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.passengerlist);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new PassengerAdapter(getActivity(),getTrip());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        driverView   = (TextView) view.findViewById(R.id.driver_name);
        rateDriver = (LinearLayout)view.findViewById(R.id.ratingBar);
        rateDriver.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Rate(driverView.getText().toString());
            }
        });
        return view;
    }
    public List<PassengerModel> getTrip()
    {
        data = new ArrayList<>();
        String[] Firsname = {"Andile Zola","Avumile Zola","Mondli Zola","Sanele Zola","Monde Zola","Yolanda Zola","Sabelo Zola","Mondli Zola","Sanele Zola","Avumile Zola","Monde Zola"};
        for (int i=0;i<Firsname.length;i++)
        {
            String gender = i%2==0 ? "Female" : "Male";
            PassengerModel current = new PassengerModel("",Firsname[i],"",gender,"","");
            data.add(current);
        }
        return data;
    }

    public void Rate(final String name)
    {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this.getActivity());
        LayoutInflater factory=LayoutInflater.from(this.getActivity());
        final View textEntryView = factory.inflate(R.layout.rate, null);
        alt_bld.setView(textEntryView).setPositiveButton("RATE",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                Toast.makeText(getActivity(),"Rated",Toast.LENGTH_SHORT).show();
                // context.startActivity(new Intent(context,ChatActivity.class));
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.setTitle("Rate "+name+ "'s service");
        alert.setIcon(R.mipmap.applogo);
        alert.show();
    }
}
