package com.zxt.ui;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.zxt.main.SetFileName;

public class ItemPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1932517393425655135L;
	
	public static final String INFO_TEXT = "<html><body>WLAN巡检文档解析器 <br> By <br> lan4627@Gmail.com <br>"+
												"源代码地址："+
												"<a href=\"https://github.com/yx123lan/ExcelFileRead\">GitHub</a>" +
											"</body></html>";
	private ProgressBarVisibility progress;
	
	public ItemPanel(ProgressBarVisibility progress){
		this.progress = progress;
		initLayout();
	}
	
	private void initLayout(){
		setLayout(new GridLayout(1, 3));
		
		JButton info = new JButton("关于本软件");
		info.setSize(50, 50);
		info.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JDialog dialog = new JDialog();
				// 设置窗口弹出来时是在屏幕中间。
				dialog.setLocationRelativeTo(null);
				dialog.setSize(200, 200);
				JEditorPane infoText = new JEditorPane("text/html", INFO_TEXT);
				infoText.setEditable(false);
				infoText.addHyperlinkListener(hlLsnr);
				dialog.add(infoText);
				dialog.setVisible(true);
			}
		});
		
		add(info);
		
		JButton setFileName = new JButton("改文档名");
		setFileName.setSize(50, 50);
		setFileName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ReName().start();
			}
		});
		add(setFileName);
		
		JButton count = new JButton("分析文档");
		count.setSize(50, 50);
		count.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new StartBat().start();
			}
		});
		add(count);
	}
	
	private HyperlinkListener hlLsnr = new HyperlinkListener(){

		@Override
		public void hyperlinkUpdate(HyperlinkEvent e) {
			if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED)
				return;
			URL linkUrl = e.getURL();  
            if (linkUrl!=null){  
                try {  
                   Desktop.getDesktop().browse(linkUrl.toURI());  
               } catch (Exception e1) {  
                   e1.printStackTrace();  
               }  
            }
		}
	};
	
	class ReName extends Thread{

		@Override
		public void run() {
			progress.setProgressVisible(true);
			new SetFileName().setName();
			progress.setProgressVisible(false);
		}
		
	}
	
	class StartBat extends Thread{
		@Override
		public void run() {
			progress.setProgressVisible(true);
			Runtime run = Runtime.getRuntime();
			try {
				run.exec("cmd.exe /c start D:/宜通工作/批处理文件/统计.bat");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			progress.setProgressVisible(false);
		}
	}
}
