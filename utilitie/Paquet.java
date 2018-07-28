package utilitie;

import java.util.Arrays;

public class Paquet {

	private byte[] data;
	private int id;
	private int size;
	private byte sizeofsize;
	static private long _instance_id=0;
	
	//datainitaile c'est les donnes dans le paquet recu 
	public Paquet(byte []datainitiale) {
		
		
		super();
		
		_instance_id++;
		System.out.print("\nmnb = "+_instance_id);

		short header =  (short) ((datainitiale[0] & 255)<<8 | (datainitiale[1] & 255 ))  ;
			
		this.id =  (short) (header  >> 2);
		sizeofsize =  (byte) (header & 3);
		
		switch(sizeofsize)
		{
			case 0 	: 	size = 0;break;
			case 1 	:	size = datainitiale[2] & 255;break;
			case 2	:	size = ((datainitiale[2] & 255)<<8 | datainitiale[3]& 255);break;
			default :	size = (((datainitiale[2] & 255 )<< 16 | (datainitiale[3]& 255) << 8) | datainitiale[4] & 255);	
		}
		int t;
	if(size<8192)
		{this.data = new byte[size];
		t=size;
		for(int i = 0; i < t ; i++)
			data[i] = datainitiale[i+2 + sizeofsize];
		for(int i = 0; i < datainitiale.length-(t+2+sizeofsize) ; i++)
			datainitiale[i]=datainitiale[i+t+2+sizeofsize];
		}
	else 
		{this.data=new byte[datainitiale.length-sizeofsize-2];
		t=datainitiale.length;
		for(int i = 0; i <datainitiale.length-sizeofsize-2; i++)
			data[i] = datainitiale[i+2 + sizeofsize];
		
		}
	
		
		
	}

	public Paquet( int id,  byte[] data) {
		
		super();
		
		_instance_id++;
		System.out.print("\nmnb = "+_instance_id);
		this.data = data;
		this.id = id;
		this.size = data.length;
		if(size==0)this.sizeofsize = 0;
		else if(size<=0xFF)this.sizeofsize = 1;
		else if(size<=0xFFFF)this.sizeofsize = 2;
		else this.sizeofsize = 3;
	}

	public byte[] toBytes()
	{
		byte array[]=new byte[size+6+sizeofsize];
		
		array[0]=(byte) ((id>>6)& 0xFF);
		array[1]=(byte) ((byte) (id<<2 | sizeofsize )&0xFF);
		
		for(int i=0;i<4;++i)
			array[2+i]=(byte) (Paquet._instance_id>>((3-i)*8) & 0xFF);
		
		for(int i=0;i<sizeofsize;++i)
			array[6+i]=(byte) (size>>((sizeofsize-i-1)*8) & 0xFF);
		for(int i=0;i<size;++i)
			array[6+sizeofsize+i]=data[i];
		return array;
	}
	@Override
	public String toString() {
		return "Paquet [data=" + Arrays.toString(data) + ", id=" + id + ", size=" + size + "]";
	}

	public byte[] getData() {
		return data;
	}

	public int getId() {
		return id;
	}

	public int getSize() {
		return size;
	}

	public byte getSizeofsize() {
		return sizeofsize;
	}

	public static Paquet getInstance(int id,byte[] data) {
		// TODO Auto-generated method stub
		return new Paquet(id,data);
	}
	
	
	
}
