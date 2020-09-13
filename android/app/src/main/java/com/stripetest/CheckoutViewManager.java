package com.stripetest;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;
import com.stripe.android.Stripe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CheckoutViewManager extends SimpleViewManager<CheckoutPageView> {
  public static final String REACT_CLASS = "CheckoutViewManager";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public CheckoutPageView createViewInstance(ThemedReactContext context) {
    CheckoutPageView checkoutPageView = new CheckoutPageView(context); //If your customview has more constructor parameters pass it from here.
    return checkoutPageView;
  }

  //PART 3: Added Receive Event.
  @javax.annotation.Nullable
  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    //For frequent updates like on change or movement, read about getExportedCustomBubblingEventTypeConstants
    return MapBuilder.<String, Object>builder()
            .put("nativeClick", //Same as name registered with receiveEvent
                    MapBuilder.of("registrationName", "onClick"))
            .build();
  }

}