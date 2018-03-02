package com.sandec.wakhyudi.usba.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etNis = (EditText)findViewById(R.id.et_nis);
        etPass = (EditText)findViewById(R.id.et_pass);

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
                    Bundle b = new Bundle();
                    b.putString("nis",nis);
                    Intent i = new Intent(LoginActivity.this,TokenActivity.class);
                    i.putExtras(b);
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
}
