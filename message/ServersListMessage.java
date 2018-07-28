package message;

import java.util.ArrayList;

import utilitie.Buffer;
import utilitie.GameServerInformations;

public class ServersListMessage extends Message 
   {
      
      public static final	long	protocolId	=	 30;
      
      public 	ArrayList<GameServerInformations>	servers;
      
      public 	long	alreadyConnectedToServerId	=	 0;
      
      public 	boolean	canCreateNewCharacter	=	 false;
      
      
 public ServersListMessage(byte[] data) {
		super();
		servers =new ArrayList<GameServerInformations>();
		
		deserializeAs_ServersListMessage(data);
	}


public 	void	deserializeAs_ServersListMessage(byte []data){
	 		Buffer buffer=new Buffer(data);
		try {
			GameServerInformations	_item1	=	 null;
         	int	_serversLen	=	 buffer.readUShort();
         for(	long	_i1	=	 0; _i1 < _serversLen; _i1++)
         {
            _item1 = new GameServerInformations(buffer);
            
            this.servers.add(_item1);
         }
	
			this.alreadyConnectedToServerId = buffer.readVarUhShort();
			this.canCreateNewCharacter = buffer.readBoolean();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}