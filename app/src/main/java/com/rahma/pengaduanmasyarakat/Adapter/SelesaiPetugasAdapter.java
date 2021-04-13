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

import com.rahma.pengaduanmasyarakat.DetailBeranda;
import com.rahma.pengaduanmasyarakat.Detail_proses;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_ProsesPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.E_SelesaiPetugas;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SelesaiPetugasAdapter extends RecyclerView.Adapter<SelesaiPetugasAdapter.SelesaiPetugasViewHolder> {
    private List<E_SelesaiPetugas> selesaiPetugas;
    E_SelesaiPetugas eSelesaiPetugas;
    Context mContext;
    CardView cardView;

    public SelesaiPetugasAdapter(Context mContext, List<E_SelesaiPetugas> e_selesaiPetugases){
        this.mContext = mContext;
        selesaiPetugas = e_selesaiPetugases;
    }

    @NonNull
    @Override
    public SelesaiPetugasAdapter.SelesaiPetugasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proses_petugas,parent,false);

        return new SelesaiPetugasAdapter.SelesaiPetugasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelesaiPetugasAdapter.SelesaiPetugasViewHolder holder, int position) {
        eSelesaiPetugas = selesaiPetugas.get(position);
        holder.tgl.setText(eSelesaiPetugas.getTglPengaduan());
        holder.laporan.setText(eSelesaiPetugas.getIsiLaporan());
        holder.nama.setText(eSelesaiPetugas.getNama());
        Bitmap setFoto=null;
        String foto;
        foto = eSelesaiPetugas.getFoto();
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
        return selesaiPetugas.size();
    }

    public class SelesaiPetugasViewHolder extends RecyclerView.ViewHolder {
        public final TextView tgl,laporan,nama;
        public ImageView fotoo ;
        CardView cardView;
        public SelesaiPetugasViewHolder(@NonNull View itemView) {
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
                    if (position != RecyclerView.NO_POSITION) {
                        Intent i = new Intent(mContext, DetailBeranda.class);
                        i.putExtra("id_pengaduan", selesaiPetugas.get(position).getIdPengaduan());
                        i.putExtra("tgl_pengaduan", selesaiPetugas.get(position).getTglPengaduan());
                        i.putExtra("isi_laporan", selesaiPetugas.get(position).getIsiLaporan());
                        i.putExtra("foto", selesaiPetugas.get(position).getFoto());
                        i.putExtra("nama", selesaiPetugas.get(position).getNama());
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }
}
