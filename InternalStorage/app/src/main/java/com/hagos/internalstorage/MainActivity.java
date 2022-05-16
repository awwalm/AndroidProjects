package com.hagos.internalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    EditText editText;
    private String filename = "myfile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edittext);
    }


    // saveData function
    public void saveData(View v)
    {
        //
        String str = editText.getText().toString();

        FileOutputStream out = null;
        try
        {
            // gets a file ready for writing on the disk
            out = openFileOutput(filename, Context.MODE_APPEND);
            // performs the actual writing on the disk
            out.write(str.getBytes());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // We need to know that the FileOutputStream object is not null, before we proceed any further
            if (out != null)
            {
                try
                {
                    // closes the file and releases any system resources associated with it.
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    } /*end saveData*/

    // loadData function
    public void loadData(View v)
    {
        //
        TextView tv = (TextView) findViewById(R.id.textview);
        FileInputStream in = null;
        StringBuilder sb = new StringBuilder();

        try
        {
            in = openFileInput(filename);

            int read = 0;
            // reads one byte of data at a time, returns -1 at the end of the file
            while ((read = in.read()) != -1)
            {
                // cast the returned int to char to be compatible with StringBuilder
                sb.append((char) read);
            }
            tv.setText(sb.toString());
            //...
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}

