package com.example.termproject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Product extends AppCompatActivity {

    private ArrayList<String> productShopper = new ArrayList<>();
    private ArrayList<String> productPrice = new ArrayList<>();

    private ArrayAdapter<String> shopAdapter,priceAdapter;

    private CircleImageView image;
    private ListView shopList,priceList;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shopAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                productShopper);
        priceAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                productPrice);
        image = findViewById(R.id.image);
        shopList = findViewById(R.id.ListView);
        priceList = findViewById(R.id.ListView2);
        textView = findViewById(R.id.textView);
        shopList.setAdapter(shopAdapter);
        priceList.setAdapter(priceAdapter);
        Intent intent = getIntent();
        FindProduct(intent.getStringExtra("result"));
    }

    private void FindProduct (final String result) {
        Toast.makeText(getApplicationContext(), "Searching Product: " + result, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://192.168.1.25/Project/test.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Integer error = jsonObject.getInt("error");
                            if (error == 0)
                            {
                                String getName = jsonObject.getString("name");
                                textView.setText(getName);
                                String getImageURL = jsonObject.getString("image");
                                JSONArray getPrice = jsonObject.getJSONArray("prices");
                                JSONArray getShopper = jsonObject.getJSONArray("shoppers");
                                Picasso.get().load(getImageURL).into(image);
                                for (int i = 0; i < getPrice.length(); i++) {
                                    String newString = getPrice.get(i).toString().replace("&#8378;","₺");
                                    productPrice.add(newString);
                                    productShopper.add(getShopper.get(i).toString());
                                }
                                priceAdapter.notifyDataSetChanged();
                                shopAdapter.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Product Not Found: " + result, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("result", result);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
