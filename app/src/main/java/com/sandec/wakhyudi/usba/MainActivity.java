package com.sandec.wakhyudi.usba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sandec.wakhyudi.usba.adapter.SoalAdapter;
import com.sandec.wakhyudi.usba.model.ResponServer;
import com.sandec.wakhyudi.usba.model.Response;
import com.sandec.wakhyudi.usba.model.SoalItem;
import com.sandec.wakhyudi.usba.network.ServiceClient;
import com.sandec.wakhyudi.usba.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvMain;
    List<SoalItem>listSoal = new ArrayList<>();
    List<String>listJawaban = new ArrayList<>();
    List<String>listSoalTerjawab = new ArrayList<>();
    SoalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvMain);

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


        rvMain.getLayoutManager().scrollToPosition(3);
    }

    public void sendAnswer(View view) {

        for (int i = 0; i <adapter.getItemCount() ; i++) {
            String soal = listSoal.get(i).getSoal();
            String jawaban = listSoal.get(i).getFinalAnswer();


            listSoalTerjawab.add(soal);
            listJawaban.add(i,jawaban);

        }


        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);

        Call<ResponServer> sendAnswer = service.sendAnswer("insert","jawaban",listSoalTerjawab,listJawaban);


        sendAnswer.enqueue(new Callback<ResponServer>() {
            @Override
            public void onResponse(Call<ResponServer> call, retrofit2.Response<ResponServer> response) {
                Toast.makeText(MainActivity.this, ""+response.body().getHasil(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponServer> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("answer", "sendAnswer: "+listSoalTerjawab);
        Log.i("answer", "sendAnswer: "+listJawaban);
    }


    private static <T> void unshuffle(List<T> listSoal, Random random){
        int[] seq = new int[listSoal.size()];
        for (int i = seq.length; i >=1 ; i--) {
            seq[i-1] = random.nextInt(i);
        }

        for (int i = 0; i <seq.length ; i++) {
            Collections.swap(listSoal,i,seq[i]);
        }
    }
}
