package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
 
 
public interface LamportInt extends Remote  {
	Message recive(int senderTime) throws RemoteException;
}
