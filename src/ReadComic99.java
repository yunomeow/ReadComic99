import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class ReadComic99 {

	/**
	 * @param args
	 */
	public Main_View view;
	public TaskGen taskgen;
	public TaskMgr taskmgr;
	public ComicMgr comicmgr;
	public SearchComic searchcomic;
	public FavoriteComic favoritecomic;
	public ReadComic99(){
		view = new Main_View(this);
		taskmgr = new TaskMgr(view);
		taskgen = new TaskGen(view,taskmgr);
		comicmgr = new ComicMgr(view);
		searchcomic = new SearchComic(view);
		favoritecomic = new FavoriteComic(view,taskgen);
		view.getDownloadPanel().add(taskgen.getView().getJPanel());
		view.getDownloadPanel().add(taskmgr.getTaskMgr_View().getJPanel());
		view.getFrame().repaint();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ReadComic99();
	}
	public TaskMgr getTaskMgr(){
		return taskmgr;
	}
}
