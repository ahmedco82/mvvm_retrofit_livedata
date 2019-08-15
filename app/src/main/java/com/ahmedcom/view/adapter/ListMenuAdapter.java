package com.ahmedcom.view.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.json_parsing_url_retrofit.R;
import com.ahmedcom.service.model.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.ahmedcom.view.ui.MainActivity.NewMenu_person;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.MyViewHolder>{

      private Context context_;
      List<DataModel>person;
      ArrayList <DataModel>Menu_person = new ArrayList<>();

     public ListMenuAdapter(Context context , List<DataModel> person){
        this.context_ =context;
        this.person =  person;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType ){
     // infalte the item Layout .............
      View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent, false);
      // set the view's size, margins, paddings and layout parameters
      MyViewHolder v = new MyViewHolder(root); // pass the view to View Holder
      return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position){
        holder.name.setText(person.get(position).getTitle().toString());
        holder.price.setText(person.get(position).getPrice());
        holder.txt.setText("");
        Picasso.get().load(String.valueOf(person.get(position).getThumbnail())).into(holder.img1);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View view){
             if(NewMenu_person.size()>0){
                 Toast.makeText(context_, "لقد تم اضافة العناصر", Toast.LENGTH_LONG).show();
             }else{
                 openWindow(person.get(position));
             }
         }
       });
     }
    public void openWindow(final DataModel p){
       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context_);
       alertDialogBuilder.setMessage("Are you sure");
       alertDialogBuilder.setPositiveButton("أضافة",new DialogInterface.OnClickListener(){
       @Override
       public void onClick(DialogInterface dialogInterface , int i) {
          Menu_person.add(p);
          Toast.makeText(context_, "تم اضافة عنصر الى القائمة", Toast.LENGTH_LONG).show();
        }
      });

       alertDialogBuilder.setNegativeButton( "الغاء", new DialogInterface.OnClickListener(){
         @Override
          public void onClick(DialogInterface dialogInterface , int i){

            }
        });
        alertDialogBuilder.setNeutralButton("عرض القائمة", new DialogInterface.OnClickListener(){
         @Override
          public void onClick(DialogInterface dialogInterface , int i){
             person = Menu_person;
             NewMenu_person = Menu_person;
             notifyDataSetChanged();
           }
        });
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();
     }
    @Override
    public int getItemCount() {
         return person.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,txt;
        ImageView img1;
        public MyViewHolder(View itemView){
          super(itemView);
           price = (TextView) itemView.findViewById(R.id.price);
           // price.setText("RandomText");
           img1 = (ImageView)itemView.findViewById(R.id.iv);
           // get the reference of item view's ......
           name = (TextView)itemView.findViewById(R.id.name);
           txt  = (TextView)itemView.findViewById(R.id.city);
        }
    }
}