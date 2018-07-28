package message;

import java.util.ArrayList;

import utilitie.Buffer;

public class SelectedServerDataMessage extends Message 
   {
      
      public static final	long	protocolId	=	 42;
       
      
      private 	boolean	_isInitialized	=	 false;
      
      public 	long	serverId	=	 0;
      
      public 	String	address	=	 "";
      
      public 	long	port	=	 0;
      
      public 	boolean	canCreateNewCharacter	=	 false;
      
      public 	byte[]	ticket;
      
 

public SelectedServerDataMessage(byte [] data) {
		super();
		
		try {
			deserializeAs_SelectedServerDataMessage(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



public 	void	deserializeAs_SelectedServerDataMessage(byte [] data) throws Exception{
		Buffer buffer=new Buffer(data);
		byte	_val5	=	 0;
		this.serverId = buffer.readVarUhShort();
		this.address = buffer.readUTF();
		this.port = buffer.readUShort();
		this.canCreateNewCharacter = buffer.readBoolean();
		
	
		int	_ticketLen	=	 buffer.readVarInt();
				ticket=new byte[_ticketLen];
         for(	int	_i5	=	 0; _i5 < _ticketLen; _i5++)
         {
        	 
        	 this.ticket[_i5]= buffer.readByte();
            
         }
		
	}



public long getServerId() {
	return serverId;
}



public String getAddress() {
	return address;
}



public long getPort() {
	return port;
}



public boolean isCanCreateNewCharacter() {
	return canCreateNewCharacter;
}



public byte[] getTicket() {
	return ticket;
}	

}