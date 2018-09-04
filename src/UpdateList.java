import javax.swing.JList;


public class UpdateList implements Runnable{
	private JList taskmgr_list;
	public UpdateList(JList taskmgr_list){
		this.taskmgr_list = taskmgr_list;
	}
	public void run(){
		while(true){
			taskmgr_list.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
