package com.rahma.pengaduanmasyarakat.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rahma.pengaduanmasyarakat.Adapter.ProsesAdapter;
import com.rahma.pengaduanmasyarakat.DetailVerifikasi;
import com.rahma.pengaduanmasyarakat.EditActivity;
import com.rahma.pengaduanmasyarakat.LoginActivity;
import com.rahma.pengaduanmasyarakat.PetugasActivity;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.model_entity.E_Profil;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.M_Profil;
import com.rahma.pengaduanmasyarakat.model_entity.M_Proses;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilFragment extends Fragment {

    SharedPrefManager sharedPrefManager;
    TextView TvResultNama,resultNotelp,resultNik,resultUsername;
    View view;
    Button bt_keluar;
    ImageView bt_edit;
    Context mContext;
    public static ProfilFragment pf;
    String namaa,nohp,username,nik;
    BaseApiService mApiService;
    List<E_Profil> profils;
    E_Profil e_profil;

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



//        profil();
//        TvResultNama.setText(e_profil.getNama());
//        resultUsername.setText(e_profil.getUsername());
//        resultNotelp.setText(e_profil.getTelp());
//        resultNik.setText(e_profil.getNik());

        bt_edit = view.findViewById(R.id.btnEdit);
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditActivity.class);
                startActivity(i);
            }
        });

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

//    private void profil() {
//        nik = "08523641254632569";
//        mApiService.getProfil(nik).enqueue(new Callback<M_Profil>() {
//            @Override
//            public void onResponse(Call<M_Profil> call, Response<M_Profil> response) {
//                profils = response.body().getData();
//            }
//
//            @Override
//            public void onFailure(Call<M_Profil> call, Throwable t) {
////                tToast.makeText(ProsesFragment.this."gagal",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
