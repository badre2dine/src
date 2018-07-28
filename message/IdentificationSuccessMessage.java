package message;

import utilitie.BooleanByteWrapper;
import utilitie.Buffer;

public class IdentificationSuccessMessage extends Message
{
      public static final	long	protocolId	=	 22;
       
      
      public IdentificationSuccessMessage(byte[] data) {
		super();
		try {
			deserializeAsyncAs_IdentificationSuccessMessage(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private 	boolean	_isInitialized	=	 false;
      
      
      public 	String	login	=	 "";
      
      public 	String	nickname	=	 "";
      
      public 	long	accountId	=	 0;
      
      public 	long	communityId	=	 0;
      
      public 	boolean	hasRights	=	 false;
      
      public 	String	secretQuestion	=	 "";
      
      public 	double	accountCreation	=	 0;
      
      public 	double	subscriptionElapsedDuration	=	 0;
      
      public 	double	subscriptionEndDate	=	 0;
      
      public 	boolean	wasAlreadyConnected	=	 false;
      
      public 	long	havenbagAvailableRoom	=	 0;

      public 	void	deserializeAsyncAs_IdentificationSuccessMessage(byte []data) throws Exception {
    	  Buffer buffer = new Buffer(data);
    		int	_box0	=	 buffer.readByte();
            this.hasRights = BooleanByteWrapper.getFlag(_box0,0);
            this.wasAlreadyConnected = BooleanByteWrapper.getFlag(_box0,1);
            this.login = buffer.readUTF();
            this.nickname = buffer.readUTF();
            this.accountId = buffer.readInt();
            this.communityId = buffer.readByte();
            this.secretQuestion = buffer.readUTF();
            this.accountCreation = buffer.readDouble();
            this.subscriptionElapsedDuration = buffer.readDouble();
            this.subscriptionEndDate = buffer.readDouble();
            this.havenbagAvailableRoom = buffer.readUnsignedByte();
      }
}