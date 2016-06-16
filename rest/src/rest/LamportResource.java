/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
 
@Path("/json/lamport")
@Singleton
public class LamportResource {

	@Context
	private UriInfo context;
	private Lamport lamport;

	/**
	 * Creates a new instance of LamportResource
	 */
	public LamportResource() {
		if (lamport == null) {

			lamport = LamportSingleton.inicialLamportObject().getLamport();
		}
	}

	/**
	 * Retrieves representation of an instance of srir.LamportResource
	 * 
	 * @return an instance of java.lang.String
	 */
	private final static AtomicInteger time = new AtomicInteger(0);

	@GET
	@Path("/get")
	@Produces("text/html")
	public String getProductInJSON() {

		return String.valueOf(lamport.getTime());
		// return p.toString();

	}

	/**
	 * PUT method for updating or creating an instance of LamportResource
	 * 
	 * @param content
	 *            representation for the resource
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void putJson(String content) {
	}

	@POST
	@Path("/post")
	@Consumes("text/html")
	public String createProductInJSON(String product) {

		Integer result = (Math.max(Integer.parseInt(product), lamport.getTime()) + 1);
		return result.toString();

	}
}
