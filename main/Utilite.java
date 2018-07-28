package main;

public class Utilite {

	
	public static String ByteToHexa(byte[] data)
	{
		if(data.length!=0)
		{
			char[] dataf=new char[data.length*2];
			for(int i=0;i<data.length;++i)
			{
				int deci=(data[i] & 255);
				
				dataf[i*2]= DeciToHexa(deci>>4);
				
				
				dataf[i*2+1]= DeciToHexa(deci  & 15);
			}
		
			return String.copyValueOf(dataf);
		}
		else 
		{
			System.out.println("taille invalide ");
			return null;
		}
	}
	
	public static byte[] HexaToByte(String data)
	{
		
		char[] tablechar=data.toCharArray();
		if(tablechar.length%2==0)
		{	
			int size=tablechar.length/2;
			byte[] dataf=new byte[size];
			for(int i=0;i<size;++i)
			{
			
				dataf[i]=  (byte) (HexaToDeci(tablechar[i*2])<<4 | HexaToDeci(tablechar[i*2+1]));
			
			}
			return dataf;
		}
		else 
		{
			System.out.println("format invalide ");
			return null;
		}
	}
	public   static byte HexaToDeci(char c)
	{
		if(c>='a' && c<='f' )		return (byte) (c-87);
		else if(c>='A' && c<='F') 	return (byte) (c-55);
		else if(c>='0' && c<='9') 	return (byte) (c-48);
		else { System.out.println("pas format hexa"); return (byte) -1;}

	}
	public   static char DeciToHexa(int i)
	{
		if(i>=0 && i<=9 )		return (char) (i+48);
		else if(i>=10 && i<=15) 	return (char) (i+55);
		else { System.out.println("pas format hexa"); return (char) -1;}

	}
}
