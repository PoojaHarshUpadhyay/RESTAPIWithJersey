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
		  

		 Resume objResume = new Resume();
	     HashMap<String, String> map =  objResume.readPdf();	
		
	     
		 if(question == null && description == null) {
			 return "Enter your question";
		 }
		 
		 if(map.containsKey(question)) {
			 return map.get(question);
		 }
		  else {
			  return "Question not handled";
		  }
	  }
}
