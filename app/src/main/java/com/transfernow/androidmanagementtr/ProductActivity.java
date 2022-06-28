package com.transfernow.androidmanagementtr;;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.transfernow.androidmanagementtr.R;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);
        recyclerView = findViewById(R.id.recyclerView);

        mQueue = Volley.newRequestQueue(this);

//        MyAdapter myAdapter = new MyAdapter(this, ids, pictures, prices, names, descs, quantities);
//        recyclerView.setAdapter(myAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        String url = "http://192.168.0.246:3001/product";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    mTextViewResult.setText(""); // reset displaied text on screen in app
                    mTextViewResult.setMovementMethod(new ScrollingMovementMethod()); // make it scrolling
                    JSONArray jsonArray = response;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject product = jsonArray.getJSONObject(i);
                        String picture = product.getString("url");
                        Integer id = product.getInt("id");
                        String name  = product.getString("name");
                        Integer price = product.getInt("price");
                        Integer quantity = product.getInt("quantity");
                        String desc = product.getString("desc");
                        mTextViewResult.append(String.valueOf(id) + "\n" + picture + "\n" + String.valueOf(price) + "\n" +
                                name + "\n" + desc + "\n" + String.valueOf(quantity) +
                                "\n----------------------------------\n");

                    }

//                    for (int i = 0; i < ids.size(); i++) {
//                        mTextViewResult.append(ids.get(i) + " | ");
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        mQueue.add(request);
    }
}