package com.rightarrows.blinkbasket;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateSchedule extends AppCompatActivity {
    Button daily,alternate_days,thrice_days,minus_quantity,add_quantity,subs_main;
    private  int quantity_value=1;
    String dateSelectedByUser_start;
    String dateSelectedByUser_end;
    String item_id;

    public String getMindate() {
        return mindate;
    }

    public void setMindate(String mindate) {
        Log.d("hojo",mindate);
        this.mindate = mindate;
    }

    public String getMaxdate() {

        return maxdate;
    }

    public void setMaxdate(String maxdate) {
        this.maxdate = maxdate;
    }

    String mindate;
    String maxdate;
    TextView quantity,product_name,item_desc,item_quan,tv_item_price;
    ImageView img_product;
    int type_schedule=0;
    EditText start_date,end_date;
    Intent intent;
    boolean end_date_check=false;
    long cal_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);
        daily= findViewById(R.id.btn_daily);
        minus_quantity= findViewById(R.id.btn_minus_quantity);
        add_quantity= findViewById(R.id.btn_plus_quantity);
        img_product= findViewById(R.id.img_product);
        product_name= findViewById(R.id.product_name);
        item_desc= findViewById(R.id.item_desc1);
        alternate_days= findViewById(R.id.btn_alternate_day);
        thrice_days= findViewById(R.id.btn_every3_days);
        subs_main= findViewById(R.id.subs_main);
        start_date= findViewById(R.id.et_start_date);
        end_date= findViewById(R.id.et_end_date);
        quantity= findViewById(R.id.tv_quantity);
        item_quan= findViewById(R.id.tv_item_quantity1);
        tv_item_price= findViewById(R.id.tv_item_price);
        intent= getIntent();
        getmaxmin();
        item_quan.setText(intent.getStringExtra("item_quantity"));
        tv_item_price.setText(intent.getStringExtra("item_price"));
        item_id= intent.getStringExtra("item_id");
        item_desc.setText(intent.getStringExtra("item_desc"));
        product_name.setText(intent.getStringExtra("item_name"));

        //intent.getStringExtra("item_img")
        Glide.with(this)
                .load("http://www.pointpleasantresort.com/sites/default/files/images/Generic%20Bag(1).jpg")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.user_profile_img)
                        .fitCenter())
                .into(img_product);

        minus_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity_value==1)
                {

                }
                else {
                    quantity_value-=1;
                    quantity.setText(""+quantity_value);
                }
            }
        });
        add_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity_value+=1;
                quantity.setText(""+quantity_value);
            }
        });
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start_date.setText("");
                end_date.setText("");
                type_schedule=1;
                daily.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint));
                alternate_days.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint_less));
                thrice_days.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint_less));


            }
        });
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type_schedule==0)
                    Toast.makeText(CreateSchedule.this, "Select the frequency first", Toast.LENGTH_SHORT).show();
                else
                {

                    showDatePicker("start");
                }


            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type_schedule==0)
                    Toast.makeText(CreateSchedule.this, "Select the frequency first", Toast.LENGTH_SHORT).show();
                else if(start_date.getText().toString().isEmpty())
                    Toast.makeText(CreateSchedule.this, "Select the start date first", Toast.LENGTH_SHORT).show();
                else
                    showDatePicker("end");


            }
        });
        alternate_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_date.setText("");
                end_date.setText("");
                type_schedule=2;
                daily.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint_less));
                alternate_days.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint));
                thrice_days.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint_less));

            }
        });
        thrice_days.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                start_date.setText("");
                end_date.setText("");
                type_schedule=3;
                daily.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint_less));
                alternate_days.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint_less));
                thrice_days.setBackgroundTintList(ContextCompat.getColorStateList(CreateSchedule.this, R.color.color_tint));
            }
        });

        subs_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type_schedule==0)
                    Toast.makeText(CreateSchedule.this, "Select a Scheduling Type", Toast.LENGTH_SHORT).show();
                else if(start_date.getText().toString().isEmpty())
                {
                    Toast.makeText(CreateSchedule.this, "Select a Start date", Toast.LENGTH_SHORT).show();
                }
                else if(end_date.getText().toString().isEmpty())
                {
                    Toast.makeText(CreateSchedule.this, "Select a End Date", Toast.LENGTH_SHORT).show();
                }
                else if(end_date_check==false)
                {
                    Toast.makeText(CreateSchedule.this, "Select a valid End Date", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendValuesToServer();
                }
            }
        });

    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void sendValuesToServer() {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.SUBSCRIPTION_SCHEDULE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject responseObj = new JSONObject(response);
                    String result = responseObj.getString("result");
                    if(result.equals("true"))
                    {
                        startActivity(new Intent(getApplicationContext(), VerifyOtp.class));
                        finish();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "  : " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("quantity", quantity.getText().toString());
                params.put("start_date",start_date.getText().toString());
                params.put("end_date",end_date.getText().toString());
                params.put("uid","2");
                params.put("item_id",item_id);
                params.put("type",type_schedule+"");
                return params;
            }

        };
        RetryPolicy policy = new DefaultRetryPolicy(Config.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        strReq.setShouldCache(false);
        Volley.newRequestQueue(this).add(strReq);
    }

    private void  showDatePicker(final String type) {


        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MINUTE, 90);
        cal_in= calendar.getTimeInMillis();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        int date = calendar.get(Calendar.DATE);
        DatePickerDialog dialog = new DatePickerDialog(CreateSchedule.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                DecimalFormat mFormat= new DecimalFormat("00");
                String x =  mFormat.format(Double.valueOf(selectedMonth+1));
                if(type.equals("start"))
                {

                    dateSelectedByUser_start = selectedDay + "/" +x+ "/" + selectedYear;

                    start_date.setText(dateSelectedByUser_start);
                }

                else if(type.equals("end"))
                {
                    if(start_date.getText().toString().isEmpty())
                        Toast.makeText(CreateSchedule.this, "First select start date", Toast.LENGTH_SHORT).show();
                    else
                    {

                        Date currentTime = Calendar.getInstance().getTime();
                        String hr= currentTime.toString().substring(9,10);
                        String min= currentTime.toString().substring(12,13);
                        Log.d("rockc",hr+" "+min);
                        dateSelectedByUser_end = selectedDay + "/" +x+ "/" + selectedYear;
                        int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"),dateSelectedByUser_start, dateSelectedByUser_end);

                        if(dateDifference<0)
                            Toast.makeText(CreateSchedule.this, "Select the proper end date", Toast.LENGTH_SHORT).show();
                        else if(type_schedule==1)
                        {
                            double c= Math.ceil(dateDifference/2);
                            if(c<1)
                            {
                                end_date.setText("");
                                Toast.makeText(CreateSchedule.this, "There should be minimum 3  subscription orders", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                end_date_check= true;
                                end_date.setText(dateSelectedByUser_end);
                            }

                        }
                        else if(type_schedule==2)
                        {
                            double c= Math.ceil(dateDifference/4);
                            Log.d("bolo",dateDifference+" "+c);
                            if(c<1)
                            {
                                end_date.setText("");
                                Toast.makeText(CreateSchedule.this, "There should  minimum 3  subscription orders", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                end_date_check= true;
                                end_date.setText(dateSelectedByUser_end);
                            }
                        }
                        else if(type_schedule==3)
                        {
                            double c= Math.ceil(dateDifference/8);
                            Log.d("harry",c+"");
                            if(c<1)
                            {
                                end_date.setText("");
                                Toast.makeText(CreateSchedule.this, "There should be minimum 3  subscription orders", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                end_date_check= true;
                                end_date.setText(dateSelectedByUser_end);
                            }
                        }
                    }

                }
                }

        }, year, month, date);

        Log.d("hari",getMindate()+" "+getMaxdate());
        dialog.getDatePicker().setMinDate( Long.parseLong(getMindate()));
                dialog.getDatePicker().setMaxDate(Long.parseLong(getMaxdate()));

        dialog.show();
        }

    private void getmaxmin() {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.GET_MAX_MIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObj = new JSONObject(response);
                    mindate = responseObj.getString("min");
                    maxdate=responseObj.getString("max");
                    Log.d("bojo",maxdate+" "+mindate);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                    try{
                        Date date = sdf.parse(mindate+" 00:00:00");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        mindate=calendar.getTimeInMillis()+"";
                        setMindate(mindate);
                        Log.d("bojo2",mindate);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }

                    try{
                        Date date = sdf.parse(maxdate+" 00:00:00");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        maxdate=calendar.getTimeInMillis()+"";
                        setMaxdate(maxdate);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "  : " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) ;
        RetryPolicy policy = new DefaultRetryPolicy(Config.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        strReq.setShouldCache(false);
        Volley.newRequestQueue(this).add(strReq);


    }

    private void convertinmilliseconds(String x) {

    }
}
