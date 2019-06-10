package com.aldi.sismul;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fathzer.soft.javaluator.DoubleEvaluator;


public class calcFragment extends Fragment {

    private static final String TAG = "calcFragment";

    private EditText e1, e2;
    private int count = 0;
    private String expression = "";
    private String text = "";
    private Double result = 0.0;
    private Context context = getActivity();
    private int counterInsert = 1;
    DatabaseHelper databaseHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calc_fragment, container, false);

        e1 = (EditText) view.findViewById(R.id.editText1);
        e2 = (EditText) view.findViewById(R.id.editText2);
//        e2.setText("0");

        view.findViewById(R.id.num0).setOnClickListener(mListener);
        view.findViewById(R.id.num1).setOnClickListener(mListener);
        view.findViewById(R.id.num2).setOnClickListener(mListener);
        view.findViewById(R.id.num3).setOnClickListener(mListener);
        view.findViewById(R.id.num4).setOnClickListener(mListener);
        view.findViewById(R.id.num5).setOnClickListener(mListener);
        view.findViewById(R.id.num6).setOnClickListener(mListener);
        view.findViewById(R.id.num7).setOnClickListener(mListener);
        view.findViewById(R.id.num8).setOnClickListener(mListener);
        view.findViewById(R.id.num9).setOnClickListener(mListener);
        view.findViewById(R.id.dot).setOnClickListener(mListener);
        view.findViewById(R.id.clear).setOnClickListener(mListener);
        view.findViewById(R.id.backSpace).setOnClickListener(mListener);
        view.findViewById(R.id.plus).setOnClickListener(mListener);
        view.findViewById(R.id.minus).setOnClickListener(mListener);
        view.findViewById(R.id.divide).setOnClickListener(mListener);
        view.findViewById(R.id.multiply).setOnClickListener(mListener);
        view.findViewById(R.id.sqrt).setOnClickListener(mListener);
        view.findViewById(R.id.square).setOnClickListener(mListener);
        view.findViewById(R.id.posneg).setOnClickListener(mListener);
        view.findViewById(R.id.equal).setOnClickListener(mListener);
        view.findViewById(R.id.openBracket).setOnClickListener(mListener);
        view.findViewById(R.id.closeBracket).setOnClickListener(mListener);

        databaseHelper = new DatabaseHelper(this.getActivity());

        return view;
    }

    private final View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.num0:
                    e2.setText(e2.getText() + "0");
                    break;

                case R.id.num1:
                    e2.setText(e2.getText() + "1");
                    break;

                case R.id.num2:
                    e2.setText(e2.getText() + "2");
                    break;

                case R.id.num3:
                    e2.setText(e2.getText() + "3");
                    break;


                case R.id.num4:
                    e2.setText(e2.getText() + "4");
                    break;

                case R.id.num5:
                    e2.setText(e2.getText() + "5");
                    break;

                case R.id.num6:
                    e2.setText(e2.getText() + "6");
                    break;

                case R.id.num7:
                    e2.setText(e2.getText() + "7");
                    break;

                case R.id.num8:
                    e2.setText(e2.getText() + "8");
                    break;

                case R.id.num9:
                    e2.setText(e2.getText() + "9");
                    break;

                case R.id.dot:
                    if (count == 0 && e2.length() != 0) {
                        e2.setText(e2.getText() + ".");
                        count++;
                    }
                    break;

                case R.id.clear:
                    e1.setText("");
                    e2.setText("");
                    count = 0;
                    expression = "";
                    break;

                case R.id.backSpace:
                    text = e2.getText().toString();
                    if (text.length() > 0) {
                        if (text.endsWith(".")) {
                            count = 0;
                        }
                        String newText = text.substring(0, text.length() - 1);
                        //to delete the data contained in the brackets at once
                        if (text.endsWith(")")) {
                            char[] a = text.toCharArray();
                            int pos = a.length - 2;
                            int counter = 1;
                            //to find the opening bracket position
                            for (int i = a.length - 2; i >= 0; i--) {
                                if (a[i] == ')') {
                                    counter++;
                                } else if (a[i] == '(') {
                                    counter--;
                                }
                                //if decimal is deleted b/w brackets then count should be zero
                                else if (a[i] == '.') {
                                    count = 0;
                                }
                                //if opening bracket pair for the last bracket is found
                                if (counter == 0) {
                                    pos = i;
                                    break;
                                }
                            }
                            newText = text.substring(0, pos);
                        }
                        //if e2 edit text contains only - sign or sqrt at last then clear the edit text e2
                        if (newText.equals("-") || newText.endsWith("sqrt")) {
                            newText = "";
                        }
                        //if pow sign is left at the last
                        else if (newText.endsWith("^"))
                            newText = newText.substring(0, newText.length() - 1);

                        e2.setText(newText);
                    }
                    break;

                case R.id.plus:
                    operationClicked("+");
                    break;

                case R.id.minus:
                    operationClicked("-");
                    break;

                case R.id.divide:
                    operationClicked("/");
                    break;

                case R.id.multiply:
                    operationClicked("*");
                    break;

                case R.id.sqrt:
                    if (e2.length() != 0) {
                        text = e2.getText().toString();
                        e2.setText("sqrt(" + text + ")");
                    }
                    break;

                case R.id.square:
                    if (e2.length() != 0) {
                        text = e2.getText().toString();
                        e2.setText("(" + text + ")^2");
                    }
                    break;

                case R.id.posneg:
                    if (e2.length() != 0) {
                        String s = e2.getText().toString();
                        char arr[] = s.toCharArray();
                        if (arr[0] == '-')
                            e2.setText(s.substring(1, s.length()));
                        else
                            e2.setText("-" + s);
                    }
                    break;

                case R.id.equal:
                    if (e2.length() != 0) {
                        text = e2.getText().toString();
                        expression = e1.getText().toString() + text;
                    }
                    e1.setText("");
                    if (expression.length() == 0)
                        expression = "0.0";
                    DoubleEvaluator evaluator = new DoubleEvaluator();
                    try {
                        //evaluate the expression
                        result = new ExtendedDoubleEvaluator().evaluate(expression);
                        System.out.println(result);
                        //insert expression and result in sqlite database if expression is valid and not 0.0

                        if (!expression.equals("0.0") && expression.length() != 0) {
                            addData(expression, result.toString());
                            e2.setText(result + "");
                        } else {
                            toastMessage("Masukkan Angka!");
                        }
                    } catch (Exception e) {
                        e2.setText("Invalid Expression");
                        e1.setText("");
                        expression = "";
                        e.printStackTrace();
                    }
                    System.out.println(result);
                    break;

                case R.id.openBracket:
                    e1.setText(e1.getText() + "(");
                    break;

                case R.id.closeBracket:
                    e1.setText(e1.getText() + ")");
                    break;

            }
        }
    };

    private void operationClicked(String op) {
        if (e2.length() != 0) {
            String text = e2.getText().toString();
            e1.setText(e1.getText() + text + op);
            e2.setText("");
            count = 0;
        } else {
            String text = e1.getText().toString();
            if (text.length() > 0) {
                String newText = text.substring(0, text.length() - 1) + op;
                e1.setText(newText);
            }
        }
    }

    private void addData(String exp, String result) {
        databaseHelper.addData(exp, result);
    }

    private void toastMessage(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
