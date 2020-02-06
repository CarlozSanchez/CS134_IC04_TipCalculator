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
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.ssanchez.model.Bill;

public class MainActivity extends AppCompatActivity {

    private Bill currentBill;

    // Reference
    private SeekBar tipPercentageSeekBar;
    private TextView tipPercentageTextView;
    private TextView inputAmountTextView;

    private TextView tipAmountTextView;
    private TextView totalAmountTextView;

    // instance variables to format output (currenty and percent)
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Super
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign References
        tipPercentageSeekBar = findViewById(R.id.tipPercentageSeekBar);
        tipPercentageTextView = findViewById(R.id.tipPercentageTexttView);
        inputAmountTextView = findViewById(R.id.amountEditText);
        tipAmountTextView = findViewById(R.id.tipAmountTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);

        // Intialize Bill
        currentBill = new Bill();
        currentBill.setTipPercent(tipPercentageSeekBar.getProgress() / 100.0);

        // Assign Event Listeners
        inputAmountTextView.addTextChangedListener(getNewTextWatcher());
        tipPercentageSeekBar.setOnSeekBarChangeListener(getNewSeekBarChangeListener());
    }


    // Creates a new TextWatcher for EditText view
    private TextWatcher getNewTextWatcher()
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentBill.setAmount(parseAmount());
                doCalculations();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    // Creates a new SeekBarListener for SeekBar
    private SeekBar.OnSeekBarChangeListener getNewSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercentageTextView.setText(String.valueOf(progress) + "%");
                currentBill.setTipPercent(progress / 100.0);
                doCalculations();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //doCalculations(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    // converts the text amount in input amount View to a double value
    private double parseAmount()
    {
        String inputAmountStr;
        double inputAmount;

        inputAmountStr = inputAmountTextView.getText().toString();

        try {
            inputAmount = Double.parseDouble(inputAmountStr);
        } catch (NumberFormatException e) {
            inputAmount = 0;
        }

        Log.d("input amput" , String.valueOf(inputAmount));
        return inputAmount;
    }

    // Inokes currentBill to get tip Amount and Total then updates Text views
    private void doCalculations() {
        double  tipAmount, totalAmount;

        tipAmount = currentBill.getTipAmount();
        totalAmount = currentBill.getTotalAmount();

        tipAmountTextView.setText(currency.format(tipAmount));
        totalAmountTextView.setText(currency.format(totalAmount));
    }
}


// ----   SCRAPS  ---
// percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
//    private double caculateTip(double amount, int tipPercentage) {
//        double percent, result;
//
//        percent =  (double) tipPercentage / 100;
//        result = amount * percent;
//
//        Log.d("calculate Tip", String.valueOf(result));
//
//        return result;
//    }
//
//    public double calculateTotal(double tipAmount, double originalTotal) {
//        double total = originalTotal + tipAmount;
//
//        Log.d("calculate total", String.valueOf(tipAmount));
//        return total;
//    }