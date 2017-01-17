package com.example.dpouch;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View;
import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener {

	ImageView touchPay;
    ImageView scanPay;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.home_fragement, container,false);
		touchPay= (ImageView) view.findViewById(R.id.pay_icon);
        scanPay= (ImageView) view.findViewById(R.id.scan_icon);
         touchPay.setOnClickListener(this);
        scanPay.setOnClickListener(this);

		return view;
	}


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.scan_icon)
        {
            Toast.makeText(getActivity(),"This functionality yet to be release ",Toast.LENGTH_SHORT).show();

        }
        else
        {
            NfcManager mgr= (NfcManager) getActivity().getSystemService(Context.NFC_SERVICE);
            NfcAdapter nfcAdapter=mgr.getDefaultAdapter();
            if(nfcAdapter!=null && nfcAdapter.isEnabled())
            {
                Toast.makeText(getActivity(),"Please Tap the phone against NFC Enabled POS device",Toast.LENGTH_LONG ).show();
            }
            else if (nfcAdapter!=null && !nfcAdapter.isEnabled())
            {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                Toast.makeText(getActivity(),"Please turn on Nfc sharing settings",Toast.LENGTH_LONG ).show();
//                Intent intent = new Intent(Settings.ACTION_NFCSHARING_SETTINGS);
//                startActivity(intent);
//            } if {
                Toast.makeText(getActivity(),"Please turn on Nfc  settings",Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
//            }

            }
            else
            {
                Toast.makeText(getActivity(),"Phone does not have NFC capability",Toast.LENGTH_LONG ).show();


            }


        }



    }
}
