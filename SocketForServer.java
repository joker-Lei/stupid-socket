
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
			System.out.println("�ȴ��ͻ������ӣ��˿ں�Ϊ��" + serverSocket.getLocalPort());
			server = serverSocket.accept();
			//���ӳɹ�ʱ��ȡ�Է���ַ
			System.out.println("��������:" + server.getRemoteSocketAddress());
			//��ȡ������,����ָ��ͳһ�ı����ʽ
            bufferedReader =new BufferedReader(new InputStreamReader(server.getInputStream(),"gbk"));
            //��ȡһ������
            String str;
            //ͨ��whileѭ�����϶�ȡ��Ϣ��
            while ((str = bufferedReader.readLine()) != null){
            	if(!"exit".equals(str)) {
            		//�����ӡ
                    System.out.println(str);
            	}else {
            		System.out.println("�ѶϿ���ͻ���"+ server.getInetAddress() +"��ͨ��");
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
	 * �����ڷ������������
	 * @throws IOException
	 */
	public static void serverForChat() {
		//�����������׽��ֲ�ָ���˿ںţ�ͨ��accept������������
		ServerSocket ss = null;
		Socket socket = null;
		//���Ӵ�����������������
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
				
				//���Ӵ���������ش���Ϣ���Ƿ����յ���
//			os = socket.getOutputStream();
//			Scanner scanner = new Scanner(System.in);
//			String reply = scanner.next();
//			String reply = "��Ϣ���յ�";
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
			System.out.println("�ȴ��ͻ������ӣ��˿ں�Ϊ��" + serverSocket.getLocalPort());
			server = serverSocket.accept();
			//���ӳɹ�ʱ��ȡ�Է���ַ
			System.out.println("��������:" + server.getRemoteSocketAddress());
				//��ȡ�ͻ��˵����������Ϊ�������������������������
				InputStream msgFormClient = server.getInputStream();
				byte[] buf = new byte[1024];
				int len;
				while((len = msgFormClient.read(buf)) != -1) {//��ȡ������Ϣ���ŵ�buf��
						System.out.println(new String(buf,0,len));
				}
				//���ͻ��������ظ�
				OutputStream replyToClient = server.getOutputStream();
				//ͨ��Scanner��ȡҪ���͵���Ϣ
//				Scanner scanner = new Scanner(System.in);
//				String msg = scanner.next();
				String msg = "��Ϣ���յ�";
				//��Ҫ���͵���Ϣ�����������������������
				replyToClient.write(msg.getBytes());
				//�����˴��������Ϊһ����Ϣ���ͳ�ȥ
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
	System.out.println("ͼƬ�������");
	
	//bos.close();
	fos.close();
	OutputStream os = socket.getOutputStream();
	os.write("��Ů��ã�ͼƬ���յ�".getBytes());
	
	os.close();
	is.close();
	socket.close();
	ss.close();
	}
}