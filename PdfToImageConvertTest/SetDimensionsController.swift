//
//  SetDimensionsController.swift
//  PdfToImageConvertTest
//
//  Created by sang on 26/10/23.
//

import UIKit

class SetDimensionsController: UIViewController {
    
    @IBOutlet weak var dither_label_value: UILabel!
    @IBOutlet weak var constract_label_value: UILabel!
    @IBOutlet weak var density_label_value: UILabel!
    @IBOutlet weak var speed_label_value: UILabel!
    @IBOutlet weak var dither_indetecor: UISlider!
    @IBOutlet weak var constract_indetector: UISlider!
    @IBOutlet weak var density_indector: UISlider!
    @IBOutlet weak var speed_indetector: UISlider!
    @IBOutlet weak var dither_view: UIView!
    @IBOutlet weak var contract_view: UIView!
    @IBOutlet weak var density_view: UIView!
    @IBOutlet weak var speed_view: UIView!
    @IBOutlet weak var density_quan_view: UIView!
    @IBOutlet weak var dither_quan_view: UIView!
    @IBOutlet weak var constract_quan_view: UIView!
    @IBOutlet weak var speed_qun_view: UIView!
    @IBOutlet weak var dither_check: UIView!
    @IBOutlet weak var grayscale_check: UIView!
    @IBOutlet weak var diffusion_check: UIView!
    override func viewDidLoad() {
        super.viewDidLoad()

        //radious
        dither_view.layer.cornerRadius = 10
        dither_view.layer.shadowRadius = 10
        dither_view.layer.shadowOpacity = 10
        dither_view.layer.masksToBounds = true
        //2
        contract_view.layer.cornerRadius = 10
        contract_view.layer.shadowRadius = 10
        contract_view.layer.shadowOpacity = 10
        contract_view.layer.masksToBounds = true
        //3
        density_view.layer.cornerRadius = 10
        density_view.layer.shadowRadius = 10
        density_view.layer.shadowOpacity = 10
        density_view.layer.masksToBounds = true
        //4
        //3
        speed_view.layer.cornerRadius = 10
        speed_view.layer.shadowRadius = 10
        speed_view.layer.shadowOpacity = 10
        speed_view.layer.masksToBounds = true
        
        //4
        dither_check.layer.cornerRadius = 10
        dither_check.layer.shadowRadius = 10
        dither_check.layer.shadowOpacity = 10
        dither_check.layer.masksToBounds = true
        //5
        grayscale_check.layer.cornerRadius = 10
        grayscale_check.layer.shadowRadius = 10
        grayscale_check.layer.shadowOpacity = 10
        grayscale_check.layer.masksToBounds = true
        //6
        diffusion_check.layer.cornerRadius = 10
        diffusion_check.layer.shadowRadius = 10
        diffusion_check.layer.shadowOpacity = 10
        diffusion_check.layer.masksToBounds = true
        //quan view
        speed_qun_view.layer.cornerRadius = 10
        speed_qun_view.layer.shadowRadius = 10
        speed_qun_view.layer.shadowOpacity = 10
        speed_qun_view.layer.masksToBounds = true
        //2
        density_quan_view.layer.cornerRadius = 10
        density_quan_view.layer.shadowRadius = 10
        density_quan_view.layer.shadowOpacity = 10
        density_quan_view.layer.masksToBounds = true
        //3
        constract_quan_view.layer.cornerRadius = 10
        constract_quan_view.layer.shadowRadius = 10
        constract_quan_view.layer.shadowOpacity = 10
        constract_quan_view.layer.masksToBounds = true
        //4
        dither_quan_view.layer.cornerRadius = 10
        dither_quan_view.layer.shadowRadius = 10
        dither_quan_view.layer.shadowOpacity = 10
        dither_quan_view.layer.masksToBounds = true
        
        //progress dialouge
        let defaultValue: Int = 2
        speed_indetector.value = Float(defaultValue)
        speed_label_value.text = "2"
        
        let defaultValue2: Int = 18
        density_indector.value = Float(defaultValue2)
        density_label_value.text = "18"
        let defaultValue3: Int = 76
        constract_indetector.value = Float(defaultValue3)
        constract_label_value.text = "76"
        dither_indetecor.value = Float(defaultValue3)
        dither_label_value.text = "76"
        
    }
    

    @IBAction func dither_value_changed(_ sender: UISlider) {
        let roundedValue = round(sender.value)
        dither_indetecor.value = roundedValue
        dither_label_value.text = Int(sender.value).description
    }
    @IBAction func constract_value_changed(_ sender: UISlider) {
        let roundedValue = round(sender.value)
        constract_indetector.value = roundedValue
        constract_label_value.text = Int(sender.value).description
    }
    
    @IBAction func density_value_changed(_ sender: UISlider) {
        let roundedValue = round(sender.value)
        density_indector.value = roundedValue
        density_label_value.text = Int(sender.value).description
    }
    
    @IBAction func speed_value_changed(_ sender: UISlider) {
        let roundedValue = round(sender.value)
        speed_indetector.value = roundedValue
        speed_label_value.text = Int(sender.value).description
    }
    
}
