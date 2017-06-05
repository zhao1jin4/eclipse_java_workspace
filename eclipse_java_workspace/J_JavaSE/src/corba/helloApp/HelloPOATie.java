package corba.helloApp;


/**
* corba/helloApp/HelloPOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from hello.idl
* Friday, August 24, 2012 2:08:36 PM GMT+08:00
*/

public class HelloPOATie extends HelloPOA
{

  // Constructors

  public HelloPOATie ( corba.helloApp.HelloOperations delegate ) {
      this._impl = delegate;
  }
  public HelloPOATie ( corba.helloApp.HelloOperations delegate , org.omg.PortableServer.POA poa ) {
      this._impl = delegate;
      this._poa      = poa;
  }
  public corba.helloApp.HelloOperations _delegate() {
      return this._impl;
  }
  public void _delegate (corba.helloApp.HelloOperations delegate ) {
      this._impl = delegate;
  }
  public org.omg.PortableServer.POA _default_POA() {
      if(_poa != null) {
          return _poa;
      }
      else {
          return super._default_POA();
      }
  }
  public String sayHello ()
  {
    return _impl.sayHello();
  } // sayHello

  public void shutdown ()
  {
    _impl.shutdown();
  } // shutdown

  private corba.helloApp.HelloOperations _impl;
  private org.omg.PortableServer.POA _poa;

} // class HelloPOATie
