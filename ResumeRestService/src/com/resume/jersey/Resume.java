package com.resume.jersey;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class Resume {

	HashMap<String, String> resumeMap = new HashMap<>();
	
	public HashMap<String, String> readPdf() {
		String pageContent = "";
		try {

			URL urlResume = getClass().getResource("Resume.pdf");
			URL urlCoverLetter = getClass().getResource("Cover_letter.pdf");
			PdfReader pdfReader = new PdfReader(urlResume);
			String concat = urlResume + " " + urlCoverLetter;
			resumeMap.put("Resume", concat);
			
			//Get the number of pages in pdf.
			int pages = pdfReader.getNumberOfPages(); 
		 
			//Iterate the pdf through pages.
			for(int i=1; i<=pages; i++) { 
			  //Extract the page content using PdfTextExtractor.
			   pageContent = 
			  	PdfTextExtractor.getTextFromPage(pdfReader, i);
			 
		      }
			//Close the PdfReader.
		      pdfReader.close();

		      resumeMap = resumeMapper(pageContent);
		      
	    } catch (Exception e) {
	       
	    }
		return resumeMap;
	}
	
	private HashMap<String, String>  resumeMapper(String pageContent) {
		
		String dummyText = pageContent.trim();
		String[] strArr = dummyText.split("\n");
		for(int i = 0; i < strArr.length; i++) {
			String[] temp = strArr[i].trim().split("-");
			String key = temp[0].trim();
			String value = temp[1].trim();
            resumeMap.put(key, value);
		}
		setOtherQueryParams();

		return resumeMap;
	}
	
	private void setOtherQueryParams() {
		resumeMap.put("Ping", "OK");
		resumeMap.put("Source", "https://github.com/PoojaHarshUpadhyay/RESTAPIWithJersey");	
		resumeMap.put("Puzzle", "In progress");
	}
}
