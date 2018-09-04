import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.table.DefaultTableModel;


public class FileLoader {
	private DefaultTableModel model;
	public FileLoader(DefaultTableModel model){
		this.model = model;
	}
	public void loadData(){
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("favorite.txt"), "UTF-8")); 
			String tmp = "";
			String[] token;
				while((tmp = in.readLine()) != null){
					//System.out.println(tmp);
					token = tmp.split(" ");
					model.addRow(token);
				}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
