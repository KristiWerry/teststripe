package com.stripetest;

import android.content.Context;
import android.widget.LinearLayout;

public class CheckoutPageView extends LinearLayout {

    private Context context;

    public CheckoutPageView(Context context) {
        super(context);//ADD THIS
        this.context = context;
        init();
    }

    public void init() {
        //modified here.
        inflate(context, R.layout.activity_checkout, this);
    }

}
