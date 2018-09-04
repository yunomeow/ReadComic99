import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;


public class TreeSelect implements TreeSelectionListener{
	private JLabel label;
	private ImageIcon icon;
	private ComicMgr_View view;
	public TreeSelect(JLabel label,ComicMgr_View view){
		super();
		this.label=label;
		this.view = view;
	}
	 public void valueChanged(TreeSelectionEvent event) {
		 DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)event.getPath().getLastPathComponent();
		 if(selectNode.isRoot() )return ;
		 String path;
		 path = ((File)selectNode.getUserObject()).getAbsolutePath();
		 if(path.endsWith(".jpg")){
			 view.getLargeButton().setEnabled(true);
			 view.getSmallButton().setEnabled(true);
			 view.getOriginButton().setEnabled(true);
		 }else{
			 view.getLargeButton().setEnabled(false);
			 view.getSmallButton().setEnabled(false);
			 view.getOriginButton().setEnabled(false);
		 }
		 icon = new ImageIcon(path);
		 label.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
		 view.setNowRate(100);
		 view.setOriginWidth(icon.getIconWidth());
		 view.setOriginHeight(icon.getIconHeight());
		 view.setChooseIcon(icon);
		 view.getLabelScrollPane().getViewport().setViewPosition(new Point(0,0));
		 label.setIcon(icon);
		 if(view.getTree().getSelectionPath() != null)
			 checkButton();
	 }
	 public void checkButton(){
		 JTree tree = view.getTree();
		 DefaultMutableTreeNode prePage = ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getPreviousSibling();
		 if(prePage == null)
			 view.getPrePageButton().setEnabled(false);
		 else
			 view.getPrePageButton().setEnabled(true);
		 DefaultMutableTreeNode preParent = ((DefaultMutableTreeNode)((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getParent()).getPreviousSibling();
		 if(preParent == null)
			 view.getPreVolumeButton().setEnabled(false);
		 else
			 view.getPreVolumeButton().setEnabled(true);
		 
		 
		 DefaultMutableTreeNode nextPage = ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getNextSibling();
		 if(nextPage == null)
			 view.getNextPageButton().setEnabled(false);
		 else
			 view.getNextPageButton().setEnabled(true);
		 DefaultMutableTreeNode nextParent = ((DefaultMutableTreeNode)((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getParent()).getNextSibling();
		 if(nextParent == null)
			 view.getNextVolumeButton().setEnabled(false);
		 else
			 view.getNextVolumeButton().setEnabled(true);		 
	 }
}
