// MainActivity.java
// Calculates a bill total based on a tip percentage
package deitel.tipcalculator;

import android.os.Bundle; // for saving state information
import androidx.appcompat.app.AppCompatActivity; // base class
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill input
import android.widget.SeekBar; // for changing the bill percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // Seek-bar listener
import android.widget.TextView; // for displaying text

import java.text.NumberFormat; // for currency formatting

import deitel.tipcalculator.R;


public class MainActivity extends AppCompatActivity
{
    // currency and percent formatter objects
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // bill amount entered by the user
    private double percent = 0.15; // initial tip percentage
    private TextView amountTexView; // shows formatted bill amount
    private TextView percentTextView; // shows tip percentage
    private TextView tipTextView; // shows calculated tip amount
    private TextView totalTextView; // shows calculated total bill amount

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);     // call superclass's version
        setContentView(R.layout.activity_main); // inflate and associate the GUI with the XML identifier

        // get references to programmatically manipulated TextViews
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        tipTextView.setText(currencyFormat.format(0)); // set text to 0
        totalTextView.setText(currencyFormat.format(0)); // set text to 0

        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    } /*end onCreate*/

    // calculate and display tip and total amounts
    private void calculate()
    {
        // format percent and display in percentTextView
        percentTextView.setText(percentFormat.format(percent));

        // calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        // display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));

    } /*end calculate*/

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            percent = progress / 100.0; // set percent based on progress
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {  }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {  }

    }; /*end OnSeekBarChangeListener inner class*/

    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after)
        {
            try { // get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString())/100.0;
                amountTexView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                amountTexView.setText("");
                billAmount = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int before, int count) {   }

        @Override
        public void afterTextChanged(Editable s) {   }

    }; /*end TextWatcher inner class*/

} /*end MainActivity class*/
