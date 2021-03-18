//package com.rahma.pengaduanmasyarakat;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.GradientDrawable;
//import android.graphics.drawable.ShapeDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.temanlapak.R;
//import com.temanlapak.data.local.PreferencesHelper;
//import com.temanlapak.model.auth.ThemeResponse;
//import com.temanlapak.model.common.ApiResponse;
//import com.temanlapak.model.kategori.KategoriResponse;
//import com.temanlapak.model.product.ProductParam;
//import com.temanlapak.model.product.ProductResponse;
//import com.temanlapak.model.product.StockResponse;
//import com.temanlapak.model.satuan.SatuanResponse;
//import com.temanlapak.util.ContextProvider;
//import com.temanlapak.util.CurrencyUtil;
//import com.temanlapak.util.CurrencyUtilFormat;
//import com.temanlapak.util.DialogFactory;
//import com.temanlapak.util.Toasts;
//import com.temanlapak.view.ImagePickerActivity;
//import com.temanlapak.view.commons.AbsController;
//import com.temanlapak.view.kategori.KategoriChooserAdapter;
//import com.temanlapak.view.main.InventoryActivity;
//import com.temanlapak.view.main.MainActivity;
//import com.temanlapak.view.satuan.SatuanChooserAdapter;
//
//import net.derohimat.baseapp.ui.view.BaseImageView;
//
//import java.io.File;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import javax.inject.Inject;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//import dagger.Lazy;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//
//import static android.app.Activity.RESULT_OK;
//import static android.content.Context.LAYOUT_INFLATER_SERVICE;
//import static com.temanlapak.view.main.InventoryController.ACTION_ADD_PRODUCT;
//import static com.temanlapak.view.main.InventoryController.ACTION_DETAIL_PRODUCT;
//import static com.temanlapak.view.main.InventoryController.KEY_ACTION_PRODUCT;
//import static com.temanlapak.view.main.InventoryController.KEY_DATA_PRODUCT;
//import static com.yalantis.ucrop.UCropFragment.TAG;
//
//public class ProductController extends AbsController implements ProductView {
//
//    private static final int PERMISSION_REQUEST_STORAGE = 2;
//    public static final int REQUEST_IMAGE = 100;
//
//    @Inject ProductPresenter presenter;
//    @Inject Lazy<ProgressDialog> dialogLazy;
//    @Inject PreferencesHelper preferencesHelper;
//    @Inject KategoriChooserAdapter kategoriAdapter;
//    @Inject SatuanChooserAdapter satuanAdapter;
//    @Inject PreferencesHelper preference;
//
//    @Bind(R.id.product_image) BaseImageView imagePreview;
//    @Bind(R.id.product_inp_name) EditText inpName;
//    @Bind(R.id.product_inp_harga_modal) EditText inpHargaModal;
//    @Bind(R.id.product_inp_harga_jual) EditText inpHargaJual;
//
//    @Bind(R.id.product_add_new_kategori) TextView newKategori;
//    @Bind(R.id.product_spin_kategori) Spinner spinKategori;
//    @Bind(R.id.product_layout_new_kategori) LinearLayout layoutNewKategori;
//    @Bind(R.id.product_inp_kategori) EditText inpKategori;
//
//    @Bind(R.id.product_add_new_satuan) TextView newSatuan;
//    @Bind(R.id.product_spin_satuan) Spinner spinSatuan;
//    @Bind(R.id.product_layout_new_satuan) LinearLayout layoutNewSatuan;
//    @Bind(R.id.product_inp_satuan) EditText inpSatuan;
//
//    @Bind(R.id.product_track_stok) Switch trackStok;
//    @Bind(R.id.product_inp_stok) EditText inpQty;
//    @Bind(R.id.bg_barang) RelativeLayout bg;
//    @Bind(R.id.layout_parent) RelativeLayout layoutParent;
//
//    ThemeResponse theme;
//
//    ProductResponse dataProduct;
//    List<KategoriResponse> dataKategori = new ArrayList<>();
//    List<SatuanResponse> dataSatuan = new ArrayList<>();
//
//    int idKategori = 0;
//    int idSatuan = 0;
//    int trackStokStatus = 0;
//    int id_barang = 0;
//    String mediaPath = "";
//    String hargaModal = "";
//    String hargaJual = "";
//
//    private PopupWindow mPopupWindow;
//
//    public ProductController(Bundle args) {
//        super(args);
//    }
//
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.controller_product;
//    }
//
//    @Override
//    protected void onViewBound(View view) {
//        getActivityComponent().inject(this);
//        bindPresenter(this, presenter);
//
//        deleteCache(getActivity());
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Drawable bgRadius = getActivity().getDrawable(R.drawable.btn_masuk);
//            if (bgRadius instanceof ShapeDrawable) {
//                ((ShapeDrawable) bgRadius).getPaint().setColor(Color.parseColor("#2D2E29"));
//            } else if (bgRadius instanceof GradientDrawable) {
//                ((GradientDrawable) bgRadius).setColor(Color.parseColor("#2D2E29"));
//            } else if (bgRadius instanceof ColorDrawable) {
//                ((ColorDrawable) bgRadius).setColor(Color.parseColor("#2D2E29"));
//            }
//        }
//
//        dataProduct = getArgs().getParcelable(KEY_DATA_PRODUCT);
//
//        theme = preference.getThemeResponse();
//        if (theme != null) {
//            if (theme.getWarnaPrimary() != null && !theme.getWarnaPrimary().equals(""))
//                bg.setBackgroundColor(Color.parseColor(theme.getWarnaPrimary()));
//        }
//
//        spinKategori.setAdapter(kategoriAdapter);
//        spinKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                idKategori = kategoriAdapter.getDatas().get(position).getIdKategori();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinSatuan.setAdapter(satuanAdapter);
//        spinSatuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                idSatuan = satuanAdapter.getDatas().get(position).getIdSatuan();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        trackStok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    trackStokStatus = 1;
//                    inpQty.setVisibility(View.VISIBLE);
//
//                    if (getArgs().getString(KEY_ACTION_PRODUCT).equals(ACTION_ADD_PRODUCT))
//                        inpQty.setText("0");
//
//                    new Handler().postDelayed(() -> {
//                        showPopup();
//                    }, 100);
//
//                } else {
//                    trackStokStatus = 0;
//                    inpQty.setVisibility(View.GONE);
//
//                    if (getArgs().getString(KEY_ACTION_PRODUCT).equals(ACTION_ADD_PRODUCT))
//                        inpQty.setText("0");
//                }
//            }
//        });
//        presenter.loadKategori();
//        presenter.loadSatuan();
//
//        if (dataProduct != null)
//            setDataProduct();
//
//        inpHargaModal.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                inpHargaModal.removeTextChangedListener(this);
//
//                try {
//                    String originalString = s.toString();
//
//                    Long longval;
//                    if (originalString.contains(",")) {
//                        originalString = originalString.replaceAll(",", "");
//                    }
//                    longval = Long.parseLong(originalString);
//
//                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                    formatter.applyPattern("#,###,###,###");
//                    String formattedString = formatter.format(longval);
//
//                    //setting text after format to EditText
//                    inpHargaModal.setText(formattedString);
//                    inpHargaModal.setSelection(inpHargaModal.getText().length());
//                } catch (NumberFormatException nfe) {
//                    nfe.printStackTrace();
//                }
//
//                inpHargaModal.addTextChangedListener(this);
//
//                hargaModal = inpHargaModal.getText().toString().replaceAll("[$,.]", "");
//            }
//        });
//
//        inpHargaJual.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                inpHargaJual.removeTextChangedListener(this);
//
//                try {
//                    String originalString = s.toString();
//
//                    Long longval;
//                    if (originalString.contains(",")) {
//                        originalString = originalString.replaceAll(",", "");
//                    }
//                    longval = Long.parseLong(originalString);
//
//                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                    formatter.applyPattern("#,###,###,###");
//                    String formattedString = formatter.format(longval);
//
//                    //setting text after format to EditText
//                    inpHargaJual.setText(formattedString);
//                    inpHargaJual.setSelection(inpHargaJual.getText().length());
//                } catch (NumberFormatException nfe) {
//                    nfe.printStackTrace();
//                }
//
//                inpHargaJual.addTextChangedListener(this);
//
//                hargaJual = inpHargaJual.getText().toString().replaceAll("[$,.]", "");
//            }
//        });
//    }
//
//    private void setDataProduct() {
//        if (dataProduct.getFoto().equals("-"))
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                imagePreview.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_black_24dp));
//            }
//            else
//                imagePreview.setImageUrl(dataProduct.getFoto());
//                inpName.setText(dataProduct.getNama_barang());
//                inpHargaModal.setText(CurrencyUtilFormat.priceToString(String.valueOf(dataProduct.getHpp())));
//                inpHargaJual.setText(CurrencyUtilFormat.priceToString(String.valueOf(dataProduct.getHarga_jual())));
//
//        if (dataProduct.getTrackStok() == 1) {
//            trackStok.setChecked(true);
//            inpQty.setVisibility(View.VISIBLE);
//        }
//
//        for (int i=0; i<dataProduct.getStock().size(); i++) {
//            StockResponse stock = dataProduct.getStock().get(i);
//            if (stock.getPosisiPelanggan() == 0) {
//                if (stock.getQty() == 0){
//                    inpQty.setText(String.valueOf(stock.getQty()));
//                    inpQty.setEnabled(true);
//                }else {
//                    inpQty.setText(String.valueOf(stock.getQty()));
//                    inpQty.setEnabled(false);
//                }
//
//                break;
//            }
//        }
//
////        inpQty.setEnabled(true);
//    }
//
//
//    @Override
//    public void getKategori(List<KategoriResponse> data) {
//        dataKategori = data;
//
//        kategoriAdapter.clear();
//        kategoriAdapter.add(data);
//
//        if (dataProduct != null) setKategori();
//    }
//    private void setKategori() {
//        for (int i=0; i<dataKategori.size(); i++) {
//            KategoriResponse kat = dataKategori.get(i);
//            if (kat.getIdKategori() == dataProduct.getIdKategori()) {
//                spinKategori.setSelection(i);
//                break;
//            }
//        }
//    }
//
//    @Override
//    public void getSatuan(List<SatuanResponse> data) {
//        dataSatuan = data;
//
//        satuanAdapter.clear();
//        satuanAdapter.add(data);
//
//        if (dataProduct != null) setSatuan();
//    }
//    private void setSatuan() {
//        for (int i=0; i<dataSatuan.size(); i++) {
//            SatuanResponse sat = dataSatuan.get(i);
//            if (sat.getIdSatuan() == dataProduct.getIdSatuan()) {
//                spinSatuan.setSelection(i);
//                break;
//            }
//        }
//    }
//
//    @Override
//    public void afterStoreProduct(int new_id_barang, String photoPath) {
//        id_barang = new_id_barang;
//
//        if (!photoPath.equals("")) {
//            File file = new File(photoPath);
//
//            new Handler().postDelayed(() -> {
//                uploadMultipart(file);
//            }, 100);
//        } else {
//            DialogFactory.createSimpleOkDialog(getContext(), "Sukses", "Produk tersimpan").show();
//
//            new Handler().postDelayed(() -> {
//                finishActivity();
//                InventoryActivity.start(getContext(), theme.getWarnaPrimary(), theme.getWarnaSecondary());
//            }, 1000);
//        }
//    }
//
//    @Override
//    public void afterPhotoUploaded(ApiResponse apiResponse) {
//        DialogFactory.createSimpleOkDialog(getContext(), "Sukses", "Produk tersimpan").show();
//
//        new Handler().postDelayed(() -> {
//            finishActivity();
//            MainActivity.start(getContext(),"4");
//        }, 1000);
//
//    }
//
//    @Override
//    public void showProgress(boolean isShow) {
//        if (isShow) {
//            dialogLazy.get().show();
//            dialogLazy.get().setCanceledOnTouchOutside(false);
//        } else {
//            dialogLazy.get().dismiss();
//        }
//    }
//
//    @Override
//    public void showLoading(boolean isShow, int loadingType) {
//        if (isShow) {
//            dialogLazy.get().show();
//
//            dialogLazy.get().setCanceledOnTouchOutside(false);
//        } else {
//            dialogLazy.get().dismiss();
//        }
//    }
//
//    @Override
//    public void showResponse(ApiResponse apiResponse) {
//        DialogFactory.createSimpleOkDialog(getContext(), "Sukses", apiResponse.getMessage()).show();
//    }
//
//    @Override
//    public void showFailedData(String title, String errorMessage) {
//        //DialogFactory.createGenericErrorDialogWithTitle(getContext(), title, errorMessage).show();
//        showDialog();
////        new Handler().postDelayed(() -> {
////            finishActivity();
////            MainActivity.start(getContext());
////        }, 1000);
//    }
//
//    @Override
//    public Context getContext() {
//        return getActivity();
//    }
//
//    @OnClick({
//            R.id.product_add_new_kategori,
//            R.id.product_close_new_kategori,
//            R.id.product_add_new_satuan,
//            R.id.product_close_new_satuan
//    })
//    public void onAddNewClicked(View view) {
//        switch(view.getId()) {
//            case R.id.product_add_new_kategori:
//                spinKategori.setVisibility(View.GONE);
//                layoutNewKategori.setVisibility(View.VISIBLE);
//                newKategori.setVisibility(View.GONE);
//                break;
//            case R.id.product_close_new_kategori:
//                spinKategori.setVisibility(View.VISIBLE);
//                layoutNewKategori.setVisibility(View.GONE);
//                inpKategori.setText("");
//                newKategori.setVisibility(View.VISIBLE);
//                break;
//            case R.id.product_add_new_satuan:
//                spinSatuan.setVisibility(View.GONE);
//                layoutNewSatuan.setVisibility(View.VISIBLE);
//                newSatuan.setVisibility(View.GONE);
//                break;
//            case R.id.product_close_new_satuan:
//                spinSatuan.setVisibility(View.VISIBLE);
//                layoutNewSatuan.setVisibility(View.GONE);
//                inpSatuan.setText("");
//                newSatuan.setVisibility(View.VISIBLE);
//                break;
//        }
//    }
//
//    @OnClick(R.id.product_image)
//    public void onProductImageClicked() {
//        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    PERMISSION_REQUEST_STORAGE);
//
//        }else{
//            ImagePickerActivity.showImagePickerOptions(getContext(), new ImagePickerActivity.PickerOptionListener() {
//                @Override
//                public void onTakeCameraSelected() {
//                    launchCameraIntent();
//                }
//
//                @Override
//                public void onChooseGallerySelected() {
//                    launchGalleryIntent();
//                }
//            });
//        }
//    }
//
//    private void launchCameraIntent() {
//        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
//        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);
//
//        // setting aspect ratio
//        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
//
//        // setting maximum bitmap width and height
//        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
//        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
//        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
//
//        startActivityForResult(intent, REQUEST_IMAGE);
//    }
//
//    private void launchGalleryIntent() {
//        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
//        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
//
//        // setting aspect ratio
//        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
//        startActivityForResult(intent, REQUEST_IMAGE);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            // When an Image is picked
//            if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && null != data) {
//
//                // Get image URI from UCrop (ImagePickerActivity)
//                Uri photoUri = data.getParcelableExtra("path");
//                mediaPath = photoUri.getPath();
//
//                // Set the Image in ImageView for Previewing the Media
//                imagePreview.setImageURI(photoUri);
//                imagePreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            } else {
//                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//            Log.e("Gagal",e.toString());
//            e.printStackTrace();
//        }
//
//    }
//
//    private void uploadMultipart(File file) {
//        RequestBody photoBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo",
//                file.getName(), photoBody);
//        presenter.uploadPhoto(id_barang, photoPart);
//    }
//
//
//
//    @OnClick(R.id.product_btn_simpan)
//    public void onBtnSimpanClicked() {
//
////        if (TextUtils.isEmpty(inpName.getText().toString())){
////            showDialog();
////        }
//
//        try {
//            ProductParam param = new ProductParam(
//                inpName.getText().toString(),
//                idKategori,
//                inpKategori.getText().toString(),
//                idSatuan,
//                inpSatuan.getText().toString(),
//                Integer.parseInt(inpHargaModal.getText().toString().replaceAll("[$,.]", "")),
//                Integer.parseInt(inpHargaJual.getText().toString().replaceAll("[$,.]", "")),
//                preferencesHelper.getUserResponse().getNom(),
//                preferencesHelper.getUserResponse().getId_cabang(),
//                trackStokStatus,
//                Double.parseDouble(inpQty.getText().toString())
//
//
//        );
//            Log.e("qty",inpQty.getText().toString());
//
//            if (getArgs().getString(KEY_ACTION_PRODUCT).equals(ACTION_ADD_PRODUCT))
//                presenter.storeProduct(param, mediaPath);
//            else if (getArgs().getString(KEY_ACTION_PRODUCT).equals(ACTION_DETAIL_PRODUCT))
//                presenter.updateProduct(dataProduct.getId_barang(), param, mediaPath);
//
//        }catch (Exception e) {
//            showDialog();
//            Log.e("Gagal",e.toString());
//            e.printStackTrace();
//        }
//
//
//
//    }
//
//    private void showDialog(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                getContext());
//
//        // set title dialog
//        alertDialogBuilder.setTitle("Pemberitahuan");
//
//        // set pesan dari dialog
//        alertDialogBuilder
//                .setMessage("Data Tidak Boleh Kosong!")
//                .setCancelable(false)
//                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // jika tombol diklik, maka akan menutup activity ini
//                            dialog.cancel();
//                    }
//                });
//
//        // membuat alert dialog dari builder
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // menampilkan alert dialog
//        alertDialog.show();
//    }
//
//    public static void deleteCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            deleteDir(dir);
//        } catch (Exception e) { e.printStackTrace();}
//    }
//
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//            return dir.delete();
//        } else if(dir!= null && dir.isFile()) {
//            return dir.delete();
//        } else {
//            return false;
//        }
//    }
//
//    private void showPopup() {
//        // Initialize a new instance of LayoutInflater service
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//
//        // Inflate the custom layout/view
//        View customView = inflater.inflate(R.layout.track_stok_popup,null);
//
//        /*
//            public PopupWindow (View contentView, int width, int height)
//                Create a new non focusable popup window which can display the contentView.
//                The dimension of the window must be passed to this constructor.
//
//                The popup does not provide any background. This should be handled by
//                the content view.
//
//            Parameters
//                contentView : the popup's content
//                width : the popup's width
//                height : the popup's height
//        */
//        // Initialize a new instance of popup window
//        mPopupWindow = new PopupWindow(
//                customView,
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT
//        );
//
//        RelativeLayout layoutBg = (RelativeLayout) customView.findViewById(R.id.layout_bg);
//        layoutBg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////               KategoriActivity.start(getContext(), colorPrimary, colorSecondary);
//                mPopupWindow.dismiss();
//            }
//
//        });
//
//        //Popupwindow popup animation
//        mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
//        // The aggregation, to control event menu must call this method
//        mPopupWindow.setFocusable(true);
//        // Set an elevation value for popup window
//        // Call requires API level 21
//        if(Build.VERSION.SDK_INT>=21){
//            mPopupWindow.setElevation(5.0f);
//        }
//
//        // Get a reference for the custom view close button
//        RelativeLayout headerLayout = (RelativeLayout) customView.findViewById(R.id.kategori_popup_layout_header);
//        TextView txtHeader = (TextView) customView.findViewById(R.id.kategori_popup_header);
//
//        if (theme.getWarnaPrimary() != null && !theme.getWarnaPrimary().equals(""))
//            headerLayout.setBackgroundColor(Color.parseColor(theme.getWarnaPrimary()));
//
//
//
//
//        /*
//            public void showAtLocation (View parent, int gravity, int x, int y)
//                Display the content view in a popup window at the specified location. If the
//                popup window cannot fit on screen, it will be clipped.
//                Learn WindowManager.LayoutParams for more information on how gravity and the x
//                and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
//                to specifying Gravity.LEFT | Gravity.TOP.
//
//            Parameters
//                parent : a parent view to get the getWindowToken() token from
//                gravity : the gravity which controls the placement of the popup window
//                x : the popup's x location offset
//                y : the popup's y location offset
//        */
//        // Finally, show the popup window at the center location of root relative layout
//        mPopupWindow.showAtLocation(layoutParent, Gravity.CENTER,0,0);
//    }
//
//
//}
