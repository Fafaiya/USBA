package com.sandec.wakhyudi.usba.activities;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sandec.wakhyudi.usba.R;
import com.sandec.wakhyudi.usba.model.ResponServer;
import com.sandec.wakhyudi.usba.network.ServiceClient;
import com.sandec.wakhyudi.usba.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
EditText etNis, etPass;
    String nis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onAttachedToWindow();
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        etNis = (EditText)findViewById(R.id.et_nis);
        etPass = (EditText)findViewById(R.id.et_pass);

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        ActivityManager activityManager = (ActivityManager)getApplicationContext()
//                .getSystemService(Context.ACTIVITY_SERVICE);
//
//        activityManager.moveTaskToFront(getTaskId(),0);
//    }

    @Override
    public void onAttachedToWindow() {
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        super.onAttachedToWindow();
    }

    public void login(View view) {

        if(!validasi()){
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Cek user ... ");
        pd.show();

        nis = etNis.getText().toString();
        String pass = etPass.getText().toString();

        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);

        Call<ResponServer> cekLogin = service.login("login","login",nis,pass);

        cekLogin.enqueue(new Callback<ResponServer>() {
            @Override
            public void onResponse(Call<ResponServer> call, Response<ResponServer> response) {
                pd.dismiss();
                String hasil = response.body().getHasil();

                if(hasil.equals("succes")){
                    getSharedPreferences("login",MODE_PRIVATE).edit().putString("nis",nis).commit();
//                    Bundle b = new Bundle();
//                    b.putString("nis",nis);
                    Intent i = new Intent(LoginActivity.this,TokenActivity.class);
//                    i.putExtras(b);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, ""+hasil, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponServer> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validasi() {
        if(etNis.getText().toString().isEmpty()){
            Toast.makeText(this, "Maaf Nis tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etPass.getText().toString().isEmpty()){
            Toast.makeText(this, "Maaf Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
