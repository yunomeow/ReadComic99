import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SearchComic_View {
	private Main_View view;
	private final SearchComic_View theview;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel picPanel;
	private JPanel textPanel;
	private JLabel picLabel;
	private JTextArea textarea;
	private JTextArea infoTextArea;
	private JPanel controlPanel;
	private JPanel searchPanel;
	private JButton searchButton;
	private JButton getInfoButton;
	private JTextField text;
	
	private JList list;
	private JScrollPane scrollPane;
	private JScrollPane textScrollPane;
	private Vector<ComicInfo> vector;
	private TransText trans;
	public SearchComic_View(Main_View view){
		this.view = view;
		theview = this;
		trans = new TransText();
		text = new JTextField();
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Thread t = new Thread(){
					public void run(){
						String str = trans.traditionalized(text.getText());
						String searchString="";
						try {
							searchString = URLEncoder.encode(str,"GB2312");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Thread connect = new SearchConnect(searchString,theview);
						connect.start();
					}
				};
				t.start();
			}
			
		});
		
		
		getInfoButton = new JButton("查看漫畫");
		getInfoButton.setEnabled(false);
		getInfoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ComicInfo  comic = (ComicInfo)list.getSelectedValue();
				Thread getContent = new SearchGetContent(comic.getTitle(),comic.getURL(),theview);
				getContent.start();
			}
			
		});
		

		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2,1));
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.X_AXIS));
		searchPanel.add(searchButton);
		searchPanel.add(text);
		controlPanel.add(searchPanel);
		controlPanel.add(getInfoButton);
		vector = new Vector<ComicInfo>();
		list = new JList(vector);
		scrollPane = new JScrollPane(list);
		list.setCellRenderer(new JlistRenderforSearch());
		
		Thread update = new Thread(){
			public void run(){
				while(true){
					if(list.getSelectedIndex() == -1){
						getInfoButton.setEnabled(false);
					}else{
						getInfoButton.setEnabled(true);
					}
					try{
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		};
		update.start();		
		
		//Thread up = new UpdateListforSearch(list);
		//up.start();
		
		leftPanel.add(controlPanel,BorderLayout.NORTH);
		leftPanel.add(scrollPane,BorderLayout.CENTER);
		leftPanel.setMaximumSize(new Dimension(200,500));
		leftPanel.setPreferredSize(new Dimension(200,500));
		leftPanel.setMinimumSize(new Dimension(200,500));
		
		rightPanel = new JPanel();
		//rightPanel.setPreferredSize(new Dimension(1200,500));
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		rightPanel.setLayout(new GridLayout(2,1));
		picPanel = new JPanel();
		picPanel.setLayout(new GridLayout(1,2));
		picLabel = new JLabel();
		picLabel.setVerticalAlignment(JLabel.CENTER);
		picLabel.setHorizontalAlignment(JLabel.CENTER);
		picLabel.setPreferredSize(new Dimension(100,200));
		infoTextArea = new JTextArea();
		infoTextArea.setEditable(false);
		infoTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		infoTextArea.setLineWrap(true);
		infoTextArea.setPreferredSize(new Dimension(100,200));
		picPanel.add(picLabel);
		picPanel.add(infoTextArea);
		rightPanel.add(picPanel);
		textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.X_AXIS));
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setEditable(false);
		textarea.setPreferredSize(new Dimension(500,200));
		textarea.setBorder(BorderFactory.createLineBorder(Color.black));
		textScrollPane = new JScrollPane(textarea);
		textPanel.add(textScrollPane);
		rightPanel.add(textarea);
		
		view.getSearchPanel().setLayout(new BoxLayout(view.getSearchPanel(),BoxLayout.X_AXIS));
		view.getSearchPanel().add(leftPanel);
		view.getSearchPanel().add(rightPanel);
	}
	public JButton getGetInfoButton(){
		return getInfoButton;
	}
	public JButton getSearchButton(){
		return searchButton;
	}
	public JList getList(){
		return list;
	}
	public Vector<ComicInfo> getVector(){
		return vector;
	}
	public JTextArea getTextArea(){
		return textarea;
	}
	public JLabel getPicLabel(){
		return picLabel;
	}
	public JTextArea getInfoTextArea(){
		return infoTextArea;
	}
}
