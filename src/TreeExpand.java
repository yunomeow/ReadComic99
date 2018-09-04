import java.io.File;
import java.io.FilenameFilter;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


public class TreeExpand implements TreeExpansionListener{
	private DefaultTreeModel model;

	public TreeExpand(DefaultTreeModel model){
		super();
		this.model = model;
	}

	@Override
	public void treeCollapsed(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
	}
	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)event.getPath().getLastPathComponent();
		if(selectNode.isRoot())return ;
		String path = ((File)selectNode.getUserObject()).getPath();
		File dir = new File(path);
		File[] list = dir.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				String path = dir.getAbsolutePath();
				if(path.endsWith("\\")==false)
					path+="\\";
				File file = new File(path+name);
				if(name.endsWith(".jpg")||file.isDirectory())
					return true;
				return false;
			}
		}
		);
		int i;
		selectNode.removeAllChildren();
		if(list != null){
			for(i=0 ; i<list.length ; i++){
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(list[i]);
					selectNode.add(node);
					if(list[i].isDirectory())
						node.add(new DefaultMutableTreeNode(new Boolean(true)));
			}
		}
		model.reload(selectNode);
	}
}
