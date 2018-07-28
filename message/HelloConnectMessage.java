package message;

import utilitie.Buffer;


public class HelloConnectMessage  extends Message{
	public static final int IprotocolId = 3;
	private String salt;
	private byte[] key;
	public int keySize;
	
	public HelloConnectMessage(byte []data) throws Exception {
		deserialize(data);
	}
	
	private void deserialize(byte []data) throws Exception {
		Buffer buffer = new Buffer(data);
		this.salt = buffer.readUTF();
		 keySize = buffer.readVarInt();
		this.key = buffer.readBytes(keySize);
	}
	
	
	
	public String getSalt() {
		return this.salt;
	}
	
	public byte[] getKey() {
		return this.key;
	}
}
