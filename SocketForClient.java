
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketForClient {
	
	public static void main(String[] args) {
			clientForChat();		
	}
	/**
	 * 仅用于接收与发送文字
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void clientForChat(){
		//指定IP地址和端口号
		String serverName = "10.194.8.140";//10.194.8.136,10.194.8.140
		int port = 8082;
		Socket client = null;
		BufferedWriter outToServer = null;
		InputStream inFormServer = null;
		try {
			
			/*
			 * 创建套接字，底层通过InetAddress.getByName(hostname)方法找到IP地址，并通过
			 * InetSocketAddress(String hostname, int port)构造器给InetSocketAddress类
			 * 的holder属性赋值，此成员变量类型为其一个私有静态内部类InetSocketAddressHolder
			 */
			System.out.println("尝试连接至 \"" + serverName + "\"端口号为:" + port);
			client = new Socket(serverName,port);
			//连接成功时可通过getRemoteSocketAddress()获取对方地址
			System.out.println("已连接至 " + client.getRemoteSocketAddress());
			//DataOutputStream stream = new DataOutputStream(outToServer);
			//通过socket获取字符流
			outToServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            //通过标准输入流获取字符流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in,"gbk"));
            while (true){
        	  String msg = bufferedReader.readLine();
        	  
        	  if(!"exit".equals(msg)) {
        		  outToServer.write(msg + "\n");
        		  outToServer.flush();
        	  }else {
        		  System.out.println("已断开与服务器"+ client.getRemoteSocketAddress() +"的通信");
        		  break;
        	  }

          }
			/*
			 * for(int i = 0;i < 5;i++) { outToServer = client.getOutputStream();
			 * //通过Scanner获取要发送的信息 Scanner scanner = new Scanner(System.in); String msg =
			 * scanner.next(); //将要发送的信息放入a输出流，传输至服务器 outToServer.write(msg.getBytes());
			 * //结束此次输出，作为一条信息发送出去 scanner.close(); //client.shutdownOutput(); //获取服务器回应
			 * inFormServer = client.getInputStream();
			 * 
			 * byte[] buf = new byte[1024]; int len; while((len = inFormServer.read(buf)) !=
			 * -1) { String s = new String(buf,0,len); System.out.print(s); } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(inFormServer != null) {
					inFormServer.close();
				}
				if(outToServer != null) {
					outToServer.close();
				}
				if(client != null) {
					client.close();
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void client() throws UnknownHostException, IOException {
		Socket socket = new Socket("10.194.8.140",8080);//10.194.8.136,10.194.8.140
		
		OutputStream os = socket.getOutputStream();
		
		FileInputStream fis = new FileInputStream("C:\\Users\\27795\\desktop\\1111.jpg");
		byte[] buffer = new byte[2014];
		int len1;
		while((len1 = fis.read(buffer)) != -1) {
			os.write(buffer,0,len1);
		}
		
		socket.shutdownOutput();
		
		InputStream is = socket.getInputStream();
		
		byte[] buf = new byte[1024];
		int len;
		while((len = is.read(buf)) != -1) {
			String s = new String(buf,0,len);
			System.out.print(s);
		}
		
		is.close();
		fis.close();
		os.close();
		socket.close();
	}
}
