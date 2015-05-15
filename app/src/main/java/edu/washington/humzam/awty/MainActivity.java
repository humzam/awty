package edu.washington.humzam.awty;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    private Button start;
    private EditText message;
    private EditText phoneNum;
    private EditText intervalEditText;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (EditText) findViewById(R.id.message_txt);
        final String messageText = message.getText().toString();
        phoneNum = (EditText) findViewById(R.id.phone_num);
        intervalEditText = (EditText) findViewById(R.id.interval_num);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        start = (Button) findViewById(R.id.start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String intervalText = intervalEditText.getText().toString();
                boolean goodInterval = intervalText.trim().length() > 0 && Integer.parseInt(intervalText) > 0;
                if (message.getText().toString().trim().length() > 0 &&         // make sure controls are filled with legitimate values
                    phoneNum.getText().toString().trim().length() > 0 &&
                    goodInterval) {
                    start.setText("Start");
                    int phoneNumber = Integer.parseInt(phoneNum.getText().toString());
                    int interval = Integer.parseInt(intervalEditText.getText().toString());
                    Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                    alarmIntent.putExtra("message", messageText);
                    alarmIntent.putExtra("phoneNum", phoneNumber);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, 0, interval * 1000* 60, pendingIntent);
                } else{
                    start.setText("Start");
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
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
}
