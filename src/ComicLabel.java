import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class ComicLabel extends JLabel{
	 private ComicMgr_View view;
	 public ComicLabel(ComicMgr_View view){
		 this.view = view;
	 }
	 public void paint(Graphics g){  
		 if(view.getChooseIcon() != null){
			 Icon ic = view.getChooseIcon();
			 Image img = ((ImageIcon)ic).getImage();
			 int w = view.getOriginWidth()*view.getNowRate()/100;
			 int h = view.getOriginHeight()*view.getNowRate()/100;
			 g.drawImage(img,0,0,w,h,this);
		 }else{
			// System.out.println("No image");
		 }
			 
	}
}
