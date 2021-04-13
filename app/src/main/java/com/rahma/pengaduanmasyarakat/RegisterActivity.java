package com.rahma.pengaduanmasyarakat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etNik,etNama,etTelp,etUsername,etPassword;
    Button btnRegister;
    TextView btnMasuk;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;
    TextInputLayout layoutnama , layoutUsername, layoutNotelp, layoutPassword,layoutNik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;
        mApiService = RetrofitClient.getAPIService();
        initComponens();
        mContext =RegisterActivity.this;

    }

    private void initComponens() {

        btnMasuk = (TextView) findViewById(R.id.btnmasuk);
        etNama = findViewById(R.id.et_nama);
        etNik = findViewById(R.id.et_nik);
        etTelp = findViewById(R.id.et_telp);
        etUsername = findViewById(R.id.et_pengguna);
        etPassword = findViewById(R.id.et_pass);
        btnRegister = findViewById(R.id.btnDaftar);
        layoutnama =findViewById(R.id.layoutnama);
        layoutNik =findViewById(R.id.layoutnik);
        layoutNotelp =findViewById(R.id.layouttelp);
        layoutUsername =findViewById(R.id.layoutpengguna);
        layoutPassword =findViewById(R.id.layoutpass);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etNama.length()==0 || etUsername.length()==0 || etTelp.length()==0 || etPassword.length()==0 || etNik.length()==0 ) {
                    Toast.makeText(mContext, "Isi Semua Field", Toast.LENGTH_LONG).show();}
                if (etNama.length()==0) {
                    layoutnama.setError("Nama tidak boleh kosong");
                }
                else if(etNama.length()!=0){
                    layoutnama.setError(null);
                    layoutnama.setErrorEnabled(false);
                }

                if (etUsername.length()==0) {
                    layoutUsername.setError("Username tidak boleh kosong");
                }
                else {
                    layoutUsername.setError(null);
                    layoutUsername.setErrorEnabled(false);
                }

                if (etTelp.length()==0) {
                    layoutNotelp.setError("NoTelp tidak boleh kosong");
                }
                if (etTelp.length() >= 14){
                    layoutNotelp.setError("max 13");
                }
                else{
                    layoutNotelp.setError(null);
                    layoutNotelp.setErrorEnabled(false);
                }
                if (etNik.length()==0) {
                    layoutNik.setError("No Identitas tidak boleh kosong");
                }
                if (etNik.length() <= 15){
                    layoutNik.setError("min 16");
                }
                else{
                    etNik.setError(null);
                    layoutNik.setErrorEnabled(false);
                }

                if (etNama.length()>0 && etUsername.length()>0 && etTelp.length()<14 && etPassword.length()>0 && etNik.length()>15){

                    requestRegister();
                }
            }

            private void requestRegister() {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true,false);
                mApiService.registerRequest(
                        etNik.getText().toString(),
                        etNama.getText().toString(),
                        etTelp.getText().toString(),
                        etUsername.getText().toString(),
                        etPassword.getText().toString()

                ).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    loading.dismiss();
                                    try {
                                        JSONObject jsonRESULT = new JSONObject(response.body().string());
                                        if (jsonRESULT.getString("status").equals("true")){
                                            Toast.makeText(mContext, "Berhasil Daftar", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(mContext, "Akun Sudah Terdaftar", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }else {
                                    Log.i("debug", "onResponse : GA BERHASIL");
                                    Toast.makeText(mContext, "Username telah terdaftar", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
    }
}