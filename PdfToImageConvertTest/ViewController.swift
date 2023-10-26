//
//  ViewController.swift
//  PdfToImageConvertTest
//
//  Created by sang on 26/10/23.
//
import UIKit
import CoreGraphics
import WebKit

class ViewController: UIViewController , WKNavigationDelegate{

    @IBOutlet weak var help_centers: UIView!
    @IBOutlet weak var set_dimensions: UIView!
    
    @IBOutlet weak var link_printers: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //radious
        link_printers.layer.cornerRadius = 10
        link_printers.layer.shadowRadius = 10
        link_printers.layer.shadowOpacity = 10
        link_printers.layer.masksToBounds = true
        //2
        help_centers.layer.cornerRadius = 10
        help_centers.layer.shadowRadius = 10
        help_centers.layer.shadowOpacity = 10
        help_centers.layer.masksToBounds = true
        //3
        set_dimensions.layer.cornerRadius = 10
        set_dimensions.layer.shadowRadius = 10
        set_dimensions.layer.shadowOpacity = 10
        set_dimensions.layer.masksToBounds = true
        
        
        let tapGesture_link_printers = UITapGestureRecognizer(target: self, action: #selector(handleTap_link_anyu_printer(_:)))
        link_printers.addGestureRecognizer(tapGesture_link_printers)
        //2
        let tapGesture_setdiamensionss = UITapGestureRecognizer(target: self, action: #selector(handleTap_setdimensions(_:)))
        set_dimensions.addGestureRecognizer(tapGesture_setdiamensionss)
        //3
        let tapGesture_help_cwnteree = UITapGestureRecognizer(target: self, action: #selector(handleTap_helpcenter(_:)))
        help_centers.addGestureRecognizer(tapGesture_help_cwnteree)
        
          }
           
           // WKNavigationDelegate method to handle the page load finish event
           func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
               // The PDF is loaded and displayed
           }
    @objc func handleTap_link_anyu_printer(_ sender: UITapGestureRecognizer) {
           // Handle the tap gesture here
           print("Tap Gesture Recognized")
        
        
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let secondController = storyboard.instantiateViewController(withIdentifier: "set_dimensions") as! SetDimensionsController
        present(secondController, animated: true, completion: nil)
            
        
       }
    @objc func handleTap_setdimensions(_ sender: UITapGestureRecognizer) {
           // Handle the tap gesture here
           print("Tap Gesture Recognized")
       
            
        
       }
    @objc func handleTap_helpcenter(_ sender: UITapGestureRecognizer) {
           // Handle the tap gesture here
           print("Tap Gesture Recognized")
       
            
        
       }
    
    func convertPDFToImage(pdfData: Data) -> UIImage? {
        // Create a PDF document from the data
        guard let dataProvider = CGDataProvider(data: pdfData as CFData),
              let pdfDocument = CGPDFDocument(dataProvider) else {
            return nil
        }
        
        // Get the first page of the PDF
        guard let page = pdfDocument.page(at: 1) else {
            return nil
        }
        
        // Set the size of the resulting image (you can adjust the size as needed)
        let pageSize = page.getBoxRect(.mediaBox)
        let scale = UIScreen.main.scale // Use the screen scale for Retina displays
        let width = pageSize.size.width * scale
        let height = pageSize.size.height * scale
        
        // Create a bitmap context
        guard let context = CGContext(data: nil, width: Int(width), height: Int(height), bitsPerComponent: 8, bytesPerRow: 0, space: CGColorSpaceCreateDeviceRGB(), bitmapInfo: CGImageAlphaInfo.premultipliedFirst.rawValue) else {
            return nil
        }
        
        // Set the context's scale to match the screen scale
        context.scaleBy(x: scale, y: scale)
        
        // Render the PDF page to the context
        context.drawPDFPage(page)
        
        // Get the image from the context
        guard let image = context.makeImage() else {
            return nil
        }
        
        return UIImage(cgImage: image)
    }

  
}

