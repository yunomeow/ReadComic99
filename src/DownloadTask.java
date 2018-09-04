import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class DownloadTask extends Thread{	
	private String url;
	private String savepath;
	private long filesize;
	private long nowsize;
	private int state;
	private String name;
	private File file;
	private JFrame  frame;
	private String foldername;
	private long lastsize;
	public DownloadTask(String URL,String SAVEPATH,String NAME,String foldername,JFrame frame){
		url = URL;
		savepath = SAVEPATH;
		name = NAME;
		nowsize = 0;
		lastsize = 0;
		state = 0;
		filesize = 1;
		this.foldername = foldername;
		this.frame = frame;
	}
	public void run(){
		URLConnection urlconnection = null;
		URL downloadURL = null;
		InputStream urlinput;
		OutputStream fileOutputStream;
		int tmp;
		
		
		try {
			state = 1;
			//url = URLEncoder.encode(url,"UTF-8");
			//System.out.println(url);
			downloadURL = new URL(url);
			//System.out.println("in");
			urlconnection = downloadURL.openConnection();
			urlconnection.setRequestProperty("User-Agent",  
			"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			urlconnection.setRequestProperty("referer", "http://dm.99manga.com/comic/217/");
			
			filesize = urlconnection.getContentLength();
			
			
			urlinput = new BufferedInputStream(urlconnection.getInputStream());
			file = new File(savepath);
			fileOutputStream = new BufferedOutputStream (new FileOutputStream(file));
			
			while ((tmp = urlinput.read()) != -1){
				fileOutputStream.write(tmp);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			urlinput.close();
			
			
		}catch (IOException e){
			//JOptionPane.showMessageDialog(frame, name+"發生錯誤");

		}finally{
			state = 2;
		}

	}
	public long getNowSize(){
		if(file != null){
			nowsize = file.length();
		}
		return nowsize;
	}
	public long getFileSize(){
		return filesize;
	}
	public int getDownloadState(){
		return state;
	}
	public String getFileName(){
		return name;
	}
	public String getFolderName(){
		return foldername;
	}
	public void setLastSize(long s){
		lastsize = s;
	}
	public long getLastSize(){
		return lastsize;
	}
}
