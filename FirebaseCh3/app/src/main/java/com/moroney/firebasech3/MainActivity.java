package com.moroney.firebasech3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.snackbar.Snackbar;

import com.moroney.firebasech3.StockPortfolio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity
{
    // declare references
    Button writeButton;
    Button queryButton;
    Button updateButton;
    Button deleteButton;

    private final String WRITE_OK = "WRITE SUCCESSFUL";
    private final String WRITE_NOT_OK = "FAILED TO WRITE";
    private final String READ_OK = "READ SUCCESSFUL";
    private final String READ_NOT_OK = "FAILED TO READ";
    private final String UPDATE_OK = "UPDATE SUCCESSFUL";
    private final String UPDATE_NOT_OK = "FAILED TO UPDATE";
    private final String DELETE_OK = "SUCCESSFULLY DELETED";
    private final String DELETE_NOT_OK = "FAILED TO DELETE";
    private final String PROCESSING = "PROCESSING...";
    private final String UNKNOWN = "Delete Attempted";

    TextView viewText;

    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance(); //create db reference
        myRef = database.getReference("portfolios"); //get reference

        writeButton = (Button) findViewById(R.id.writeButton); // reference to writeButton
        queryButton = (Button) findViewById(R.id.queryButton); // reference to queryButton
        updateButton = (Button) findViewById(R.id.updateButton); // reference to updateButton
        deleteButton = (Button) findViewById(R.id.deleteButton); // reference to deleteButton

        viewText = (TextView) findViewById(R.id.viewText); // reference to TextView

        // event listener to the writeButton
        writeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ArrayList<StockPortfolio> myFolios = new ArrayList<StockPortfolio>();

                /////////////////////////////////////

                StockPortfolio myFolio = new StockPortfolio
                        ("demoFolio", "lmoroney", "lm@hotmail.com");

                ArrayList<Stock> myFolioHoldings = new ArrayList<Stock>();

                Stock stock1 = new Stock("GOOG", "Google", 100);
                Stock stock2 = new Stock("AAPL", "Apple", 50);
                Stock stock3 = new Stock("MSFT", "Microsoft", 10);

                myFolioHoldings.add(stock1);
                myFolioHoldings.add(stock2);
                myFolioHoldings.add(stock3);

                // an ArrayList from StockPortfolio class
                myFolio.portfolioHoldings = myFolioHoldings; //put an ArrayList in an ArrayList

                /////////////////////////////////////

                StockPortfolio myOtherFolio = new StockPortfolio
                        ("realFolio", "lmoroney", "lmwork@hotmail.com");

                ArrayList<Stock> myOtherFolioHoldings = new ArrayList<Stock>();

                Stock stock4 = new Stock("IBM", "IBM", 50);
                Stock stock5 = new Stock("MMM","3M", 10);

                myOtherFolioHoldings.add(stock4);
                myOtherFolioHoldings.add(stock5);

                myOtherFolio.portfolioHoldings = myOtherFolioHoldings;

                /////////////////////////////////////

                myFolios.add(myFolio);  // add ArrayList myFolio in ArrayList myFolios
                myFolios.add(myOtherFolio); // add ArrayList myOtherFolio in ArrayList myFolios

                // add everything in one swoop to Firebase
                // the result is a nested layer of multiple trees
                myRef.setValue(myFolios);

                Snackbar.make(findViewById(R.id.coordinator), WRITE_OK,
                         Snackbar.LENGTH_SHORT).show();


            } /*end writeButton onClick() method*/

        }); /*end OnClickListener() inner class*/


        // event listener for updateButton
        updateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Query query = myRef.orderByChild("portfolioName").equalTo("demoFolio");

                query.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        try
                        {
                            DataSnapshot nodeShot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeShot.getKey();

                            // HashMap object containing the KEY object (String) and
                            // the VALUE object we need to update it to (Object)
                            HashMap<String, Object> update = new HashMap<>();
                            /*SQL equivalent is given as:
                             * UPDATE 0 SET portfolioOwner = New Owner WHERE portfolioName = demoFolio
                             * */
                            update.put("portfolioOwner", "New Owner");
                            myRef.child(key).updateChildren(update);

                            Snackbar.make(findViewById(R.id.coordinator),
                                    UPDATE_OK, Snackbar.LENGTH_SHORT).show();
                        }
                        catch (NoSuchElementException nse)
                        {
                            nse.printStackTrace();
                            Snackbar.make(findViewById(R.id.coordinator),
                                    UPDATE_NOT_OK, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        // update failed
                        Snackbar.make(findViewById(R.id.coordinator),
                                UPDATE_NOT_OK, Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        }); /*end updateButton event listener inner class*/


        // event listener for deleteButton
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Query query = myRef.orderByChild("portfolioName").equalTo("demoFolio");

                query.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        try
                        {
                            DataSnapshot nodeShot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeShot.getKey();
                            myRef.child(key).removeValue();

                            Snackbar.make(findViewById(R.id.coordinator),
                                    DELETE_OK, Snackbar.LENGTH_SHORT).show();
                        }
                        catch (NoSuchElementException nse)
                        {
                            Snackbar.make(findViewById(R.id.coordinator),
                                    UNKNOWN+"\n"+DELETE_NOT_OK, Snackbar.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Snackbar.make(findViewById(R.id.coordinator),
                                DELETE_NOT_OK, Snackbar.LENGTH_SHORT).show();
                    }
                });

            }/*end onClick()*/

        });/*end setOnClickListener() inner class*/


        // event listener for queryButton
        queryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Query query = myRef.orderByChild("portfolioName").equalTo("realFolio");

                query.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        GenericTypeIndicator< ArrayList<StockPortfolio> > t =
                                new GenericTypeIndicator<ArrayList<StockPortfolio>>() {  };

                        ArrayList<StockPortfolio> myFolios = dataSnapshot.getValue(t);
                        String pName = "";

                        // while loop with internal try-catch block for querying entire database
                        // for the stated Query value - x is used in case we don't know location
                        int x = 0;
                        while (x < myFolios.size())
                        {
                            try
                            {
                                if (myFolios != null)
                                {
                                    pName = myFolios.get(x).portfolioName;
                                    viewText.setText(pName);
                                    Snackbar.make(findViewById(R.id.coordinator),
                                            READ_OK, Snackbar.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            catch (NullPointerException n)
                            {
                                x++;
                                n.printStackTrace();
                                Snackbar.make(findViewById(R.id.coordinator),
                                        PROCESSING, Snackbar.LENGTH_SHORT).show();
                            }
                        } /*end while loop*/


                    }/*end onDataChange()*/

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        //
                    }
                }); /*value event listener*/

            } /*end onClick() for queryButton*/

        }); /*end queryButton setOnClickListener() inner class*/


        // value even listener which receives an onDataChange event
        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                GenericTypeIndicator< ArrayList<StockPortfolio> > t =
                        new GenericTypeIndicator< ArrayList<StockPortfolio> >() {   };

                ArrayList<StockPortfolio> myFolios = dataSnapshot.getValue(t);

                try
                {
                    String value = myFolios.get(0).portfolioName;
                    viewText.setText(value.toString());
                }
                catch (NullPointerException n)
                {
                    n.printStackTrace();
                    Log.i("FirebaseCh3", "Operation might be partial");
                    Snackbar.make(findViewById(R.id.coordinator),
                            UNKNOWN, Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error)
            {   // Failed to read value
                Log.w("FirebaseCh3", "Failed to read value", error.toException());
            }

        }); /*end database reference ValueEventListener*/

    } /*end onCreate*/

}
