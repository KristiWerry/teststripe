//
//  CheckoutViewController.swift
//  StripeTest
//
//  Created by Macbook Air on 8/4/20.
//

import UIKit
import Foundation


class CheckoutViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
  
  func authenticationPresentingViewController() -> UIViewController? {
      let delegate = UIApplication.shared.delegate as? AppDelegate
      return delegate?.rootViewController
  }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
