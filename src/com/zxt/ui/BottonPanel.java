package com.zxt.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.zxt.main.AnalyzeFile;
import com.zxt.table.TimeBucket;

public class BottonPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 26419816723180658L;
	
	private static final String ERROR = "错误";
	private static final String ERROR_MESSAGE = "请把各AC节点用户上线数、各AC节点AP上线总数统计文档放到D:\\Excel目录下";
	
	public static final String USER_DATA = "用户上线数";
	public static final String AP_DATA = "AP上线总数";

	private ProgressBarVisibility progress;
	// 默认是早上的文档
	private TimeBucket timeBucket;

	private JTextField year;
	private JTextField month;
	private JTextField day;

	public BottonPanel(ProgressBarVisibility progress) {
		super();
		this.progress = progress;
		initLayout();
	}

	private void initLayout() {
		Calendar time = Calendar.getInstance();
		int yearTime = time.get(Calendar.YEAR);
		int monthTime = time.get(Calendar.MONTH) + 1;
		int dayTime = time.get(Calendar.DAY_OF_MONTH);
		// 判断是上午还是下午。
		initTimeBucket(time.get(Calendar.HOUR_OF_DAY));

		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		setLayout(layout);

		year = new JTextField("" + yearTime);
		year.setPreferredSize(new Dimension(80, 30));
		add(year);

		JTextArea yearTitle = new JTextArea("年");
		yearTitle.setEditable(false);
		add(yearTitle);

		month = new JTextField("" + monthTime);
		month.setPreferredSize(new Dimension(80, 30));
		add(month);

		JTextArea monthTitle = new JTextArea("月");
		monthTitle.setEditable(false);
		add(monthTitle);

		day = new JTextField("" + dayTime);
		day.setPreferredSize(new Dimension(80, 30));
		add(day);

		JTextArea dayTitle = new JTextArea("日");
		dayTitle.setEditable(false);
		add(dayTitle);

		JComboBox<TimeBucket> timeBucketBox = new JComboBox<TimeBucket>();
		timeBucketBox.setPreferredSize(new Dimension(80, 30));
		timeBucketBox.addItem(TimeBucket.AM);
		timeBucketBox.addItem(TimeBucket.PM);
		timeBucketBox.setSelectedItem(timeBucket);
		timeBucketBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				timeBucket = (TimeBucket) e.getItem();
			}
		});
		add(timeBucketBox);

		JButton button = new JButton("写入Excel");
		button.setPreferredSize(new Dimension(120, 30));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new WriteExcel().start();
			}
		});

		add(button);
	}
	
	private void initTimeBucket(int time){
		System.out.println("time: "+time);
		if(time < 12)
			timeBucket = TimeBucket.AM;
		else
			timeBucket = TimeBucket.PM;
	}

	private String getFileName(String name) {
		String fileName = null;
		File files = new File("D:/Excel");
		if(!files.exists()){
			files.mkdir();
			return null;
		}
		for (String str : files.list()) {
			if(str.contains(name))
				fileName = files.getPath()+"/"+str;
		}
		return fileName;
	}

	class WriteExcel extends Thread {
		@Override
		public void run() {
			progress.setProgressVisible(true);
			String user = getFileName(USER_DATA);
			String ap   = getFileName(AP_DATA);
			if(user == null || ap == null){
				JOptionPane.showMessageDialog(
						null,
						ERROR_MESSAGE,
						ERROR,
						JOptionPane.ERROR_MESSAGE);
			}else{
				new AnalyzeFile().drawTable(
						user,
						ap,
						year.getText() + "/" + month.getText() + "/" + day.getText(),
						timeBucket);
			}
			// 打开本次写入Excel文档的提醒日志。
			Runtime run = Runtime.getRuntime();
			try {
				run.exec("cmd.exe /c start D:/Excel/log.txt");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			progress.setProgressVisible(false);
		}
	};
}
