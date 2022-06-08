package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class sendActivity extends AppCompatActivity {

    TextView name;
    ImageView image;
    Bundle bundle;
    Context context;
    String txt;
    int inputImg;
//    FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        context = this;

        name = (TextView) findViewById(R.id.textView);
        image = (ImageView) findViewById(R.id.imageView);
        bundle = getIntent().getExtras();

        if (bundle != null) {
            txt = bundle.getString("text");
            name.setText(txt);
            inputImg = bundle.getInt("image");
            image.setImageResource(inputImg);
        }

        Button copyText = (Button) findViewById(R.id.copy_btn);
        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String getString = name.getText().toString();
                ClipData clip = ClipData.newPlainText("PlainTex", getString);

//                clipboard.setPrimaryClip(clip);
//                clip.getDescription();

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_baseline_warning_24);
                builder.setTitle("Copy");
                builder.setMessage("Do you want really copy?");

                builder.setCancelable(false);  //when you press back button alert does not exit

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        clipboard.setPrimaryClip(clip);
                        clip.getDescription();
                        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();  //for alert box create

//                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();


            }
        });

        Button share_btn = (Button) findViewById(R.id.share);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();



        share_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
                shareImage(bitmap);
            }
        });


        Button saveImg = (Button) findViewById(R.id.download_img);
        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap1 = bitmapDrawable.getBitmap();
//                saveBitMap(context, bitmap1);


                ViewGroup viewGroup=findViewById(android.R.id.content);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                View view= LayoutInflater.from(context).inflate(R.layout.alert_dialog,viewGroup,false);
                builder.setCancelable(false);
                builder.setView(view);

                TextView txtCancel=view.findViewById(R.id.Cancel_btn);
                Button btn=view.findViewById(R.id.btn_download);



                AlertDialog alertDialog=builder.create();

                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Downloading",Toast.LENGTH_SHORT).show();
                        saveBitMap(context, bitmap1);

                    }
                });

                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();



            }
        });

    }

    private void shareImage(Bitmap bitmap) {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        Uri bmpUri;
        String textToShare = txt;
        bmpUri = saveImage(bitmap, getApplicationContext());
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        share.putExtra(Intent.EXTRA_SUBJECT, "New App");
        share.putExtra(Intent.EXTRA_TEXT, textToShare);
        startActivity(Intent.createChooser(share, "share Content"));

    }

    private static Uri saveImage(Bitmap image, Context context) {
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdir();
            File file = new File(imagesFolder, "share_image.jpg");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();    //use on buffered streams
            stream.close();

            uri = FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                    "com.example.task" + ".provider", file);
        } catch (Exception e) {
            Log.d("TAG", "Exception" + e.getMessage());
        }

        return uri;
    }

    private void saveBitMap(Context context, Bitmap Final_bitmap) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "File Name");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");

        }
        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            Final_bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
            Toast.makeText(context, "image is downloaded in device gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery(context, pictureFile.getAbsolutePath());
    }


    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Toast.makeText(context, "image is downloaded in device gallery", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }

}


