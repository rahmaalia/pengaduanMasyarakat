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

import com.rahma.pengaduanmasyarakat.Detail_proses;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.E_Selesai;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SelesaiAdapter extends RecyclerView.Adapter<SelesaiAdapter.SelesaiViewHolder>{
    private List<E_Selesai> selesaiList;
    E_Selesai eSelesai;
    Context mContext;
    CardView cardView;


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
        holder.status.setText(eSelesai.getStatus());
        Bitmap setFoto=null;
        String foto;
        foto = eSelesai.getFoto();
        try {
            URL url = new URL(RetrofitClient.BASE_URL_FOTO + foto);
            setFoto = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }  catch (IOException e) {
            e.printStackTrace();
        }
        holder.fotoo.setImageBitmap(setFoto);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(mContext, Detail_proses.class);
//                i.putExtra("id_pengaduan",eSelesai.getIdPengaduan());
//                i.putExtra("tgl_pengaduan", eSelesai.getTglPengaduan());
//                i.putExtra("isi_laporan", eSelesai.getIsiLaporan());
//                i.putExtra("foto",eSelesai.getFoto());
//                mContext.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return selesaiList.size();
    }

    public class SelesaiViewHolder extends RecyclerView.ViewHolder {
        public final TextView tgl, laporan, status;
        ImageView fotoo;
        CardView cardView;
        public SelesaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tgl);
            laporan = itemView.findViewById(R.id.tv_detail);
            fotoo = itemView.findViewById(R.id.imageL);
            status = itemView.findViewById(R.id.statuss);
            cardView = itemView.findViewById(R.id.cvLaporan);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Intent i = new Intent(mContext, Detail_proses.class);
                        i.putExtra("id_pengaduan",selesaiList.get(position).getIdPengaduan());
                        i.putExtra("tgl_pengaduan", selesaiList.get(position).getTglPengaduan());
                        i.putExtra("isi_laporan", selesaiList.get(position).getIsiLaporan());
                        i.putExtra("foto",selesaiList.get(position).getFoto());
                        i.putExtra("status",selesaiList.get(position).getStatus());
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }
}
