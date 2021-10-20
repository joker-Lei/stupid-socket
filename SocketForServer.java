
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketForServer extends Thread{
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {		
			int port = 8082;
//			try {
//				Thread server = new SocketForServer(port);
//				server.start();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			Socket server = null;
			BufferedReader bufferedReader = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("等待客户端连接，端口号为：" + serverSocket.getLocalPort());
			server = serverSocket.accept();
			//连接成功时获取对方地址
			System.out.println("已连接至:" + server.getRemoteSocketAddress());
			//获取输入流,并且指定统一的编码格式
            bufferedReader =new BufferedReader(new InputStreamReader(server.getInputStream(),"gbk"));
            //读取一行数据
            String str;
            //通过while循环不断读取信息，
            while ((str = bufferedReader.readLine()) != null){
            	if(!"exit".equals(str)) {
            		//输出打印
                    System.out.println(str);
            	}else {
            		System.out.println("已断开与客户端"+ server.getInetAddress() +"的通信");
            		break;
            	}
            	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(server != null) {
					server.close();						
				}
				if(serverSocket != null) {
					serverSocket.close();						
				}
				if(bufferedReader != null) {
					bufferedReader.close();						
				}
			}catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
	}
	
	public SocketForServer(int port) throws IOException {
	      serverSocket = new ServerSocket(port);
	      serverSocket.setSoTimeout(10000000);
	   }

	/**
	 * 仅用于发送与接收文字
	 * @throws IOException
	 */
	public static void serverForChat() {
		//创建服务器套接字并指定端口号，通过accept方法创建连接
		ServerSocket ss = null;
		Socket socket = null;
		//连接创建输入流接收文字
		InputStream is = null;
		OutputStream os = null;
		try {
			ss = new ServerSocket(8082);
			socket = ss.accept();
			is = socket.getInputStream();
			
			//ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len;
			for(int i = 0;i < 100;i++) {
				
				while((len = is.read(buf)) != -1) {
					System.out.println(new String(buf,0,len));
				}
				
				//连接创建输出流回传信息（是否已收到）
//			os = socket.getOutputStream();
//			Scanner scanner = new Scanner(System.in);
//			String reply = scanner.next();
//			String reply = "信息已收到";
//			os.write(reply.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(os != null) {
					os.close();
				}
				if(is != null) {
					is.close();
				}
				if(socket != null) {
					socket.close();
				}
				if(ss != null) {
					ss.close();
				}										
			}catch (IOException e) {
					e.printStackTrace();
				}	
			
		}
		
	}
	
	@Override
	public void run() {
		Socket server = null;
//		for(int i = 0;i < 5;i++) {
		try {
			System.out.println("等待客户端连接，端口号为：" + serverSocket.getLocalPort());
			server = serverSocket.accept();
			//连接成功时获取对方地址
			System.out.println("已连接至:" + server.getRemoteSocketAddress());
				//获取客户端的输出流，作为自身的输入流，解析此输入流
				InputStream msgFormClient = server.getInputStream();
				byte[] buf = new byte[1024];
				int len;
				while((len = msgFormClient.read(buf)) != -1) {//读取流中信息，放到buf中
						System.out.println(new String(buf,0,len));
				}
				//给客户端作出回复
				OutputStream replyToClient = server.getOutputStream();
				//通过Scanner获取要发送的信息
//				Scanner scanner = new Scanner(System.in);
//				String msg = scanner.next();
				String msg = "信息已收到";
				//将要发送的信息放入输出流，传输至服务器
				replyToClient.write(msg.getBytes());
				//结束此次输出，作为一条信息发送出去
//				scanner.close();
//				server.shutdownOutput();
				
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
//				try {
//					if(server != null) {
//						server.close();						
//					}
//					if(serverSocket != null) {
//						serverSocket.close();						
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
	}
	
	public static void server() throws IOException {
	ServerSocket ss = new ServerSocket(8080);
	
	Socket socket = ss.accept();
	
	InputStream is = socket.getInputStream();
	if(is != null) {
		System.out.println("data accepting...");
	}
	FileOutputStream fos = new FileOutputStream("accepted.jpg");
	
	
	//ByteArrayOutputStream bos = new ByteArrayOutputStream();
	byte[] buf = new byte[1024];
	int len;
	while((len = is.read(buf)) != -1) {
		fos.write(buf,0,len);
	}
	System.out.println("图片接收完毕");
	
	//bos.close();
	fos.close();
	OutputStream os = socket.getOutputStream();
	os.write("美女你好，图片已收到".getBytes());
	
	os.close();
	is.close();
	socket.close();
	ss.close();
	}
}