package com.ujjani.imagedownloadfrominternet;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button btnDownloadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btnDownloadImage = findViewById(R.id.btnDownload);

        btnDownloadImage.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View view) {


        DownloadImageTask downloadImageTask = new DownloadImageTask(MainActivity.this);
        downloadImageTask.execute("https://www.google.co.in/search?q=android+robo&sxsrf=ALeKk006FW_9SITD6Ud7H6UQ0zTGmRSAlg:1594104696239&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjCmJ3hxrrqAhVZbysKHbCaDmMQ_AUoAXoECA4QAw&biw=1536&bih=754#imgrc=FwbSC-garpqCjM");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

        ProgressDialog progressDialog;
        Context context;

        public DownloadImageTask(Context context) {

            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Downloading image....Please Wait");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String stringUrl = params[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(stringUrl);
                InputStream inputStream = url.openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
                Toast.makeText(context, "Image downloaded", Toast.LENGTH_SHORT).show();
            }
        }
    }

}