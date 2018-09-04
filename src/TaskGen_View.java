import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class TaskGen_View {
	
    public  final ImageIcon ICON_STARTDOWNLOAD =
    	new ImageIcon("startdownload.png");
    	//new ImageIcon(getClass().getResource("/startdownload.png"));
    
	private Main_View view;
	private JButton start_button;
	private JButton accept_button;
	private JButton choosepath_button;
	private JPanel taskGen_panel;
	private JPanel firstline_panel;
	private JPanel secondline_panel;
	private JLabel comicnum_label;
	private JLabel savepath_label;
	private JLabel page_label;
	private JLabel hint_label;
	private JTextField comicnum_text;
	private JTextField savepath_text;
	private JComboBox combobox;
	private JFileChooser filechooser;
	private JSpinner spinner;
	private Vector<ComicVolume> comic_vector;
	private TaskGen_Controller controller;
	private String title;
	private TaskMgr taskmgr;
	private LinkedBlockingQueue<Runnable> taskQueue;
	private ThreadPoolExecutor pool;
	public TaskGen_View(Main_View v,TaskMgr taskmgr){
		this.taskmgr = taskmgr;
		controller = new TaskGen_Controller(this);
		view = v;
		comic_vector = new Vector<ComicVolume>();
		combobox = new JComboBox();
		taskGen_panel = new JPanel();
		taskGen_panel.setLayout(new GridLayout(4,1));
		taskGen_panel.setMaximumSize(new Dimension(2000,200));
		taskGen_panel.setPreferredSize(new Dimension(2000,200));
		taskGen_panel.setMinimumSize(new Dimension(500,200));
		firstline_panel = new JPanel();
		firstline_panel.setLayout(new BoxLayout(firstline_panel,BoxLayout.X_AXIS));
		createFirstLine(firstline_panel);
		
		secondline_panel = new JPanel();
		secondline_panel.setLayout(new BoxLayout(secondline_panel,BoxLayout.LINE_AXIS));
		createSecondLine(secondline_panel);
		
		
		start_button = new JButton();
		start_button.setIcon(ICON_STARTDOWNLOAD);
		//start_button.setFont(new Font("",Font.PLAIN,36));
		start_button.addActionListener(controller);

		taskGen_panel.add(firstline_panel);
		taskGen_panel.add(secondline_panel);		
		taskGen_panel.add(combobox);
		taskGen_panel.add(start_button);

		//pane.add(taskGen_panel);
		filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		
		taskQueue = new LinkedBlockingQueue<Runnable>();
		pool = new ThreadPoolExecutor(5,2000,10,TimeUnit.SECONDS,taskQueue);
	}
	
	
	private void createFirstLine(Container pane){
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		comicnum_label = new JLabel("漫畫編號");
		comicnum_label.setMaximumSize(new Dimension(100,50));
		comicnum_label.setPreferredSize(new Dimension(100,50));
		comicnum_label.setBorder(raisedbevel);
		page_label = new JLabel("PAGE");
		hint_label = new JLabel("設為0代表下載整話");
		page_label.setMaximumSize(new Dimension(100,50));
		page_label.setPreferredSize(new Dimension(100,50));
		page_label.setBorder(raisedbevel);

		accept_button = new JButton("確定");
		accept_button.addActionListener(controller);
		
		comicnum_text = new JTextField();
		comicnum_text.setMaximumSize(new Dimension(100,50));
		comicnum_text.setMinimumSize(new Dimension(100,50));
		comicnum_text.setPreferredSize(new Dimension(100,50));
		comicnum_text.setFont(new Font("",Font.PLAIN,26));
		
		spinner = new JSpinner();
		spinner.setFont(new Font("",Font.PLAIN,28));
		spinner.setMaximumSize(new Dimension(100,50));
		spinner.setModel(new SpinnerNumberModel(0,0,1000,1));
		
		pane.add(comicnum_label);
		pane.add(comicnum_text);
		pane.add(accept_button);
		pane.add(page_label);
		pane.add(spinner);
		pane.add(hint_label);
		pane.add(new JSeparator(SwingConstants.VERTICAL));
	}
	
	
	private void createSecondLine(Container pane){
		savepath_label = new JLabel("儲存路徑");
		choosepath_button = new JButton("選擇路徑");
		choosepath_button.addActionListener(controller);
		savepath_text = new JTextField();
		savepath_text.setFont(new Font("",Font.PLAIN,26));
		
		pane.add(savepath_label);
		pane.add(savepath_text);
		pane.add(choosepath_button);
	}
	
	public JButton getStartButton(){
		return start_button;
	}
	public JButton getAcceptButton(){
		return accept_button;
	}
	public JTextField getComicNum(){
		return comicnum_text;
	}
	public Vector<ComicVolume> getComicVector(){
		return comic_vector;
	}
	public JComboBox getComboBox(){
		return combobox;
	}
	public JFrame getFrame(){
		return view.getFrame();
	}
	public JButton getChoosePathButton(){
		return choosepath_button;
	}
	public JFileChooser getFileChooser(){
		return filechooser;
	}
	public JTextField getSavePath(){
		return savepath_text;
	}
	public void setTitle(String TITLE){
		title = TITLE;
	}
	public String getTitle(){
		return title;
	}
	public int getPage(){
		return ((Integer)spinner.getValue()).intValue();
	}
	public TaskMgr getTaskMgr(){
		return taskmgr;
	}
	public JPanel getJPanel(){
		return taskGen_panel;
	}
	public LinkedBlockingQueue<Runnable> getTaskQueue(){
		return taskQueue;
	}
	public ThreadPoolExecutor getPool(){
		return pool;
	}
}
