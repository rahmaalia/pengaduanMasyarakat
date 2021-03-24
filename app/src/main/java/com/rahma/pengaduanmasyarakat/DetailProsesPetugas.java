package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

public class DetailProsesPetugas extends AppCompatActivity {
    TextView tanggal,laporan,nama;
    BaseApiService mApiInterface;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    ImageView imageView,exit;
    Button btnTanggapi;
    int id_pengaduan,pengaduan_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_proses_petugas);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiInterface = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        tanggal = findViewById(R.id.tglDetailP);
        laporan = findViewById(R.id.laporanDetailP);
        imageView = findViewById(R.id.imageDetailP);
        nama = findViewById(R.id.namaPP);
        exit = findViewById(R.id.exitPP);
        btnTanggapi = findViewById(R.id.btnTanggap);

        final Intent intent = getIntent();
        tanggal.setText(getIntent().getExtras().getString("tgl"));
        laporan.setText(getIntent().getExtras().getString("laporan"));
        nama.setText(getIntent().getExtras().getString("nama"));
        id_pengaduan=intent.getIntExtra("id",1);
        pengaduan_id = id_pengaduan;

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailProsesPetugas.this,BerandaActivity.class);
                startActivity(i);
            }
        });

        btnTanggapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailProsesPetugas.this,FormTanggapan.class);
                i.putExtra("id",pengaduan_id);
                startActivity(i);
            }
        });
    }
}