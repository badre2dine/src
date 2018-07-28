package utilitie;

import message.ByteArray;
import message.CharactersListMessage;
import message.SelectedServerDataMessage;

import utilitie.Encryption;

public class Commande {

	
	static public  byte [] ServerSelectionMessage(Buffer buffer,int serverId)
	{
		
		buffer.reset();
		try {
			buffer.writeVarShort(serverId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return Paquet.getInstance(40, buffer.getArray()).toBytes();
	}
	static public byte [] AuthenticationTicketMessage(Buffer buffer,message.SelectedServerDataMessage SSDM) {
		buffer.reset();
	    buffer.writeUTF("fr");
	    String b=new String(Encryption.decodeWithAES(SSDM.getTicket()));
	    buffer.writeUTF(b);
	    
	    return Paquet.getInstance(110, buffer.getArray()).toBytes();
	}
	static public byte [] CheckIntegrityMessqge(Buffer buffer) {
		buffer.reset();
		try {
		for(int i=0;i<256;i++)
		
			buffer.writeByte((byte) i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    
	    return Paquet.getInstance(6372, buffer.getArray()).toBytes();
	}
	static public byte [] CharacterListRequestMessage() {
		
	   
	    
	    return Paquet.getInstance(150, new byte[0]).toBytes();
	}
	static public  byte []CharacterSelectionMessage(Buffer buffer, double pid) {
		buffer.reset();
		try {
			buffer.writeVarLong(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    
	    return Paquet.getInstance(152, buffer.getArray()).toBytes();
	}
	


	
	
	
}
