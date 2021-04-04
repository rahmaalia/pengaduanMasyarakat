package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.io.IOException;
import java.net.URL;

public class DetailVerifikasi extends AppCompatActivity {
    TextView tanggal,laporan,nama;
    BaseApiService mApiInterface;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    ImageView imageView,exit;
    Button btnTanggapi;
    int id_pengaduan;
    Bitmap setFoto = null;
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_verifikasi);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiInterface = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        tanggal = findViewById(R.id.tglDetailV);
        laporan = findViewById(R.id.laporanDetailv);
        imageView = findViewById(R.id.imageDetailV);
        nama = findViewById(R.id.namaV);
        exit = findViewById(R.id.exitV);
        btnTanggapi = findViewById(R.id.btnTanggapV);

        final Intent intent = getIntent();
        laporan.setText(getIntent().getExtras().getString("laporan"));
        nama.setText(getIntent().getExtras().getString("nama"));
        tanggal.setText(getIntent().getExtras().getString("tgl"));
        id_pengaduan=intent.getIntExtra("idp",1);
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
                Intent i = new Intent(DetailVerifikasi.this,PetugasActivity.class);
                startActivity(i);
            }
        });

        btnTanggapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailVerifikasi.this,FormTanggapan.class);
                i.putExtra("id",id_pengaduan);
                startActivity(i);
            }
        });
    }
}