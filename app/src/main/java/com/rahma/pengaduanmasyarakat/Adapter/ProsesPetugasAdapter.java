package com.rahma.pengaduanmasyarakat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.E_ProsesPetugas;

import java.util.List;

public class ProsesPetugasAdapter extends RecyclerView.Adapter<ProsesPetugasAdapter.ProsesPetugasViewHolder> {
    private List<E_ProsesPetugas> prosesPetugasList;
    E_ProsesPetugas eProsesPetugas;
    Context mContext;
    CardView cardView;

    public ProsesPetugasAdapter(Context mContext, List<E_ProsesPetugas> e_prosesPetugases){
        this.mContext = mContext;
        prosesPetugasList = e_prosesPetugases;
    }

    @NonNull
    @Override
    public ProsesPetugasAdapter.ProsesPetugasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proses_petugas,parent,false);

        return new ProsesPetugasAdapter.ProsesPetugasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProsesPetugasAdapter.ProsesPetugasViewHolder holder, int position) {
        eProsesPetugas = prosesPetugasList.get(position);
        holder.tgl.setText(eProsesPetugas.getTglPengaduan());
        holder.laporan.setText(eProsesPetugas.getIsiLaporan());
        holder.nama.setText(eProsesPetugas.getNama());
    }

    @Override
    public int getItemCount() {
        return prosesPetugasList.size();
    }

    public class ProsesPetugasViewHolder extends RecyclerView.ViewHolder {
        public final TextView tgl,laporan,nama;
        public ImageView foto = null;
        public ProsesPetugasViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tglP);
            laporan = itemView.findViewById(R.id.tv_detailP);
            nama = itemView.findViewById(R.id.namaP);
            foto = itemView.findViewById(R.id.imageP);
            cardView = itemView.findViewById(R.id.cvBerandaP);
        }
    }
}
