// Programmer: Sabad Sanchez
// Date: 2/2/2020
// Class: CS134 Tue,Thu 11am-1pm
// Project: IC04 - Tip Calculator

// This program calculates the tip amount and total of a given amount.

package edu.miracosta.cs134.ssanchez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.ssanchez.model.Bill;

public class MainActivity extends AppCompatActivity {


    private Bill currentBill;


    // Constants
    private static final String MONEY_FORMAT = "$%,.2f";

    // Reference
    private SeekBar tipPercentageSeekBar;
    private TextView tipPercentageTextView;
    private TextView inputAmountTextView;

    private TextView tipAmountTextView;
    private TextView totalAmountTextView;

    // instance variables to format output (currenty and percent)
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Assign References
        currentBill = new Bill();

        tipPercentageSeekBar = findViewById(R.id.tipPercentageSeekBar);
        tipPercentageTextView = findViewById(R.id.tipPercentageTexttView);
        inputAmountTextView = findViewById(R.id.amountEditText);
        tipAmountTextView = findViewById(R.id.tipAmountTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);

        currentBill.setTipPercent(tipPercentageSeekBar.getProgress() / 100.0);

        // implement interface for the EditText
//        inputAmountTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                doCalculations(tipPercentageSeekBar.getProgress());
//            }
//        });


        inputAmountTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doCalculations(tipPercentageSeekBar.getProgress());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do Nothing
            }
        });


        // Override Seek Bar Change Listener
        tipPercentageSeekBar.setOnSeekBarChangeListener(newSeekBarChangeListener());
    }

   // percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

    private TextWatcher setNewTextWatcher()
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private SeekBar.OnSeekBarChangeListener newSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercentageTextView.setText(String.valueOf(progress) + "%");
                doCalculations(progress);
                currentBill.setTipPercent(tipPercentageSeekBar.getProgress() / 100);
                //tipPercentageTextView.setText(percent.format(currentBill.getTipPercent());
                //calculateBill();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //doCalculations(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                doCalculations(seekBar.getProgress());
            }
        };
    }

    private void doCalculations(int tipPercentage) {
        String inputAmountStr, tipAmountStr, totalAmountStr;
        double inputAmount, tipAmount, total;

        inputAmountStr = inputAmountTextView.getText().toString();
        // amountText =  String.valueOf( seekBar.getProgress());

        try {
            inputAmount = Double.parseDouble(inputAmountStr);
        } catch (NumberFormatException e) {
            inputAmount = 0;
        }

        tipAmount = caculateTip(inputAmount, tipPercentage);
        total = calculateTotal(tipAmount, inputAmount);


        tipAmountStr = String.format(MONEY_FORMAT, tipAmount);
        totalAmountStr = String.format(MONEY_FORMAT, total);

       // tipAmountTextView.setText(tipAmountStr);
       // totalAmountTextView.setText(totalAmountStr);

        tipAmountTextView.setText(currency.format(tipAmount));
        totalAmountTextView.setText(currency.format(total));

    }

    private double caculateTip(double amount, int tipPercentage) {
        double percent, result;

        percent =  (double) tipPercentage / 100;
        result = amount * percent;

        Log.d("calculate Tip", String.valueOf(result));

        return result;
    }

    public double calculateTotal(double tipAmount, double originalTotal) {
        double total = originalTotal + tipAmount;

        Log.d("calculate total", String.valueOf(tipAmount));
        return total;
    }

}
