package com.example.sunghuncom.hw3_0516;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.telephony.SmsManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText1;
    EditText editText2;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link android to webView
        WebView browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        //load URL to webview
        browser.loadUrl("file:///android_asset/buttons.html");
        editText1=(EditText)findViewById(R.id.edit_text1);
        editText2=(EditText)findViewById(R.id.edit_text2);
        sendButton=(Button)findViewById(R.id.send_btn);

        //set click listener
        sendButton.setOnClickListener(this);

        //hide the keyboard
        editText1.setOnTouchListener(new OnTouchListener() {

            //hide keyboard
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
    }


    public class JavaScriptInterface{
        Context mContext;
        JavaScriptInterface(Context c){
            mContext = c;
        }

        //append the number to textView
        @JavascriptInterface
        public void setNum(String setnum){
            editText1.append(setnum);
        }
    }

    @Override
    public void onClick(View v){
        String phoneNum = editText1.getText().toString();
        String message = editText2.getText().toString();

        /* why it is run
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNum));
        intent.putExtra("sms_body", message + "");
        startActivity(intent);*/

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, message, null, null);
            //send message
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
