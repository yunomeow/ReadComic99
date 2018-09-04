import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.ImageIcon;


public class NetIcon extends Thread{
	private ImageIcon icon = null;
	private String str;
	public NetIcon(String str){
		this.str = str;
	}
	public void run(){
		icon = getNetIcon();
	}
	public ImageIcon getNetIcon(){
		URL url;
		try {
			url = new URL(str);
		
		URLConnection urlconnection;
			urlconnection = url.openConnection();
		
		urlconnection.setRequestProperty("User-Agent",
		"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
		InputStream in = urlconnection.getInputStream();
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			int tmp;
			while ((tmp = in.read()) != -1){
				bs.write(tmp);
			}
			byte[] b = bs.toByteArray();
		ImageIcon icon = new ImageIcon(b);
		return icon;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ImageIcon getIcon(){
		return icon;
	}
}
