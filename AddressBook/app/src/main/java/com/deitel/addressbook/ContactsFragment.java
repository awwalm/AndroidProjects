/*
* ContactsFragment (Section 9.10)—This class manages the contact-list RecyclerView and
*  the FloatingActionButton for adding contacts.
* On a phone, this is the first Fragment presented by MainActivity.
* On a tablet, MainActivity always displays this Fragment.
* ContactsFragment’s nested interface defines callback methods implemented by MainActivity
*  so that it can respond when a contact is selected or added.
*/

package com.deitel.addressbook;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deitel.addressbook.data.DatabaseDescription.Contact;

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    // callback method implemented by MainActivity
    public interface ContactsFragmentListener
    {
        // called when contact selected
        void onContactSelected(Uri contactUri);
        // called when add Button is pressed
        void onAddContact();
    }

    private static final int CONTACT_LOADER = 0; // identifies Loader
    // used to inform the MainActivity when a contact is selected
    private ContactsFragmentListener listener;
    private ContactsAdapter contactsAdapter; //  adapter for recyclerView

    // configures this fragment's GUI
    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true); // fragment has menu items to display

        // inflate GUI and get reference to the RecyclerView
        View view = inflater.inflate
                        (R.layout.fragment_contacts, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // recyclerView should display items in a vertical list
        recyclerView.setLayoutManager
                (new LinearLayoutManager(getActivity().getBaseContext()));

        // create recyclerView's adapter and item click listener
        contactsAdapter = new ContactsAdapter( new ContactsAdapter.ContactClickListener()
        {
            @Override
            public void onClick(Uri contactUri)
            {
                listener.onContactSelected(contactUri);
            }
        });

        // set the adapter
        recyclerView.setAdapter(contactsAdapter);
        // attach a custom ItemDecorator to draw dividers between list items
        recyclerView.addItemDecoration(new ItemDivider(getContext()));
        // improves performance if RecyclerView's layout size never changes
        recyclerView.setHasFixedSize(true);

        // get the FloatingActionButton and configure its listener
        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(
            new View.OnClickListener()
            {   // displays the AddEditFragment when FAB is touched
                @Override
                public void onClick(View view)
                {
                    listener.onAddContact();
                }
            }
        );
        return view;
    } /*end onCreateView*/

    // set ContactsFragmentListener when fragment attached
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        listener = (ContactsFragmentListener) context;
    }

    // remove ContactsFragmentListener when Fragment detached
    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    // initialize a Loader when this fragment's activity is created
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader
                (CONTACT_LOADER, null,
                (androidx.loader.app.LoaderManager.LoaderCallbacks<Cursor>) this);
    }

    // called from MainActivity when other Fragment's update database
    public void updateContactList()
    {
        contactsAdapter.notifyDataSetChanged();
    }

    // called by LoadManager to create a Loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {   // create an appropriate CursorLoader based on the id argument;
        // only one Loader in this fragment, so the switch-case is unnecessary
        switch (id)
        {
            case CONTACT_LOADER:
                return new androidx.loader.content.CursorLoader
                        (getActivity(),
                        Contact.CONTENT_URI, // Uri of contacts
                        null,   // null projection returns all columns
                        null,    // null selection returns all rows
                        null, // no selection arguments
                        Contact.COLUMN_NAME + " COLLATE NOCASE ASC"); //sort order
            default:return null;
        }
    }

    // called by LoadManager when loading completes, so the RecyclerView can be refreshed
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        contactsAdapter.swapCursor(data);
    }

    // called by LoaderManager when the Loader is being reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        contactsAdapter.swapCursor(null);
    }
}
