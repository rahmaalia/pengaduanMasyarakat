package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahma.pengaduanmasyarakat.Adapter.TanggapanSelesaiAdapter;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_Tanggapan;
import com.rahma.pengaduanmasyarakat.model_entity.M_Tanggapan;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBeranda extends AppCompatActivity {
    TextView tanggal,laporan,nama;
    BaseApiService mApiInterface;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    ImageView imageView,exit;
    List<E_Tanggapan> tanggapans;
    RecyclerView rvTanggapan;
    TanggapanSelesaiAdapter tanggapanSelesaiAdapter;
    int id_pengaduan;

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
        rvTanggapan = findViewById(R.id.rv_tanggapanBeranda);

        final Intent intent = getIntent();
        id_pengaduan=intent.getIntExtra("id",1);
        tanggal.setText(getIntent().getExtras().getString("tgl"));
        laporan.setText(getIntent().getExtras().getString("laporan"));
        nama.setText(getIntent().getExtras().getString("nama"));

        getTanggapan();
        mApiInterface.getTanggapan(id_pengaduan).enqueue(new Callback<M_Tanggapan>() {
            @Override
            public void onResponse(Call<M_Tanggapan> call, Response<M_Tanggapan> response) {
                tanggapans = response.body().getData();
                tanggapanSelesaiAdapter = new TanggapanSelesaiAdapter(mContext,tanggapans);
                rvTanggapan.setAdapter(tanggapanSelesaiAdapter);
                tanggapanSelesaiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<M_Tanggapan> call, Throwable t) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBeranda.this,BerandaActivity.class);
                startActivity(i);
            }
        });
    }

    private void getTanggapan() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        rvTanggapan.setLayoutManager(layoutManager);
        rvTanggapan.setItemAnimator(new DefaultItemAnimator());
    }
}