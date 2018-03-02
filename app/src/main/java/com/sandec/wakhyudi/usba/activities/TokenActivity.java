package com.sandec.wakhyudi.usba.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.sandec.wakhyudi.usba.R;
import com.sandec.wakhyudi.usba.model.ResponServer;
import com.sandec.wakhyudi.usba.network.ServiceClient;
import com.sandec.wakhyudi.usba.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenActivity extends AppCompatActivity {
    PinView pvValidasi;
    Bundle b;
    String nis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        pvValidasi = (PinView) findViewById(R.id.pv_validasi);
        b = getIntent().getExtras();
    }

    public void cekToken(View view) {
        if (!valid()) {
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Cek Token");
        pd.show();

        nis = b.getString("nis");
        String token = pvValidasi.getText().toString();
        ServiceClient service = ServiceGenerator.createService(ServiceClient.class);

        Call<ResponServer> sendToken = service.token("token", "login", nis, token);

        sendToken.enqueue(new Callback<ResponServer>() {
            @Override
            public void onResponse(Call<ResponServer> call, Response<ResponServer> response) {
                pd.dismiss();
                String hasil = response.body().getHasil();

                switch (hasil) {
                    case "succes":
                        Bundle c = new Bundle();
                        c.putString("nis", nis);
                        Intent i = new Intent(TokenActivity.this, MenuActivity.class);
                        i.putExtras(c);
                        startActivity(i);
                        finish();
                        break;
                    case "used":
                        Toast.makeText(TokenActivity.this, "Token sudah pernah digunakan", Toast.LENGTH_SHORT).show();
                        break;
                    case "failed":
                        Toast.makeText(TokenActivity.this, "Token salah", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponServer> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(TokenActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean valid() {
        if (pvValidasi.getText().toString().isEmpty()) {
            Toast.makeText(this, "Token tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
