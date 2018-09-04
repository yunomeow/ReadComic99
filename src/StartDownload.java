import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import javax.swing.JOptionPane;


public class StartDownload extends Thread{
	private TaskGen_View view;
	private String[] ServerList;
	private Vector<DownloadTask> vector ;
	public StartDownload(TaskGen_View view){
		vector = view.getTaskMgr().getTaskMgr_View().getVector();
		this.view = view;
		ServerList = new String[12];
		ServerList[0]="http://210.22.14.110:99/dm01/";
		ServerList[1]="http://210.22.14.111:99/dm02/";
		ServerList[2]="http://221.4.182.219:99/dm03/";
		ServerList[3]="http://61.163.92.190:99/dm04/";
		ServerList[4]="http://221.4.182.218:99/dm05/";
		ServerList[5]="http://221.4.182.219:99/dm06/";
		ServerList[6]="http://218.107.52.44:99/dm07/";
		ServerList[7]="http://221.4.182.219:99/dm08/";
		ServerList[8]="http://218.107.52.44:99/dm09/";
		ServerList[9]="http://221.4.182.219:99/dm10/";
		ServerList[10]="http://221.4.182.218:99/dm11/";
		ServerList[11]="http://221.4.182.219:99/dm12/";

	}
	public void run(){
		if(view.getComicVector().size()== 0){
			JOptionPane.showMessageDialog(view.getFrame(), "尚未連線"); 
			return;
		}
		int index = view.getComboBox().getSelectedIndex();
		if(index == 0){
			int i,size;
			size = view.getComicVector().size();
			for(i=1;i<size;i++){
				String path;
				if(view.getSavePath().getText() != ""){
					path = view.getSavePath().getText()+"\\"+view.getTitle()+"\\"+view.getComicVector().get(i).getNAME();
				}else{
					path = view.getTitle()+"\\"+view.getComicVector().get(i).getNAME();
				}
				System.out.println("12312412412412");

				File file = new File(path);
				file.mkdirs();
				if(file.exists() == false){
					JOptionPane.showMessageDialog(view.getFrame(), "儲存路徑錯誤"); 
					return ;
				}
				downloadOneVolume("http://dm.99manga.com"+view.getComicVector().get(i).getURL(),path,i);
			}
		}else{
			String path;
			if(view.getSavePath().getText() != ""){
				path = view.getSavePath().getText()+"\\"+view.getTitle()+"\\"+view.getComicVector().get(index).getNAME();
			}else{
				path = view.getTitle()+"\\"+view.getComicVector().get(index).getNAME();
			}
			
			System.out.println(path);
			File file = new File(path);
			file.mkdirs();
			if(file.exists() == false){
				JOptionPane.showMessageDialog(view.getFrame(), "儲存路徑錯誤"); 
				return ;
			}
			downloadOneVolume("http://dm.99manga.com"+view.getComicVector().get(index).getURL(),path,index);
		}
		
	}
	public void downloadOneVolume(String url,String path,int index){
		String inputLine;
		String[] token;
		int server;
		URL list_url;
		int i;
		try {
			token = url.split("=");
			server = Integer.parseInt(token[1]);
			list_url = new URL(url);
			URLConnection urlconnection = list_url.openConnection();
			urlconnection.setRequestProperty("User-Agent",  
			"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)"); 

			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlconnection.getInputStream(),"GB2312"));

			while ((inputLine = in.readLine()) != null) {
				if(inputLine.length() > 16 && (inputLine.substring(0,16)).compareTo("var PicListUrl =") == 0){
					break;
				}
			}
			in.close();
			token = inputLine.split("(var PicListUrl = \")|[|\"]");
			System.out.println(ServerList[server-1]+token[1]);
			
			
			if(view.getPage() == 0){
				for(i=1;i<token.length-1;i++){
					String str = String.format("%03d", i);   
					Thread t = new DownloadTask(ServerList[server-1]+token[i],path+"\\"+str+".jpg",str+".jpg"
							,view.getComicVector().get(index).getNAME(),view.getFrame());
					vector.add((DownloadTask)t);
					view.getPool().execute(t);
					//t.start();
				}
				view.getTaskMgr().getTaskMgr_View().getList().updateUI();
			}else{
				if(view.getPage() >= token.length){
					JOptionPane.showMessageDialog(view.getFrame(), "頁數錯誤"); 
				}else{
					String str = String.format("%03d", view.getPage());   
					Thread t = new DownloadTask(ServerList[server-1]+token[view.getPage()],path+"\\"+str+".jpg",str+".jpg"
							,view.getComicVector().get(index).getNAME(),view.getFrame());
					vector.add((DownloadTask)t);
					view.getPool().execute(t);
				}
				view.getTaskMgr().getTaskMgr_View().getList().updateUI();
			}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
