package com.sandec.wakhyudi.usba.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.sandec.wakhyudi.usba.R;
import com.sandec.wakhyudi.usba.adapter.NoSoalAdapter;
import com.sandec.wakhyudi.usba.adapter.SoalAdapter;
import com.sandec.wakhyudi.usba.model.QuestionNumberModel;
import com.sandec.wakhyudi.usba.model.ResponServer;
import com.sandec.wakhyudi.usba.model.Response;
import com.sandec.wakhyudi.usba.model.SoalItem;
import com.sandec.wakhyudi.usba.network.ServiceClient;
import com.sandec.wakhyudi.usba.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class MenuActivity extends AppCompatActivity {
    RecyclerView rvMenuSoal;
    RecyclerView rvSoal;
    TextView tvCountDownTimer;
    List<SoalItem> listSoal = new ArrayList<>();
    List<String> listJawaban = new ArrayList<>();
//    List<String> listSoalTerjawab = new ArrayList<>();
//    List<String> listSoalTerjawab1 = new ArrayList<>();
//    List<String> listSoalTerjawab2 = new ArrayList<>();
    List<Integer> listIndekSoal = new ArrayList<>();
    List<Integer> noSoal = new ArrayList<>();

    SoalAdapter adapter;
    NoSoalAdapter noSoalAdapter;
    ProgressDialog pd;
    Timer timer;
    int hour = 1;
    int minute = 30;
    int second = 10;


    List<QuestionNumberModel> listNoSoal;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvToolbar = (TextView) findViewById(R.id.tv_toolbar);
        tvToolbar.setText("Pemrograman Perangkat Bergerak");
        //toolbar.setTitle("Pemrograman Perangkat Bergerak");

        setSupportActionBar(toolbar);

        //---menambahkan banyak soal untuk no soal//


        for (int i = 1; i <= 40; i++) {

            noSoal.add(i);

        }

        //----//

        rvMenuSoal = findViewById(R.id.rv_menu_navigation);
        rvSoal = findViewById(R.id.rv_soal);
        tvCountDownTimer = (TextView) findViewById(R.id.tv_content_time);

        rvMenuSoal.setLayoutManager(new GridLayoutManager(this, 5));
        rvSoal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //bagian ini digunakan ketika swipe kiri kanan rv nya menjadi full
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvSoal);

        pd = new ProgressDialog(this);
        pd.setMessage("Load data from server");
        pd.show();

        //blok ini digunakan untuk mendapat soal dari server
        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);
        Call<Response> requestSoal = service.getListSoal("read", "soal");
        requestSoal.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                pd.dismiss();
                listSoal = response.body().getListSoal();

                //dilakukan untuk mengacak soal
                Collections.shuffle(listSoal);

                adapter = new SoalAdapter(listSoal, MenuActivity.this);
                rvSoal.setAdapter(adapter);

                //untuk mencetak objek yang menghandle countdown timer
                timer = new Timer();

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        //proses menghitung mundur ini
                        //kita lakukan di background agar tidak menganggu main Thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                tvCountDownTimer.setText("Sisa waktu : " + hour + " : " + minute + " : " + second);

                                //memberi event jika waktu habis
                                //mulai dari jam dulu
                                if (hour == 0 && minute == 0 && second == 0) {
                                    Toast.makeText(MenuActivity.this,
                                            "Maaf waktu anda habis",
                                            Toast.LENGTH_SHORT).show();
                                    timer.cancel();
                                    kirimJawabanFinal();

                                } else if (minute == 0 && second == 0) {
                                    hour--;
                                    minute = 60;
                                    second = 60;
                                } else if (second == 0) {
                                    minute--;
                                    second = 60;
                                }
//                                if (second == 0) {
//                                    Toast.makeText(MainActivity.this,
//                                            "Maaf waktu anda habis",
//                                            Toast.LENGTH_SHORT).show();
//                                    timer.cancel();
//                                }
                                second--;
                            }
                        });
                    }
                }, 0, 1000);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(MenuActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // navigationView.setNavigationItemSelectedListener(this);
        listNoSoal = new ArrayList<>();

        loadNomor();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }


    //mengirim jawaban ke server di menu nav drawer
    public void kirimJawaban(View view) {
        kirimJawabanFinal();
    }

    //ketika no di navigasi drawer diklik
    public void cobaNo(View view) {
        TextView tv = view.findViewById(R.id.tv_item_question_number);
        String noSoal = (String) tv.getText();
        Toast.makeText(this, "" + tv.getText(), Toast.LENGTH_SHORT).show();
        rvSoal.scrollToPosition(Integer.valueOf(noSoal) - 1);

        closeNavigation();
    }


    //this part for showing question nummber di navigation drawer
    public void loadNomor() {
        listNoSoal.clear();
        for (int i = 0; i < noSoal.size(); i++) {
            QuestionNumberModel no = new QuestionNumberModel(noSoal.get(i));
            listNoSoal.add(no);
        }

        NoSoalAdapter noSoalAdapter = new NoSoalAdapter(this, listNoSoal);
        rvMenuSoal.setAdapter(noSoalAdapter);
    }


    public void closeNavigation() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{

        }
    }

    public void kirimJawabanFinal(){
        pd = new ProgressDialog(this);
        pd.setMessage("send Answer to Server");
        pd.show();

        closeNavigation();

        String nis = getSharedPreferences("login", MODE_PRIVATE).getString("nis", "");
        String jawaban;
        String jawaban2;


        //menghapus field yang tersimpan sebelumnya
//        listSoalTerjawab.clear();
//        listSoalTerjawab1.clear();
//        listSoalTerjawab2.clear();
        listJawaban.clear();
        listIndekSoal.clear();

        for (int i = 0; i < 40; i++) {
            String soal = listSoal.get(i).getSoal();
            int index = listSoal.get(i).getNoSoal();
            if (listSoal.get(i).getFinalAnswer() == null) {
                jawaban = "none";

            } else {
                jawaban = listSoal.get(i).getFinalAnswer();
            }
//                listSoalTerjawab.add(i, soal);
            listJawaban.add(i, jawaban);

            listIndekSoal.add(index);
        }


//        pecahJawaban(listSoalTerjawab);
//        Log.d("data", "kirimJawaban: " + listIndekSoal);
//        Log.d("data", "kirimJawaban: "+listJawaban);

        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);
        Call<ResponServer> sendAnswer = service.sendAnswer("insert", "jawaban", nis, listIndekSoal, listJawaban);

        sendAnswer.enqueue(new Callback<ResponServer>() {
            @Override
            public void onResponse(Call<ResponServer> call, retrofit2.Response<ResponServer> response) {
                pd.dismiss();
                String hasil = response.body().getHasil();
                if (hasil.equals("berhasil mengirim jawaban")){
                    startActivity(new Intent(MenuActivity.this,ResultActivity.class));
                    finish();
                }else {
                    Toast.makeText(MenuActivity.this, "" + "Pengiriman gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponServer> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(MenuActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public void pecahJawaban(List<String>listSoalTerjawab){
//        for (int i = 0; i <40 ; i++) {
//            String soal = listSoalTerjawab.get(i);
//            if (i < 20) {
//                listSoalTerjawab1.add(soal);
//            } else {
//                listSoalTerjawab2.add(soal);
//
//            }
//        }
//    }
}
