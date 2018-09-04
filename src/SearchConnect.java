import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchConnect extends Thread{
	private String inputLine;
	private String webpage;
	private String searchString;
	private SearchComic_View view;
	public SearchConnect(String s,SearchComic_View view){
		searchString = s;
		this.view = view;
	}
	public void run(){

		try{
			//view.getSearchButton().setEnabled(false);
			view.getVector().clear();
			URL url = new URL("http://so.dmwz.net/?key=" + searchString);
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
			
			Pattern target = Pattern.compile("<a target='_blank' href='http://www.99comic.com/Comic/.*?'>.*?</a>",Pattern.CASE_INSENSITIVE);
			Matcher matcher = target.matcher(webpage);
			String tmp;
			String[] token;
			int count = 0;
			while(matcher.find()){
				if(count%3 == 1){
					tmp = matcher.group();
					token = tmp.split("(<a target='_blank' href=')|('>)|(</a>)");
					//System.out.println(token[2]);
					view.getVector().add(new ComicInfo(token[2],token[1]));
				//	view.getVector().add(token[2]);
				}
				count++;
			}
			//System.out.println("end"+view.getVector().size());
			view.getList().updateUI();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
		//	e.printStackTrace();
		}catch (NumberFormatException e){
		}finally{
			//view.getSearchButton().setEnabled(true);
		}
	}
}

