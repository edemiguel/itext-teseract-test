package com.test.teseract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.qoppa.ocr.OCRBridge;
import com.qoppa.ocr.OCRException;
import com.qoppa.ocr.TessJNI;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.source.PDFSource;
import com.qoppa.pdf.source.RAFilePDFSource;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class Test2 {

	private static final int RESOLUCION = 300;
	private static String URI_JPDFPROCESS_FOLDER = null;
	private static File TMP_FOLDER = new File("C:/path_to_tmp_folder");
	private static File pdfFile = new File("C:/Users/user/Downloads/verDoc.pdf");

	
	static {
		try {
			URI_JPDFPROCESS_FOLDER = "C:/Repositorio/jpdfprocess/";
			OCRBridge.initialize(URI_JPDFPROCESS_FOLDER + "libtessjni", URI_JPDFPROCESS_FOLDER + "tesslang");
		} catch (OCRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("Comienzo del programa");
		PDFDocument pdfDoc = null;
		PDFSource pdfSource;
		try {
			pdfSource = new RAFilePDFSource(pdfFile);
			pdfDoc = new PDFDocument(pdfSource, null);
			PDFDocument split = new PDFDocument();
			for (int count = 0; count < pdfDoc.getPageCount(); count++) {
				System.out.println(new Timestamp(System.currentTimeMillis()) + " Página " + count);
				PDFPage page = pdfDoc.getPage(count);
				page = performOCR(page);
				split.appendPage(page);
			}

			System.out.println(new Timestamp(System.currentTimeMillis()) + " Comienzo a grabar archivo");
			File fileSplit =  new File(TMP_FOLDER, "hello.pdf");
			split.saveDocument(new FileOutputStream(fileSplit));
			System.out.println(new Timestamp(System.currentTimeMillis()) + " Fin grabar archivo");
			
		} catch (PDFException | OCRException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fin del programa");
	}

	public static PDFPage performOCR(PDFPage pdfPage) throws PDFException, OCRException {
		System.out.println(new Timestamp(System.currentTimeMillis()) + " Comienzo OCR ");
		TessJNI ocr = new TessJNI();
		String pageOCR = ocr.performOCR("spa", pdfPage, RESOLUCION);
		pdfPage.insert_hOCR(pageOCR, true);
		System.out.println(new Timestamp(System.currentTimeMillis()) + " Fin OCR ");
		
		return pdfPage;
	}

}
