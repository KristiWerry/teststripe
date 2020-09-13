package com.stripetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.exception.APIConnectionException;
import com.stripe.android.exception.APIException;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.exception.InvalidRequestException;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import java.util.Objects;
import com.stripe.android.view.CardMultilineWidget;

import org.jetbrains.annotations.NotNull;

public class StripeBridge extends ReactContextBaseJavaModule {
  private static ReactApplicationContext reactContext;

  private static final String DURATION_SHORT_KEY = "SHORT";
  private static final String DURATION_LONG_KEY = "LONG";

  private static final String TAG = "com.reactlibrary.stripe";

  private Stripe stripe;

  StripeBridge(ReactApplicationContext context) {
    super(context);
    reactContext = context;
    stripe = new Stripe(
            reactContext.getApplicationContext(),
            Objects.requireNonNull("pk_test_51HDJpeCyOWQPytLp0nuT6O7uTvG7lXeM4CFkkrG1naJtLTjRKBHHP1ibuut9qx0JgdjqMz7SQ8YNMlKGaetQ0Ouz00Llr9tpzm"));
  }

  @Override
  public String getName() {
    return "StripeBridge";
  }

  @ReactMethod
  public void pay(String clientSecret, Callback successCallback) {
    CardMultilineWidget cardInputWidget = reactContext.getCurrentActivity().findViewById(R.id.card_multiline_widget);
    PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
    if (params != null && clientSecret != null && reactContext.getCurrentActivity() != null) {
      ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
              .createWithPaymentMethodCreateParams(params, clientSecret);
      stripe.confirmPayment(reactContext.getCurrentActivity(), confirmParams);
      successCallback.invoke(null, "SUCCESS", null); //second null will be paymentIntent.stripeId
    }
  }


  @ReactMethod
  public void createPayment(String clientSecret, String cc, String month, String year, String cvc, Callback successCallback) throws APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
    CardMultilineWidget cardInputWidget = reactContext.getCurrentActivity().findViewById(R.id.card_multiline_widget);
    PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
    if (params != null && clientSecret != null && reactContext.getCurrentActivity() != null) {
      ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
              .createWithPaymentMethodCreateParams(params, clientSecret);
      stripe.confirmPayment(reactContext.getCurrentActivity(), confirmParams);
      stripe.retrievePaymentIntent(clientSecret, new ApiResultCallback<PaymentIntent>() {
        @Override
        public void onSuccess(@NotNull PaymentIntent paymentIntent) {
          successCallback.invoke(null, "SUCCESS", null); //second null will be paymentIntent.stripeId
        }

        @Override
        public void onError(@NotNull Exception e) {
          successCallback.invoke(null, "ERROR", null); //second null will be paymentIntent.stripeId
        }
      });
    }

//    Activity activity = reactContext.getCurrentActivity();
//    CardInputWidget cardInputWidget = activity.findViewById(R.id.card_multiline_widget);
//    activity.runOnUiThread(new Runnable() {
//
//      @Override
//      public void run() {
//        cardInputWidget.setCardNumber(cc);
//        cardInputWidget.setExpiryDate(Integer.parseInt(month), Integer.parseInt(year));
//        cardInputWidget.setCvcCode(cvc);
//        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
//
//        if (params != null) {
//          ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
//                  .createWithPaymentMethodCreateParams(params, clientSecret);
//          final Context context = reactContext.getApplicationContext();
//          stripe = new Stripe(
//                  context,
//                  PaymentConfiguration.getInstance(context).getPublishableKey()
//          );
//          stripe.confirmPayment(Objects.requireNonNull(reactContext.getCurrentActivity()), confirmParams);
//          successCallback.invoke(null, "SUCCESS", null); //second null will be paymentIntent.stripeId
//        }
//      }
//    });


    //create payment method with card params

    //create payment intent params with client secret 

    //set payment intent method = payment method

    //confirm payment, get returned status -> swtich case
  }
}