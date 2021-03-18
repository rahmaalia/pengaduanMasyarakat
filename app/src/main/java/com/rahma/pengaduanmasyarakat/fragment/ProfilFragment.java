package com.rahma.pengaduanmasyarakat.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rahma.pengaduanmasyarakat.LoginActivity;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

public class ProfilFragment extends Fragment {

    SharedPrefManager sharedPrefManager;
    TextView TvResultNama,resultNotelp,resultNik,resultUsername;
    View view;
    Button bt_keluar;
    Context mContext;
    public static ProfilFragment pf;
    String namaa,nohp,username,nik;
    BaseApiService mApiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        mContext = getContext();

        sharedPrefManager = new SharedPrefManager(mContext);
        mContext = getContext();
        pf = this;

        TvResultNama = view.findViewById(R.id.tvNama);
        resultUsername = view.findViewById(R.id.usernamep);
        resultNotelp = view.findViewById(R.id.no_telpp);
        resultNik = view.findViewById(R.id.nikp);

        TvResultNama.setText(sharedPrefManager.getSpNama());
        resultUsername.setText(sharedPrefManager.getSpUsername());
        resultNotelp.setText(sharedPrefManager.getSpTelp());
        resultNik.setText(sharedPrefManager.getSpNik());

        namaa = sharedPrefManager.getSpNama();
        nohp = sharedPrefManager.getSpTelp();
        username = sharedPrefManager.getSpUsername();
        nik = sharedPrefManager.getSpNik();




        bt_keluar = (Button) view.findViewById(R.id.bt_keluar);
        bt_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertdialogBuilder = new AlertDialog.Builder(mContext);
                alertdialogBuilder.setTitle("Keluar");

                alertdialogBuilder
                        .setMessage("Apakah anda yakin?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sharedPrefManager = new SharedPrefManager(getContext());
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGIN, false);
                                startActivity(new Intent(getContext(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
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

        return view;
    }
}
