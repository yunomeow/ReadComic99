import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ListCellRenderer;

public class JlistRenderforSearch extends JPanel implements ListCellRenderer {
	private JLabel label;
	public JlistRenderforSearch() {
		//setHorizontalAlignment(CENTER);
		//setVerticalAlignment(CENTER);
		label = new JLabel();
		//label.setPreferredSize(new Dimension(200,20));
		//label.setHorizontalAlignment(JLabel.CENTER);
		//label.setBorder(BorderFactory.createLineBorder(Color.green));


		add(label);
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.black));

	}
	public Component getListCellRendererComponent(
			JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		ComicInfo task = (ComicInfo)value;
		label.setText(task.getTitle());
		return this;
	}
}




