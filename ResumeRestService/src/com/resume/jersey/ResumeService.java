package com.resume.jersey;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/resume")
public class ResumeService {
	
	
	
	  @GET
	  @Path("/")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getOrder(@QueryParam("q") String question, @QueryParam("d") String description) {
		  
		  String response = "";


		 Resume objResume = new Resume();
	     HashMap<String, String> map =  objResume.readPdf();	
		
	     
		 if(question == null && description == null) {
			 response = "Enter your question and description";
		 }
		 
		 if(question.equalsIgnoreCase("Puzzle")) {
			 String[] puzzleArray = description.split(":");
			 if(puzzleArray.length < 2) {
				 response = "invalid description";
	        }
			String result = objResume.solvePuzzle(puzzleArray[1].trim());
			response = " " + result;
			 
		 }
		 if(map.containsKey(question)) {
			 response = map.get(question);
		 } 
		 return response;
	  }
}
