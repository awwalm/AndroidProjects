package com.moroney.firebasech2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignedInActivity extends AppCompatActivity
{
    private FirebaseAuth auth;
    TextView userEmail;
    TextView userName;
    private Button signOutButton;
    //private Toast toast = Toast.makeText(this, "SignOutFailed", Toast.LENGTH_LONG);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // check to see if the user is valid, if not, then go back to where you came from
        if (currentUser == null)
        {
            startActivity(MainActivity.createIntent(this));
            finish();
            return;
        }

        userEmail = (TextView) findViewById(R.id.user_email);
        userName = (TextView) findViewById(R.id.user_display_name);

        // reads from the database, but only some credentials
        userEmail.setText(currentUser.getEmail()); // uses predefined methods to get user email
        userName.setText(currentUser.getDisplayName()); // as above, this gets username

        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(signOutListener);

    }

    // intent to make this activity to be started by an Intent
    public static Intent createIntent(Context context, IdpResponse idpResponse)
    {
        //Intent in = IdpResponse.getIntent(idpResponse);
        return new Intent().setClass(context, SignedInActivity.class).putExtra
                                (ExtraConstants.IDP_RESPONSE, idpResponse);
    }

    // event listener for sign out
    private View.OnClickListener signOutListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {   // @TODO implement @fn signOut() method
            signOut();
        }
    };

    // signOut method to start a new MainActivity to bring you back to the sign-in screen.
    public void signOut()
    {
        AuthUI.getInstance().signOut(this).addOnCompleteListener
        (new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    startActivity(MainActivity.createIntent(SignedInActivity.this));
                    finish();
                }
                else
                {   // Sign out failed
                  // toast.show(); // doesn't work
                }
            }
        });
    }

}
