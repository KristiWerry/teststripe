package com.stripetest;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardMultilineWidget;

import java.util.Objects;

public class CheckoutPageView extends LinearLayout {

    private ThemedReactContext context;
    private Stripe stripe;

    public CheckoutPageView(ThemedReactContext context) {
        super(context);//ADD THIS
        this.context = context;
        init();
    }

    public void init() {
        //modified here.
        inflate(context, R.layout.activity_checkout, this);
        stripe = new Stripe(
                context,
                Objects.requireNonNull("pk_test_51HDJpeCyOWQPytLp0nuT6O7uTvG7lXeM4CFkkrG1naJtLTjRKBHHP1ibuut9qx0JgdjqMz7SQ8YNMlKGaetQ0Ouz00Llr9tpzm"));

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);

        payButton.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                //PART 3: This is a sample to receive callback/events from Android to RN's JS and visa versa.
                //Save to remove if don't need to care events sent
                callNativeEvent();
                //END OF PART 3
            }
        });

    }

    //PART 3: Added Receive Event.
    public void callNativeEvent() {
        //This output a message to Javascript as an event.
        WritableMap event = Arguments.createMap();
        event.putString("customNativeEventMessage", "Emitted an event"); //Emmitting an event to Javascript

        //Create a listener where that emits/send the text to JS when action is taken.
        ReactContext reactContext = (ReactContext)getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                "nativeClick",    //name has to be same as getExportedCustomDirectEventTypeConstants in MyCustomReactViewManager
                event);
    }
}
