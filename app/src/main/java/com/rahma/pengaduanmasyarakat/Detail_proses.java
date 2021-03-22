package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_proses extends AppCompatActivity {
    TextView tanggal,laporan;
    BaseApiService mApiInterface;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    ImageView imageView,exit,dalete;
    int id_pengaduan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_proses);

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        mApiInterface = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        tanggal = findViewById(R.id.tglDetail);
        laporan = findViewById(R.id.laporanDetail);
        imageView = findViewById(R.id.imageDetail);
        exit = findViewById(R.id.exit);
        dalete = findViewById(R.id.delete);

        final Intent intent = getIntent();
        tanggal.setText(getIntent().getExtras().getString("tgl"));
        laporan.setText(getIntent().getExtras().getString("laporan"));
        id_pengaduan=intent.getIntExtra("id",1);

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
}