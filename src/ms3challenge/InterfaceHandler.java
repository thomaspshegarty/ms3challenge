package ms3challenge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class InterfaceHandler {

	private JFrame frame;
	private JPanel panel;
	private JFileChooser jfc;
	
	public void set_gui(String intro) throws IOException{
		jfc = new JFileChooser();
		jfc.addActionListener(new FileListen());
		
		panel = new JPanel();
		panel.add(jfc);
		
		frame = new JFrame(intro);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel,BorderLayout.CENTER);
		frame.setPreferredSize(new Dimension(600,400));
		frame.pack();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	private class FileListen implements ActionListener{
		
		private boolean check_suffix(File f) {
			String fname = f.getName();
			return fname.matches("^.*\\.csv$");
		}
		
		public void actionPerformed(ActionEvent arg0) {
			MainHandler mh = new MainHandler();
			
			File file_pass = jfc.getSelectedFile();
			frame.dispose();
			
			if(file_pass == null ) {
				return;
			}
			else if(!check_suffix(file_pass))
			{
			
				InterfaceHandler ih = new InterfaceHandler();
				try {
					ih.set_gui("Please select the correct filetype.");
				} catch (IOException e) {
					System.out.println("Failed to set new Interface.");
				}
			}else {
				mh.run(file_pass);
			}
		}
	}
	
}
