package com.resume.jersey;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * Resume class that will parse data from Resume PDF, solve puzzel and return result.
 * @author pooja
 *
 */
public class Resume {

	HashMap<String, String> resumeMap = new HashMap<>();
	
	public HashMap<String, String> readPdf() {
		String pageContent = "";
		try {

			URL urlResume = getClass().getResource("Resume.pdf");
			URL urlCoverLetter = getClass().getResource("Cover_letter.pdf");
			PdfReader pdfReader = new PdfReader(urlResume);
			
			int pages = pdfReader.getNumberOfPages(); 
		 
			//Iterate the pdf through pages.
			for(int i=1; i<=pages; i++) { 
			   pageContent = 
			  	PdfTextExtractor.getTextFromPage(pdfReader, i);
		      }
		      pdfReader.close();

		      resumeMap = resumeMapper(pageContent);
		      
	    } catch (Exception e) {
	    	System.err.println("Exception occured: "+e.getMessage());
	    }
		return resumeMap;
	}
	
	/**
	 * Mapper function that will map the key words from pdf and store in HashMap.
	 * @param pageContent
	 * @return map
	 */
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
	
	/**
	 * Set other key value pair in HashMap.
	 */
	private void setOtherQueryParams() {
		resumeMap.put("Ping", "OK");
		resumeMap.put("Source", "https://github.com/PoojaHarshUpadhyay/RESTAPIWithJersey");	
		String conat = "https://github.com/PoojaHarshUpadhyay/RESTAPIWithJersey/blob/master/ResumeRestService/src/com/resume/jersey/Resume.pdf" 
		+ "\n" 
		+ "https://github.com/PoojaHarshUpadhyay/RESTAPIWithJersey/blob/master/ResumeRestService/src/com/resume/jersey/Cover_letter.pdf";
		resumeMap.put("Resume", conat );
	}
	
	/**
	 * Puzzel 
	 * @param input is the description of query parameter.
	 * @return result
	 */
	public String solvePuzzle(String input) {
		 String[] strArr = input.trim().split("\\r?\\n");
	        int size = strArr.length;

	        char[][] inputMatrix = new char[size][size]; //Input matrix to work on
	        char[][] checkFlipMatrix = new char[size][size]; //Check whether the matrix[i][j] index is fliped.

	        String topMatrix = strArr[0];
	        inputMatrix[0][0] = 0;
	        int startIndex = 0;
	        //Fill 0 row of matrix 
	        for (int i = 1; i < topMatrix.length() + 1; i++) {
	            inputMatrix[0][i] = topMatrix.charAt(startIndex);
	            startIndex++;
	        }
	        
	        for (int i = 1; i < inputMatrix.length; i++) {
	            for (int j = 0; j < inputMatrix.length; j++) {
	                if (inputMatrix[i][j] == 0) {
	                    inputMatrix[i][j] = strArr[i].charAt(j);
	                }
	            }
	        }

	      //Fill the '='
	        for (int i = 0; i < inputMatrix.length; i++) {
	            for (int j = 0; j < inputMatrix.length; j++) {
	                if (i == j && inputMatrix[i][j] != 0) {
	                    inputMatrix[i][j] = '=';
	                }
	            }
	        }

	        //Flip the already given sign in matrix
	        for (int i = 1; i < inputMatrix.length; i++) {
	            for (int j = 1; j < inputMatrix.length; j++) {
	                if (inputMatrix[i][j] != '=' && inputMatrix[i][j] != '-'
	                        && checkFlipMatrix[i][j] != '1' && checkFlipMatrix[j][i] != '1') {
	                    char sign = inputMatrix[i][j];
	                    char flipChar = flipSign(sign);
	                    inputMatrix[j][i] = flipChar;
	                    checkFlipMatrix[i][j] = '1';
	                    checkFlipMatrix[j][i] = '1';
	                }
	            }
	        }

	        //Iterate through row, if it finds any value it will fill the same value without flip
	        char temp = 'x';
	        for (int i = 1; i < inputMatrix.length; i++) {
	            // get value
	            for (int j = 1; j < inputMatrix.length; j++) {
	                if (inputMatrix[i][j] != '=' && inputMatrix[i][j] != '-' && checkFlipMatrix[i][j] == '1') {
	                    temp = inputMatrix[i][j];
	                }
	            }
	            //fill row and column
	            for (int j = 1; j < inputMatrix.length; j++) {
	                if (inputMatrix[i][j] != '=' && inputMatrix[i][j] == '-' && temp != 'x') {
	                    inputMatrix[i][j] = temp;
	                    checkFlipMatrix[i][j] = '1';
	                    char flipChar = flipSign(temp);
	                    inputMatrix[j][i] = flipChar;
	                    checkFlipMatrix[j][i] = '1';
	                }
	            }
	            temp = 'x';
	        }
	        
	        String result = convertToStr(inputMatrix);
	        return result;

	}
	
	/**
	 * Convert the matrix to string.
	 * @param myArray
	 * @return string
	 */
	 private  String convertToStr(char[][] myArray) {
	        String aString;
	        aString = "";
	        int column;
	        int row;

	        for (row = 0; row < myArray.length; row++) {
	            for (column = 0; column < myArray[0].length; column++) {
	                aString = aString + myArray[row][column];
	            }
	            aString = aString + System.lineSeparator();
	        }
	        return aString.trim();
	    }

	 /**
	  * Flip the sign
	  * @param c
	  * @return flipped value
	  */
	    private  char flipSign(char c) {
	        if (c == '>') {
	            return '<';
	        } else if (c == '<') {
	            return '>';
	        } else {
	            return '-';
	        }
	    }
}
