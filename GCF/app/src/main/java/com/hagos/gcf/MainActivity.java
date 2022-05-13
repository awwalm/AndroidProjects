package com.hagos.gcf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // data members
    private EditText fno;
    private EditText sno;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //...
        fno = (EditText) findViewById(R.id.firstno);
        sno = (EditText) findViewById(R.id.secondno);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        // clear out the contents of the text fields on very startup
        fno.setText("");
        sno.setText("");

    } /*end onStart*/


    @Override
    public void onClick(View v)
    {
        boolean a = TextUtils.isEmpty(fno.getText());
        boolean b = TextUtils.isEmpty(sno.getText());



        if ((!a & !b))
        {
            int c = Integer.parseInt(fno.getText().toString());
            int d = Integer.parseInt(sno.getText().toString());

            if ((c != 0) && (d != 0))
            {
                int firstnumber = Integer.parseInt(fno.getText().toString());
                int secondnumber = Integer.parseInt(sno.getText().toString());

                Intent intent = new Intent(this, Calculate.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fno", firstnumber);        // storing fno in the bundle
                bundle.putInt("sno", secondnumber);       // storing sno in the bundle
                intent.putExtra("gcfdata", bundle); // naming the bundle as gcfdata
                startActivity(intent);
            }

        }

    } /*end onClick*/
}
