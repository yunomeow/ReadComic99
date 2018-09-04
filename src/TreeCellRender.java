import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;


public class TreeCellRender	extends JLabel implements TreeCellRenderer{
    protected Color m_textSelectionColor;
    protected Color m_textNonSelectionColor;
    protected Color m_bkSelectionColor;
    protected Color m_bkNonSelectionColor;
    protected Color m_borderSelectionColor;
   
    protected boolean m_selected;
 // new ImageIcon(getClass().getResource("/computer.png"));
    public  final ImageIcon ICON_COMPUTER =
    	new ImageIcon("computer.png");
    	//new ImageIcon(getClass().getResource("/computer.png"));
    public final ImageIcon ICON_DISK =
    	new ImageIcon("disk.png");
    	//new ImageIcon(getClass().getResource("/disk.png"));
    public  final ImageIcon ICON_FOLDER =
    	new ImageIcon("folder.png");
    	//new ImageIcon(getClass().getResource("/folder.png"));
    public  final ImageIcon ICON_EXPANDEDFOLDER =
    	new ImageIcon("folderopen.png");
    	//new ImageIcon(getClass().getResource("/folderopen.png"));
    public  final ImageIcon ICON_FILE =
    	new ImageIcon("file.png");
    	//new ImageIcon(getClass().getResource("/file.png"));
	public TreeCellRender()
    {
		super();
        m_textSelectionColor = UIManager.getColor(
            "Tree.selectionForeground");
        m_textNonSelectionColor = UIManager.getColor(
            "Tree.textForeground");
        m_bkSelectionColor = UIManager.getColor(
            "Tree.selectionBackground");
        m_bkNonSelectionColor = UIManager.getColor(
            "Tree.textBackground");
        m_borderSelectionColor = UIManager.getColor(
            "Tree.selectionBorderColor");
        setOpaque(false);
    }

    public Component getTreeCellRendererComponent(JTree tree,
        Object value, boolean sel, boolean expanded, boolean leaf,
        int row, boolean hasFocus)

    {
        
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)value;
            Object obj = node.getUserObject();
            if(obj.toString() == "根目錄"){
            	setIcon(ICON_COMPUTER);
            	setText(obj.toString());
            }
            else{
            	if(obj instanceof File){
            		File f = (File)obj;
            		String s = f.getAbsolutePath();
            		if(!s.endsWith(":\\")){
            			s = f.getName();
            			if(f.isDirectory())
            				setIcon(ICON_FOLDER);
            			else
            				setIcon(ICON_FILE);

            		}else{
            			setIcon(ICON_DISK);
            		}
            		setText(s);

            	}
            }

            

            setFont(tree.getFont());
            setForeground(sel ? m_textSelectionColor :
                m_textNonSelectionColor);
            setBackground(sel ? m_bkSelectionColor :
                m_bkNonSelectionColor);
            m_selected = sel;
            return this;
        }
    public void paintComponent(Graphics g)
    {
        Color bColor = getBackground();
        Icon icon = getIcon();

        g.setColor(bColor);
        int offset = 0;
        if(icon != null && getText() != null)
            offset = (icon.getIconWidth() + getIconTextGap());
        g.fillRect(offset, 0, getWidth() - 1 - offset,
            getHeight() - 1);

        if (m_selected)
        {
            g.setColor(m_borderSelectionColor);
            g.drawRect(offset, 0, getWidth()-1-offset, getHeight()-1);
        }

        super.paintComponent(g);
    }
}
