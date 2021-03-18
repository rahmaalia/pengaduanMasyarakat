package com.rahma.pengaduanmasyarakat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.E_Selesai;

import java.util.List;

public class SelesaiAdapter extends RecyclerView.Adapter<SelesaiAdapter.SelesaiViewHolder>{
    private List<E_Selesai> selesaiList;
    E_Selesai eSelesai;
    Context mContext;

    public SelesaiAdapter(Context mContext, List<E_Selesai> e_selesais){
        this.mContext = mContext;
        selesaiList = e_selesais;
    }

    @NonNull
    @Override
    public SelesaiAdapter.SelesaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporanku,parent,false);

        return new SelesaiAdapter.SelesaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelesaiAdapter.SelesaiViewHolder holder, int position) {
        eSelesai = selesaiList.get(position);
        holder.tgl.setText(eSelesai.getTglPengaduan());
        holder.laporan.setText(eSelesai.getIsiLaporan());
    }

    @Override
    public int getItemCount() {
        return selesaiList.size();
    }

    public class SelesaiViewHolder extends RecyclerView.ViewHolder {
        public final TextView tgl,laporan;
        ImageView foto;
        public SelesaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tgl);
            laporan = itemView.findViewById(R.id.tv_detail);
            foto = itemView.findViewById(R.id.imageL);
        }
    }
}
