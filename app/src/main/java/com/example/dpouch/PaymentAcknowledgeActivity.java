package com.example.dpouch;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PaymentAcknowledgeActivity extends Activity implements View.OnClickListener{

    TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_acknowledge);
        link=(TextView)findViewById(R.id.link);
        link.setText("Please click here to view Transaction History");
        link.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Home.class);
        startActivity(intent);

    }
}
