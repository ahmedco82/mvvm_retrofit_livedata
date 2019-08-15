package com.ahmedcom.view_model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ahmedcom.service.model.DataModel;
import com.ahmedcom.service.repository.ApiClient;
import com.ahmedcom.service.repository.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListViewModel extends ViewModel {

    ArrayList<DataModel> retroModelArrayList = new ArrayList<>();

    ArrayList<DataModel> ListMenu=new ArrayList<>();
    DataModel retroModel;

    private MutableLiveData<List<DataModel>>allData=new MutableLiveData<>();
    public MutableLiveData<List<DataModel>>getAllData(){
        return allData;
    }

    public void init(ArrayList<DataModel>MList){
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getString();
        ListMenu = MList;
        call.enqueue(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Log.i("Responsestring", response.body().toString());
                // Toast.makeText()
                if (response.isSuccessful()){
                    if (response.body() != null){
                        //Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        writeGson(jsonresponse);
                    } else {
                         Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call , Throwable t){
                Log.i("ERRR", t.toString());
            }
        });
    }

    private void writeGson(String response){
        JSONArray json = null;
        try{
            json = new JSONArray(response);
            for(int i=0; i<json.length(); i++){
                JSONObject e = json.getJSONObject(i);
                String n =   e.getString("title");
                retroModel = new DataModel();
                retroModel.setRef(e.getString("ref"));
                retroModel.setDescription(e.getString("description"));
                retroModel.setTitle(e.getString("title"));
                retroModel.setPrice(e.getString("price"));
                retroModel.setThumbnail(e.getString("thumbnail"));
                retroModelArrayList.add(retroModel);
            }
            if(ListMenu.size()>0){
                allData.setValue(ListMenu);
            }else{
                allData.setValue(retroModelArrayList);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
