/*
 * MainActivity (Section 9.9)—This class manages the app’s Fragments and
 * implements their callback interface methods to respond when a contact is selected,
 * a new contact is added, or an existing contact is updated or deleted.
 */

// Hosts the app's fragments and handles communication between them

package com.deitel.addressbook;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.net.Uri;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
    implements ContactsFragment.ContactsFragmentListener,
               DetailFragment.DetailFragmentListener,
               AddEditFragment.AddEditFragmentListener
{
    // key for storing a contact's Uri in a Bundle passed to a fragment
    public static final String CONTACT_URI = "contact_uri";
    private ContactsFragment contactsFragment; // displays contact list

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // if layout contains fragmentContainer, the phone layout is in use;
        // create and display a ContactsFragment
        //if (savedInstanceState != null && findViewById(R.id.fragmentContainer) != null)
        //{
        // create ContactsFragment
        contactsFragment = new ContactsFragment();

        // add the fragment to the FrameLayout
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, contactsFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit(); // display ContactsFragment
        //}                                                 // content_main.xml
        if (!(savedInstanceState != null && findViewById(R.id.fragmentContainer) != null))
        {
            contactsFragment = ((ContactsFragment) getSupportFragmentManager().
                    findFragmentById(R.id.contactsFragment));
        }

    }

    // display DetailFragment for selected contact
    @Override
    public void onContactSelected(Uri contactUri)
    {
        if (findViewById(R.id.fragmentContainer) != null) // phone
        {
            displayContact(contactUri,R.id.fragmentContainer);
        }
        else
        {   // tablet
            // removes top of the back stack
            getSupportFragmentManager().popBackStack();
            displayContact(contactUri, R.id.rightPaneContainer);
        }
    }

    // display AddEditFragment to add a new contact
    @Override
    public void onAddContact()
    {
        if (findViewById(R.id.fragmentContainer) != null) // phone
        {
            displayAddEditFragment(R.id.fragmentContainer, null);
        }
        else
        {   // tablet
            displayAddEditFragment(R.id.rightPaneContainer, null);
        }
    }

    // display a contact
    private void displayContact(Uri contactUri, int viewID)
    {
        DetailFragment detailFragment = new DetailFragment();

        // specify contact's Uri as an argument to the DetailFragment
        Bundle arguments = new Bundle();
        arguments.putParcelable(CONTACT_URI, contactUri);
        detailFragment.setArguments(arguments);

        // use a FragmentTransaction to display the DetailFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(viewID, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit(); // cause DetailFragment to display
    }


    // display fragment for adding a new or editing an existing contact
    private void displayAddEditFragment (int viewID, Uri contactUri)
    {
        AddEditFragment addEditFragment = new AddEditFragment();

        // if editing existing contact, provide contactUri as an argument
        if (contactUri != null)
        {
            Bundle arguments = new Bundle();
            arguments.putParcelable(CONTACT_URI, contactUri);
            addEditFragment.setArguments(arguments);
        }

        // use a FragmentTransaction to display the AddEditFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(viewID, addEditFragment);
        transaction.addToBackStack(null);
        transaction.commit(); // causes AddEditFragment to display
    }


    // return to contact list when displayed contact deleted
    @Override
    public void onContactDeleted()
    {
        // removes top of back stack
        getSupportFragmentManager().popBackStack();
        //contactsFragment.updateContactList(); // refresh contacts
    }

    // display the AddEditFragment to edit an existing contact
    @Override
    public void onEditContact(Uri contactUri)
    {
        if (findViewById(R.id.fragmentContainer) != null) // phone
        {
            displayAddEditFragment(R.id.fragmentContainer, contactUri);
        }
        else
        {   // tablet
            displayAddEditFragment(R.id.rightPaneContainer, contactUri);
        }
    }


    // update GUI after new contact or updated contact saved
    @Override
    public void onAddEditCompleted(Uri contactUri)
    {
        // removes top of back
        getSupportFragmentManager().popBackStack();
       // contactsFragment.updateContactList(); // refresh contacts

        if (findViewById(R.id.fragmentContainer) == null)
        {   // tablet
            // removes top of back stack
            getSupportFragmentManager().popBackStack();

            // on tablet, display contact that was just added or edited
            displayContact(contactUri, R.id.rightPaneContainer);

        }
    }

}






































        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/