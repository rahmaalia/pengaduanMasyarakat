package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    TextView btnDaftar;
    EditText etUsername;
    EditText etpassword;
    Button btnLogin;
    ProgressDialog loading;
    BaseApiService mApiService;
    TextInputLayout layoutu,layoutp;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiService = RetrofitClient.getAPIService();


        if (sharedPrefManager.getSpLogin()){
            startActivity(new Intent(LoginActivity.this, BerandaActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        if (sharedPrefManager.getSpLoginPetugas()){
            startActivity(new Intent(LoginActivity.this, PetugasActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        Button btnMoveActivity = findViewById(R.id.btnLogin);
        TextView btRegister=findViewById(R.id.txtDaftar);
        btnMoveActivity.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        mContext =LoginActivity.this;
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);
        initComponents();



    }

    private void initComponents() {
        btnDaftar = (TextView) findViewById(R.id.txtDaftar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        layoutp=findViewById(R.id.layoutp);
        layoutu=findViewById(R.id.layoutuser);
        etUsername = (EditText) findViewById(R.id.et_namapengguna);
        etpassword = (EditText) findViewById(R.id.et_katasandi);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsername.length()==0){
                    layoutu.setError("Username Harus Diisi");

                }
                else if (etUsername.length()!=0){
                    layoutu.setError(null);
                    layoutu.setErrorEnabled(false);
                }
                if(etpassword.length()==0){
                    layoutp.setError("Password Harus Diisi");

                }
                else if (etpassword.length()!=0){
                    layoutp.setError(null);
                    layoutp.setErrorEnabled(false);
                }
                if(etUsername.length()!=0 && etpassword.length()!=0){
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true,false);
                    mApiService.loginRequest(etUsername.getText().toString(), etpassword.getText().toString())
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()){
                                        loading.dismiss();
                                        try {
                                            JSONObject jsonRESULT = new JSONObject(response.body().string());
                                            if (jsonRESULT.getString("status").equals("true")){

                                                if (jsonRESULT.getJSONObject("data").getString("role_id").equals("1")){
                                                    Intent intent=new Intent(LoginActivity.this,PetugasActivity.class);
                                                    int petugas_id = jsonRESULT.getJSONObject("data").getInt("id_petugas");
                                                    String namaP = jsonRESULT.getJSONObject("data").getString("nama_petugas");
                                                    String notelpP = jsonRESULT.getJSONObject("data").getString("telp");
                                                    String usernameP = jsonRESULT.getJSONObject("data").getString("username");

                                                    sharedPrefManager.saveSPint(String.valueOf(SharedPrefManager.SP_IDUSER), petugas_id);
                                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, namaP);
                                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TELP, notelpP);
                                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_USERNAME, usernameP);
                                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGIN_PETUGAS, true);
                                                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                                    finish();
                                                }
                                                else {
                                                    Toast.makeText(mContext, "Berhasil login", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(LoginActivity.this, BerandaActivity.class);
                                                    String nama = jsonRESULT.getJSONObject("data").getString("nama");
                                                    String notelp = jsonRESULT.getJSONObject("data").getString("telp");
                                                    String username = jsonRESULT.getJSONObject("data").getString("username");
                                                    String nik = jsonRESULT.getJSONObject("data").getString("nik");
                                                    int user_id = jsonRESULT.getJSONObject("data").getInt("user_id");
                                                    Log.d("username", "user" + username);

                                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TELP, notelp);
                                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_USERNAME, username);
                                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK, nik);
                                                    sharedPrefManager.saveSPint(String.valueOf(SharedPrefManager.SP_IDUSER), user_id);
                                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGIN, true);
                                                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                                    finish();
                                                }

                                            }
                                            else{
                                                Toast.makeText(mContext, "Akun belum Terdaftar", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e){
                                            e.printStackTrace();
                                        } catch (IOException e){
                                            e.printStackTrace();
                                        }
                                    } else {
                                        loading.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(mContext, "Username / password salah", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                }
                            });}
            }
        });


        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:
                Intent moveIntent = new Intent(LoginActivity.this, BerandaActivity.class);
                startActivity(moveIntent);
                break;

            case R.id.txtDaftar:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

}