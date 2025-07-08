package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.myapplication.database.HistoryDAO;
import com.maltaisn.calcdialog.CalcDialog;

public class MainActivity extends AppCompatActivity {

    @Nullable
    private BigDecimal value = null;
    private TextView tvResult;
    private String currentInput = "";
    private String expression = "";
    private final HistoryDAO historyDAO;
    private boolean isNewCalculation = true;

    public MainActivity() {
        historyDAO = new HistoryDAO(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        historyDAO.open();

        setupNumberButtons();
        setupOperatorButtons();
        setupControlButtons();
        setupSpecialButtons();

        // History FAB button
        findViewById(R.id.fabHistory).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });


        final CalcDialog calcDialog = new CalcDialog();

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcDialog.getSettings().setInitialValue(value);
                calcDialog.show(getSupportFragmentManager(), "calc_dialog");
            }
        });
    }

    private void setupSpecialButtons() {
        findViewById(R.id.buttonDot).setOnClickListener(v -> onDotClick());
        findViewById(R.id.buttonPercent).setOnClickListener(v -> onPercentClick());
        findViewById(R.id.buttonBackspace).setOnClickListener(v -> onBackspaceClick());
    }

    private void onDotClick() {
        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) {
                currentInput = "0";
            }
            currentInput += ".";
            updateDisplay();
        }
    }

    private void onPercentClick() {
        if (!currentInput.isEmpty()) {
            try {
                BigDecimal value = new BigDecimal(currentInput);
                if (!expression.isEmpty()) {
                    // If there's an expression, calculate percentage of the first number
                    BigDecimal baseNumber = new BigDecimal(expression.substring(0, expression.length() - 1));
                    value = baseNumber.multiply(value).divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
                } else {
                    value = value.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP);
                }
                currentInput = value.stripTrailingZeros().toPlainString();
                updateDisplay();
            } catch (NumberFormatException e) {
                tvResult.setText(R.string.calculation_error);
            }
        }
    }

    private void onBackspaceClick() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            updateDisplay();
        } else if (!expression.isEmpty()) {
            expression = expression.substring(0, expression.length() - 1);
            updateDisplay();
        }
    }

    private void updateDisplay() {
        String displayText = expression + currentInput;
        if (displayText.isEmpty()) {
            displayText = "0";
        }
        tvResult.setText(displayText);
    }

    private void setupNumberButtons() {
        int[] numberIds = {
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        };

        for (int id : numberIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> onNumberClick(button.getText().toString()));
        }
    }

    private void setupOperatorButtons() {
        int[] operatorIds = {
            R.id.buttonPlus, R.id.buttonMinus,
            R.id.buttonMultiply, R.id.buttonDivide
        };

        for (int id : operatorIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> onOperatorClick(button.getText().toString()));
        }
    }

    private void setupControlButtons() {
        findViewById(R.id.buttonClear).setOnClickListener(v -> {
            expression = "";
            currentInput = "";
            isNewCalculation = true;
            updateDisplay();
        });

        findViewById(R.id.buttonEquals).setOnClickListener(v -> calculateResult());
    }

    private void onNumberClick(String number) {
        if (isNewCalculation) {
            currentInput = "";
            expression = "";
            isNewCalculation = false;
        }
        currentInput += number;
        updateDisplay();
    }

    private void onOperatorClick(String operator) {
        if (currentInput.isEmpty() && expression.isEmpty()) {
            return;
        }

        if (!currentInput.isEmpty()) {
            if (!expression.isEmpty()) {
                calculateResult();
            }
            expression = currentInput + operator;
            currentInput = "";
        } else if (!expression.isEmpty()) {
            // Replace the last operator
            expression = expression.substring(0, expression.length() - 1) + operator;
        }
        updateDisplay();
    }

    private void calculateResult() {
        if (expression.isEmpty() && currentInput.isEmpty()) return;
        if (currentInput.isEmpty()) return;

        try {
            String expressionStr = expression + currentInput;
            char operator = expressionStr.charAt(expression.length() - 1);
            BigDecimal num1 = new BigDecimal(expression.substring(0, expression.length() - 1));
            BigDecimal num2 = new BigDecimal(currentInput);
            BigDecimal result;

            switch (operator) {
                case '+':
                    result = num1.add(num2);
                    break;
                case '-':
                    result = num1.subtract(num2);
                    break;
                case '×':
                    result = num1.multiply(num2);
                    break;
                case '÷':
                    if (num2.compareTo(BigDecimal.ZERO) == 0) {
                        tvResult.setText(R.string.division_by_zero_error);
                        return;
                    }
                    result = num1.divide(num2, 8, RoundingMode.HALF_UP);
                    break;
                default:
                    return;
            }

            String resultStr = result.stripTrailingZeros().toPlainString();

            // Save to history
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .format(new Date());
            historyDAO.insertHistory(expressionStr + "=" + resultStr, resultStr, timestamp);

            currentInput = resultStr;
            expression = "";
            isNewCalculation = true;
            updateDisplay();
        } catch (Exception e) {
            tvResult.setText(R.string.calculation_error);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        historyDAO.close();
    }

}