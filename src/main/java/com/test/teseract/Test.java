package com.test.teseract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.collections4.ListUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.pdfocr.OcrPdfCreator;
import com.itextpdf.pdfocr.tesseract4.Tesseract4LibOcrEngine;
import com.itextpdf.pdfocr.tesseract4.Tesseract4OcrEngineProperties;

public class Test {
	static final Tesseract4OcrEngineProperties tesseract4OcrEngineProperties = new Tesseract4OcrEngineProperties();
	private static List<File> LIST_IMAGES_OCR = Arrays.asList(new File("C:/Users/user/Downloads/digimas1.JPG"));
	private static File pdfFile = new File("C:/Users/user/Downloads/verDoc.pdf");
	private static File tmpFolder = new File("D:/Program Files/eclipse/workspace-remote/itext-teseract-test/target");

	public static void main(String[] args) {
		final Tesseract4LibOcrEngine tesseractReader = new Tesseract4LibOcrEngine(tesseract4OcrEngineProperties);
		tesseract4OcrEngineProperties.setPathToTessData(new File("C:/Users/user/git/tessdata_best"));
		tesseract4OcrEngineProperties.setLanguages(Arrays.asList("eng", "spa"));
		OcrPdfCreator ocrPdfCreator = new OcrPdfCreator(tesseractReader);

		PdfWriter writer;
		try {
			List<File> images = toImage2(pdfFile);
			List<List<File>> imageList = ListUtils.partition(images, 10);
			int i = 0;
			for (List<File> image : imageList) {
				writer = new PdfWriter(new File(tmpFolder, "hello" + i + ".pdf"));
				ocrPdfCreator.createPdf(image, writer).close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static List<File> toImage2(File pdfFile) throws InvalidPasswordException, IOException {
		List<File> result = new ArrayList<>();

		PDDocument document = PDDocument.load(pdfFile);
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage pageImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
			
			File outputfile = File.createTempFile("image", ".jpg", tmpFolder);
			outputfile.deleteOnExit();
			ImageIO.write(pageImage, "jpg", outputfile);
			result.add(outputfile);
		}

		return result;
	}

//	private static List<File> toImage(File pdfFile) throws PDFException, IOException {
//		PDFSource pdfSource;
//		List<File> result = new ArrayList<>();
//
//		pdfSource = new RAFilePDFSource(pdfFile);
//		PDFDocument pdfDoc = new PDFDocument(pdfSource, null);
//
//		for (int count = 0; count < pdfDoc.getPageCount(); count++) {
//			PDFPage page = pdfDoc.getPage(count);
//			BufferedImage pageImage = page.getImage(200);
//			File outputfile = File.createTempFile("image", ".jpg", tmpFolder);
//			outputfile.deleteOnExit();
//			ImageIO.write(pageImage, "jpg", outputfile);
//			result.add(outputfile);
//		}
//		return result;
//	}

}
