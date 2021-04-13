package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
    BaseApiService mApiService;
    TextInputLayout layoutnama , layoutUsername, layoutNotelp,layoutNik;
    EditText etNik,etNama,etTelp,etUsername;
    Button btnEdit;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    String nik;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);
        sharedPrefManager = new SharedPrefManager(this);

        mContext = this;
        mContext =EditActivity.this;

        nik = sharedPrefManager.getSpNik();
        user_id = sharedPrefManager.getSpIduser();

        etNama = findViewById(R.id.et_namaU);
        etNik = findViewById(R.id.et_nikU);
        etTelp = findViewById(R.id.et_telpU);
        etUsername = findViewById(R.id.et_penggunaU);
        btnEdit = findViewById(R.id.btnEditt);
        layoutnama =findViewById(R.id.layoutnamaU);
        layoutNik =findViewById(R.id.layoutnikU);
        layoutNotelp =findViewById(R.id.layouttelpU);
        layoutUsername =findViewById(R.id.layoutpenggunaU);

        etNama.setText(sharedPrefManager.getSpNama());
        etUsername.setText(sharedPrefManager.getSpUsername());
        etTelp.setText(sharedPrefManager.getSpTelp());
        etNik.setText(sharedPrefManager.getSpNik());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateMasyarakat();
                updateUser();
            }
        });
    }

    private void updateUser() {
        mApiService.updateUser(
                user_id,etUsername.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                    try {
                        JSONObject jsonRESULT = new JSONObject(response.body().string());
                        if (jsonRESULT.getString("status").equals("true")){
                            Toast.makeText(mContext, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditActivity.this, BerandaActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void updateMasyarakat() {
        mApiService.updateMasyarakat(
                etNik.getText().toString(),
                etNama.getText().toString(),
                etUsername.getText().toString(),
                etTelp.getText().toString()
                ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                    try {
                        JSONObject jsonRESULT = new JSONObject(response.body().string());
                        if (jsonRESULT.getString("status").equals("true")){
                            Toast.makeText(mContext, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditActivity.this, BerandaActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}