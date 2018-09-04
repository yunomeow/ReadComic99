import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class FavoriteDownload extends Thread{
	private TaskGen_View view;
	private FavoriteComic_View favoriteView;
	private String[] ServerList;
	private Vector<DownloadTask> vector ;
	private String title;
	private DefaultTableModel model ;
	private String folder;
	public FavoriteDownload(TaskGen_View view,FavoriteComic_View favoriteView){
		vector = view.getTaskMgr().getTaskMgr_View().getVector();
		this.view = view;
		this.favoriteView = favoriteView;
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

		model = (DefaultTableModel)favoriteView.getTable().getModel(); 
	}
	public void run(){

		int k,row;

		row = model.getRowCount();
		disableAll();
		favoriteView.getStateLabel().setText("下載生成中");
		for(k=0;k<row;k++){
			if("無更新".equals((String)model.getValueAt(k, 3))==true)
				continue;
			title = (String)model.getValueAt(k,0);
			String path;
			if(view.getSavePath().getText() != ""){
				path = view.getSavePath().getText()+"\\"+title+"\\"+(String)model.getValueAt(k,2);
			}else{
				path = "\\"+title+"\\"+(String)model.getValueAt(k,2);
			}
			folder = title+"\\"+(String)model.getValueAt(k,2);
			//System.out.println(path);
			File file = new File(path);
			file.mkdirs();
			if(file.exists() == false){
				JOptionPane.showMessageDialog(view.getFrame(), "儲存路徑錯誤"); 
				return ;
			}
			String str = Connect(k);
			//System.out.println(str);
			downloadOneVolume("http://dm.99manga.com"+str,path);
			model.setValueAt("無更新",k,3);
		}
			favoriteView.getFileSaver().saveFile();
			enableAll();
			favoriteView.getStateLabel().setText("無動作");
	}
	public void downloadOneVolume(String url,String path){
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
			for(i=1;i<token.length-1;i++){
				String str = String.format("%03d", i);   
				Thread t = new DownloadTask(ServerList[server-1]+token[i],path+"\\"+str+".jpg",str+".jpg"
						,folder,view.getFrame());
				vector.add((DownloadTask)t);
				view.getPool().execute(t);
				//t.start();
			}
			view.getTaskMgr().getTaskMgr_View().getList().updateUI();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void disableAll(){
		favoriteView.getAddButton().setEnabled(false);
		favoriteView.getDeleteButton().setEnabled(false);
		favoriteView.getClearButton().setEnabled(false);
		favoriteView.getUpdateButton().setEnabled(false);
		favoriteView.getDownloadButton().setEnabled(false);
	}
	public void enableAll(){
		favoriteView.getAddButton().setEnabled(true);
		favoriteView.getDeleteButton().setEnabled(true);
		favoriteView.getClearButton().setEnabled(true);
		favoriteView.getUpdateButton().setEnabled(true);
		favoriteView.getDownloadButton().setEnabled(true);
	}
	public String Connect(int index){
		String inputLine;
		String webpage;
		int comic_num;
		comic_num = Integer.parseInt((String)model.getValueAt(index,1));
		URL url;
		try {
			url = new URL("http://dm.99manga.com/comic/" + comic_num);

			URLConnection urlconnection = url.openConnection();
			urlconnection.setRequestProperty("User-Agent",
			"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlconnection.getInputStream(),"GBK"));
			webpage = "";
			while((inputLine = in.readLine()) != null){
				webpage += inputLine;
			}
			Pattern target = Pattern.compile("<a href=/manga/.*?>.*?</a>",Pattern.CASE_INSENSITIVE);
			Matcher matcher = target.matcher(webpage);
			String tmp;
			String[] token;
			if(matcher.find()){
				tmp = matcher.group();
				token = tmp.split("(<a href=)|( target=_blank>)|(</a>)");
				return token[1];
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}