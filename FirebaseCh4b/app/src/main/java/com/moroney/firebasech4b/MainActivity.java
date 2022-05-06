package com.moroney.firebasech4b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    // global variables
    Button LoginButton;
    Button GetPictureButton;
    Button GetMetadataButton;

    TextView ShowPathTextView;

    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference imageRef;

    private final int CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize login button and its event listener
        LoginButton = (Button) findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doSignIn();
            }
        });

        // initialize picture getter button and its event listener
        GetPictureButton = (Button) findViewById(R.id.GetPictureButton);
        GetPictureButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                downloadPicture();
            }
        });

        // initialize GetMetaData button and its event listener
        GetMetadataButton = (Button) findViewById(R.id.GetMetaDataButton);
        GetMetadataButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getMetadata();
            }
        });

        // initialize TextView
        ShowPathTextView = (TextView) findViewById(R.id.ShowPath);

    } /*end onCreate*/


    // getMetadata() function
    private void getMetadata()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("image.jpg");

        imageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri)
            {
                String url = uri.toString();
                ShowPathTextView.setText(url);
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String foo = e.getLocalizedMessage();
            }
        });

    } /*end getMetadata function*/


    // download picture method
    private void downloadPicture()
    {
        // initialize storage
        storage = FirebaseStorage.getInstance();
        // and get a reference to it
        storageRef = storage.getReference();
        // make the reference become a child
        imageRef = storageRef.child("image.jpg");

        try
        {
            final File localFile = File.createTempFile("image", "jpg");
            // getFile is an asynchronous operation so it needs a callback monitor
            imageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
                {   // set GetMetadata button to enabled
                    GetMetadataButton.setEnabled(true);

                    // Local file has been created, create a bitmap image
                    Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    // load the bitmap image onto the ImageView
                    ImageView myImage = (ImageView) findViewById(R.id.ShowPicture);
                    myImage.setImageBitmap(myBitmap);
                }
            // in case of failure, also add a callback monitor
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    String foo = e.getLocalizedMessage();
                }
            });
        }
        catch (IOException ioe)
        {
            ioe.getLocalizedMessage();
            ioe.printStackTrace();
        }
        catch (Exception ex)
        {
            String foo = ex.getLocalizedMessage();
        }

    } /*end download picture method*/


    // sign in method
    private void doSignIn()
    {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously()
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        GetPictureButton.setEnabled(true);
                    }
                }).addOnFailureListener(this, new OnFailureListener()
                {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    String foo = e.getLocalizedMessage();
                }
        });
    } /*end doSignIn()*/


}
