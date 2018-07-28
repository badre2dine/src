package utilitie;

public class Buffer {
	
	private byte [] array;
	private int posR;
	private int size;
	/** les masque : permet de conserver les format des variable **/
	final private static long  MASK_ULONG=0xFFFFFFFFFFFFFFFFl; // 8 octe non signe utilise avec les varibales du type long 
	final private static long  MASK_UINT=0xFFFFFFFFl; // 4 octe non signe utilise avec les varibales du type long 
	final private static int  MASK_USHORT=0xFFFF; // 2 octe non signe utilise avec les varibales du type int 
	final private static int  MASK_BYTE=0xFF; // pour esole une octe
	final private static 	int	INT_SIZE	=	 32;
    
    final private static 	int	SHORT_SIZE	=	 16;
    
    final private static 	int	SHORT_MIN_VALUE	=	 -32768;
    
    final  private static 	int	SHORT_MAX_VALUE	=	 32767;
    
    final private static 	int	UNSIGNED_SHORT_MAX_VALUE	=	 65536;
    
    final private static 	int	CHUNCK_BIT_SIZE	=	 7;
    
    final private static 	int	MAX_ENCODING_LENGTH	=	 (int) Math.ceil(INT_SIZE / CHUNCK_BIT_SIZE);
    
    final private static 	int	MASK_10000000	=	128;
    final private static 	int MASK_01111111	=	127;
	public Buffer(byte[] array) {
		super();
		this.array = array;
		posR=0;
		size=array.length;
	}
	
	public boolean readBoolean() throws Exception 
	{
		
		if(isEmpty(1))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			return array[posR++]!=0;
		}
	}
	
	public byte readByte() throws Exception 
	{
		
		if(isEmpty(1))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			return array[posR++];
			
		}
	}
	 public int readUnsignedByte()  throws Exception 
	{
		
		if(isEmpty(1))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			return array[posR++] & 0Xff;
			
		}
	}
	public short readShort() throws Exception 
	{
		
		if(isEmpty(2))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			return (short) (array[posR++]<<8 | array[posR++] & MASK_BYTE);
			
		}
	}
	public int readUShort() throws Exception 
	{
		
		if(isEmpty(2))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			return (array[posR++]<<8 | array[posR++] & MASK_BYTE) & MASK_USHORT;
			
		}
	}
	public int readInt() throws Exception 
	{
		byte bytenumber=4;
		if(isEmpty(bytenumber))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			int result=0;
			for(int i=bytenumber-1;i>=0;--i)
			{
				result|=(array[posR++]& MASK_BYTE)<<(i*8);
				
			}
			return result;		
		}
	}
	
	/** entier non signe java reste en traitement**/
	public long readUInt() throws Exception 
	{
		byte bytenumber=4;
		if(isEmpty(bytenumber))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			long result=0;
			
			for(int i=bytenumber-1;i>=0;--i)
			{
				result|=(array[posR++]& MASK_BYTE)<<(i*8);
				
			}
			return  result & MASK_UINT;
			//return (array[posR++]<<24 | (array[posR++] & MASK_BYTE)<<16 | (array[posR++]& MASK_BYTE)<<8  | array[posR++] & MASK_BYTE) & MASK_UINT ;
		}
	}
	public long readLong() throws Exception 
	{
		byte bytenumber=8;
		if(isEmpty(bytenumber))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			long result=0l;
			for(int i=bytenumber-1;i>=0;--i)
			{
				result|=((array[posR++]& MASK_BYTE) & MASK_ULONG)<<(i*8);
				
			}
			return  result ;
						
		}
	}
	public Double readDouble() throws Exception 
	{
		byte bytenumber=8;
		if(isEmpty(bytenumber))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			long result=0l;
			for(int i=bytenumber-1;i>=0;--i)
			{
				result|=((array[posR++]& MASK_BYTE) & MASK_ULONG)<<(i*8);
				
			}
			return  Double.longBitsToDouble(result) ;
						
		}
	}
	public String readUTF()
	{
		try {
			
			char[] string=new char[readUShort()];
			int posI=posR;
			
			for(int i=0;posR<string.length+posI;++i)
			{
				
				if( (array[posR] & 128)>>7 == 0)
				{ 
					string[i]=(char)(array[posR++]& 0xFF);
				}
				else 
				{
					if((array[posR] & 224)>>5 ==6) { string[i]=(char) (( array[posR++] & 31)<<6  | array[posR++] & 63) ;}
					else if((array[posR] & 240)>>4 ==14)string[i]=(char) ((array[posR++] & 15)<<12 | (array[posR++] & 63)<<6  | array[posR++] & 63 );
					else if((array[posR] & 240)>>4 ==15)string[i]=(char) ((array[posR++] & 7)<<18 	| (array[posR++] & 63)<<12 |(array[posR++] & 63)<<6 | array[posR++] & 63 );
					else string[i]=' ';
				}
			}
			return String.copyValueOf(string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	public void writeBoolean(boolean data) throws Exception
	{
		if(isEmpty(1))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			array[posR++]=(byte) (data?1:0);
						
		}
		
	}
	public void writeByte(byte data) throws Exception
	{
		if(isEmpty(1))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			array[posR++]=data;
						
		}
		
	}
	
	public void writeShort(short data) throws Exception
	{
		if(isEmpty(1))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			array[posR++]=(byte) (data>>8 & MASK_BYTE);
			array[posR++]=(byte) (data & MASK_BYTE);
						
		}
		
	}
	public void writeUShort(int data) throws Exception
	{
		if(isEmpty(1))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			array[posR++]=(byte) (data>>8 & MASK_BYTE);
			array[posR++]=(byte) (data & MASK_BYTE);
						
		}
		
	}
	

	public void writeInt(int data) throws Exception
	{
		byte bytenumber=4;
		if(isEmpty(bytenumber))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			
			for(int i=bytenumber-1;i>=0;--i)
			array[posR++]=(byte) (data>>i*8 & MASK_BYTE);			
		}
		
	}
	public void writeUInt(long data) throws Exception
	{
		byte bytenumber=4;
		if(isEmpty(bytenumber))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			
			for(int i=bytenumber-1;i>=0;--i)
			array[posR++]=(byte) (data>>i*8 & MASK_BYTE);			
		}
		
	}
	public void writeLong(long data) throws Exception
	{
		byte bytenumber=8;
		if(isEmpty(bytenumber))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			
			for(int i=bytenumber-1;i>=0;--i)
			array[posR++]=(byte) (data>>i*8 & MASK_BYTE);			
		}
		
	}
	public void writeDouble(double data) throws Exception
	{
		byte bytenumber=8;
		if(isEmpty(bytenumber))
		{	
			throw new Exception("le buffer et vide ");
		}
		else
		{
			long lng = Double.doubleToLongBits(data);
			for(int i=bytenumber-1;i>=0;--i)
			array[posR++]=(byte) (lng>>i*8 & MASK_BYTE);			
		}
		
	}
	
	public void writeUTF(String data)
	{
		char[] string=data.toCharArray();
		
		try {
			
			int posI=posR;
			
			posR=posR+2;
			for(int i=0;i<string.length;i++) 
			{
				
				if(string[i]<=0x7F)
				{
					array[posR++]=(byte) string[i];
				}
				else if(string[i]<=0x7FF)
				{
				
					array[posR++]=(byte) (0xC0 | (string[i] & 0x07C0)  >>6);
					array[posR++]=(byte) (0x80 | (string[i] & 0x003F));
				}
				else if(string[i]<=0xFFFF)
				{
					array[posR++]=(byte) (0xE0 | (string[i] & 0xF000) >> 12);
					array[posR++]=(byte) (0x80 | (string[i] & 0x0FC0) >> 6);
					array[posR++]=(byte) (0x80 | (string[i] & 0x003F)  );
				}
				else if(string[i]<=0x10FFFF)
				{
					array[posR++]=(byte) (0xF0 | (string[i] & 0x1C0000) >> 18);
					array[posR++]=(byte) (0x80 | (string[i] & 0x03F000) >> 12);
					array[posR++]=(byte) (0x80 | (string[i] & 0x000FC0) >> 6);
					array[posR++]=(byte) (0x80 | (string[i] & 0x00003F)  );
				}
				else array[posR++]=(byte) ' ';
			}
			
			array[posI]=(byte) ((posR-2-posI)>>8 & MASK_BYTE);
			array[posI+1]=(byte) ((posR-2-posI) & MASK_BYTE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean isEmpty(int ofset)// si le buffer est  vide ou la taille insifusant
	{
		
		return ofset+posR>size;
	}
	public void reset()
	{
		posR=0;
	}

	public byte[] getArray() {
		byte data[]=new byte[posR];
		for(int i=0;i<posR;i++)data[i]=array[i];
		return data;
	}
	public byte []readBytes(int size)
	{
		byte data[] =new byte[size];
		
		for(int i=0;i<data.length;i++)data[i]=array[posR++];
		return data;
		
	}
	public void writeBytes(byte[] data) {
		// TODO Auto-generated method stub
		for(int i=0;i<data.length;i++)array[posR++]=data[i];
	}
	
	public  int readVarInt() throws Exception 
    {
       int b = 0;
       int value= 0;
       int offset= 0;
       for(boolean hasNext= false; offset < 32; )
       {
          b = readByte();
          hasNext = (b & 128) == 128;
          if(offset > 0)
          {
             value = value + ((b & 127) << offset);
          }
          else
          {
             value = value + (b & 127);
          }
          offset = offset + 7;
          if(!hasNext)
          {
             return value;
          }
       }
       throw new Error("Too much data");
       
    }
	public 	int readVarShort() throws Exception{
         	int	b	=	 0;
         	int	value	=	 0;
         	int	offset	=	 0;
         for(	boolean	hasNext	=	 false; offset < SHORT_SIZE; )
         {
            b = readByte();
            hasNext = (b & MASK_10000000) == MASK_10000000;
            if(offset > 0)
            {
               value = value + ((b & MASK_01111111) << offset);
            }
            else
            {
               value = value + (b & MASK_01111111);
            }
            offset = offset + CHUNCK_BIT_SIZE;
            if(!hasNext)
            {
               if(value > SHORT_MAX_VALUE)
               {
                  value = value - UNSIGNED_SHORT_MAX_VALUE;
               }
               return value;
            }
         }
         throw new Error("Too much data");
      }
    public 	long	readVarUhShort() throws Exception{
        return this.readVarShort();
     }
     
     public 	long	readVarLong() throws Exception{
        return this.readInt64().toNumber();
     }
     
     public 	long	readVarUhLong() throws Exception{
        return this.readUInt64().toNumber();
     }
	 public 	void writeVarInt(int	value) throws Exception{
	         	int	b	=	 0;
	         	
	         if(value >= 0 && value <= MASK_01111111)
	         {
	            writeByte((byte) value);
	           
	            return;
	         }
	         	int	c	=	 value;
	         for(	; c != 0; )
	         {
	            
	         
	            b = c & MASK_01111111;
	            c = c >>> CHUNCK_BIT_SIZE;
	            if(c > 0)
	            {
	               b = b | MASK_10000000;
	            }
	           writeByte((byte) b);
	         }
	        
	      }
	 public 	void writeVarShort(int	value) throws Exception{
	         int	b	=	 0;
	         if(value > SHORT_MAX_VALUE || value < SHORT_MIN_VALUE)
	         {
	            throw new Error("Forbidden value");
	         }
	         	
	         if(value >= 0 && value <= MASK_01111111)
	         {
	            writeByte((byte) value);
	           
	            return;
	         }
	         	int	c	=	 value & 65535;
	         for(; c != 0; )
	         {
	            
	           
	            b = c & MASK_01111111;
	            c = c >>> CHUNCK_BIT_SIZE;
	            if(c > 0)
	            {
	               b = b | MASK_10000000;
	            }
	            writeByte((byte) b);
	         }
	        
	      }
	 public 	void writeVarLong(double	value) throws Exception{
	         	long	i	=	 0; 
	         	Int64	val	=	 Int64.fromNumber(value);
	         if(val.high == 0)
	         {
	            this.writeint32(val.low);
	         }
	         else
	         {
	            for(i = 0; i < 4; )
	            {
	              writeByte((byte) (val.low & 127 | 128));
	               val.low = val.low >>> 7;
	               i++;
	            }
	            if((val.high & 268435455 << 3) == 0)
	            {
	              writeByte((byte) (val.high << 4 | val.low));
	            }
	            else
	            {
	               writeByte((byte) ((val.high << 4 | val.low) & 127 | 128));
	               writeint32(val.high >>> 3);
	            }
	         }
	      }
	 private 	Int64 readInt64() throws Exception{
	         	int	b	=	 0;
	         	Int64	result	=	 new Int64();
	         	int	i	=	 0;
	         while(true)
	         {
	            b = readUnsignedByte();
	            if(i == 28)
	            {
	               break;
	            }
	            if(b >= 128)
	            {
	               result.low = (int) (result.low | (b & 127) << i);
	               i = i + 7;
	               continue;
	            }
	            result.low = result.low | b << i;
	            return result;
	         }
	         if(b >= 128)
	         {
	            b = b & 127;
	            result.low = result.low | b << i;
	            result.high = b >>> 4;
	            i = 3;
	            while(true)
	            {
	               b = readUnsignedByte();
	               if(i < 32)
	               {
	                  if(b >= 128)
	                  {
	                     result.high = result.high | (b & 127) << i;
	                  }
	                  else
	                  {
	                     break;
	                  }
	               }
	               i = i + 7;
	            }
	            result.high = result.high | b << i;
	            return result;
	         }
	         result.low = result.low | b << i;
	         result.high = b >>> 4;
	         return result;
	      }
     private 	Int64	readUInt64() throws Exception{
      	int	b	=	 0;
      	Int64	result	=	 new Int64();
      	int	i	=	 0;
      while(true)
      {
         b = readUnsignedByte();
         if(i == 28)
         {
            break;
         }
         if(b >= 128)
         {
            result.low = result.low | (b & 127) << i;
            i = i + 7;
            continue;
         }
         result.low = result.low | b << i;
         return result;
      }
      if(b >= 128)
      {
         b = b & 127;
         result.low = result.low | b << i;
         result.high = b >>> 4;
         i = 3;
         while(true)
         {
            b = readUnsignedByte();
            if(i < 32)
            {
               if(b >= 128)
               {
                  result.high = result.high | (b & 127) << i;
               }
               else
               {
                  break;
               }
            }
            i = i + 7;
         }
         result.high = result.high | b << i;
         return result;
      }
      result.low = result.low | b << i;
      result.high = b >>> 4;
      return result;
   }	 

	private 	void writeint32(long	value) throws Exception{
	         while(value >= 128)
	         {
	            writeByte((byte) (value & 127 | 128));
	            value = value >>> 7;
	         }
	         writeByte((byte) value);
	      }

	
	
	
	
	
}
