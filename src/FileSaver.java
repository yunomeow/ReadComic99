import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.table.DefaultTableModel;


public class FileSaver {
	private DefaultTableModel model;
	public FileSaver(DefaultTableModel model){
		this.model = model;
	}
	public void saveFile(){
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("favorite.txt"),"UTF-8");
			int row = model.getRowCount();
			Object obj;
			String tmp;
			int i,j;
			for(i=0;i<row;i++){
				tmp = "";
				for(j=0;j<4;j++){
					obj = model.getValueAt(i,j);
					if(j != 3){
						tmp = tmp + (String)obj + " ";
					}else
						tmp = tmp + (String)obj + "\n";
				}
				out.write(tmp);
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
