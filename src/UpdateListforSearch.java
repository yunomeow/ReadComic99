import javax.swing.JList;


public class UpdateListforSearch extends Thread{
	private JList list;
	public UpdateListforSearch(JList list){
		this.list = list;
	}
	public void run(){
		while(true){
			list.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
