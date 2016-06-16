package corba;

import org.omg.CosNaming.*;

import LamportApp.*;

import org.omg.CORBA.*;

public class Client {
	static LamportInt lamport;

	public void start(String args[], Lamport mylamport) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is
			// part of the Interoperable naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			String name = "Lamport";
			lamport = LamportIntHelper.narrow(ncRef.resolve_str(name));

			mylamport.setTime(Math.max(mylamport.getTime(), lamport.receive(mylamport.getTime())) + 1);

			// helloImpl.shutdown();

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

}
