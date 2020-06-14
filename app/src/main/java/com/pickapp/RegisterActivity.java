package com.pickapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.GenderModel;
import backend.HttpUtility;
import backend.SectionModel;
import backend.TownshipModel;
import backend.UrlLinks;

public class RegisterActivity extends AppCompatActivity
{
    private TownshipModel TownshipArray[];
    private SectionModel SectionArray[];
    private GenderModel GenderArray[];
    List<String> TownshipNames=null;
    List<String> SectionNames=null;
    List<String> GenderNames=null;
    private JSONObject regJson = null;
    private JSONObject autocompleteJson = null;
    private JSONObject SectionsJson = null;
    private JSONObject GenderJson = null;

    private AutoCompleteTextView regnumber_Autocomplete,township_Autocomplete;
    Spinner  section_Spinner,gender_Spinner,taxi_spinner;
    EditText name,surname,regnum,password,confirmpass,email,cellphone,address;
    String   _Name,_Surname,_Cellphone,_Email,_Password,_Password2,_Gender,_RegNum,_Address,_Section,_Township,_ProPic;
    Button   submit;
    String[] gender = null;
    String[] taxi = null;
    ProgressDialog b;
    ImageView profilePicture;
    RadioGroup UserType;
    LinearLayout Driver_Fields;

    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SetViewById();
        //GetGender();
        gender = new String[]{"Select your gender","Male","Female"};
        ArrayAdapter<String> genAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gender);
        gender_Spinner.setAdapter(genAdapter);

        taxi = new String[]{"Select taxi type","Mini bus","Meter taxi"};
        ArrayAdapter<String> taxiAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,taxi);
        taxi_spinner.setAdapter(taxiAdapter);

        regnumber_Autocomplete    = (AutoCompleteTextView) findViewById(R.id.regnumber_id);
        String[] regString = new String[]{"AA123GP","BB456GP","CC789GP","DD987GP","EE654GP","FF321GP","GG123GP","HH456GP","II789GP","JJ987GP","AA111GP","BB222GP","CC333GP","DD444GP","EE555GP","FF666GP","GG777GP","HH888GP","II999GP","JJ000GP"};
        ArrayAdapter<String> regNumbers = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,regString);
        regnumber_Autocomplete.setAdapter(regNumbers);

        //township_Autocomplete = (AutoCompleteTextView) findViewById(R.id.township_id);
        //this.townshipString = new String[]{"Soshanguve","Mabopane","Garankuwa","Attridgeville","Mamelodi","Winterveld","Stinkwater"};
        //ArrayAdapter<String> townships = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,townshipString);
        //township_Autocomplete.setAdapter(townships);
        profilePicture.setScaleType(ImageView.ScaleType.FIT_XY);
        regnumber_Autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(RegisterActivity.this,"He he he",Toast.LENGTH_LONG).show();
            }
        });
        township_Autocomplete.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                TownshipAutocomplete(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s)
            {

                // TODO Auto-generated method stub
            }
        });
        township_Autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (!township_Autocomplete.getText().toString().equals(""))
                {
                    SectionNames = null;
                    section_Spinner.setAdapter(null);
                    _Township = TownshipArray[position].getId().toString();
                    GetExtensionsByTownshipId(_Township.toString());
                }
            }
        });
        section_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                Toast.makeText(RegisterActivity.this,"Hey you",Toast.LENGTH_LONG).show();
                if (!section_Spinner.getSelectedItem().toString().equals(""))
                {
                    _Section = SectionArray[position].getId().toString();
                    //Toast.makeText(RegisterActivity.this,"Section="+_Section,Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        profilePicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(RegisterActivity.this,"Hey ya",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        /*gender_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {

                if (!gender_Spinner.getSelectedItem().toString().equals(""))
                {
                    _Gender = GenderArray[position].getId().toString();
                    //Toast.makeText(RegisterActivity.this,_Gender.toString(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });*/

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _Name       =   name.getText().toString();
                _Surname    =   surname.getText().toString();
                _Cellphone  =   cellphone.getText().toString();
                _Email      =   email.getText().toString();
                _Password   =   password.getText().toString();
                _Password2  =   confirmpass.getText().toString();
                //_RegNum   =   regnum.getText().toString();
                _Gender     =   gender_Spinner.getSelectedItem().toString();
                _Address    =   address.getText().toString();

                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.user_profile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable)profilePicture.getDrawable()).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                byte [] byte_arr = stream.toByteArray();
                _ProPic = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                Log.d("ProImageImage",_ProPic);
                Log.d("ProImageImage",String.valueOf(_ProPic.length()));
//                Toast.makeText(RegisterActivity.this,_ProPic.toString().length(),Toast.LENGTH_SHORT).show();
                if(_Gender != gender[0])
                {
                    signup(_Name,_Surname,_ProPic,_Cellphone,_Email,_Password,_Password2,_Gender,_Address,_Section,_Township);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Please choose gender",Toast.LENGTH_SHORT).show();
                }
            }
        });

        UserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.passenger_type:
                          //Toast.makeText(RegisterActivity.this,"KE Passenger",Toast.LENGTH_LONG).show();
                        Driver_Fields.setVisibility(View.GONE);
                              break;
                    case R.id.driver_type:
                         //Toast.makeText(RegisterActivity.this,"KE Driver",Toast.LENGTH_LONG).show();
                        Driver_Fields.setVisibility(View.VISIBLE);
                                break;
                }

            }
        });


        final int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1)
        {
            if (!checkIfAlreadyhavePermission())
            {
                Log.d("isGranted","Not Granted");
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            else
            {
                //startYourCameraIntent();
            }
        }
    }

    private boolean checkIfAlreadyhavePermission()
    {
        int result = ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.user_profile);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
    public void SetViewById()
    {
        this.township_Autocomplete            = (AutoCompleteTextView) findViewById(R.id.township_id);
        this.regnumber_Autocomplete           = (AutoCompleteTextView) findViewById(R.id.regnumber_id);
        this.gender_Spinner                   = (Spinner)  findViewById(R.id.gender_id);
        this.section_Spinner                  = (Spinner)  findViewById(R.id.section_id);
        this.gender_Spinner                   = (Spinner)  findViewById(R.id.gender_id);
        this.name                             = (EditText) findViewById(R.id.name);
        this.surname                          = (EditText) findViewById(R.id.surname);
        this.regnum                           = (EditText) findViewById(R.id.regnumber_id);
        this.cellphone                        = (EditText) findViewById(R.id.cellnumber);
        this.email                            = (EditText) findViewById(R.id.email);
        this.password                         = (EditText) findViewById(R.id.pass1);
        this.confirmpass                      = (EditText) findViewById(R.id.pass2);
        this.address                          = (EditText) findViewById(R.id.address);
        this.submit                           = (Button)   findViewById(R.id.register_id);
        this.profilePicture                   = (ImageView) findViewById(R.id.user_profile);
        this.UserType                         = (RadioGroup) findViewById(R.id.usertype_id);
        this.taxi_spinner                     = (Spinner)  findViewById(R.id.taxi_type);
        this.Driver_Fields                    = (LinearLayout) findViewById(R.id.driver_fields);
        Driver_Fields.setVisibility(View.GONE);
    }

    //////////////////////////////////////SIGNUP\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //public void signup(final String tname,final String tsurname,final String tcellphone,final String temail,final String tpassone,final String tpasstwo,final String tgen,final String tregnum,final String taddress,final String tsection,final String ttownship)
    public void signup(final String tname,final String tsurname,final String tprofilePicture,final String tcellphone,final String temail,final String tpassone,final String tpasstwo,final String tgen,final String taddress,final String tsection,final String ttownship)
    {
        /*if(!tname.equals("")&&!tsurname.equals("")&&!tcellphone.equals("")&&!temail.equals("")&&!tpassone.equals("")&&!tpasstwo.equals("")&&!taddress.equals("")&&!tsection.equals("")&&!ttownship.equals(""))
        {*/
            new AsyncTask<String,String,String>()
            {
                String res=null;
                @Override
                protected void onPreExecute()
                {
                    b = new ProgressDialog(RegisterActivity.this);
                    RegisterActivity.this.runOnUiThread(new Runnable()
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
                        String requestURL = UrlLinks.ADD_USER;
                        params.put("firstname",tname.toString());
                        params.put("lastname",tsurname.toString());
                        params.put("propic",tprofilePicture.toString());
                        params.put("cellnumber",tcellphone.toString());
                        params.put("email",temail.toString());
                        params.put("physicaladdress",taddress.toString());
                        params.put("sectionid",tsection.toString());
                        params.put("townshipid",ttownship.toString());
                        params.put("regnum","");
                        params.put("gender",tgen.toString());
                        params.put("password",tpassone.toString());
                        params.put("passwordcomp",tpasstwo.toString());
                        Log.d("411","411 01");
                        try
                        {
                            Log.d("411","411 02");
                            HttpUtility.sendPostRequest(requestURL,params);
                            String[] response = HttpUtility.readMultipleLinesRespone();
                            StringBuffer responseBuffer = new StringBuffer();
                            for(String line : response)
                            {
                                responseBuffer.append(line).append("\n");
                                Log.d("411",line);
                            }
                            regJson = new JSONObject(responseBuffer.toString());
                            if(regJson!=null)
                                res=regJson.getString("msg").toString();

                            //Toast.makeText(RegisterActivity.this,"KE "+ res,Toast.LENGTH_LONG).show();
                            Log.d("411","411 03" + res);
                        }
                        catch (IOException ex) {ex.printStackTrace();}
                        HttpUtility.disconnect();
                    }
                    catch (Exception e)
                    {
                        Log.d("eer",e.toString());
                        return null;
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(String s)
                {
                    Log.d("411","411 04");
                    Toast.makeText(RegisterActivity.this,res,Toast.LENGTH_LONG).show();
                    b.dismiss();
                }
            }.execute();
        /*}
        else
        {
            Toast.makeText(RegisterActivity.this,"Please fill all fields",Toast.LENGTH_LONG).show();
        }*/
    }
    public void GetGender()
    {
        new AsyncTask<String,String,String>()
        {
            String message=null;
            JSONArray data;
            int success;
            String id= null;
            String name=null;
            JSONObject GenderArrayObject=null;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... param)
            {
                try
                {
                    Map<String,String> params = new HashMap<String,String>();
                    String requestURL = UrlLinks.GET_GENDER;
                    params.put("id","");
                    try
                    {
                        HttpUtility.sendPostRequest(requestURL, params);
                        //HttpUtility.sendGetRequest(requestURL);
                        String[] response = HttpUtility.readMultipleLinesRespone();
                        StringBuffer responseBuffer = new StringBuffer();
                        for(String line : response)
                        {
                            responseBuffer.append(line).append("\n");
                        }
                        GenderJson = new JSONObject(responseBuffer.toString());
                        if(GenderJson!=null)
                        {
                            message=GenderJson.getString("msg").toString();
                            success=GenderJson.getInt("success");
                            data=GenderJson.getJSONArray("data");
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    HttpUtility.disconnect();
                }
                catch (Exception e){return null;}
                return null;
            }
            @Override
            protected void onPostExecute(String s)
            {
                Log.d("GEN","3= "+GenderJson.toString());
                //Toast.makeText(RegisterActivity.this,GenderJson.toString(),Toast.LENGTH_SHORT).show();
                if(success==1)
                {
                    GenderNames= new ArrayList<>();
                    GenderArray = new  GenderModel[data.length()];

                    for(int i=0;i<data.length();i++)
                    {
                        try
                        {
                            GenderArrayObject = data.getJSONObject(i);
                            id    = GenderArrayObject.getString("genderid");
                            name  = GenderArrayObject.getString("gendername");

                            GenderArray[i] = new GenderModel(id,name);
                            GenderNames.add(name);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    String Gender[] = new String[GenderNames.size()];
                    int cnt = 0;
                    for(String nm : GenderNames)
                    {
                        Gender[cnt] = nm;
                        cnt++;
                    }
                    ArrayAdapter<String> genAdapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,Gender);
                    gender_Spinner.setAdapter(genAdapter);
                }
            }
        }.execute();
    }
    ///////////////////////////////////////////////GETTERS////////////////////////////////////////////////////
    public void TownshipAutocomplete(final String search_str)
    {
        new AsyncTask<String,String,String>()
        {
            String message=null;
            JSONArray data;
            int success;
            String id= null;
            String name=null;
            JSONObject notification=null;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... param)
            {
                try
                {
                    Map<String,String> params = new HashMap<String,String>();
                    String requestURL = UrlLinks.TOWNSHIP_AUTOCOMPLETE;
                    params.put("township",search_str);
                    try
                    {
                        HttpUtility.sendPostRequest(requestURL, params);
                        String[] response = HttpUtility.readMultipleLinesRespone();
                        StringBuffer responseBuffer = new StringBuffer();
                        for(String line : response)
                        {
                            responseBuffer.append(line).append("\n");
                        }
                        autocompleteJson = new JSONObject(responseBuffer.toString());
                        if(autocompleteJson!=null)
                        {
                            message=autocompleteJson.getString("msg").toString();
                            success=autocompleteJson.getInt("success");
                            data=autocompleteJson.getJSONArray("data");
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    HttpUtility.disconnect();
                }
                catch (Exception e){return null;}
                return null;
            }
            @Override
            protected void onPostExecute(String s)
            {
                //Toast.makeText(RegisterActivity.this,autocompleteJson.toString(),Toast.LENGTH_SHORT).show();
                if(success==1)
                {
                    TownshipNames= new ArrayList<>();
                    TownshipArray = new  TownshipModel[data.length()];

                    for(int i=0;i<data.length();i++)
                    {
                        try
                        {
                            notification = data.getJSONObject(i);
                            id    = notification.getString("townshipid");
                            name  = notification.getString("townshipname");

                            TownshipArray[i] = new TownshipModel(id,name);
                            TownshipNames.add(name);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    if(!TownshipNames.contains(township_Autocomplete.getText().toString()))
                    {
                        String tst[] = new String[TownshipNames.size()];
                        int cnt = 0;
                        for(String nm : TownshipNames)
                        {
                            tst[cnt] = nm;
                            cnt++;
                        }
                        ArrayAdapter<String> townshipAdapter =  new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_dropdown_item_1line,tst);
                        township_Autocomplete.setAdapter(townshipAdapter);
                        township_Autocomplete.showDropDown();
                        TownshipNames.clear();
                        //int indOrin = Integer.valueOf(townshipAdapter.getPosition(township_Autocomplete.getText().toString()));
                    }
                }
            }
        }.execute();
    }
    ///////////////////////////////////////////////GETTERS////////////////////////////////////////////////////
    public void GetExtensionsByTownshipId(final String townshipId)
    {
        new AsyncTask<String,String,String>()
        {
            String message=null;
            JSONArray data;
            int success;
            String id= null;
            String name=null;
            JSONObject SectionsJsonObject=null;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(String... param)
            {
                try
                {
                    Map<String,String> params = new HashMap<String,String>();
                    String requestURL = UrlLinks.GET_EXT_BY_TOWNSHIP_ID;
                    params.put("townshipid",townshipId);
                    try
                    {
                        HttpUtility.sendPostRequest(requestURL, params);
                        String[] response = HttpUtility.readMultipleLinesRespone();
                        StringBuffer responseBuffer = new StringBuffer();
                        for(String line : response)
                        {
                            responseBuffer.append(line).append("\n");
                        }
                        SectionsJson = new JSONObject(responseBuffer.toString());
                        if(SectionsJson!=null)
                        {
                            message = SectionsJson.getString("msg").toString();
                            success = SectionsJson.getInt("success");
                            data    = SectionsJson.getJSONArray("data");
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    HttpUtility.disconnect();
                }
                catch (Exception e){return null;}
                return null;
            }
            @Override
            protected void onPostExecute(String s)
            {
                if(success==1)
                {
                    Toast.makeText(RegisterActivity.this,data.toString(),Toast.LENGTH_LONG).show();
                    SectionNames = new ArrayList<>();
                    SectionArray = new SectionModel[data.length()];

                    for(int i=0;i<data.length();i++)
                    {
                        try
                        {
                            SectionsJsonObject = data.getJSONObject(i);
                            id                 = SectionsJsonObject.getString("sectionid");
                            name               = SectionsJsonObject.getString("sectionName");

                            SectionArray[i] = new SectionModel(id,name);
                            SectionNames.add(name);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    String SectionSpinnerList[] = new String[SectionNames.size()];
                    int cnt = 0;
                    for(String nm : SectionNames)
                    {
                        SectionSpinnerList[cnt] = nm;
                        cnt++;
                    }

                    ArrayAdapter<String> sectionAdapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item,SectionSpinnerList);
                    section_Spinner.setAdapter(sectionAdapter);
                    SectionNames.clear();
                    /*if(!SectionNames.contains(township_Autocomplete.getText().toString()))
                    {
                        String tst[] = new String[TownshipNames.size()];
                        int cnt = 0;
                        for(String nm : TownshipNames)
                        {
                            tst[cnt] = nm;
                            cnt++;
                        }
                        ArrayAdapter<String> townshipAdapter =  new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_dropdown_item_1line,tst);
                        township_Autocomplete.setAdapter(townshipAdapter);
                        township_Autocomplete.showDropDown();
                        TownshipNames.clear();
                        //int indOrin = Integer.valueOf(townshipAdapter.getPosition(township_Autocomplete.getText().toString()));
                    }*/
                }
            }
        }.execute();
    }
}
