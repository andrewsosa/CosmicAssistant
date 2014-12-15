package com.andrewsosa.cosmicassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class CardBuilder extends Fragment {

    // The pager to worry about
    ViewPager pager;

    // Buttons on the builder layout
    ArrayList<Button> btnNumList = new ArrayList<>();
    Button btnDel;
    Button btnInverse;

    Button btnDone;
    Button btnCancel;


    // Texts
    StringBuilder currentValue = new StringBuilder();
    static String currentType;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_cardbuilder, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Listeners
        try {
           getView().findViewById(R.id.btnCancel).setOnClickListener(new FinalButtonListener());
           getView().findViewById(R.id.btnDone).setOnClickListener(new FinalButtonListener());
        } catch(NullPointerException e) {
           Log.e("Cosmic Companion", e.getMessage());
        }

        try {
            getView().findViewById(R.id.btn0).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn1).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn2).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn3).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn4).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn5).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn6).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn7).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn8).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btn9).setOnClickListener(new NumPadButtonListener());

            getView().findViewById(R.id.btnDelete).setOnClickListener(new NumPadButtonListener());
            getView().findViewById(R.id.btnMinus).setOnClickListener(new NumPadButtonListener());


        } catch (Exception e) {
            Log.e("Cosmic Companion", e.getMessage());
        }
    }

    public static void setCurrentType(String type) {
        currentType = type;
    }

    public void setPager(ViewPager pager) {
        this.pager = pager;
    }

    public static CardBuilder newInstance(ViewPager parent) {
        CardBuilder cardBuilder = new CardBuilder();
        cardBuilder.setPager(parent);
        return cardBuilder;
    }

    private class NumPadButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn0: numberHandler(0); break;
                case R.id.btn1: numberHandler(1); break;
                case R.id.btn2: numberHandler(2); break;
                case R.id.btn3: numberHandler(3); break;
                case R.id.btn4: numberHandler(4); break;
                case R.id.btn5: numberHandler(5); break;
                case R.id.btn6: numberHandler(6); break;
                case R.id.btn7: numberHandler(7); break;
                case R.id.btn8: numberHandler(8); break;
                case R.id.btn9: numberHandler(9); break;

                case R.id.btnDelete: deleteHandler(); break;
                case R.id.btnMinus: inverseHandler(); break;

                default: break;

            }
        }

        public void numberHandler(int i) {

            currentValue.append(i);
            updateHandler();
        }

        public void deleteHandler() {
            if(currentValue.length() > 0) currentValue.deleteCharAt(currentValue.length() - 1);
            updateHandler();
        }

        public void inverseHandler() {

            if((currentValue.length() > 0) && (currentValue.charAt(0) == '-')) {
                currentValue.deleteCharAt(0);
            } else {
                currentValue.insert(0, '-');
            }

            updateHandler();

        }

        public void updateHandler() {
            DashboardActivity.updateCurrentValue(currentValue.toString());
        }
    }

    private class FinalButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnCancel: cancelHandler(); break;
                case R.id.btnDone: doneHandler(); break;
                default: break;
            }
        }

        public void doneHandler() {
            Card card = Card.newInstance(currentType, Integer.parseInt(currentValue.toString()));
            CardList.addCard(card);

            cancelHandler();
        }

        public void cancelHandler() {

            // Clear texts
            DashboardActivity.updateCurrentType("");
            DashboardActivity.updateCurrentValue("");

            // Clear Values
            currentValue = new StringBuilder();

            // Change views back
            pager.setCurrentItem(0, true);

        }

    }
}
