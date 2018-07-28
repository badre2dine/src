package message;

import utilitie.BooleanByteWrapper;
import utilitie.Buffer;
import utilitie.Encryption;

public class IdentificationMessage extends Message
{
   
   public static final int  protocolId = 4;
    

   public void serializeAs_IdentificationMessage(boolean autoconnect,boolean useCertificate ,boolean useLoginToken ,byte major,byte minor,byte release ,int revision,byte patch,byte buildType,byte install,byte technology,String lang ,String login,String password,short serverId,Long sessionOptionalSalt,int[] failedAttempts,HelloConnectMessage HCM,Buffer param1) throws Exception 
   {
	   param1.reset();
		byte[] credentials = Encryption.getCredentials(HCM.getKey(), HCM.getSalt(), login, password);
      int _loc2_ = 0;
      _loc2_ = BooleanByteWrapper.setFlag(_loc2_,0,autoconnect);
      _loc2_ = BooleanByteWrapper.setFlag(_loc2_,1,useCertificate);
      _loc2_ = BooleanByteWrapper.setFlag(_loc2_,2,useLoginToken);
      param1.writeByte((byte) _loc2_);
      this.Version(major, minor, release, revision, patch, buildType, install, technology, param1);
      param1.writeUTF(lang);
      param1.writeVarInt(credentials.length);
      _loc2_ = 0;
     for(int i=0;i<credentials.length;i++)
     {  
    	 param1.writeByte(credentials[i]);
     }
      param1.writeShort(serverId);
      if(sessionOptionalSalt < -9.007199254740992E15 || sessionOptionalSalt > 9.007199254740992E15)
      {
         throw new Error("Forbidden value (" + sessionOptionalSalt + ") on element sessionOptionalSalt.");
      }
      param1.writeVarLong(sessionOptionalSalt);
      param1.writeShort( (short) failedAttempts.length);
      for(int i = 0; i < failedAttempts.length; i++)
      {
         if(failedAttempts[i] < 0)
         {
            throw new Error("Forbidden value (" + failedAttempts[i] + ") on element 9 (starting at 1) of failedAttempts.");
         }
         param1.writeVarShort(failedAttempts[i]);
      }
   }
   
   
   public void Version(byte major,byte minor,byte release ,int revision,byte patch,byte buildType,byte install,byte technology,Buffer buffer) throws Exception
   {
	   buffer.writeByte(major);
	   buffer.writeByte(minor);
	   buffer.writeByte(release);
	   buffer.writeInt(revision);
	   buffer.writeByte(patch);
	   buffer.writeByte(buildType);
	   buffer.writeByte(install);
	   buffer.writeByte(technology);
	   
   }
}