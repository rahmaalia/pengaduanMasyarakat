package com.rahma.pengaduanmasyarakat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
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
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProsesAdapter extends RecyclerView.Adapter<ProsesAdapter.BarangViewHolder> {
    private List<E_Proses> pengaduanList;
    E_Proses ePengaduan;
    Context mContext;


    public ProsesAdapter(Context mContext, List<E_Proses> e_pengaduans){
        this.mContext = mContext;
        pengaduanList = e_pengaduans;
    }

    @NonNull
    @Override
    public ProsesAdapter.BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporanku,parent,false);

        return new ProsesAdapter.BarangViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProsesAdapter.BarangViewHolder holder, int position) {

        ePengaduan = pengaduanList.get(position);
        holder.tgl.setText(ePengaduan.getTglPengaduan());
        holder.laporan.setText(ePengaduan.getIsiLaporan());
        holder.status.setText(ePengaduan.getStatus());
        Log.d("onBindViewHolder: ", ePengaduan.toString());
        Bitmap setFoto = null;
        String foto;
        foto = ePengaduan.getFoto();
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
//                i.putExtra("id_pengaduan",ePengaduan.getIdPengaduan());
//                i.putExtra("tgl_pengaduan", ePengaduan.getTglPengaduan());
//                i.putExtra("isi_laporan", ePengaduan.getIsiLaporan());
//                i.putExtra("foto",ePengaduan.getFoto());
//                i.putExtra("status",ePengaduan.getStatus());
//                mContext.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return pengaduanList.size();
    }

    public class BarangViewHolder extends RecyclerView.ViewHolder {
        public final TextView tgl,laporan,status;
        public ImageView fotoo ;
        CardView cardView;
        public BarangViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tgl);
            laporan = itemView.findViewById(R.id.tv_detail);
            fotoo = itemView.findViewById(R.id.imageL);
            cardView = itemView.findViewById(R.id.cvLaporan);
            status = itemView.findViewById(R.id.statuss);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Intent i = new Intent(mContext, Detail_proses.class);
                        i.putExtra("id_pengaduan",pengaduanList.get(position).getIdPengaduan());
                        i.putExtra("tgl_pengaduan", pengaduanList.get(position).getTglPengaduan());
                        i.putExtra("isi_laporan", pengaduanList.get(position).getIsiLaporan());
                        i.putExtra("foto",pengaduanList.get(position).getFoto());
                        i.putExtra("status",pengaduanList.get(position).getStatus());
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }
}
