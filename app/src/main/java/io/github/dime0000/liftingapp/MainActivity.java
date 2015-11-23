package io.github.dime0000.liftingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // PRESENTATION: reciever recognizess messages from APP's UID (automatically generated in CloudPebble)
    private PebbleKit.PebbleDataReceiver mDataReceiver;
    private static final UUID APP_UUID = UUID.fromString("5b5cfec9-24a8-4f69-b8fb-8e4a1b4934ae");
    private static final int KEY_EXERCISE_VALUE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //PRESENTATION: This is the only main thing. everything above is auto generated
    @Override
    protected void onResume() {
        super.onResume();

        if(mDataReceiver == null) {
            Log.d("HERE-->", "mDataReceiver is null");
            mDataReceiver = new PebbleKit.PebbleDataReceiver(APP_UUID) {

                @Override
                public void receiveData(Context context, int transactionId, PebbleDictionary dict) {
                    // Message received, over!
                    // Always ACK

                    Log.d("HERE-->", "mDataReceiver DATA RECIEVED");

                    PebbleKit.sendAckToPebble(context, transactionId);

                    // Up received?
                    if(dict.getString(KEY_EXERCISE_VALUE) != null) {

                        TextView textView = (TextView)findViewById(R.id.text_view);
                        textView.setText(dict.getString(KEY_EXERCISE_VALUE));
                    }
                }

            };

            PebbleKit.registerReceivedDataHandler(getApplicationContext(), mDataReceiver);
        }

    }
}
