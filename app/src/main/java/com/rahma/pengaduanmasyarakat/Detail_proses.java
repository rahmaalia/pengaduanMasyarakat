package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahma.pengaduanmasyarakat.Adapter.ProsesAdapter;
import com.rahma.pengaduanmasyarakat.Adapter.TanggapanSelesaiAdapter;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_Tanggapan;
import com.rahma.pengaduanmasyarakat.model_entity.M_Tanggapan;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_proses extends AppCompatActivity {
    TextView tanggal,laporan,status;
    BaseApiService mApiInterface;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    ImageView imageView,exit,dalete;
    int id_pengaduan,pengaduan_id,fotoo;
    List<E_Tanggapan> tanggapans;
    RecyclerView rvTanggapan;
    TanggapanSelesaiAdapter tanggapanSelesaiAdapter;
    Bitmap setFoto = null;
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_proses);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiInterface = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        tanggal = findViewById(R.id.tglDetail);
        laporan = findViewById(R.id.laporanDetail);
        imageView = findViewById(R.id.imageDetail);
        exit = findViewById(R.id.exit);
        dalete = findViewById(R.id.delete);
        rvTanggapan = findViewById(R.id.rv_tanggapan);
        status = findViewById(R.id.statusDetail);

        final Intent intent = getIntent();
        tanggal.setText(getIntent().getExtras().getString("tgl_pengaduan"));
        laporan.setText(getIntent().getExtras().getString("isi_laporan"));
        id_pengaduan = intent.getIntExtra("id_pengaduan",1);
        foto = getIntent().getStringExtra("foto");
        status.setText(getIntent().getExtras().getString("status"));

        pengaduan_id = id_pengaduan;

        try {
            URL url = new URL(RetrofitClient.BASE_URL_FOTO + foto);
            setFoto = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(setFoto);
        
        getTanggapan();
        mApiInterface.getTanggapan(pengaduan_id).enqueue(new Callback<M_Tanggapan>() {
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

        dalete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiInterface.deletePengaduan(id_pengaduan).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){

                            AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(mContext);
                            alertdialogBuilder.setTitle("Hapus Laporan");

                            alertdialogBuilder
                                    .setMessage("Apakah anda yakin?")
                                    .setCancelable(false)
                                    .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(Detail_proses.this,"Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(Detail_proses.this,BerandaActivity.class);
                                            startActivity(intent);
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
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Detail_proses.this,BerandaActivity.class);
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