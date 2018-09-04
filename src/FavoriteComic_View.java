import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


public class FavoriteComic_View {
	private Main_View view;
	private JButton addButton;
	private JButton deleteButton;
	private JButton clearButton;
	private JButton updateButton;
	private JButton downloadButton;
	
	private JTextField addTextField;
	private JLabel stateLabel;
	
	private JPanel buttonPanel;
	
	private Object[] columnNames = {"漫畫標題","漫畫編號","最新進度","更新狀態"};
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollpane;
	private FileLoader fileloader;
	private FileSaver filesaver;
	private TaskGen taskgen;
	public FavoriteComic_View(Main_View view,TaskGen taskgen){
		this.taskgen = taskgen;
		Container pane = view.getFavoritePanel();
		FavoriteComic_Controller controller = new FavoriteComic_Controller(this);
		pane.setLayout(new BorderLayout());
		this.view = view;
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,7));
		addButton = new JButton("加入");
		addButton.addActionListener(controller);
		buttonPanel.add(addButton);
		addTextField = new JTextField();
		buttonPanel.add(addTextField);
		deleteButton = new JButton("刪除");
		deleteButton.addActionListener(controller);
		buttonPanel.add(deleteButton);
		clearButton = new JButton("清除全部");
		clearButton.addActionListener(controller);
		buttonPanel.add(clearButton);
		updateButton = new JButton("檢查更新");
		updateButton.addActionListener(controller);
		buttonPanel.add(updateButton);
		downloadButton = new JButton("下載更新");
		downloadButton.addActionListener(controller);
		buttonPanel.add(downloadButton);
		stateLabel = new JLabel();
		stateLabel.setText("無動作");
		stateLabel.setForeground(Color.MAGENTA);
		stateLabel.setHorizontalAlignment(JLabel.CENTER);
		stateLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		buttonPanel.add(stateLabel);
		
		pane.add(buttonPanel,BorderLayout.NORTH);
		
		model = new DefaultTableModel(columnNames, 0)
		{
	        public boolean isCellEditable(int row, int col){
	            return false;
	        }
		}; 
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setAutoCreateRowSorter(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollpane = new JScrollPane(table);
		pane.add(scrollpane);
		fileloader = new FileLoader(model);
		fileloader.loadData();
		filesaver = new FileSaver(model);

	}
	public JButton getAddButton(){
		return addButton;
	}
	public JButton getDeleteButton(){
		return deleteButton;
	}
	public JButton getClearButton(){
		return clearButton;
	}
	public JButton getUpdateButton(){
		return updateButton;
	}
	public JButton getDownloadButton(){
		return downloadButton;
	}
	public JTable getTable(){
		return table;
	}
	public FileSaver getFileSaver(){
		return filesaver;
	}
	public JTextField getAddLabel(){
		return addTextField;
	}
	public JFrame getFrame(){
		return view.getFrame();
	}
	public JLabel getStateLabel(){
		return stateLabel;
	}
	public TaskGen getTaskGen(){
		return taskgen;
	}
}
