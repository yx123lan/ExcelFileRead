package com.zxt.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppUI {
	public static TextArea text;
	AppUI() {
		final Frame window = new Frame();
		window.setLayout(new BorderLayout());
		window.setSize(500, 500);
		text = new TextArea("点击下面按钮开始解析文档和写入Excel\n");
		text.setSize(500, 450);
		Button button = new Button("执行");
		button.setSize(50, 50);
		button.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				MainFile.start();
			}
		});
		window.add(text, BorderLayout.CENTER);
		window.add(button, BorderLayout.SOUTH);
		window.setVisible(true);
		window.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				window.setVisible(false);
				System.exit(0);
				super.windowClosing(e);
			}
		});
	}
	public static void addText(String str){
		text.append(str+"\n");
	}
}
