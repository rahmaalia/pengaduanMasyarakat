package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

public class DetailBeranda extends AppCompatActivity {
    TextView tanggal,laporan,nama;
    BaseApiService mApiInterface;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    ImageView imageView,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_beranda);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiInterface = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        tanggal = findViewById(R.id.tglDetailB);
        laporan = findViewById(R.id.laporanDetailB);
        imageView = findViewById(R.id.imageDetailB);
        nama = findViewById(R.id.NamaDB);
        exit = findViewById(R.id.exitB);

        final Intent intent = getIntent();
        tanggal.setText(getIntent().getExtras().getString("tgl"));
        laporan.setText(getIntent().getExtras().getString("laporan"));
        nama.setText(getIntent().getExtras().getString("nama"));

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBeranda.this,BerandaActivity.class);
                startActivity(i);
            }
        });
    }
}