package com.andrewsosa.cosmicassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;


public class DashboardActivity extends FragmentActivity {


    // Button for adding cards
    FloatingActionButton newButton;

    // The viewpager
    static ViewPager pager;

    // Sum in header bar
    static TextView sumText;
    static TextView currentType;
    static TextView currentValue;
    static int sumValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Listener for new values button
        newButton = (FloatingActionButton) findViewById(R.id.add_button);
        newButton.setOnClickListener(new FloatingActionButtonListener());


        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        sumText = (TextView) findViewById(R.id.sumValue);
        currentType = (TextView) findViewById(R.id.currentType);
        currentValue = (TextView) findViewById(R.id.currentValue);

        // Assign button listeners
        findViewById(R.id.btnShips).setOnClickListener(new NewCardButtonListener());
        findViewById(R.id.btnAttack).setOnClickListener(new NewCardButtonListener());
        findViewById(R.id.btnKicker).setOnClickListener(new NewCardButtonListener());
        findViewById(R.id.btnReinforcements).setOnClickListener(new NewCardButtonListener());
        findViewById(R.id.btnMisc).setOnClickListener(new NewCardButtonListener());
    }

    public static void updateSumValue(int newValue) {
        sumValue = newValue;
        sumText.setText(String.valueOf(sumValue));
    }

    public static void updateCurrentType(String newText) {
        currentType.setText(newText);
    }

    public static void updateCurrentValue(String newValue) {
        currentValue.setText(newValue);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Adapter????
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Card List", "Card Builder"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) return new CardList();
            else return CardBuilder.newInstance(pager);
        }
    }

    // Lisener for new card buttons
    private class NewCardButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnShips: clickHandler("Ships"); break;
                case R.id.btnAttack: clickHandler("Attack"); break;
                case R.id.btnKicker: clickHandler("Kicker"); break;
                case R.id.btnReinforcements: clickHandler("Reinforcements"); break;
                case R.id.btnMisc: clickHandler("Misc."); break;
                default: break;
            }
        }

        public void clickHandler(String s) {
            CardBuilder.setCurrentType(s);
            updateCurrentType(s);
            pager.setCurrentItem(1, true);
        }

    }

    // Listener for the material button
    private class FloatingActionButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setTitle("Select value type");

            builder.setItems(new String[]{"Ships", "Encounter", "Reinforcements", "Kicker", "Misc."},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                        }
                    });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();

            dialog.show();

        }
    }


}
