//
//  ViewController.swift
//  appium-cucumber
//
//  Created by Robert Cooper on 5/7/18.
//  Copyright © 2018 Robert Cooper. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var button: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func didReceiveButtonClick(_ sender: Any) {
        button.isHidden = true;
        
    }
    
}

