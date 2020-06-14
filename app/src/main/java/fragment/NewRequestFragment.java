package fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pickapp.HomeActivty;
import com.pickapp.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import backend.HttpUtility;
import backend.UrlLinks;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment implements View.OnClickListener
{
    Button request;
    RadioGroup OriginType,DestinationType,DriverSearch;
    EditText OriginLocation,DestinationLocation,RouteDescription,Seats,Fare;
    String __OriginType,_OriginLocation,_DestinationType,_DestinationLocation,_RouteDescription,_Seats,_Fare,_DriverSearch;
    ProgressDialog b;
    private JSONObject requestJson = null;

    public NewRequestFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_new_request, container, false);
        SetViewsId(view);
        request.setOnClickListener(this);
        OriginType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.home_address:
                        break;
                    case R.id.my_location:
                        break;
                }
            }
        });

        DestinationType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.to_town:
                        break;
                    case R.id.to_local:
                        break;
                }
            }
        });

        DriverSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.close_driver:
                        break;
                    case R.id.choose_driver:
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch(id)
        {
            case R.id.submit_request:
            {
                /*__OriginType = 1;
                _OriginLocation = ,
                _DestinationType = ,
                _DestinationLocation = ,
                _RouteDescription,
                _Seats,
                _Fare,
                _DriverSearch*/
                AddRequest("1","11.2222","15.2222","21.2222","31.2222","Betreax","Andries","1","1","2");
            }
            break;
        }
    }
    private void SetViewsId(View view)
    {
        request             = (Button)view.findViewById(R.id.submit_request);
        OriginType          = (RadioGroup) view.findViewById(R.id.pickup_location);
        DestinationType     = (RadioGroup) view.findViewById(R.id.destination_location);
        DriverSearch        = (RadioGroup) view.findViewById(R.id.driver_search);
        OriginLocation      = (EditText) view.findViewById(R.id.passenger_origin);
        DestinationLocation = (EditText) view.findViewById(R.id.passenger_dest);
        RouteDescription    = (EditText) view.findViewById(R.id.route_description);
        Seats               = (EditText) view.findViewById(R.id.booked_seats);
        Fare                = (EditText) view.findViewById(R.id.trip_fare);
    }

    //////////////////////////////////////SEND REQUEST\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void AddRequest(final String tuserid,final String toriginlongitude,final String toriginlatitude,final String tdestlongitude,final String tdestlatitude,final String tdestinationname,final String tdestdescription,final String tseats,final String torigintypeid,final String ttriptypeid)
    {
        new AsyncTask<String,String,String>()
        {
            String ResponseMessage =null;
            String ResponseSuccess =null;
            @Override
            protected void onPreExecute()
            {
                b = new ProgressDialog(getActivity());
                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        b.setTitle("");
                        b.setCancelable(true);
                        b.setMessage("Please wait...");
                        b.show();
                    }
                });
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... param)
            {
                try
                {
                    Map<String,String> params = new HashMap<String,String>();
                    String requestURL = UrlLinks.ADD_REQUEST;
                    params.put("userid",tuserid.toString());
                    params.put("originlongitude",toriginlongitude.toString());
                    params.put("originlatitude",toriginlatitude.toString());
                    params.put("destlongitude",tdestlongitude.toString());
                    params.put("destlatitude",tdestlatitude.toString());
                    params.put("destinationname",tdestinationname.toString());
                    params.put("destdescription",tdestdescription.toString());
                    params.put("seats",tseats.toString());
                    params.put("origintypeid",torigintypeid.toString());
                    params.put("triptypeid",ttriptypeid.toString());

                    try
                    {
                        HttpUtility.sendPostRequest(requestURL,params);
                        String[] response = HttpUtility.readMultipleLinesRespone();
                        StringBuffer responseBuffer = new StringBuffer();
                        for(String line : response)
                        {
                            Log.d("line", line.toString());
                            responseBuffer.append(line).append("\n");
                        }
                        requestJson = new JSONObject(responseBuffer.toString());
                        Log.d("requestJson", requestJson.toString());
                        if(requestJson!=null)
                        {
                            ResponseMessage = requestJson.getString("msg").toString();
                            ResponseSuccess = requestJson.getString("success").toString();
                        }

                        Log.d("ServerResponse", ResponseMessage);
                    }
                    catch (IOException ex) {ex.printStackTrace();}
                    HttpUtility.disconnect();
                }
                catch (Exception e)
                {
                    return null;
                }
                return null;
            }
            @Override
            protected void onPostExecute(String s)
            {
                b.dismiss();
                if(ResponseSuccess.equals("1"))
                {
                    Log.d("ResponseSuccess", ResponseSuccess);
                    Toast.makeText(getActivity(),ResponseMessage,Toast.LENGTH_LONG).show();
                    HomeActivty.FragCount = 4;
                    getDriverFragment();
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_right);
                }
            }
        }.execute();
    }
    public void getDriverFragment()
    {
        getFragmentManager()
                .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(R.id.content_frame, new ChooseDriverFragment(), "Request Fragment")
                .addToBackStack(null)
                .commit();
    }
}
