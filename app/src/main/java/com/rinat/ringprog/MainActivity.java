package com.rinat.ringprog;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ringdiagram.RingDiagram;


public class MainActivity extends ActionBarActivity {

    public RingDiagram iView;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iView = (RingDiagram) findViewById(R.id.ImageView1);
        editText1 = (EditText)findViewById(R.id.editText);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);
        editText6 = (EditText)findViewById(R.id.editText6);

    }

    public void sendMessage(View view) {
        iView.setIcon(R.drawable.ic_test);
        iView.setFirstLoopColor(editText1.getText().toString());
        iView.setSecondLoopColor(editText2.getText().toString());
        iView.setFirstLoopMaxValue(Double.valueOf(editText3.getText().toString()));
        iView.setSecondLoopMaxValue(Double.valueOf(editText4.getText().toString()));
        iView.setValue(Integer.valueOf(editText5.getText().toString()));
        iView.setAnimationDuration(Long.valueOf(editText6.getText().toString()));

        iView.surfaceCreated(iView.getHolder());
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
