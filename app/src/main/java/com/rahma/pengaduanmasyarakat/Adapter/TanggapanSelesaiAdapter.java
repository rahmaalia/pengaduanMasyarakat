package com.rahma.pengaduanmasyarakat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.model_entity.E_SelesaiPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.E_Tanggapan;

import java.util.List;

public class TanggapanSelesaiAdapter extends RecyclerView.Adapter<TanggapanSelesaiAdapter.TanggapanSelesaiViewHolder> {
    private List<E_Tanggapan> tanggapan;
    E_Tanggapan eTanggapan;
    Context mContext;

    public TanggapanSelesaiAdapter(Context mContext, List<E_Tanggapan> e_tanggapans){
        this.mContext = mContext;
        tanggapan = e_tanggapans;
    }

    @NonNull
    @Override
    public TanggapanSelesaiAdapter.TanggapanSelesaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tanggapan,parent,false);

        return new TanggapanSelesaiAdapter.TanggapanSelesaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TanggapanSelesaiAdapter.TanggapanSelesaiViewHolder holder, int position) {
        eTanggapan = tanggapan.get(position);
        holder.tanggal.setText(eTanggapan.getTglTanggapan());
        holder.tanggapan.setText(eTanggapan.getTanggapan());
    }

    @Override
    public int getItemCount() {
        return tanggapan.size();
    }

    public class TanggapanSelesaiViewHolder extends RecyclerView.ViewHolder {
        public final TextView tanggapan,tanggal;
        public TanggapanSelesaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tglTanggapan);
            tanggapan = itemView.findViewById(R.id.tanggapan);
        }
    }
}
