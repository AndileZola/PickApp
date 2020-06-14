package com.pickapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import fragment.AcceptedFragment;
import fragment.ChangeCalculatorFragment;
import fragment.ChooseDriverFragment;
import fragment.HomeFragment;
import fragment.NewRequestFragment;

public class HomeActivty extends AppCompatActivity implements View.OnClickListener,SearchView.OnQueryTextListener
{
    private Menu _menu = null;
    private Animation animationSlideInLeft, animationSlideOutRight;
    private ImageView profile,request,change,history;
    public static int FragCount=1;
    int Frag_x = 0;
    private int[] FragCountAray = new int[10];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profile  = (ImageView)  findViewById(R.id.profileImage_id);
        request  =  (ImageView) findViewById(R.id.request_id);
        change   = (ImageView)  findViewById(R.id.change_id);
        history  = (ImageView)  findViewById(R.id.trip_full_history);

        profile.setOnClickListener(this);
        request.setOnClickListener(this);
        change.setOnClickListener(this);
        history.setOnClickListener(this);

        animationSlideOutRight= AnimationUtils.loadAnimation(this,R.anim.abc_slide_in_bottom);
        animationSlideInLeft  = AnimationUtils.loadAnimation(this,R.anim.abc_slide_in_top);
        animationSlideOutRight.setDuration(1000);
        animationSlideInLeft.setDuration(1000);

       profile.startAnimation(animationSlideOutRight);
       request.startAnimation(animationSlideOutRight);
       change.startAnimation(animationSlideOutRight);
       history.startAnimation(animationSlideOutRight);

       getHomeFragment();
    }
    @Override
public void onClick(View v)
{
    int id = v.getId();
    switch(id)
    {
        case R.id.profileImage_id:
        {
            //getProfileFragment("");
            if(FragCount != 1)
            {
                FragCount = 1;
                //FragCountAray[Frag_x++] = FragCount;
                hideSearch(true);
                getHomeFragment();
                profile.startAnimation(animationSlideInLeft);
                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_right);
            }
        }
        break;
        case R.id.request_id:
        {
            //getChatFragment();
            if(FragCount != 2) //Avoiding same fragments to stack on top of each other
            {
                FragCount = 2;
                //FragCountAray[Frag_x++] = FragCount;
                hideSearch(false);
                getRequestFragment();
                request.startAnimation(animationSlideInLeft);
                overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_right);
            }
        }
        break;
        case R.id.change_id:
        {
            if(FragCount != 4) //Avoiding same fragments to stack on top of each other
            {
                FragCount = 4;
                //FragCountAray[Frag_x++] = FragCount;
                hideSearch(false);
                getChangeFragment();
                change.startAnimation(animationSlideInLeft);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
        }
        break;
        case R.id.trip_full_history:
        {
            FragCount = 3;
            //FragCountAray[Frag_x++] = FragCount;
            history.startAnimation(animationSlideInLeft);
        }
        break;
    }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_activty, menu);
        this._menu = menu;
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        if (id == R.id.logout)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    public void hideSearch(boolean aTrue)
    {
        Log.d("TAG","Hidden");
        _menu.getItem(2).setVisible(aTrue);
    }
    @Override
    public boolean onQueryTextChange(String newText)
    {
        switch (FragCount)
        {
            case 1:
            {
                hideSearch(false);
                HomeFragment.query(newText);
            }
            break;
            case 2:
            {
                hideSearch(false);
                HomeFragment.query(newText);
            }
            break;
            case 3:
            {
                hideSearch(false);
            }
            break;
            case 4:
            {
                hideSearch(true);
            }
            break;
        }
        return true;
    }
    public boolean onQueryTextSubmit(String query){return false;}
    @Override
    public void onBackPressed()
    {
        //Fragment newRequestFragment = getFragmentManager().findFragmentById(R.id.home_frag_id);
        //Log.d("Frag",String.valueOf(FragCountAray[Frag_x]));
        //if(!(FragCountAray[Frag_x]==1))
          //super.onBackPressed();
    }
    public void getHomeFragment()
    {
        FragCount=1;
        //FragCountAray[Frag_x++] = FragCount;
        getSupportFragmentManager()
        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        .replace(R.id.content_frame,new HomeFragment(), "Home Fragment")
        .addToBackStack(null)
        .commit();
    }
    public void getRequestFragment()
    {
        //FragCount=0;
        getSupportFragmentManager()
        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        .replace(R.id.content_frame,new NewRequestFragment(), "Request Fragment")
        .addToBackStack(null)
        .commit();
    }
    public void getAcceptedFragment()
    {
        getSupportFragmentManager()
        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        .replace(R.id.content_frame,new AcceptedFragment(), "Driver Fragment")
        .addToBackStack(null)
        .commit();
    }
    public void getChangeFragment()
    {
        getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(R.id.content_frame,new ChangeCalculatorFragment(), "Change Fragment")
                .addToBackStack(null)
                .commit();
    }
    public void getTripHistoryFragment()
    {
        getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(R.id.content_frame, new AcceptedFragment(), "Request Fragment")
                .addToBackStack(null)
                .commit();
    }

}
