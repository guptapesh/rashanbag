package com.rightarrows.blinkbasket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyOtp extends AppCompatActivity {
    PinView pinView;
    Button verify_otp;
    String mobile_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mobile_number= sharedPreferences.getString("user_number","");
        pinView= findViewById(R.id.pinView);
        verify_otp= findViewById(R.id.btn_next_otp);
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinView.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(VerifyOtp.this, "Please Enter the Otp", Toast.LENGTH_SHORT).show();
                }
                else if(pinView.getText().toString().length()<4)
                {
                    Toast.makeText(VerifyOtp.this, "Enter the valid Otp", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    verifyotp(pinView.getText().toString());
                }
            }
        });
    }

    private void verifyotp(final String otp) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.VERIFY_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("hey12",response.toString());

                    JSONObject responseObj = new JSONObject(response);
                    String result = responseObj.getString("result");
                    if(result.equals("true"))
                    {
                        Toast.makeText(VerifyOtp.this, "Otp Verified", Toast.LENGTH_SHORT).show();
                        if(responseObj.getString("user").equals("false"))
                        startActivity(new Intent(getApplicationContext(), Register.class));
                        else
                            startActivity(new Intent(getApplicationContext(), Nav_Main.class));
                    }
                    else if(result.equals("false"))
                    {
                        Toast.makeText(VerifyOtp.this, "Enter a valid Otp", Toast.LENGTH_SHORT).show();
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
                params.put("otp", otp+"");
                params.put("number", mobile_number+"");
                return params;
            }

        };
        RetryPolicy policy = new DefaultRetryPolicy(Config.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        strReq.setShouldCache(false);
        Volley.newRequestQueue(this).add(strReq);
    }
}
