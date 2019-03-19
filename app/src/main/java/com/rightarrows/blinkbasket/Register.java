package com.rightarrows.blinkbasket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

public class Register extends AppCompatActivity {

    EditText name,email,address;
    Button register;
    String mobile_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SharedPreferences sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mobile_number = sharedPreferences.getString("user_number", "");
        name= findViewById(R.id.user_name);
        email= findViewById(R.id.user_email);
        address= findViewById(R.id.user_address);
        register= findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().isEmpty())
                    Toast.makeText(Register.this, "Enter the name", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isDigitsOnly(name.getText().toString().trim()))
                    Toast.makeText(getApplicationContext(), R.string.only_digits_not_allowed, Toast.LENGTH_SHORT).show();
               else  if (!name.getText().toString().matches("[a-zA-Z_\\s]{3,30}"))
                    Toast.makeText(getApplicationContext(), R.string.enter_valid_name, Toast.LENGTH_SHORT).show();
                else if(email.getText().toString().trim().isEmpty())
                    Toast.makeText(Register.this, "Enter the email", Toast.LENGTH_SHORT).show();
                else if( !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
                    Toast.makeText(Register.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                else if(address.getText().toString().trim().isEmpty())
                    Toast.makeText(Register.this, "Enter the address", Toast.LENGTH_SHORT).show();
                else
                {
                    sendValuesToServer(name.getText().toString(),email.getText().toString(),address.getText().toString());
                }
            }
        });
    }

    private void sendValuesToServer(final String s, final String s1, final String s2) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.USER_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject responseObj = new JSONObject(response);

                    if(responseObj.getString("error").equals("1"))
                    {
                        Toast.makeText(Register.this, "Number Not Verified. Re-verify It", Toast.LENGTH_SHORT).show();
                    }
                    else if(responseObj.getString("error").equals("2"))
                    {
                        Toast.makeText(Register.this, "Email Already Registered", Toast.LENGTH_SHORT).show();
                    }
                    else if(responseObj.getString("error").equals("4"))
                    {
                        Toast.makeText(Register.this, "Number Already Registered", Toast.LENGTH_SHORT).show();
                    }
                     else if(responseObj.getString("error").equals("4"))
                     {
                          startActivity(new Intent(getApplicationContext(),Nav_Main.class));
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
                params.put("name", s+"");
                params.put("email",s1);
                params.put("address",s2);
                params.put("number",mobile_number);
                return params;
            }

        };
        RetryPolicy policy = new DefaultRetryPolicy(Config.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        strReq.setShouldCache(false);
        Volley.newRequestQueue(this).add(strReq);
    }
}
