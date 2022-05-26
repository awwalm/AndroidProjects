package com.example.webscraper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class MainActivity extends AppCompatActivity
{
    ConstraintLayout theLayout;
    TextView webContentTextView;
    TextView titleTextView;
    ImageView webContentImage;
    TextInputEditText urlTIEditText;
    Button scrapeButton;

    String title;
    String paragraph = "";
    Bitmap bitmap;

    ProgressDialog progressDialog;
    Content content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theLayout = findViewById(R.id.theLayout);
        webContentTextView = findViewById(R.id.webContentTextView);
        titleTextView = findViewById(R.id.titleTextView);
        webContentImage = findViewById(R.id.webContentImage);
        urlTIEditText = findViewById(R.id.urlTIEditText);
        scrapeButton = findViewById(R.id.scrapeButton);

        urlTIEditText.setImeOptions(EditorInfo.IME_ACTION_GO);

        content = new Content();
        content.setUrl("https://yahoo.com/");
        content.execute();

        Snackbar.make(scrapeButton,
                "sites without relevant tags might show up as blank",
                Snackbar.LENGTH_INDEFINITE).show();

        scrapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String targetUrl = Objects.requireNonNull(urlTIEditText.getText()).toString().trim();
                Content newContent = new Content();
                newContent.setUrl(targetUrl);
                newContent.execute();
            }
        });

        urlTIEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                urlTIEditText.setImeOptions(EditorInfo.IME_ACTION_GO);
                try {
                    if (i == EditorInfo.IME_ACTION_GO)
                    {
                        String targetUrl = Objects.requireNonNull(urlTIEditText.getText()).toString().trim();
                        Content newContent = new Content();
                        newContent.setUrl(targetUrl);
                        newContent.execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null)
        {
            progressDialog.dismiss();
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    class Content extends AsyncTask<Void, Void, Void>
    {
        private String url;

        void setUrl(String url) { this.url = url; }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                Document document = Jsoup.connect(url).get();
                Element img = document.select("img").first();
                String imgSrc = img.absUrl("src");
                InputStream input = new java.net.URL(imgSrc).openStream();

                paragraph = document.select("p").first().text();
                bitmap = BitmapFactory.decodeStream(input);
                title = document.title();

            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);

            webContentImage.setImageBitmap(bitmap);
            titleTextView.setText(title);
            webContentTextView.setText(paragraph);

            System.out.println(title);
            progressDialog.dismiss();
        }

    }


}


