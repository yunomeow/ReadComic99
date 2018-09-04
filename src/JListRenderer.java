import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;


public class JListRenderer extends JPanel implements ListCellRenderer {
	private JProgressBar progressbar;
	private JLabel label;
	private JLabel state_label;
	private JLabel speed_label;
	private NumberFormat formatter = new DecimalFormat("0.00"); 
	public JListRenderer() {
		//setHorizontalAlignment(CENTER);
		//setVerticalAlignment(CENTER);
		label = new JLabel();
		label.setPreferredSize(new Dimension(200,35));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.orange));
		
		state_label = new JLabel();
		state_label.setPreferredSize(new Dimension(100,35));
		state_label.setHorizontalAlignment(JLabel.CENTER);
		state_label.setBorder(BorderFactory.createLineBorder(Color.blue));

		speed_label = new JLabel();
		speed_label.setPreferredSize(new Dimension(100,35));
		speed_label.setHorizontalAlignment(JLabel.CENTER);
		speed_label.setBorder(BorderFactory.createLineBorder(Color.green));
		add(label);
		add(state_label);
		add(speed_label);
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setMaximumSize(new Dimension(100,35));
		add(separator);
		progressbar = new JProgressBar();
		progressbar.setStringPainted(true);
		//progressbar.setMaximumSize(new Dimension(200,100));
		add(progressbar);
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.black));

	}
	public Component getListCellRendererComponent(
			JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		long percent;
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		DownloadTask task = (DownloadTask)value;
		long lsize = task.getNowSize(),speed;
		percent = (lsize*100)/(task).getFileSize();
		speed = lsize - task.getLastSize();
		task.setLastSize(lsize);
		progressbar.setValue((int)percent);
		label.setText(task.getFolderName()+"\\"+task.getFileName());
		if(task.getDownloadState() == 0){
			state_label.setText("等待中");
		}else if(task.getDownloadState() == 1)
			state_label.setText("下載中");
		else if(task.getDownloadState() == 2)
			state_label.setText("已完成");
		String s = formatter.format((double)speed/1000.0);
		speed_label.setText(s+"KB/s");
		return this;
	}
}


