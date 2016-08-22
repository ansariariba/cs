import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import javax.crypto.*;
import java.util.*;
 
public class Server
{
 
    private static Socket socket;
 
    public static void main(String[] args)
    {
        try
        {
 
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");
 
            //Server is running always. This is done using this while(true) loop
            while(true)
            {
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String m = br.readLine();
                System.out.println("Message received from client is :\t"+ m);
 
                //Multiplying the number by 2 and forming the return message
				
                String returnMessage="msg";
                try
                {
                   if(!m.equals("two"))
                   {
                    
					 FileOutputStream s1 = null;
					 s1 = new FileOutputStream("serverf1.txt");
			     final PrintStream printStream = new PrintStream(s1);
                    printStream.print(m);
                    printStream.close();
			 	   s1.close();
				   
                 	  returnMessage = "file being uploaded\n";
					}
					
					if(m.equals("two"))
					{		FileInputStream f= new FileInputStream("serverf1.txt");
			
								int ch;
					       	  String lines = "";
				         	while((ch=f.read())!=-1)
				     		{
											lines=lines+(char)ch;
						
				     		}
								returnMessage = lines+"\n";
							}
            	    }
                catch(NumberFormatException e)
                {
                    //Input was not a number. Sending proper message back to client.
                    
                }
 
                //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
				
                bw.write(returnMessage);
                System.out.println("Message sent to the client is:\t "+returnMessage);
                bw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}