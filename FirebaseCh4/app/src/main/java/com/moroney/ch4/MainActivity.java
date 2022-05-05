package com.moroney.ch4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity
{
    // global variables
    FirebaseAuth mAuth;
    Button signInButton;
    Button takePictureButton;
    FirebaseStorage storage;
    StorageReference storageRef;

    private final int CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Firebase auth instance and sign-in button
        mAuth = FirebaseAuth.getInstance();
        signInButton = (Button) findViewById(R.id.loginButton);

        // sigin button event listener
        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doSignIn();
            }
        });

        // initialize picture capture button and its event listener
        takePictureButton = (Button) findViewById(R.id.takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                takePictureAndUpload();
            }
        });

    } /*end onCreate*/


    // method for taking photo
    private void takePictureAndUpload()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // startActivityForResult(cameraIntent, Integer.parseInt(CAMERA_SERVICE));
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }


    // sign in function
    private void doSignIn()
    {
        mAuth.signInAnonymously()
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>()
        {
            @Override
            public void onSuccess(AuthResult authResult)
            {
                takePictureButton.setEnabled(true);
            }
        }).addOnFailureListener(this, new OnFailureListener()
        //}).addOnFailureListener(getBaseContext().getMainExecutor(), new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String foo = e.getLocalizedMessage();
            }
        });
    } /*end doSignIn()*/


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMERA_REQUEST) && (resultCode == Activity.RESULT_OK))
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageData = stream.toByteArray();

            storage = FirebaseStorage.getInstance();
            // this becomes the child node for the location of the image
            storageRef = storage.getReference();
            // send the image to the database by unprofessionally hard-coding the title
            StorageReference imageRef = storageRef.child("image.jpg");

            // call a putBytes method that handles the file upload asynchronously
            // the upload is done byte by byte
            UploadTask uploadTask = imageRef.putBytes(imageData);
            // add onFailure listener to catch any errors if upload fails
            uploadTask.addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    String ex = e.getLocalizedMessage();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                    Toast.makeText(getApplicationContext(),
                                    downloadUrl.toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                    /*
                    StorageMetadata urlMetadata = taskSnapshot.getMetadata();
                    StorageReference urlReference = urlMetadata.getReference();
                    Task urlTask = urlReference.getDownloadUrl();
                    */
                }
            });

        }

    }

}
