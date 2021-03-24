package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormTanggapan extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    Context mContext;
    EditText edtTAnggapan;
    Button btnTanggapan;
    BaseApiService mApiService;
    String Date;
    int id_pengaduan,id_petugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tanggapan);

        sharedPrefManager = new SharedPrefManager(this);
        mContext = this;
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar date =Calendar.getInstance();
        Date= DateFormat.format(date.getTime());

        edtTAnggapan = findViewById(R.id.et_tanggapan);
        btnTanggapan = findViewById(R.id.btnTanggapan);

        id_petugas = sharedPrefManager.getSpIdpetugas();
        final Intent intent = getIntent();
        id_pengaduan=intent.getIntExtra("id",1);

        btnTanggapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.inputTanggapan(id_pengaduan,Date,edtTAnggapan.getText().toString(),id_petugas)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    try {
                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                        if (jsonRESULTS.getString("status").equals("true")){
                                            Toast.makeText(mContext, "BERHASIL MENANGGAPI", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(FormTanggapan.this,DetailProsesPetugas .class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    Log.i("debug", "onResponse : GA BERHASIL");
                                    Toast.makeText(mContext, String.valueOf(id_petugas), Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                requestTanggapan();
            }
        });
    }

    private void requestTanggapan() {

    }
}