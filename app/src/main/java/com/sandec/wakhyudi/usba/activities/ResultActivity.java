package com.sandec.wakhyudi.usba.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sandec.wakhyudi.usba.R;
import com.sandec.wakhyudi.usba.model.ResponServer;
import com.sandec.wakhyudi.usba.network.ServiceClient;
import com.sandec.wakhyudi.usba.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    TextView tvResult;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvResult = (TextView)findViewById(R.id.tv_result_nilai);

        pd = new ProgressDialog(this);
        pd.setMessage("load result from server");
        pd.show();

        String nis = getSharedPreferences("login",MODE_PRIVATE).getString("nis","");

        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);

        Call<ResponServer> getResult = service.getResultExam("read_result","hasil",nis);

        getResult.enqueue(new Callback<ResponServer>() {
            @Override
            public void onResponse(Call<ResponServer> call, Response<ResponServer> response) {
                pd.dismiss();
                String hasil = response.body().getHasil();
                tvResult.setText(hasil);
            }

            @Override
            public void onFailure(Call<ResponServer> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(ResultActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void selesai(View view) {
        getSharedPreferences("login",MODE_PRIVATE).edit().clear().commit();
        getSharedPreferences("jawaban",MODE_PRIVATE).edit().clear().commit();
        startActivity(new Intent(ResultActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
