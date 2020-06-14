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

import adapters.DriverAdapter;
import backend.DriverModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseDriverFragment extends Fragment
{
    private RecyclerView recyclerView;
    private static DriverAdapter adapter;
    private static List<DriverModel> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_driver, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.driverlist);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new DriverAdapter(getActivity(),getTrip());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        return view;
    }
    public List<DriverModel> getTrip()
    {
        data = new ArrayList<>();
        String[] Firsname = {"Taxi Driver1","Taxi Driver2","Taxi Driver3","Taxi Driver4","Taxi Driver5","Taxi Driver6","Taxi Driver7","Taxi Driver7","Taxi Driver8","Taxi Driver9","Taxi Driver10","Taxi Driver11","Taxi Driver12","Taxi Driver13","Taxi Driver14"};
        for (int i=0;i<Firsname.length;i++)
        {
            DriverModel current = new DriverModel("",Firsname[i],"","","","");
            data.add(current);
        }
        return data;
    }
    public static void query(String newText)
    {
        final List<DriverModel> filteredModelList = filter(data,newText);
        adapter.setFilter(filteredModelList);
    }
    private static List<DriverModel> filter(List<DriverModel> models,String query)
    {
        query = query.toLowerCase();
        final List<DriverModel> filteredModelList = new ArrayList<>();
        for (DriverModel model : models)
        {
            final String text = model.getFirstName().toLowerCase();
            if (text.contains(query))
            {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}

