package com.andrewsosa.cosmicassistant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrewsosa on 12/10/14.
 */
public class CardList extends Fragment {

    private static CardListAdapter cardListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cardlist, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ArrayList<Card> cards = new ArrayList<>();

        ListView list = (ListView) getView().findViewById(R.id.cardList);

        cardListAdapter = new CardListAdapter(getActivity(), cards);
        list.setAdapter(cardListAdapter);

        DashboardActivity.updateSumValue(getCardSum());


        getView().findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardListAdapter.cards.clear();
                cardListAdapter.notifyDataSetChanged();
            }
        });

    }

    public static int getCardSum() {
        return cardListAdapter.sumElements();
    }

    public static void addCard(Card c) {
        cardListAdapter.add(c);
        DashboardActivity.updateSumValue(getCardSum());
    }

    public class CardListAdapter extends ArrayAdapter<Card> {

        private final Context c;
        public final ArrayList<Card> cards;


        public CardListAdapter(Context c, ArrayList<Card> cards) {
            super(c, R.layout.fragment_card, cards);

            this.c = c;
            this.cards = cards;

        }

        public Integer sumElements() {
            Integer sum = 0;
            Integer encounter = 0;
            Integer kicker = 1;

            for(Card c : cards) {
                if (c != null) {

                    if(c.getCardType().equals("Kicker")) {
                        kicker = c.getCardValue();
                    }
                    else if(c.getCardType().equals("Attack")) {
                        encounter = encounter + c.getCardValue();
                    }
                    else sum = sum + c.getCardValue();
                }

            }

            return sum + (encounter * kicker);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // Init stuff
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.fragment_card, parent, false);

            // Get view elements
            TextView cardType = (TextView) v.findViewById(R.id.cardType);
            TextView cardValue = (TextView) v.findViewById(R.id.cardValue);

            // Assign view elements
            Card card = cards.get(position);

            if (card.getCardType() != null) cardType.setText(card.getCardType());

            // Data manipulation before display
            String tempValue = String.valueOf(card.getCardValue());
            String type = card.getCardType();
            if (type.equals("Kicker")) tempValue = "x" + tempValue;
            if (type.equals("Reinforcements")) tempValue = "+" + tempValue;
            if (card.getCardValue() != null) cardValue.setText(tempValue);


            ImageButton btnRemove = (ImageButton) v.findViewById(R.id.btnRemove);

            btnRemove.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v) {

                    cards.remove(position);
                    notifyDataSetChanged();
                }
            });

            return v;

        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            DashboardActivity.updateSumValue(sumElements());
        }
    }

}
