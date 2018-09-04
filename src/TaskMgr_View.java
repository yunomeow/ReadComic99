import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



public class TaskMgr_View {
	
    public final ImageIcon ICON_TASKMGR =
    	new ImageIcon("taskmgr.png");
    	//new ImageIcon(getClass().getResource("/taskmgr.png"));	
	
	private Main_View view;
	private JPanel taskmgr_panel;
	private JLabel taskmgr_label;
	private JList taskmgr_list;
	private JScrollPane scrollpane;
	private Vector<DownloadTask> vector;
	private JButton clearAllButton;
	private JPanel upPanel;
	public TaskMgr_View(Main_View v){
		view = v;
		vector = new Vector<DownloadTask> ();
		taskmgr_panel = new JPanel();
		taskmgr_panel.setLayout(new BoxLayout(taskmgr_panel,BoxLayout.X_AXIS));
		view.getDownloadPanel().add(taskmgr_panel);
		taskmgr_label = new JLabel();
		taskmgr_label.setIcon(ICON_TASKMGR);
		taskmgr_label.setMaximumSize(new Dimension(128,128));
		clearAllButton = new JButton("清除已下載的檔案");
		clearAllButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				 int i = 0;
				 while(i < vector.size()){
					 if(vector.elementAt(i).getDownloadState() == 2)
						 vector.remove(i);
					 else
						 i++;
				 }
			 }
		});
		upPanel = new JPanel();
		upPanel.setLayout(new BoxLayout(upPanel,BoxLayout.Y_AXIS));
		upPanel.add(taskmgr_label);
		upPanel.add(clearAllButton);
		
		
		
		taskmgr_list = new JList(vector);
		scrollpane = new JScrollPane(taskmgr_list);
		taskmgr_list.setCellRenderer(new JListRenderer());
		
		taskmgr_panel.add(upPanel);
		taskmgr_panel.add(scrollpane);
		
		Thread t = new Thread(new UpdateList(taskmgr_list));
		t.start();
		
	}
	public Vector<DownloadTask> getVector(){
		return vector;
	}
	public JList getList(){
		return taskmgr_list;
	}
	public JPanel getJPanel(){
		return taskmgr_panel;
	}
}
