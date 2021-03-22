package com.rahma.pengaduanmasyarakat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rahma.pengaduanmasyarakat.DetailBeranda;
import com.rahma.pengaduanmasyarakat.Detail_proses;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.model_entity.E_Beranda;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;

import java.util.List;

public class BerandaAdapter extends RecyclerView.Adapter<BerandaAdapter.BerandaViewHolder> {
    private List<E_Beranda> berandaList;
    E_Beranda eBeranda;
    Context mContext;
    CardView cardView;
    String statuss;


    public BerandaAdapter(Context mContext, List<E_Beranda> e_berandas){
        this.mContext = mContext;
        berandaList = e_berandas;

    }

    @NonNull
    @Override
    public BerandaAdapter.BerandaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beranda,parent,false);
        return new BerandaAdapter.BerandaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BerandaAdapter.BerandaViewHolder holder, int position) {
        eBeranda = berandaList.get(position);
        holder.tgl.setText(eBeranda.getTglPengaduan());
        holder.laporan.setText(eBeranda.getIsiLaporan());
        holder.nama.setText(eBeranda.getNama());
        holder.status.setText(eBeranda.getStatus());

        statuss= eBeranda.getStatus();

    }

    @Override
    public int getItemCount() {
        return berandaList.size();
    }

    public class BerandaViewHolder extends RecyclerView.ViewHolder {
        public final TextView tgl,laporan,nama,status;
        public ImageView foto;
        @SuppressLint("ResourceAsColor")
        public BerandaViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tglb);
            laporan = itemView.findViewById(R.id.tv_detailb);
            foto = itemView.findViewById(R.id.imageB);
            nama = itemView.findViewById(R.id.namaB);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.cvBeranda);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Intent i = new Intent(mContext, DetailBeranda.class);
                        i.putExtra("tgl", berandaList.get(position).getTglPengaduan());
                        i.putExtra("laporan", berandaList.get(position).getIsiLaporan());
                        i.putExtra("nama", berandaList.get(position).getNama());
                        mContext.startActivity(i);
                    }
                }
            });

            if (statuss == "selesai"){
                status.setBackgroundResource(R.color.colorPrimary);
            }
        }
    }
}
