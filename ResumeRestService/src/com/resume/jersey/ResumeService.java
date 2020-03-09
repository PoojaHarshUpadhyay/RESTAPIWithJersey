package com.resume.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class ResumeService {
	
	  @GET
	  @Path("/")
	  public String sayHi() {
	    return "Hello Jersey";
	  }
}
