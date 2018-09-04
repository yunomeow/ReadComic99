import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


public class ComicMgr_ButtonActionListener implements ActionListener{
	private ComicMgr_View view;
	public ComicMgr_ButtonActionListener(ComicMgr_View v){
		this.view = v;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == view.getLargeButton()){
			int rate = view.getNowRate();
			if(rate < 200)
				rate+=10;
			view.setNowRate(rate);
			change();
		}else if(e.getSource() == view.getSmallButton()){
			int rate = view.getNowRate();
			if(rate > 50)
				rate-=10;
			view.setNowRate(rate);
			change();
		}else if(e.getSource() == view.getOriginButton()){
			view.setNowRate(100);
			change();
		}else if(e.getSource() == view.getPrePageButton()){
			int oldRate = view.getNowRate();
			if(view.getTree().getSelectionPath()!=null){
				DefaultMutableTreeNode preNode = ((DefaultMutableTreeNode)view.getTree().getSelectionPath().getLastPathComponent()).getPreviousSibling();
				TreePath path = new TreePath(preNode.getPath());
				view.getTree().setSelectionPath(path);
			}
			view.setNowRate(oldRate);
			change();
		}else if(e.getSource() == view.getPreVolumeButton()){
			int oldRate = view.getNowRate();
			if(view.getTree().getSelectionPath()!=null){
				TreeNode parent = ((DefaultMutableTreeNode)view.getTree().getSelectionPath().getLastPathComponent()).getParent();
				if(parent != null){
					TreeNode preParent = ((DefaultMutableTreeNode)parent).getPreviousSibling();
					TreePath path = new TreePath(((DefaultMutableTreeNode)preParent).getPath());
					view.getTree().setSelectionPath(path);
					view.getTree().expandPath(path);
					if(preParent != null && ((DefaultMutableTreeNode)preParent).getFirstChild() != null){
						TreeNode child = ((DefaultMutableTreeNode)preParent).getFirstChild();
						path = new TreePath(((DefaultMutableTreeNode)child).getPath());
						view.getTree().setSelectionPath(path);
					}
				}
				view.setNowRate(oldRate);
				change();
			}
		}else if(e.getSource() == view.getNextPageButton()){
			int oldRate = view.getNowRate();
			if(view.getTree().getSelectionPath()!=null){
				DefaultMutableTreeNode nextNode = ((DefaultMutableTreeNode)view.getTree().getSelectionPath().getLastPathComponent()).getNextSibling();
				TreePath path = new TreePath(nextNode.getPath());
				view.getTree().setSelectionPath(path);
			}
			view.setNowRate(oldRate);
			change();
		}else if(e.getSource() == view.getNextVolumeButton()){
			int oldRate = view.getNowRate();
			if(view.getTree().getSelectionPath()!=null){
				TreeNode parent = ((DefaultMutableTreeNode)view.getTree().getSelectionPath().getLastPathComponent()).getParent();
				if(parent != null){
					TreeNode nextParent = ((DefaultMutableTreeNode)parent).getNextSibling();
					TreePath path = new TreePath(((DefaultMutableTreeNode)nextParent).getPath());
					view.getTree().setSelectionPath(path);
					view.getTree().expandPath(path);
					if(nextParent != null && ((DefaultMutableTreeNode)nextParent).getFirstChild() != null){
						TreeNode child = ((DefaultMutableTreeNode)nextParent).getFirstChild();
						path = new TreePath(((DefaultMutableTreeNode)child).getPath());
						view.getTree().setSelectionPath(path);
					}
				}
			}
			view.setNowRate(oldRate);
			change();
		}
	}
	private void change(){
		
		int w = view.getOriginWidth()*view.getNowRate()/100;
		int h = view.getOriginHeight()*view.getNowRate()/100;
		view.getLabel().setPreferredSize(new Dimension(w,h));
		Icon imageicon = view.getLabel().getIcon();
		Image image = ((ImageIcon)imageicon).getImage();
		Image smallImage = image.getScaledInstance(w, h,Image.SCALE_FAST);  
		ImageIcon smallIcon = new ImageIcon(smallImage);  
		view.getLabel().setIcon(smallIcon);
		view.getLabel().repaint();
	}
}
