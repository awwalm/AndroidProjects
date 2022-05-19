package com.example.myapplicationdatepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    //----------------------------------------------------------DATE PICKER 1
    String date1;
    public static final String DATE_DIALOG_1 = "datePicker1";
    static TextView txt_date1;
    private static int mYear1;
    private static int mMonth1;
    private static int mDay1;
    //----------------------------------------------------------DATE PICKER 1


    //----------------------------------------------------------DATE PICKER 2
    String date2;
    public static final String DATE_DIALOG_2 = "datePicker2";
    static TextView txt_date2;
    private static int mYear2;
    private static int mMonth2;
    private static int mDay2;
    //----------------------------------------------------------DATE PICKER 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //----------------------------------------------------------DATE PICKER 1
        txt_date1 = findViewById(R.id.txt_date1);
        txt_date1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment newFragment1 = new DatePickerFragment1();
                newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
            }
        });
        //----------------------------------------------------------DATE PICKER 1


        //----------------------------------------------------------DATE PICKER 2
        txt_date2 = findViewById(R.id.txt_date2);
        txt_date2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment newFragment2 = new DatePickerFragment2();
                newFragment2.show(getSupportFragmentManager(), DATE_DIALOG_2);
            }
        });
        //----------------------------------------------------------DATE PICKER 2
    }

    //----------------------------------------------------------DATE PICKER 1
    public static class DatePickerFragment1 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // set default date

            //Date Time FROM BEFORE
//            String date =  txt_date1.getText().toString().trim();
//            String[] data = date.split("-", 3);
//            int year = Integer.parseInt(data[0]);
//            int month = Integer.parseInt(data[1])-1;    //Because January is 0
//            int day = Integer.parseInt(data[2]);

            //Date Time NOW
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(
                    getActivity(),R.style.DatePicker1, this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // get selected date
            mYear1 = year;
            mMonth1 = month;
            mDay1 = day;
            // show selected date to date button
            txt_date1.setText(new StringBuilder()
                    .append(mYear1).append("-")
                    .append(mMonth1 + 1).append("-")
                    .append(mDay1).append(" "));
        }
    }
    //----------------------------------------------------------DATE PICKER 1


    //----------------------------------------------------------DATE PICKER 2
    public static class DatePickerFragment2 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // set default date

            //Date Time FROM BEFORE
            String date =  txt_date2.getText().toString().trim();
            String[] data = date.split("-", 3);
            int year = Integer.parseInt(data[0]);
            int month = Integer.parseInt(data[1])-1;    //Because January is 0
            int day = Integer.parseInt(data[2]);

            //Date Time NOW
            //final Calendar c = Calendar.getInstance();
            //int year = c.get(Calendar.YEAR);
            //int month = c.get(Calendar.MONTH);
            //int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),R.style.DatePicker2, this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // get selected date
            mYear2 = year;
            mMonth2 = month;
            mDay2 = day;
            // show selected date to date button
            txt_date2.setText(new StringBuilder()
                    .append(mYear2).append("-")
                    .append(mMonth2 + 1).append("-")
                    .append(mDay2).append(" "));
        }
    }
    //----------------------------------------------------------DATE PICKER 2

}
