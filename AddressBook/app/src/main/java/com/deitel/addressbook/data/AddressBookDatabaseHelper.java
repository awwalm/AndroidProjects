/*
* AddressBookDatabaseHelper (Section 9.7)—A subclass of SQLiteOpenHelper
* that creates the database and enables AddressBookContentProvider to access it.
*
* KEYWORD: DATABASE IS CREATED HERE
*/

package com.deitel.addressbook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.deitel.addressbook.data.DatabaseDescription.Contact;

public class AddressBookDatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "AddressBook.db";
    private static int DATABASE_VERSION = 1;

    // constructor
    public AddressBookDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creates the contacts table when the database is created
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // SQL for creating the contacts table
        final String CREATE_CONTACTS_TABLE =
                "CREATE TABLE "  +   Contact.TABLE_NAME  +   "(" +
                Contact._ID +   " integer primary key, " +
                Contact.COLUMN_NAME +   " TEXT, "   +
                Contact.COLUMN_PHONE +   " TEXT, "   +
                Contact.COLUMN_EMAIL +   " TEXT, "   +
                Contact.COLUMN_STREET +   " TEXT, "   +
                Contact.COLUMN_CITY +   " TEXT, "   +
                Contact.COLUMN_STATE +   " TEXT, "   +
                Contact.COLUMN_ZIP +   " TEXT);";

        // Create the contacts table
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // normally defines how to upgrade the database when the schema changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // not needed so far
    }
}
