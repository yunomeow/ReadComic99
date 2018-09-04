import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class FavoriteConnect  implements Runnable{
	private FavoriteComic_View view;
	private int comic_num;
	private String inputLine;
	private String webpage;
	private String[] data;
	public FavoriteConnect(FavoriteComic_View view){
		this.view = view;
	}
	public void run(){
		try{
			disableAll();
			data = new String[4];
			data[1] = view.getAddLabel().getText();
			DefaultTableModel model =  (DefaultTableModel)view.getTable().getModel();
			int i,row = model.getRowCount();
			for(i=0;i<row;i++){
				if(data[1].equals((String)model.getValueAt(i, 1)) == true) {
					JOptionPane.showMessageDialog(view.getFrame(), "漫畫已存在");
					return;
				}
			}
			view.getStateLabel().setText("連線中");
			
			comic_num = Integer.parseInt(view.getAddLabel().getText());
			
			URL url = new URL("http://dm.99manga.com/comic/" + comic_num);
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
			Pattern target2 = Pattern.compile("content=\".*?漫画",Pattern.CASE_INSENSITIVE);
			Matcher matcher = target.matcher(webpage);
			Matcher matcher2 = target2.matcher(webpage);
			String tmp;
			String[] token;
			matcher2.find();
			tmp = matcher2.group();
			token = tmp.split("(content=\")|(漫画)");
			//System.out.println(token[1]);
			data[0] = token[2];

			if(matcher.find()){
				tmp = matcher.group();
				token = tmp.split("(<a href=)|( target=_blank>)|(</a>)");
				data[2] = token[2];
			}
			data[3] = "無更新";
			

			model.addRow(data);
			view.getFileSaver().saveFile();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(view.getFrame(), "找不到漫畫");
			//	e.printStackTrace();
		}catch (NumberFormatException e){
			JOptionPane.showMessageDialog(view.getFrame(), "輸入錯誤"); 
		}finally{
			view.getStateLabel().setText("無動作");
			enableAll();
		}
	}
	public void disableAll(){
		view.getAddButton().setEnabled(false);
		view.getDeleteButton().setEnabled(false);
		view.getClearButton().setEnabled(false);
		view.getUpdateButton().setEnabled(false);
		view.getDownloadButton().setEnabled(false);
	}
	public void enableAll(){
		view.getAddButton().setEnabled(true);
		view.getDeleteButton().setEnabled(true);
		view.getClearButton().setEnabled(true);
		view.getUpdateButton().setEnabled(true);
		view.getDownloadButton().setEnabled(true);
	}
}


