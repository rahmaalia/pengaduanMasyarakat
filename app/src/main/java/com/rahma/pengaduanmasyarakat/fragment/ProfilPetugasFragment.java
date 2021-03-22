package com.rahma.pengaduanmasyarakat.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rahma.pengaduanmasyarakat.LoginActivity;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;


public class ProfilPetugasFragment extends Fragment {

    SharedPrefManager sharedPrefManager;
    Context mContext;
    Button bt_keluar;
    public static ProfilPetugasFragment pf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil_petugas, container, false);

        mContext = getContext();
        sharedPrefManager = new SharedPrefManager(mContext);

        pf = this;

        bt_keluar = (Button) view.findViewById(R.id.bt_keluarPetugas);
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
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGIN_PETUGAS, false);
                                startActivity(new Intent(getContext(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                getActivity().finish();
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