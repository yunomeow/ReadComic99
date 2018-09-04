import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


public class ComicMgr_View {
	
    public final ImageIcon ICON_LARGE =
    	new ImageIcon("large.png");
    	//new ImageIcon(getClass().getResource("/large.png"));	
    public final ImageIcon ICON_SMALL =
    	new ImageIcon("small.png");
    	//new ImageIcon(getClass().getResource("/small.png"));
    public final ImageIcon ICON_ORIGIN =
    	new ImageIcon("origin.png");
    	//new ImageIcon(getClass().getResource("/origin.png"));
    
	private Main_View view;
	private JScrollPane treeScrollPane;
	private JScrollPane labelScrollPane;
	private JPanel watchPanel;
	private JPanel upButtonPanel;
	private JPanel downButtonPanel;
	
	private JTree tree;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel model;
	
	private JLabel label;
	
	private JButton smallButton;
	private JButton originButton;
	private JButton largeButton;
	private JButton preVolumeButton;
	private JButton prePageButton;
	private JButton nextPageButton;
	private JButton nextVolumeButton;
	
	private ImageIcon chooseIcon;
	private int originWidth;
	private int originHeight;
	private int nowRate;
	
	private int startX;
	private int startY;
	
	private ComicMgr_ButtonActionListener listener;
	
	public ComicMgr_View(Main_View v){
		this.view = v;
		Container pane = view.getManagePanel();
		listener = new ComicMgr_ButtonActionListener(this);
		
		label = new ComicLabel(this);
		root = new DefaultMutableTreeNode("根目錄");
		model = new DefaultTreeModel(root);
		createNode();
		tree = new JTree(model);
		tree.addTreeExpansionListener(new TreeExpand(model));
		tree.addTreeSelectionListener(new TreeSelect(label,this));
		tree.setCellRenderer(new TreeCellRender());
		treeScrollPane = new JScrollPane(tree);
		
		
		watchPanel = new JPanel();
		watchPanel.setLayout(new BorderLayout());
		
		upButtonPanel = new JPanel();
		upButtonPanel.setLayout(new GridLayout(1,3));
		smallButton = new JButton("縮小");
		smallButton.setIcon(ICON_SMALL);
		smallButton.addActionListener(listener);
		smallButton.setEnabled(false);
		upButtonPanel.add(smallButton);
		originButton = new JButton("原始大小");
		originButton.addActionListener(listener);
		originButton.setIcon(ICON_ORIGIN);
		originButton.setEnabled(false);
		upButtonPanel.add(originButton);
		largeButton = new JButton("放大");
		largeButton.addActionListener(listener);
		largeButton.setIcon(ICON_LARGE);
		largeButton.setEnabled(false);
		upButtonPanel.add(largeButton);
		watchPanel.add(upButtonPanel,BorderLayout.NORTH);
		
		labelScrollPane = new JScrollPane(label);
		labelScrollPane.addMouseMotionListener(new   MouseMotionAdapter()   {
			public   void   mouseDragged(MouseEvent   e)   {
				int x = labelScrollPane.getViewport().getViewPosition().x+e.getX()-startX;
				int y = labelScrollPane.getViewport().getViewPosition().y-e.getY()+startY;
				int w = getOriginWidth()*getNowRate()/100;
				int h = getOriginHeight()*getNowRate()/100;
				if(x > w-labelScrollPane.getWidth())
					x = w-labelScrollPane.getWidth()+15;
				if(y > h-labelScrollPane.getHeight())
					y = h-labelScrollPane.getHeight()+15;
				if(x < 0)x = 0;
				if(y < 0)y = 0;
				labelScrollPane.getViewport().setViewPosition
				(new Point(x,y));
			}
		});

		labelScrollPane.addMouseListener(new   java.awt.event.MouseAdapter()   {
			public   void   mousePressed(MouseEvent   e)   {
				startX   =   e.getX();
				startY   =   e.getY();
			}
		}); 				
		watchPanel.add(labelScrollPane,BorderLayout.CENTER);
		

		
		
		
		
		
		
		
		downButtonPanel = new JPanel();
		downButtonPanel.setLayout(new GridLayout(1,4));
		preVolumeButton = new JButton("上一集");
		preVolumeButton.addActionListener(listener);
		downButtonPanel.add(preVolumeButton);
		prePageButton = new JButton("上一頁");
		prePageButton.addActionListener(listener);
		downButtonPanel.add(prePageButton);
		nextPageButton = new JButton("下一頁");
		nextPageButton.addActionListener(listener);
		downButtonPanel.add(nextPageButton);
		nextVolumeButton = new JButton("下一集");
		nextVolumeButton.addActionListener(listener);
		downButtonPanel.add(nextVolumeButton);
		watchPanel.add(downButtonPanel,BorderLayout.SOUTH);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                treeScrollPane, watchPanel);
		splitPane.setDividerLocation(200);
		splitPane.setOneTouchExpandable(true);

		pane.add(splitPane);
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
	}
	public void createNode(){
		File[] list = File.listRoots();
		int i;
		for(i=0 ; i<list.length ; i++){
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(list[i]);
			model.insertNodeInto(node, root, i);
			model.insertNodeInto(new DefaultMutableTreeNode(new Boolean(true)), node, 0);
		}
	}
	public ImageIcon getChooseIcon(){
		return chooseIcon;
	}
	public void setChooseIcon(ImageIcon icon){
		chooseIcon = icon;
	}
	public void setNowRate(int rate){
		nowRate = rate;
	}
	public int getNowRate(){
		return nowRate;
	}
	public void setOriginWidth(int width){
		originWidth = width;
	}
	public int getOriginWidth(){
		return originWidth;
	}
	public void setOriginHeight(int height){
		originHeight = height;
	}
	public int getOriginHeight(){
		return originHeight;
	}
	public JScrollPane getLabelScrollPane(){
		return labelScrollPane;
	}
	public JButton getSmallButton(){
		return smallButton;
	}
	public JButton getOriginButton(){
		return originButton;
	}
	public JButton getPreVolumeButton(){
		return preVolumeButton;
	}
	public JButton getPrePageButton(){
		return prePageButton;
	}
	public JButton getNextVolumeButton(){
		return nextVolumeButton;
	}
	public JButton getNextPageButton(){
		return nextPageButton;
	}
	public JButton getLargeButton(){
		return largeButton;
	}
	public JLabel getLabel(){
		return label;
	}
	public JTree getTree(){
		return tree;
	}
}
