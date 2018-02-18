package com.sandec.wakhyudi.usba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sandec.wakhyudi.usba.adapter.SoalAdapter;
import com.sandec.wakhyudi.usba.model.Response;
import com.sandec.wakhyudi.usba.model.SoalItem;
import com.sandec.wakhyudi.usba.network.ServiceClient;
import com.sandec.wakhyudi.usba.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvMain;
    List<SoalItem>listSoal = new ArrayList<>();
    SoalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));

        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);

        Call<Response> requestSoal = service.getListSoal("read","soal");

        requestSoal.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                listSoal = response.body().getListSoal();

                //dilakukan untuk mengacak soal
                Collections.shuffle(listSoal);


                adapter = new SoalAdapter(listSoal,MainActivity.this);
                rvMain.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
