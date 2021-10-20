
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
	 * �����ڽ����뷢������
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void clientForChat(){
		//ָ��IP��ַ�Ͷ˿ں�
		String serverName = "10.194.8.140";//10.194.8.136,10.194.8.140
		int port = 8082;
		Socket client = null;
		BufferedWriter outToServer = null;
		InputStream inFormServer = null;
		try {
			
			/*
			 * �����׽��֣��ײ�ͨ��InetAddress.getByName(hostname)�����ҵ�IP��ַ����ͨ��
			 * InetSocketAddress(String hostname, int port)��������InetSocketAddress��
			 * ��holder���Ը�ֵ���˳�Ա��������Ϊ��һ��˽�о�̬�ڲ���InetSocketAddressHolder
			 */
			System.out.println("���������� \"" + serverName + "\"�˿ں�Ϊ:" + port);
			client = new Socket(serverName,port);
			//���ӳɹ�ʱ��ͨ��getRemoteSocketAddress()��ȡ�Է���ַ
			System.out.println("�������� " + client.getRemoteSocketAddress());
			//DataOutputStream stream = new DataOutputStream(outToServer);
			//ͨ��socket��ȡ�ַ���
			outToServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            //ͨ����׼��������ȡ�ַ���
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in,"gbk"));
            while (true){
        	  String msg = bufferedReader.readLine();
        	  
        	  if(!"exit".equals(msg)) {
        		  outToServer.write(msg + "\n");
        		  outToServer.flush();
        	  }else {
        		  System.out.println("�ѶϿ��������"+ client.getRemoteSocketAddress() +"��ͨ��");
        		  break;
        	  }

          }
			/*
			 * for(int i = 0;i < 5;i++) { outToServer = client.getOutputStream();
			 * //ͨ��Scanner��ȡҪ���͵���Ϣ Scanner scanner = new Scanner(System.in); String msg =
			 * scanner.next(); //��Ҫ���͵���Ϣ����a������������������� outToServer.write(msg.getBytes());
			 * //�����˴��������Ϊһ����Ϣ���ͳ�ȥ scanner.close(); //client.shutdownOutput(); //��ȡ��������Ӧ
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
