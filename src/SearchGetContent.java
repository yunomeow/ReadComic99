import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;


public class SearchGetContent extends Thread{
	private String title;
	private String comicUrl;
	private String inputLine;
	private String webpage;
	private SearchComic_View view;
	public SearchGetContent(String title,String comicUrl,SearchComic_View view){
		this.title = title;
		this.comicUrl = comicUrl;
		this.view = view;
	}
	public void run(){
		try{
			String title = ((ComicInfo)view.getList().getSelectedValue()).getTitle();
			
			view.getGetInfoButton().setEnabled(false);
			URL url = new URL(comicUrl);
			URLConnection urlconnection = url.openConnection();
			urlconnection.setRequestProperty("User-Agent",
			"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlconnection.getInputStream(),"GBK"));
			webpage = "";
			while((inputLine = in.readLine()) != null){
				//System.out.println(inputLine);
				webpage += inputLine;
			}
			
//			<li><b>状态：</b><font color=#C60A00>连载</font></li>

			Pattern target = Pattern.compile("<li><b>.*?</li>",Pattern.CASE_INSENSITIVE);
			Pattern target2 = Pattern.compile("<div class=\"cCtt\">.*?<div style=\"clear: left;\">",Pattern.CASE_INSENSITIVE);
			Pattern target3 = Pattern.compile("<div id=\"column1\">.*?</div>",Pattern.CASE_INSENSITIVE);

			Matcher matcher = target.matcher(webpage);
			Matcher matcher2 = target2.matcher(webpage);
			Matcher matcher3 = target3.matcher(webpage);
			String[] token;
			token = comicUrl.split("/");
			String comicnum =token[token.length-1].substring(2);
			String tmp,line = "標題："+title+"\n"+"編號："+comicnum;
			
			
			while(matcher.find()){
				tmp = matcher.group();
		        String tagPattern = "<.*?>";
		        line = line + "\n" + tmp.replaceAll(tagPattern, "");
			}
			view.getInfoTextArea().setText(line);
			while(matcher2.find()){
				tmp = matcher2.group();
		        String tagPattern = "(<a href.*?</a>)|(<A href.*?</A>)|(<.*?>)";
		        String str =tmp.replaceAll(tagPattern, "");
		        int i,len = str.length();
		        for(i=0;i<len;i++){
		        	if(str.charAt(i) != ' ')
		        		break;
		        }
		        str = str.substring(i);
				view.getTextArea().setText(str);
			}
			while(matcher3.find()){
				tmp = matcher3.group();
		        token = tmp.split("(<div id=\"column1\"><img src=\")|(\")");
		        ImageIcon icon = null;
		        NetIcon iconThread = new NetIcon("http://www.99comic.com"+token[1]);
		        view.getPicLabel().setText("讀取中、請稍候");
		        view.getPicLabel().setIcon(null);
		        iconThread.start();
		        while(icon == null){
		        	icon = iconThread.getIcon();
		        	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        view.getPicLabel().setIcon(icon);
		        view.getPicLabel().setText("");
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			//	e.printStackTrace();
		}catch (NumberFormatException e){
		}finally{
			view.getGetInfoButton().setEnabled(true);
		}
	}

}
