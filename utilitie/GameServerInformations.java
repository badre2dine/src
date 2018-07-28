package utilitie;
public class GameServerInformations 
   {
      
      public static final	long	protocolId	=	 25;
       
      
      public 	long	id	=	 0;
      
      public 	int	type	=	 -1;
      
      public 	boolean	isMonoAccount	=	 false;
      
      public 	long	status	=	 1;
      
      public 	long	completion	=	 0;
      
      public 	boolean	isSelectable	=	 false;
      
      public 	long	charactersCount	=	 0;
      
      public 	long	charactersSlots	=	 0;
      
      public 	double	date	=	 0;
      
public GameServerInformations(Buffer buffer) {
		super();
		deserializeAs_GameServerInformations(buffer);
	}

public 	void	deserializeAs_GameServerInformations(Buffer buffer){
	
      	int _box0;
		try {
			_box0 = buffer.readByte();
			this.isMonoAccount = BooleanByteWrapper.getFlag(_box0,0);
	        this.isSelectable = BooleanByteWrapper.getFlag(_box0,1);
			this.id = buffer.readVarUhShort();
			this.type = buffer.readByte();
			this.status = buffer.readByte();
	 		this.completion = buffer.readByte();
			this.charactersCount = buffer.readByte();
			this.charactersSlots = buffer.readByte();
	 		this.date = buffer.readDouble();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
      }
  }