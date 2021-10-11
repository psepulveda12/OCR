import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class pdf_to_png {

	public static void processImg(BufferedImage ipimage, float scaleFactor, float offset) throws IOException, TesseractException {
		BufferedImage opimage = new BufferedImage(1050,1024,ipimage.getType());

		Graphics2D graphic = opimage.createGraphics();

		graphic.drawImage(ipimage, 0, 0,1050, 1024, null);
		graphic.dispose();

		RescaleOp rescale
		= new RescaleOp(scaleFactor, offset, null);

		BufferedImage fopimage
		= rescale.filter(opimage, null);
		ImageIO.write(fopimage,"png",new File("C:\\output"));

		Tesseract it = new Tesseract();

		it.setDatapath("C:\\Tess4J\\tessdata");

		String str = it.doOCR(fopimage);
		System.out.println(str);
	}

	public static void main(String[] args) throws IOException, TesseractException {

		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
		PDDocument document = PDDocument.load(new File("file.pdf"));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); ++page)
		{ 
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

			ImageIOUtil.writeImage(bim, "file" + "-" + (page+1) + ".png", 300);		

			File f = new File("C:\\file" + "-" + (page+1) + ".png");

			BufferedImage ipimage = ImageIO.read(f);

			double d = ipimage.getRGB(ipimage.getTileWidth() / 2,ipimage.getTileHeight() / 2);

			if (d >= -1.4211511E7 && d < -7254228) {
				processImg(ipimage, 3f, -10f);
				System.out.println("1");
			}
			else if (d >= -7254228 && d < -2171170) {
				processImg(ipimage, 1.455f, -47f);
				System.out.println("2");
			}
			else if (d >= -2171170 && d < -1907998) {
				processImg(ipimage, 1.35f, -10f);
				System.out.println("3");
			}
			else if (d >= -1907998 && d < -257) {
				processImg(ipimage, 1.19f, 0.5f);
				System.out.println("4");
			}
			else if (d >= -257 && d < -1) {
				processImg(ipimage, 1f, 0.5f);
				System.out.println("5");
			}
			else if (d >= -1 && d < 2) {
				processImg(ipimage, 1f, 0.35f);
				System.out.println("6");
			}
		}
		document.close();
	}

}

