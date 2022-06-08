package com.example.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.task.dataBase.DataBaseHelper;
import com.example.task.dataBase.Details;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ModelClass> userList;
    LinearLayoutManager layoutManager;
    Adapter adapter;
    Context context;

    DataBaseHelper db= new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= this;

        //Adding data in dataBase
        db.insertuserdata("Mango");
        db.insertuserdata("Grapes");
        db.insertuserdata("Kiwi");
        db.insertuserdata("Pineapple");
        db.insertuserdata("Strawberry");
        db.insertuserdata("Pomegranate");
        db.insertuserdata("Tomato");
        db.insertuserdata("Watermelon");
        db.insertuserdata("Carrot");
        db.insertuserdata("Cabbage");


        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(context,userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        userList = new ArrayList<>();
//
//        userList.add(new ModelClass(R.drawable.mango, "Mango"));
//        userList.add(new ModelClass(R.drawable.grapes, "Grapes"));
//        userList.add(new ModelClass(R.drawable.kiwi, "Kiwi"));
//        userList.add(new ModelClass(R.drawable.pineapple, "Pineapple"));
//        userList.add(new ModelClass(R.drawable.strawberry, "Strawberry"));
//        userList.add(new ModelClass(R.drawable.peach, "Peach"));
//        userList.add(new ModelClass(R.drawable.pomegranate, "Pomegranate"));
//        userList.add(new ModelClass(R.drawable.tomato, "Tomato"));
//        userList.add(new ModelClass(R.drawable.watermelon, "Watermelon"));
//        userList.add(new ModelClass(R.drawable.carrot, "Carrot"));
//        userList.add(new ModelClass(R.drawable.cabbage, "Cabbage"));


//
        List<String> arr=db.getAllDetails();      //fetch list of DataBase
      userList.add(new ModelClass(R.drawable.mango, arr.get(0)));
      userList.add(new ModelClass(R.drawable.grapes, arr.get(1)));
        userList.add(new ModelClass(R.drawable.kiwi, arr.get(2)));
        userList.add(new ModelClass(R.drawable.pineapple, arr.get(3)));
        userList.add(new ModelClass(R.drawable.strawberry, arr.get(4)));
        userList.add(new ModelClass(R.drawable.peach, arr.get(5)));
        userList.add(new ModelClass(R.drawable.pomegranate, arr.get(6)));
        userList.add(new ModelClass(R.drawable.tomato, arr.get(7)));
        userList.add(new ModelClass(R.drawable.watermelon, arr.get(8)));
        userList.add(new ModelClass(R.drawable.carrot, arr.get(9)));
        userList.add(new ModelClass(R.drawable.cabbage, arr.get(10)));



    }
}