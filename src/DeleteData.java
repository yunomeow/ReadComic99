import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class DeleteData extends Thread{
	private DefaultTableModel model;
	private JTable table;
	private int type;
	private FavoriteComic_View view;
	public DeleteData(JTable table,int type,FavoriteComic_View view){
		this.table = table;
		model = (DefaultTableModel)table.getModel();
		this.type = type;
		this.view = view;
	}
	public void run(){
		disableAll();
		if(type == 0){
			if(table.getSelectedRow() != -1)
				model.removeRow(table.getSelectedRow());
			//System.out.println(model.getRowCount());
		}else if(type == 1){
			while(model.getRowCount() > 0){
				//System.out.println(model.getRowCount());
				model.removeRow(model.getRowCount()-1);
			}
		}
		view.getFileSaver().saveFile();
		enableAll();
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
