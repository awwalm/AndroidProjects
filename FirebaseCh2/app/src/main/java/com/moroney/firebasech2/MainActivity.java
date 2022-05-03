package com.moroney.firebasech2;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static final int RC_SIGN_IN = 100;
    private FirebaseAuth auth;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Before using Firebase AuthUI, you need to initialize Firebase Auth and check to see
        * if there's a user already signed-in.
        */
        if (auth.getInstance().getCurrentUser() != null)
        {   // start signed in activity
            startActivity(SignedInActivity.createIntent(this, null));
            finish();
        }
        else {
            signInButton = (Button) findViewById(R.id.signInButton);
            // onClick listener for the sign in button
            signInButton.setOnClickListener
            (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
                        selectedProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
                        selectedProviders.add(new AuthUI.IdpConfig.EmailBuilder().build());
                        //selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder().build());

                        startActivityForResult
                        (
                            //AuthUI.getInstance().createSignInIntentBuilder().build(), RC_SIGN_IN
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    //.setTheme(R.style.CustomTheme)
                                    .setLogo(R.drawable.firelogo)
                                    .setAvailableProviders(selectedProviders)
                                    .setIsSmartLockEnabled(true)
                                    .build(),       RC_SIGN_IN
                        );

                    }
                }
            ); /*end inner class listener*/
        }
    } /*end onCreate*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            handleSignInResponse(resultCode, data);
            return;
        }
    }

    /*if the signin was successful, call the signed in activity, otherwise, describe failure*/
    @MainThread
    private void handleSignInResponse(int resultCode, Intent data)
    {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        Toast toast;
        // Successfully signed in
        if (resultCode == Activity.RESULT_OK)
        {
            startActivity(SignedInActivity.createIntent(this, response));
            finish();
            return;
        }
        else
        {   // sign in failed
            if (response == null)
            {   // User pressed back button
                toast = Toast.makeText
                        (this, "Sign in was canceled", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK)
            {
                toast = Toast.makeText
                        (this, "You have no internet connection", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR)
            {
                toast = Toast.makeText(this, "Unknown Error ⁉⁉", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        }

        toast = Toast.makeText(this, "Unknown Error !", Toast.LENGTH_LONG);
        toast.show();

    } /*end sign-in handler method*/

    // used by sign-in activity: if a user sign-in is invalid, close and restart MainActivity
    public static Intent createIntent(Context context)
    {
        Intent in = new Intent();
        in.setClass(context, MainActivity.class);
        return in;
    }

} /*end main class*/
