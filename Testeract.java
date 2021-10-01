import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Testeract {
    public static void main(String[] args)
    {
        Tesseract tesseract = new Tesseract();
        try {
        	
        	// the path of your tess data folder
            // inside the extracted file
        	System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
            tesseract.setDatapath("tessdata");
  
            // path of your image file
            String text
            	= tesseract.doOCR(new File("file.pdf"));
             
            System.out.print(text);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }
        
        System.out.print("Termination");
        
    }
}