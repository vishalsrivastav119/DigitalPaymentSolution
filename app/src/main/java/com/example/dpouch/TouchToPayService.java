package com.example.dpouch;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

/**
 * Created by 586924 on 11/3/2016.
 */

public class TouchToPayService extends HostApduService{
    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {

        System.out.println("Ajitesh connected" +commandApdu);

        return new byte[0];
    }

    @Override
    public void onDeactivated(int reason) {
        System.out.println("Ajitesh on deactivated" +reason);

    }
}
