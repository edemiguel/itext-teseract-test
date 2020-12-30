package com.test.teseract;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITessAPI.TessOcrEngineMode;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.ITesseract.RenderedFormat;
import net.sourceforge.tess4j.Tesseract;

public class Test3 {

	public static void main(String[] args) throws Exception {
		testCreateDocuments();
	}

	public static void testCreateDocuments() throws Exception {
		ITesseract instance = new Tesseract();
		instance.setDatapath(new File("C:/Users/kikoncio/git/tessdata").getPath());
		instance.setLanguage("spa");
		instance.setOcrEngineMode(TessOcrEngineMode.OEM_TESSERACT_LSTM_COMBINED);
		instance.setTessVariable("textonly_pdf", String.valueOf(ITessAPI.TRUE));
		
		File imageFile1 = new File("C:/Users/user/Downloads/verDoc.pdf");
		File imageFile2 = new File("C:/Users/user/Downloads/digimas1.JPG");
		String outputbase1 = "D:/Program Files/eclipse/workspace-remote/itext-teseract-test/target/aaaa";
		String outputbase2 = "D:/Program Files/eclipse/workspace-remote/itext-teseract-test/target/bbbb";
		List<RenderedFormat> formats = new ArrayList<RenderedFormat>(
				Arrays.asList(RenderedFormat.PDF));
		instance.createDocuments(new String[] { imageFile1.getPath(), imageFile2.getPath() },
				new String[] { outputbase1, outputbase2 }, formats);
		
	}

}
