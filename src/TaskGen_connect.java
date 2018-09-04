import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;


public class TaskGen_connect implements Runnable{
	private TaskGen_View view;
	private int comic_num;
	private String inputLine;
	private String webpage;
	private Vector<ComicVolume> vector;
	public TaskGen_connect(TaskGen_View tskview){
		view = tskview;
		vector = view.getComicVector();
	
	}
	public void run(){
		synchronized(vector){
			view.getAcceptButton().setEnabled(false);
			try{
				comic_num = Integer.parseInt(view.getComicNum().getText());
				vector.removeAllElements();
				view.getComboBox().removeAllItems();
				//System.out.println("removeAll");
				URL url = new URL("http://dm.99manga.com/comic/" + comic_num);
				URLConnection urlconnection = url.openConnection();
				urlconnection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
				BufferedReader in = new BufferedReader(new InputStreamReader(
						urlconnection.getInputStream(),"GBK"));
				webpage = "";
				while((inputLine = in.readLine()) != null){
					webpage += inputLine;
					System.out.print(inputLine);
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
				//for(int i=0;i<token.length;i++)
					//System.out.println(i+": " +token[i]);				
				view.setTitle(token[2]);
				System.out.println("0:"+ token[0]);
				System.out.println("1:"+token[1]);
				vector.add(new ComicVolume("All",""));
				view.getComboBox().addItem("All");
				System.out.println("add");
				while(matcher.find()){
					tmp = matcher.group();
					token = tmp.split("(<a href=)|( target=_blank>)|(</a>)");
					token[2] = token[2].replaceAll("[:\\/><*?\"]", " ");
					vector.add(new ComicVolume(token[1],token[2]));
					view.getComboBox().addItem(token[2]);
					System.out.println("add");
				}
				System.out.println("end");
				//view.getComboBox().updateUI();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(view.getFrame(), "找不到漫畫"); 
			//	e.printStackTrace();
			}catch (NumberFormatException e){
				JOptionPane.showMessageDialog(view.getFrame(), "輸入錯誤"); 
			}finally{
				view.getAcceptButton().setEnabled(true);
			}
		}
	}
}
