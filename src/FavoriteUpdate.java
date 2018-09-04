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


public class FavoriteUpdate  implements Runnable{
	private FavoriteComic_View view;
	private int comic_num;
	private String inputLine;
	private String webpage;
	private String volume;
	private String val;
	private DefaultTableModel model ;
	public FavoriteUpdate(FavoriteComic_View view){
		this.view = view;
		model =  (DefaultTableModel)view.getTable().getModel();
	}
	public void run(){
		try{
			disableAll();
			JOptionPane.showMessageDialog(view.getFrame(), "更新需要較長時間，請耐心等候");
			int k,num = model.getRowCount();
			for(k = 0;k < num; k++){
				view.getStateLabel().setText("更新" + (String)model.getValueAt(k, 0));
				comic_num = Integer.parseInt((String)model.getValueAt(k, 1));
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
				Matcher matcher = target.matcher(webpage);
				String tmp;
				String[] token;

				if(matcher.find()){
					tmp = matcher.group();
					token = tmp.split("(<a href=)|( target=_blank>)|(</a>)");
					volume = token[2];
				}
				if(volume.equals((String)model.getValueAt(k, 2))== false){
					model.setValueAt(volume, k, 2);
					model.setValueAt("有更新", k, 3);
				}

			}
			view.getFileSaver().saveFile();
			JOptionPane.showMessageDialog(view.getFrame(), "更新完成");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(view.getFrame(), "更新失敗");
			//	e.printStackTrace();
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



