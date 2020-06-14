package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pickapp.R;

import java.util.ArrayList;
import java.util.List;

import adapters.TripAdapter;
import backend.TripModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{
    private RecyclerView recyclerView;
    private static TripAdapter adapter;
    private static List<TripModel> data;
    public HomeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.triplist);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new TripAdapter(getActivity(),getTrip());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        return view;
    }
    public List<TripModel> getTrip()
    {
        data = new ArrayList<>();
        String[] destination = {"Pretoria","Rosslyn","Wonderpark","Pretoria","Rosslyn","Wonderpark","Pretoria","Rosslyn","Wonderpark","Pretoria","Rosslyn","Wonderpark"};
        for (int i=0;i<destination.length;i++)
        {
            TripModel current = new TripModel(destination[i],"","1","20.00 ZAR","20-05-2017");
            data.add(current);
        }
        return data;
    }
    public static void query(String newText)
    {
        final List<TripModel> filteredModelList = filter(data,newText);
        adapter.setFilter(filteredModelList);
    }
    private static List<TripModel> filter(List<TripModel> models,String query)
    {
        query = query.toLowerCase();
        final List<TripModel> filteredModelList = new ArrayList<>();
        for (TripModel model : models)
        {
            final String text = model.getDestination().toLowerCase();
            if (text.contains(query))
            {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
