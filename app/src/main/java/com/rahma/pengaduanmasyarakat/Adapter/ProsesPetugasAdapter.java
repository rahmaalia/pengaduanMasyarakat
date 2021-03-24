package com.rahma.pengaduanmasyarakat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rahma.pengaduanmasyarakat.DetailProsesPetugas;
import com.rahma.pengaduanmasyarakat.Detail_proses;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.E_ProsesPetugas;

import java.io.IOException;
import java.net.URL;
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
        Bitmap setFoto=null;
        String foto;
        foto = eProsesPetugas.getFoto();
        try {
            URL url = new URL(RetrofitClient.BASE_URL_FOTO + foto);
            setFoto = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        holder.fotoo.setImageBitmap(setFoto);
    }

    @Override
    public int getItemCount() {
        return prosesPetugasList.size();
    }

    public class ProsesPetugasViewHolder extends RecyclerView.ViewHolder {
        public final TextView tgl,laporan,nama;
        public ImageView fotoo ;
        public ProsesPetugasViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tglP);
            laporan = itemView.findViewById(R.id.tv_detailP);
            nama = itemView.findViewById(R.id.namaP);
            fotoo = itemView.findViewById(R.id.imageP);
            cardView = itemView.findViewById(R.id.cvBerandaP);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Intent i = new Intent(mContext, DetailProsesPetugas.class);
                        i.putExtra("id",prosesPetugasList.get(position).getIdPengaduan());
                        i.putExtra("tgl", prosesPetugasList.get(position).getTglPengaduan());
                        i.putExtra("laporan", prosesPetugasList.get(position).getIsiLaporan());
                        i.putExtra("nama", prosesPetugasList.get(position).getNama());
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }
}
