/*
* AddressBookContentProvider (Section 9.8)—A ContentProvider subclass that defines query,
* insert, update and delete operations on the database.
*
* KEYWORD: DATABASE MANIPULATION SEMANTICS ARE SPECIFIED HERE
*/

package com.deitel.addressbook.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;

import android.content.UriMatcher;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.deitel.addressbook.R;
import com.deitel.addressbook.data.DatabaseDescription.Contact;

public class AddressBookContentProvider extends ContentProvider
{
    // used to access the database
    private AddressBookDatabaseHelper dbHelper;

    // UriMatcher helps ContentProvider determine operation to perform
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // constants used with UriMatcher to determine operation to perform
    private static final int ONE_CONTACT = 1; // manipulate one contact
    private static final int CONTACTS = 2; // manipulate contacts table

    // static block to configure this ContentProvider's UriMatcher
    static
    {
        // Uri for Contact with the specified id (#)
        uriMatcher.addURI
                (DatabaseDescription.AUTHORITY, Contact.TABLE_NAME + "/#", ONE_CONTACT);

        // Uri for Contacts table
        uriMatcher.addURI
                (DatabaseDescription.AUTHORITY, Contact.TABLE_NAME, CONTACTS);
    }

    // called when the AddressBookContentProvider is created
    @Override
    public boolean onCreate()
    {
        // create the AddressBookDatabaseHelper
        dbHelper = new AddressBookDatabaseHelper(getContext());
        return true; // ContentProvider successfully created
    }

    // required method: Not used in this app so we return null
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    // query the database
    @Override
    public Cursor query
    (Uri uri,String[] projection,String selection,String[] selectionArgs,String sortOrder)
    {
        // create SQLiteQueryBuilder for querying contacts table
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Contact.TABLE_NAME);

        switch (uriMatcher.match(uri))
        {
            // contact with specified id will be selected
            case ONE_CONTACT:
                queryBuilder.appendWhere
                        ( Contact._ID + "=" + uri.getLastPathSegment() );
                break;
            // all contacts will be selected
            case CONTACTS:
                break;
            default:
                throw new UnsupportedOperationException
                        (getContext().getString(R.string.invalid_query_uri)+uri);
        }

        // execute the query to select one or all contacts
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(), projection, selection,
                            selectionArgs, null,null, sortOrder);
        // configure to watch for content changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // insert a new contact in the database
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        Uri newContactUri = null;

        switch (uriMatcher.match(uri))
        {
            case CONTACTS:
                // insert the new contact--success yields new contact's row id
                long rowId = dbHelper.getWritableDatabase()
                        .insert(Contact.TABLE_NAME, null, values);

                // if the contact was inserted, create an appropriate Uri;
                // Otherwise, throw an exception
                if (rowId > 0)
                {
                    // SQLite row IDs start at 1
                    newContactUri = Contact.buildContactUri(rowId);
                    // notify observers that the database changed
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                else
                    throw new SQLException
                        (getContext().getString(R.string.insert_failed)+uri);
                break;

            default:
                throw new UnsupportedOperationException
                    (getContext().getString(R.string.invalid_insert_uri)+uri);
        }
        return newContactUri;
    }

    // update an existing contact in the database
    @Override
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs)
    {
        int numberOfRowsUpdated; // 1 if update successful; 0 otherwise

        switch (uriMatcher.match(uri))
        {
            case ONE_CONTACT:
                // get from the uri the id of contact to update
                String id = uri.getLastPathSegment();

                // update the contact
                numberOfRowsUpdated = dbHelper.getWritableDatabase().update
                (Contact.TABLE_NAME, values, Contact._ID + "=" + id, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException
                    (getContext().getString(R.string.invalid_update_uri) + uri);
        }

        // if changes were made, notify observers that the database changed
        if (numberOfRowsUpdated != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsUpdated;
    }

    // delete an existing contact from the database
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int numberOfRowsDeleted;

        switch (uriMatcher.match(uri))
        {
            case ONE_CONTACT:
                // get from the uri the id of contact to update
                String id = uri.getLastPathSegment();

                // delete the contact
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete
                    (Contact.TABLE_NAME, Contact._ID + "=" + id, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException
                    (getContext().getString(R.string.invalid_delete_uri) + uri);
        }

        // notify observers that the database changed
        if (numberOfRowsDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return numberOfRowsDeleted;
    }

}
