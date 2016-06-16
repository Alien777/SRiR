package LamportApp;


/**
* LamportApp/LamportIntHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LamportApp.idl
* Wednesday, June 15, 2016 8:36:18 PM CEST
*/

abstract public class LamportIntHelper
{
  private static String  _id = "IDL:LamportApp/LamportInt:1.0";

  public static void insert (org.omg.CORBA.Any a, LamportApp.LamportInt that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static LamportApp.LamportInt extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (LamportApp.LamportIntHelper.id (), "LamportInt");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static LamportApp.LamportInt read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_LamportIntStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, LamportApp.LamportInt value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static LamportApp.LamportInt narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof LamportApp.LamportInt)
      return (LamportApp.LamportInt)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      LamportApp._LamportIntStub stub = new LamportApp._LamportIntStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static LamportApp.LamportInt unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof LamportApp.LamportInt)
      return (LamportApp.LamportInt)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      LamportApp._LamportIntStub stub = new LamportApp._LamportIntStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
