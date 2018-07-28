package message;

import utilitie.Buffer;

public class HaapiSessionMessage extends Message 
   {
      
      public static final	long	protocolId	=	 6769;
       
      
   


	public HaapiSessionMessage(byte[] data) {
		super();
		deserializeAs_HaapiSessionMessage(data);
	}
	private String key;


	private byte type;
public 	void	deserializeAs_HaapiSessionMessage(byte []data){

	  Buffer buffer = new Buffer(data);
 try {
	this.key = buffer.readUTF();
	this.type = buffer.readByte();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
}