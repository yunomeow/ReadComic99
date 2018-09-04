import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Main_View {
	
    public  final ImageIcon ICON_DOWNLOADCOMIC =
    	new ImageIcon("downloadcomic.png");
    	//new ImageIcon(getClass().getResource("/downloadcomic.png"));
    public  final ImageIcon ICON_WATCHCOMIC =
    	new ImageIcon("watchcomic.png");
    	//new ImageIcon(getClass().getResource("/watchcomic.png"));
    public  final ImageIcon ICON_SEARCHCOMIC =
    	new ImageIcon("searchcomic.png");
    	//new ImageIcon(getClass().getResource("/searchcomic.png"));
    public  final ImageIcon ICON =
    	new ImageIcon("icon.png");
    public  final ImageIcon ICON_FAVORITE = 
    	new ImageIcon("favoriteicon.png");
    	//new ImageIcon(getClass().getResource("/icon.png"));
	private JFrame frame;
	private JPanel download_panel;
	private JPanel search_panel;
	private JPanel manage_panel;
	private JPanel favorite_panel;
	private JTabbedPane tabbedPane;
	public Main_View(ReadComic99 model){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,500);
		frame.setLocation(100,100);
		tabbedPane = new JTabbedPane();
		download_panel = new JPanel();
		download_panel.setLayout(new BoxLayout(download_panel,BoxLayout.Y_AXIS));
		search_panel = new JPanel();
		manage_panel = new JPanel();
		favorite_panel = new JPanel();
		tabbedPane.addTab("",ICON_DOWNLOADCOMIC, download_panel);
		tabbedPane.addTab("",ICON_SEARCHCOMIC, search_panel);
		tabbedPane.addTab("",ICON_WATCHCOMIC, manage_panel);
		tabbedPane.addTab("",ICON_FAVORITE, favorite_panel);
		tabbedPane.setFont ( new Font("",Font.PLAIN ,28) );
		frame.add(tabbedPane,BorderLayout.CENTER);
		frame.setIconImage(ICON.getImage());
		frame.setTitle("ReadComic99");
		frame.setVisible(true);
	}
	public JFrame getFrame(){
		return frame;
	}
	public JPanel getDownloadPanel(){
		return download_panel;
	}
	public JPanel getSearchPanel(){
		return search_panel;
	}
	public JPanel getManagePanel(){
		return manage_panel;
	}
	public JPanel getFavoritePanel(){
		return favorite_panel;
	}
}
