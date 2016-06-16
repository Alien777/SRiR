/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.Set;
import javax.inject.Singleton;
import javax.ws.rs.core.Application;
 
@Singleton
class LamportSingleton {
	private Lamport lamport;
	private static LamportSingleton lSingleton = null;

	private LamportSingleton() {

		if (lamport == null)
			lamport = new Lamport();

	}

	public static LamportSingleton inicialLamportObject() {
		if (lSingleton == null) {
			lSingleton = new LamportSingleton();
		}
		return lSingleton;
	}

	public Lamport getLamport() {

		return lamport;
	}
}

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
		addRestResourceClasses(resources);
		return resources;
	}

	/**
	 * Do not modify addRestResourceClasses() method. It is automatically
	 * populated with all resources defined in the project. If required, comment
	 * out calling this method in getClasses().
	 */
	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(rest.LamportResource.class);
		resources.add(rest.ServiceResource.class);
	}

}
