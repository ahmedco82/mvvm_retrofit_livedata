package com.ahmedcom.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ahmedcom.view_model.ListViewModel;
import com.ahmedcom.view.adapter.ListMenuAdapter;
import com.json_parsing_url_retrofit.R;
import com.ahmedcom.service.model.DataModel;

import java.util.ArrayList;
import java.util.List;

//https://www.quora.com/How-do-you-parse-JSON-in-Android-when-the-JSON-array-doesnt-have-a-name
public class MainActivity extends AppCompatActivity {

     RecyclerView recyclerView;
     TextView totalTxt;
     private ListViewModel listViewModel;
     public static ArrayList<DataModel> NewMenu_person = new ArrayList<>();

     @Override
     protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
         recyclerView = (RecyclerView) findViewById(R.id.recycler);
         totalTxt = (TextView)findViewById(R.id.total_txt);
         // set a LinearLayoutManager with default vertical orientation ...........................
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
         recyclerView.setLayoutManager(linearLayoutManager);
         listViewModel.init(NewMenu_person);
         listViewModel.getAllData().observe(this , new Observer<List<DataModel>>(){
             @Override
             public void onChanged(@Nullable List<DataModel>data){
                  if(data != null){
                  ListMenuAdapter customAdapter = new ListMenuAdapter(MainActivity.this,data);
                  recyclerView.setAdapter(customAdapter);
                }
             }
       });
    }
}