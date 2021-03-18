//package com.rahma.pengaduanmasyarakat;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.tugasakhir.R;
//import com.example.tugasakhir.SharedPrefManager;
//import com.squareup.picasso.Picasso;
//
//public class Detail extends AppCompatActivity {
//
//    ImageView gambarHotel , btnkembali;
//    Context context;
//    TextView namaHotel , alamatHotel , tlpHotel , hargahotell;
//    Button btnBooking;
//    String nama , alamat , telepon , gambar , harga , idhotel;
//    SharedPrefManager sharedPrefManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//
//        gambarHotel = (ImageView) findViewById(R.id.fotoHotel);
//        namaHotel = (TextView) findViewById(R.id.namaHotel);
//        alamatHotel = (TextView) findViewById(R.id.alamatHotel);
//        tlpHotel = (TextView) findViewById(R.id.tlpHotel);
//        btnBooking = (Button) findViewById(R.id.btnPesan);
//        btnkembali = findViewById(R.id.kembali);
//
//        sharedPrefManager = new SharedPrefManager(this);
//
//
//        showDetailBerita();
//
//
//
//        btnkembali.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Detail.this, MainActivity.class));
//            }
//        });
//        btnBooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Detail.this, BookingActivity.class);
//
//                i.putExtra("nama_hotel",nama);
//                i.putExtra("alamat_hotel", alamat);
//                i.putExtra("telepon_hotel", telepon);
//                i.putExtra("harga_kamar", harga);
//                i.putExtra("gambar_hotel", gambar);
//                i.putExtra("id_hotel", idhotel);
//
//
//                startActivity(i);
//            }
//        });
//
//
//
//    }
//
//
//    private void showDetailBerita() {
//        nama = getIntent().getStringExtra("nama_hotel");
//        alamat = getIntent().getStringExtra("alamat_hotel");
//         telepon = getIntent().getStringExtra("telepon_hotel");
//        gambar = getIntent().getStringExtra("gambar_hotel");
//        idhotel = getIntent().getStringExtra("id_hotel");
//
////        sharedPrefManager.simpanString(SharedPrefManager.ID_HOTEL, idhotel);
//
//        namaHotel.setText(nama);
//        alamatHotel.setText(alamat);
//        tlpHotel.setText(telepon);
//
//
//        final String urlGambarHotel =
////                "http://192.168.1.4/bookhotel/images/"
//                "http://192.168.43.132/bookhotel/images/"
//
//                       + gambar;
//        Picasso.with(Detail.this).load(urlGambarHotel).into(gambarHotel);
//
//
//
//    }
//
//}
