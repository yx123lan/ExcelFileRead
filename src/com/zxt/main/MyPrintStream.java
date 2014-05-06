package com.zxt.main;

import java.awt.TextArea;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.SwingUtilities;

public class MyPrintStream extends PrintStream {
	private TextArea text;
	public MyPrintStream(OutputStream out, TextArea text) {
		super(out);
		this.text = text;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		final String messge = new String(buf, off, len);
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				
				text.append(messge+"\n");
			}
			
		});
		super.write(buf, off, len);
	}
	
}
