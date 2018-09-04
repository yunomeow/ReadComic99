import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;


public class TaskGen_Controller implements ActionListener{
	TaskGen_View view;
	public TaskGen_Controller(TaskGen_View tskview){
		view = tskview;
	}
	 public void actionPerformed(ActionEvent e) {
		 if(e.getSource() == view.getStartButton() ){
			 Thread thread = new StartDownload(view);
			 thread.start();
		 }else if(e.getSource() == view.getAcceptButton()){
			 Thread thread = new Thread(new TaskGen_connect(view));
			 thread.start();
		 }else if(e.getSource() == view.getChoosePathButton()){
		        int returnVal = view.getFileChooser().showOpenDialog(view.getFrame());

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = view.getFileChooser().getSelectedFile();
		            view.getSavePath().setText(file.getAbsolutePath());
		            //This is where a real application would open the file.
		        } else {
		        }

		 }
	 }
}
