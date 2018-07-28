package utilitie;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

import message.HelloConnectMessage;
import message.IdentificationMessage;
import message.Message;
import message.SelectedServerDataMessage;
import message.ServersListMessage;


public class Reader implements Runnable  {

	private Message orderM;
	private InputStream input;
	private byte[] buffer = new byte[8192];
	private ConcurrentLinkedQueue<Paquet> listeP;
	public Reader(InputStream input,Message orderM) {
		super();
		listeP=new ConcurrentLinkedQueue<Paquet>();
		this.input = input;
		this.orderM=  orderM;
		
	}
	void readPaquet()  
	{
		int Tsize;
		try {
			Tsize = input.read(buffer);//nombre d'octes des donnes ;
			System.out.println(Tsize);
		int Csize=0;//taille courant
		
		while(Csize<Tsize)
		{
		Paquet p=new Paquet(buffer);
		Csize=Csize+2+p.getSizeofsize()+p.getSize();

		listeP.add(p);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	void readNigpaquet(Paquet p) {
		int sizeR=p.getSize();
		try {
			FileOutputStream fs = new FileOutputStream("./RDM.swf");
			fs.write(p.getData());
			sizeR-=p.getData().length;
		while(sizeR>0)
		{
			sizeR-=input.read(buffer);//nombre d'octes des donnes ;
			
			  fs.write(buffer);		
		}
		fs.close();
		System.out.println("\nSWF file created.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			
			readPaquet();
			while(!listeP.isEmpty())
			{
				
				Paquet p=listeP.remove();
				System.out.println(p);
				switch(p.getId())
				{
				case 3 :
				{
					try {
						orderM=new HelloConnectMessage(p.getData());
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}break;
				case 30 :
				{
					try {
						orderM=new ServersListMessage(p.getData());
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}break;
				case 42 :
				{
					try {
						
						
						
						orderM=new SelectedServerDataMessage(p.getData());
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}break;
				case 6253 :
				{
					try {
						
						readNigpaquet(p);
						
						orderM=new message.RawDataMessage();
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}break;
				case 6769 :
				{
					try {
						
						
						
						orderM=new message.HaapiSessionMessage(p.getData());
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}break;
				default :
				{
					
				}
				}
			}
		}
	}
	
	public Message getOrderM()
	{
	return this.orderM;
	}
	public void setOrderM(Message orderM) {
		this.orderM = orderM;
	}
	public InputStream getInput() {
		return input;
	}
	public void setInput(InputStream input) {
		this.input = input;
	}
	

}
