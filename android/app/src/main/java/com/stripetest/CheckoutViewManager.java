package com.stripetest;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CheckoutViewManager extends SimpleViewManager<CheckoutPageView> implements ReactPackage {

  public static final String REACT_CLASS = "CheckoutViewManager";
  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Arrays.asList(new CheckoutViewManager());
  }

  @Override
  public List<NativeModule> createNativeModules(
                              ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();

    modules.add(new StripeBridge(reactContext));

    return modules;
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public CheckoutPageView createViewInstance(ThemedReactContext context) {
    return new CheckoutPageView(context); //If your customview has more constructor parameters pass it from here.
  }

}