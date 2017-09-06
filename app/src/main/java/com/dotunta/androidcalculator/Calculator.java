package com.dotunta.androidcalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Calculator extends AppCompatActivity {

    //THINGS TO DO
    //------------
    //Add more functions (percent, exponents, roots, trig functions, log, reciprocal)
    //Fix the ability to keep entering decimals
    //Add some exceptions for results that shouldn't happen

    private TextView screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        screen = (TextView) findViewById(R.id.textView);
        screen.setText(display);
    }

    private void updateScreen(){

        screen.setText(display);
    }

    public void onClickNumber(View view){
        if(result != ""){
            clear();
            updateScreen();
        }

        Button button = (Button) view;
        display += button.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch(op){
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }

    public void onClickOperator(View view){
        if(display == ""){
            return;
        }

        Button button = (Button) view;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }

        if(currentOperator != ""){
            Log.d("CalcOperator", "" + display.charAt(display.length() - 1));
            if(isOperator(display.charAt(display.length() - 1))){
                display = display.replace(display.charAt(display.length() - 1), button.getText().charAt(0));
                updateScreen();
                return;
            } else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = button.getText().toString();
        }

        display += button.getText();
        currentOperator = button.getText().toString();
        updateScreen();
    }

    private void clear(){
        display = "";
        currentOperator = "";
        result = "";
    }

    public void onClickClear(View view){
        clear();
        updateScreen();
    }

    private double operate(String numOne, String numTwo, String op){

        switch(op){
            case "+":
                return Double.valueOf(numOne) + Double.valueOf(numTwo);
            case "-":
                return Double.valueOf(numOne) - Double.valueOf(numTwo);
            case "*":
                return Double.valueOf(numOne) * Double.valueOf(numTwo);
            case "/":
                try{
                    return Double.valueOf(numOne) / Double.valueOf(numTwo);
                } catch(ArithmeticException e){
                    Log.d("Error", e.getMessage());
                }
            default:
                return -1;
        }
    }

    private boolean getResult(){
        if(currentOperator == ""){
            return false;
        }

        String operation[] = display.split(Pattern.quote(currentOperator));

        if(operation.length < 2){
            return false;
        }

        result = String.valueOf(operate(operation[0], operation[1], currentOperator));

        return true;

    }

    public void onClickEquals(View view){
        if(display == ""){
            return;
        }

        if(!getResult()){
            return;
        }
        if(result == "Infinity"){
            screen.setText("Can't / by 0");
        } else{
            screen.setText(display + "\n" + String.valueOf(result));
        }
    }

}
