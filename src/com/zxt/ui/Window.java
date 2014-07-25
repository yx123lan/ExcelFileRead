package com.zxt.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.zxt.main.MyPrintStream;

public class Window extends JFrame implements ProgressBarVisibility {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4715196073286116002L;
	
	private JProgressBar progress;

	public Window() {
		super();
		initLayout();
	}
	
	private void initLayout(){
		GridBagLayout layout = new GridBagLayout();
		setTitle("WLAN设备巡检LOG文档解析器");
		setLayout(layout);
		setSize(550, 550);
		// 设置窗口弹出来时是在屏幕中间。
		setLocationRelativeTo(null);
		JTextArea text = new JTextArea("点击按钮开始解析文档和写入Excel\n");
		text.setEditable(false);
		text.setLineWrap(true);
		text.setSize(500, 500);
		JScrollPane scroll = new JScrollPane(text);
		
		GridBagConstraints bag = new GridBagConstraints();
		BottonPanel tPanel = new BottonPanel(this);
		bag.fill = GridBagConstraints.BOTH;
		bag.gridwidth = 0;
		bag.weightx = 1;
		bag.weighty = 0;
		layout.setConstraints(tPanel, bag);
		
		bag.gridwidth = 0;
		bag.weightx = 1;
		bag.weighty = 1;
		layout.setConstraints(scroll, bag);
		
		ItemPanel bPanel = new ItemPanel(this);
		bag.gridwidth = 0;
		bag.weightx = 1;
		bag.weighty = 0;
		layout.setConstraints(bPanel, bag);
		
		progress = new JProgressBar(SwingConstants.HORIZONTAL);
		progress.setIndeterminate(true);
		progress.setVisible(false);
		bag.gridwidth = 0;
		bag.weightx = 1;
		bag.weighty = 0;
		layout.setConstraints(progress, bag);
		
		add(bPanel);
		add(scroll);
		add(tPanel);
		add(progress);
		setVisible(true);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
				super.windowClosing(e);
			}
		});
		// 重定向控制台的打印和错误信息的输出
		MyPrintStream mps = new MyPrintStream(System.out, text);
		System.setErr(mps);
		System.setOut(mps);
	}

	@Override
	public void setProgressVisible(boolean isVisible) {
		progress.setVisible(isVisible);
	}
	
}
