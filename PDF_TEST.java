import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PDF_TEST {

	public static void main(String[] args) throws Exception {
		PDF_TEST demo = new PDF_TEST();
		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
		demo.run();

	}

	private void run() throws Exception {
		PDDocument document = PDDocument.load(new File("file.pdf"));
		String text = extractTextFromScannedDocument(document);
		System.out.println(text);
	}

	private String extractTextFromScannedDocument(PDDocument document) throws IOException, TesseractException {

		// Extract images from file
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		StringBuilder out = new StringBuilder();

		Tesseract _tesseract = new Tesseract();
		_tesseract.setDatapath("tessdata");
		_tesseract.setLanguage("eng");
		

		for (int page = 0; page < document.getNumberOfPages(); page++) {
			BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

			// Create a temp image file
			File tempFile = File.createTempFile("tempfile_" + page, ".png");
			ImageIO.write(bufferedImage, "png", tempFile);

			String result = _tesseract.doOCR(tempFile);
			out.append(result);

			// Delete temp file
			tempFile.delete();

		}

		return out.toString();

	}
}