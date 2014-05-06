package com.zxt.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AppUI {

	AppUI() {
		final Frame window = new Frame();
		window.setLayout(new BorderLayout());
		window.setSize(500, 500);
		TextArea text = new TextArea("点击下面按钮开始解析文档和写入Excel\n");
		text.setEditable(false);
		text.setSize(500, 400);
		Button setFileName = new Button("rename");
		setFileName.setSize(50, 50);
		setFileName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SetFileName().setName();
			}
		});
		Panel panel = new Panel(new GridLayout(1, 2));
		panel.add(setFileName);
		
		Button count = new Button("count");
		count.setSize(50, 50);
		count.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Runtime run = Runtime.getRuntime();
				try {
					run.exec("cmd.exe /c start D:/宜通工作/批处理文件/统计.bat");
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		panel.add(count);
		
		Button button = new Button("analyze");
		button.setSize(50, 50);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainFile.start();
			}
		});
		panel.add(button);
		window.add(text, BorderLayout.CENTER);
		window.add(panel, BorderLayout.SOUTH);
		window.setVisible(true);
		window.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				window.setVisible(false);
				System.exit(0);
				super.windowClosing(e);
			}
		});
		// 重定向控制台的打印和错误信息的输出
		MyPrintStream mps = new MyPrintStream(System.out, text);
		System.setErr(mps);
		System.setOut(mps);
	}
}
