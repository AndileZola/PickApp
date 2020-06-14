package fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pickapp.R;

public class ChangeCalculatorFragment extends Fragment
{
    EditText baseFare,Total;
    EditText paid_1,people_1;
    EditText paid_2,people_2;
    EditText paid_3,people_3;
    EditText paid_4,people_4;

    TextView change_1,change_2,change_3,change_4;

    Button calculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_change_calculator, container, false);
        GetViewsById(view);
        calculate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CalculateChange();
            }
        });
        return view;
    }
    public void GetViewsById(View view)
    {
        baseFare    = (EditText) view.findViewById(R.id.base_fare);
        Total       = (EditText) view.findViewById(R.id.Total_id);

        paid_1      = (EditText) view.findViewById(R.id.paid_1);
        paid_2      = (EditText) view.findViewById(R.id.paid_2);
        paid_3      = (EditText) view.findViewById(R.id.paid_3);
        paid_4      = (EditText) view.findViewById(R.id.paid_4);

        people_1 = (EditText) view.findViewById(R.id.people_1);
        people_2 = (EditText) view.findViewById(R.id.people_2);
        people_3 = (EditText) view.findViewById(R.id.people_3);
        people_4 = (EditText) view.findViewById(R.id.people_4);

        change_1 = (TextView) view.findViewById(R.id.change_1);
        change_2 = (TextView) view.findViewById(R.id.change_2);
        change_3 = (TextView) view.findViewById(R.id.change_3);
        change_4 = (TextView) view.findViewById(R.id.change_4);

        calculate = (Button) view.findViewById(R.id.calculate_id);
    }
    private void CalculateChange()
    {
        int people1,people2,people3,people4;
        double base_Fare = Double.valueOf(baseFare.getText().toString());
        double total     = 0;
        double fare_1,fare_2,fare_3,fare_4;
        double change1,change2,change3,change4;

        if(!paid_1.getText().toString().isEmpty() && !people_1.getText().toString().isEmpty())
        {
            fare_1 = Double.valueOf(paid_1.getText().toString());
            people1 = Integer.valueOf(people_1.getText().toString());
            change1 = fare_1 - (base_Fare * people1);
            //change_1.setText("R"+String.valueOf(change1));
            setColor(change_1,change1);
            total += (fare_1 - change1);
        }
        if(!paid_2.getText().toString().isEmpty() && !people_2.getText().toString().isEmpty())
        {
            fare_2 = Double.valueOf(paid_2.getText().toString());
            people2 = Integer.valueOf(people_2.getText().toString());
            change2 = fare_2 - (base_Fare * people2);
            //change_2.setText("R"+String.valueOf(change2));
            setColor(change_2,change2);
            total += (fare_2 - change2);
        }
        if(!paid_3.getText().toString().isEmpty() && !people_3.getText().toString().isEmpty())
        {
            fare_3 = Double.valueOf(paid_3.getText().toString());
            people3 = Integer.valueOf(people_3.getText().toString());
            change3 = fare_3 - (base_Fare * people3);
            //change_3.setText("R"+String.valueOf(change3));
            setColor(change_3,change3);
            total += (fare_3 - change3);
        }

        if(!paid_4.getText().toString().isEmpty() && !people_4.getText().toString().isEmpty())
        {
            fare_4 = Double.valueOf(paid_4.getText().toString());
            people4 = Integer.valueOf(people_4.getText().toString());
            change4 = fare_4 - (base_Fare * people4);
            //change_4.setText("R"+String.valueOf(change4));
            setColor(change_4,change4);
            total += (fare_4 - change4);
        }

        Total.setText("R"+String.valueOf(total));
    }
    private  void setColor(TextView view,double change)
    {
        if(change > 0)
            view.setTextColor(Color.GREEN);
            else if(change < 0)
                 view.setTextColor(Color.RED);
                 else if(change == 0)
                      view.setTextColor(Color.BLUE);
        if(change < 0)
            view.setText("-R"+String.valueOf(change).replace("-",""));
        else
            view.setText("R"+String.valueOf(change));
    }
}
