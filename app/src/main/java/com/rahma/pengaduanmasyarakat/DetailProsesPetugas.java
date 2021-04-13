package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProsesPetugas extends AppCompatActivity {
    TextView tanggal,laporan,nama;
    BaseApiService mApiInterface;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    ImageView imageView,exit;
    Button btnTanggapi,btnVer;
    int id_pengaduan,pengaduan_id;
    String status;
    Bitmap setFoto = null;
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_proses_petugas);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiInterface = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        tanggal = findViewById(R.id.tglDetailP);
        laporan = findViewById(R.id.laporanDetailP);
        imageView = findViewById(R.id.imageDetailP);
        nama = findViewById(R.id.namaPP);
        exit = findViewById(R.id.exitPP);
//        btnTanggapi = findViewById(R.id.btnTanggap);
        btnVer = findViewById(R.id.btnVerifikasi);

        final Intent intent = getIntent();
        laporan.setText(getIntent().getExtras().getString("laporan"));
        nama.setText(getIntent().getExtras().getString("nama"));
        tanggal.setText(getIntent().getExtras().getString("tgl"));
        id_pengaduan=intent.getIntExtra("id",1);
        pengaduan_id = id_pengaduan;

        foto = getIntent().getStringExtra("foto");

        try {
            URL url = new URL(RetrofitClient.BASE_URL_FOTO + foto);
            setFoto = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(setFoto);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailProsesPetugas.this,PetugasActivity.class);
                startActivity(i);
            }
        });

//        btnTanggapi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(DetailProsesPetugas.this,FormTanggapan.class);
//                i.putExtra("id",pengaduan_id);
//                startActivity(i);
//            }
//        });
        status = "verifikasi";
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(mContext);
                alertdialogBuilder.setTitle("Verifikasi");

                alertdialogBuilder
                        .setMessage("Apakah anda yakin?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mApiInterface.updateStatus(id_pengaduan,status).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()){
                                            try {
                                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                                if (jsonRESULTS.getString("status").equals("true")){
                                                    Toast.makeText(mContext, "BERHASIL ", Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(DetailProsesPetugas.this, PetugasActivity.class);
                                                    startActivity(intent);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }else {
                                            Log.i("debug", "onResponse : GA BERHASIL");
                                            Toast.makeText(mContext, String.valueOf(status), Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog = alertdialogBuilder.create();
                alertDialog.show();

            }
        });
    }
}