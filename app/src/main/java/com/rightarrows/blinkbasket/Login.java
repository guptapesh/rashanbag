package com.rightarrows.blinkbasket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText number;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        number= findViewById(R.id.et_mobile_number);
        verify= findViewById(R.id.btn_next_otp);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(Login.this, "Please Enter the Number", Toast.LENGTH_SHORT).show();
                }
                else if(number.getText().toString().length()<10)
                    Toast.makeText(Login.this, "Enter the valid mobile number", Toast.LENGTH_SHORT).show();
                else
                {
                    SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_number",number.getText().toString());
                    editor.apply();
                    sendNumber(number.getText().toString());
                }
            }
        });
    }

    private void sendNumber(final String s) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.SEND_NUMBER, new Response.Listener<String>() {
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
                params.put("number", s+"");
                return params;
            }

        };
        RetryPolicy policy = new DefaultRetryPolicy(Config.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        strReq.setShouldCache(false);
        Volley.newRequestQueue(this).add(strReq);
    }
}
