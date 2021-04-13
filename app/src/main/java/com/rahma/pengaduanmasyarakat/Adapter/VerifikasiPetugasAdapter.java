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
import com.rahma.pengaduanmasyarakat.DetailVerifikasi;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_SelesaiPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.E_Verifikasi;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class VerifikasiPetugasAdapter extends RecyclerView.Adapter<VerifikasiPetugasAdapter.VerifikasiPetugasViewHolder> {
    private List<E_Verifikasi> verifikasi;
    E_Verifikasi e_verifikasi;
    Context mContext;

    public VerifikasiPetugasAdapter(Context mContext, List<E_Verifikasi> e_verifikasis){
        this.mContext = mContext;
        verifikasi = e_verifikasis;
    }

    @NonNull
    @Override
    public VerifikasiPetugasAdapter.VerifikasiPetugasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proses_petugas,parent,false);

        return new VerifikasiPetugasAdapter.VerifikasiPetugasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerifikasiPetugasAdapter.VerifikasiPetugasViewHolder holder, int position) {
        e_verifikasi = verifikasi.get(position);
        holder.tgl.setText(e_verifikasi.getTglPengaduan());
        holder.laporan.setText(e_verifikasi.getIsiLaporan());
        holder.nama.setText(e_verifikasi.getNama());
        Bitmap setFoto=null;
        String foto;
        foto = e_verifikasi.getFoto();
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
        return verifikasi.size();
    }

    public class VerifikasiPetugasViewHolder extends RecyclerView.ViewHolder {
        public final TextView tgl,laporan,nama;
        public ImageView fotoo ;
        CardView cardView;
        public VerifikasiPetugasViewHolder(@NonNull View itemView) {
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
                        Intent i = new Intent(mContext, DetailVerifikasi.class);
                        i.putExtra("idp", verifikasi.get(position).getIdPengaduan());
                        i.putExtra("tgl", verifikasi.get(position).getTglPengaduan());
                        i.putExtra("laporan", verifikasi.get(position).getIsiLaporan());
                        i.putExtra("foto", verifikasi.get(position).getFoto());
                        i.putExtra("nama", verifikasi.get(position).getNama());
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }
}
