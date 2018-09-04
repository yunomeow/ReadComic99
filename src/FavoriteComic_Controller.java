import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FavoriteComic_Controller implements ActionListener{
	FavoriteComic_View view;
	public FavoriteComic_Controller(FavoriteComic_View view){
		this.view = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == view.getAddButton()){
			Thread t = new Thread(new FavoriteConnect(view));
			t.start();
		}else if(e.getSource() == view.getDeleteButton()){
			Thread t = new DeleteData(view.getTable(),0,view);
			t.start();
		}else if(e.getSource() == view.getClearButton()){
			Thread t = new DeleteData(view.getTable(),1,view);
			t.start();
		}else if(e.getSource() == view.getUpdateButton()){
			Thread t = new Thread(new FavoriteUpdate(view));
			t.start();
		}else if(e.getSource() == view.getDownloadButton()){
			Thread t = new Thread(new FavoriteDownload(view.getTaskGen().getView(),view));
			t.start();
		}
		
	}

}
