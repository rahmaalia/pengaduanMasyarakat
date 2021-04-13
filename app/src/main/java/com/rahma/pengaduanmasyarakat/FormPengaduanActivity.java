package com.rahma.pengaduanmasyarakat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormPengaduanActivity extends AppCompatActivity {

    ImageView back;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    String nama,nik;
    EditText laporan;
    TextView namaresult,nikresult,tambahPoto,tanggal,hari,bulan,tahun;
    BaseApiService mApiService;
    Uri filePath;
    Bitmap bitmap;
    File file;
    View view;
    String path;
    String Date;
    ImageView imageView;
    Button btnSubmit;
    String status;
    TextInputLayout layoutLaporan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengaduann);

        sharedPrefManager = new SharedPrefManager(this);
        mContext = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar date =Calendar.getInstance();
        Date= DateFormat.format(date.getTime());

        back = findViewById(R.id.back);
        namaresult = findViewById(R.id.namaLengkap);
        nikresult = findViewById(R.id.nikl);
        laporan = findViewById(R.id.et_laporanL);
        tambahPoto = findViewById(R.id.tambahPhoto);
        tanggal = findViewById(R.id.tanggal);
        imageView = findViewById(R.id.foto);
        btnSubmit = findViewById(R.id.btnKirim);

        namaresult.setText(sharedPrefManager.getSpNama());
        nikresult.setText(sharedPrefManager.getSpNik());

        nama = sharedPrefManager.getSpNama();
        nik = sharedPrefManager.getSpNik();

        status = "proses";
        hari();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPengaduan();
                uploadImage();

            }
        });

        tambahPoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(mContext);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FormPengaduanActivity.this, BerandaActivity.class);
                startActivity(i);
            }
        });


    }

    private void requestPengaduan() {

        if (laporan.length()==0) {
            Toast.makeText(mContext, "Isi laporan", Toast.LENGTH_LONG).show();
        }
//        if(file == null){
//            Toast.makeText(mContext, "tambah foto bukti", Toast.LENGTH_LONG).show();
//        }

        if (laporan.length()>0){
            mApiService.inputLaporan(Date,
                    nik,
                    laporan.getText().toString(),
                    file.getName()
            ).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("status").equals("true")){
                            Toast.makeText(mContext, "BERHASIL LAPOR", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(FormPengaduanActivity.this,BerandaActivity .class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void selectImage(Context mContext) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                    imageView.setVisibility(view.VISIBLE);


                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                    imageView.setVisibility(view.VISIBLE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }



    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(bitmap);
                        Uri tempUri = getImageUri(mContext.getApplicationContext(), bitmap);
                        file = new File(getRealPathFromURI(tempUri));
                        tambahPoto.setText(file.getName());
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        filePath = data.getData();
                        path = FileUtil.getPath(mContext, data.getData());
                        file = new File(path);
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (filePath != null) {
                            Cursor cursor = mContext.getContentResolver().query(filePath,
                                    filePathColumn, null, null, null);
                            tambahPoto.setText(file.getName());
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }

    }



    private void uploadImage() {

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        BaseApiService uploadApis = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);
        Call<RequestBody> call = uploadApis.uploadFoto(parts);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext,"Add Item Succeeded",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call <RequestBody> call, Throwable t) {

            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (mContext.getContentResolver() != null) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }




    private void hari() {
        hari = findViewById(R.id.hari);
        bulan = (TextView) findViewById(R.id.bulan);
        tahun = (TextView) findViewById(R.id.tahun);

        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        String[] namaBulan = {"Januari","Februari","Maret", "April", "Mei", "Juni", "Juli",
                "Agustus", "September", "Oktober", "Novevenber",
                "Desember"};
        String [] namaHari = {  "Sabtu", "Minggu", "Senin", "Selasa", "Rabu", "Kamis","Jumat","sabtu"};

        String harii = namaHari[c.get(Calendar.DAY_OF_WEEK)];
        String bulann = namaBulan[c.get(Calendar.MONTH)];
        int tahunn = c.get(Calendar.YEAR);
        int date = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat jam = new SimpleDateFormat("HH:mm:ss");
        Calendar datee =Calendar.getInstance();

        hari.setText(""+ harii + "," );
        bulan.setText(""+bulann );
        tahun.setText(""+tahunn );
        tanggal.setText("" +date);

        Date = (""+date+" "+""+bulann+" "+""+tahunn+"  "+""+jam.format(datee.getTime()));
    }


}