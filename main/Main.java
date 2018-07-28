package main;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import message.HelloConnectMessage;
import message.IdentificationMessage;
import message.Message;
import message.SelectedServerDataMessage;
import message.ServersListMessage;
import utilitie.Buffer;
import utilitie.Commande;
import utilitie.Paquet;
import utilitie.Reader;

public class Main {

	private static final boolean autoconnect = false;
	private static final boolean useCertificate = false;
	private static final boolean useLoginToken = false;
	private static final byte major = 2;
	private static final byte minor = 47;
	private static final byte release = 14;
	private static final int revision =187800294;
	private static final byte patch = 0;
	private static final byte buildType = 0;
	private static final byte install = 1;
	private static final byte technology = 1;
	private static final String lang = "fr";
	private static final String login = "badrbt1";
	private static final String password = "ee3ch2j8";
	private static final short serverId = 0;
	private static final Long sessionOptionalSalt = 0l;
	private static final int[] failedAttempts = new int[0];
	private static String adresse= "213.248.126.40";
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*String buffer="000e 0155 0020 6a6a 3773 787a 7378\r\n" + 
				"427b 4b36 716c 2639 452d 6344 584a 6a2c\r\n" + 
				"433e 5522 517e 7074 b102 4dfd 1ed8 bae8\r\n" + 
				"9e7c bb65 6648 9da5 973c c190 b147 bf5a\r\n" + 
				"7958 fd5e f811 0800 e3b8 7579 7f79 6429\r\n" + 
				"e74a 27e3 c9ba 2dac db19 5eb0 3863 e053\r\n" + 
				"9fae c1da 2852 d5bd 2793 ec93 2e78 4e80\r\n" + 
				"63b5 9075 81b1 b067 a598 d168 abad 7feb\r\n" + 
				"cab4 0cc2 11ee fe22 e1cf 8f67 7192 845a\r\n" + 
				"285d 431c a67e 779f a2f6 dbf3 5c0f cee5\r\n" + 
				"981a 15a2 408e 9e40 9001 fb35 51cf 5289\r\n" + 
				"a9c0 5cb6 de61 b9bd cac2 c978 cac4 11a9\r\n" + 
				"6f96 480a 9215 45f0 e5d9 e3d3 1d3b 538e\r\n" + 
				"859d ae39 5ec3 ebb9 7da3 88c6 876a 144c\r\n" + 
				"5a75 6d99 4788 3dde da43 2ce6 6d99 6948\r\n" + 
				"ca25 08b2 5229 beb2 49c7 3a48 d952 ff7a\r\n" + 
				"fe40 7ca0 0ebf 2502 a320 0a9d 5deb 45e4\r\n" + 
				"1594 07a0 f4a8 0c01 c2aa f453 e062 cea7\r\n" + 
				"6e14 852e cd7d 30f7 bbb4 48a8 6993 3976\r\n" + 
				"de23 bd08 751e ffdd 8f82 ec25 3745 a0d1\r\n" + 
				"3b79 61e6 bbd9 7807 942b 5ceb 7bf5 00eb\r\n" + 
				"df61 6326 1c9a b3c3 c50b 46" ; */
		Socket socket = new Socket(adresse, 5555);
		InputStream in = socket.getInputStream();
		OutputStream out=socket.getOutputStream();
		Message msgR=null;
		int order=4;
		byte[] buffer = new byte[8192];
		Buffer param1 = new Buffer(buffer);
		int bytesReceived = 0;
	
		Reader rr=new Reader(in,msgR);
		Thread reader =new Thread (rr);
		reader.start();
		
		int stop =0;
		while(stop==0)
		{
			
			
			
			
			
			
			
			switch(order)
			{
			case  4 :
			{
				while(rr.getOrderM()==null) {System.out.print("");}
				IdentificationMessage	im	=new IdentificationMessage();
				param1.reset();
				im.serializeAs_IdentificationMessage(autoconnect, useCertificate, useLoginToken, major, minor, release, revision, patch, buildType, install, technology, lang, login, password, serverId, sessionOptionalSalt, failedAttempts, (HelloConnectMessage)rr.getOrderM(), param1);
				out.write(Paquet.getInstance(4,param1.getArray()).toBytes());
				param1.reset();
				param1.writeUTF("Mjs80IbNoKdsj9WJP0#01");
				out.write(Paquet.getInstance(5607,param1.getArray()).toBytes());
				param1.reset();
				order=40;
				rr.setOrderM(null);
				
			}break;
			case 40 :
			{
				while(rr.getOrderM()==null) {System.out.print("");}
				out.write(Commande.ServerSelectionMessage(param1, 206));
				Thread.sleep(1);
				order =110;
				rr.setOrderM(null);
			}break;
			case 110 :
			{
				
				 
				while((msgR=rr.getOrderM())==null) {System.out.print("");}
				reader.stop();
				socket.close();
				adresse=((SelectedServerDataMessage)msgR).getAddress();
				socket = new Socket(adresse, 5555);
				 in = socket.getInputStream();
				 out=socket.getOutputStream();
				 rr.setInput(in);
				reader =new Thread (rr);
				reader.start();
				 
				out.write(Commande.AuthenticationTicketMessage(param1, (SelectedServerDataMessage)msgR));
				order =6372;
				rr.setOrderM(null);
			}break;
			case 6372 :
			{
				while(rr.getOrderM()==null) {System.out.print("");}
				out.write(Commande.CheckIntegrityMessqge(param1));
				order =150;
				rr.setOrderM(null);
			}break;
			case 150 :
			{
						System.out.println("150");
				while(rr.getOrderM()==null) {System.out.print("");}
				out.write(Commande.CharacterListRequestMessage());
				order =7;
				rr.setOrderM(null);
			}break;
			default:
			{
				stop=1;
				
				
				
			}
			}
			
		   // System.out.println("Saisissez un entier : ");
		   // order = sc.nextInt();
		   
		}
		
			/*bytesReceived = in.read(buffer);
			System.out.println(bytesReceived + " bytes received. : ");
			Paquet paquet=new Paquet(buffer);
			System.out.println(paquet);
	
	//buffer=buffer.replaceAll(" ", "");
	//Paquet paquet=new Paquet(DatatypeConverter.parseHexBinary(buffer.replaceAll("\r\n", "")));
			bytesReceived = in.read(buffer);
			System.out.println(bytesReceived + " bytes received. : ");
			paquet=new Paquet(buffer);
			System.out.println(paquet);
	
	
	bytesReceived = in.read(buffer);
	System.out.println(bytesReceived + " bytes received. : ");
	paquet=new Paquet(buffer);
	System.out.println(paquet);
	param1.reset();
	bytesReceived = in.read(buffer);
	System.out.println(bytesReceived + " bytes received. : ");
	paquet=new Paquet(buffer);
	System.out.println(paquet);
	
	*/
	
	
	//bytesReceived = in.read(buffer);
	//System.out.println(bytesReceived + " bytes received. : ");
	//paquet=new Paquet(buffer);
	//System.out.println(paquet);
	
	
	
	
		
	}

}
