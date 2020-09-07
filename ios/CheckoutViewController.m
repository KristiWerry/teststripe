//
//  CheckoutViewController.m
//  StripeTest
//
//  Created by Macbook Air on 8/6/20.
//

#import <Foundation/Foundation.h>
#import <Stripe/Stripe.h>
#import "CheckoutViewController.h"
#import "AppDelegate.h"

@interface  CheckoutViewController ()
@end

@implementation CheckoutViewController

#pragma mark - STPAuthenticationContext
- (UIViewController *)authenticationPresentingViewController {
  AppDelegate *delegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  return delegate.rootViewController;
}
@end
