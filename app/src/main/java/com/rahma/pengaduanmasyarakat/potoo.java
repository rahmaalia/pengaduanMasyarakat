//package com.rahma.pengaduanmasyarakat;
//
//import okhttp3.RequestBody;
//
//public class potoo {
//    EditText editTextNama;
//    EditText editTextDesc;
//    EditText editTextHarga;
//    ListPetugasAdapter listPetugasAdapter;
//    Context mContext;
//    ApiService mApiService;
//    RecyclerView recyclerView;
//    private TambahPetugas.ExampleDialogListener listener;
//    private List<ListPetugas> petugasList=new ArrayList<>();
//    String Date;
//    View view;
//    ImageView imageView;
//    Uri filePath;
//    Bitmap bitmap;
//    String path;
//    String namaFoto;
//    File file;
//    private DialogInterface.OnDismissListener onDismissListener;
//    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
//        this.onDismissListener = onDismissListener;
//    }
//
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
//        if (onDismissListener != null) {
//            onDismissListener.onDismiss(dialog);
//        }
//    }
//
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        mContext=getActivity();
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_add_barang, null);
//        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
//        TextView title = new TextView(getContext());
//        listPetugasAdapter =new ListPetugasAdapter(getContext(), petugasList);
//        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar date =Calendar.getInstance();
//        Date= DateFormat.format(date.getTime());
//        editTextNama = view.findViewById(R.id.et_nama_barang);
//        editTextHarga = view.findViewById(R.id.et_harga);
//        editTextDesc=view.findViewById(R.id.et_desc);
//        imageView=view.findViewById(R.id.addfoto);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage(getContext());
//            }
//        });
//
//        title.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
//        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//        title.setTypeface(Typeface.DEFAULT_BOLD);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 20, 0, 0);
//        title.setPadding(0,30,0,0);
//        title.setLayoutParams(lp);
//        title.setText("Add Item");
//        title.setGravity(Gravity.CENTER);
//        builder.setView(view)
//                .setCustomTitle(title)
//                .setIcon(R.drawable.icon)
//                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, final int i) {
//
//                        mApiService.addBarang(editTextNama.getText().toString(),Date,editTextDesc.getText().toString(),file.getName(),Integer.parseInt(editTextHarga.getText().toString())).
//                                enqueue(new Callback<ResponseBody>() {
//
//                                    @Override
//                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                        if (response.isSuccessful()) {
//
//                                        } else {
//                                            Toast.makeText(mContext, "Make the Team Fail", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                    @Override
//                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                    }
//                                });
//                        uploadImage();
//
//                    }
//                });
//
//
//        return builder.create();
//    }
//
//
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            listener = (TambahPetugas.ExampleDialogListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() +
//                    "must implement ExampleDialogListener");
//        }
//    }
//
//    public interface ExampleDialogListener {
//
//        void applyTexts(String username, String password);
//    }
//    private void selectImage(Context context) {
//        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Add Photo");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
//
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto , 1);
//
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    @Override
//    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case 0:
//                    if (resultCode == RESULT_OK && data != null) {
//                        bitmap = (Bitmap) data.getExtras().get("data");
//                        imageView.setImageBitmap(bitmap);
//                        Uri tempUri = getImageUri(getContext().getApplicationContext(), bitmap);
//                        file = new File(getRealPathFromURI(tempUri));
//                    }
//
//                    break;
//                case 1:
//                    if (resultCode == RESULT_OK && data != null) {
//                        filePath =  data.getData();
//                        path = FileUtil.getPath(getContext(), data.getData());
//                        file = new File(path);
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (filePath != null) {
//                            Cursor cursor = getContext().getContentResolver().query(filePath,
//                                    filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                cursor.moveToFirst();
//
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                cursor.close();
//                            }
//                        }
//
//                    }
//                    break;
//            }
//        }
//    }
//    private void uploadImage() {
//
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
//
//
//        ApiService uploadApis = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
//        Call<RequestBody> call = uploadApis.uploadImage(parts);
//
//        call.enqueue(new Callback<RequestBody>() {
//            @Override
//            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
//                if (response.isSuccessful()){
//                    Toast.makeText(getContext(),"Add Item Succeeded",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call <RequestBody> call, Throwable t) {
//
//            }
//        });
//    }
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Camera", null);
//        return Uri.parse(path);
//    }
//    public String getRealPathFromURI(Uri uri) {
//        String path = "";
//        if (getContext().getContentResolver() != null) {
//            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                path = cursor.getString(idx);
//                cursor.close();
//            }
//        }
//        return path;
//    }
//}
