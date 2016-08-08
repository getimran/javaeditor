import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class EditorO implements ActionListener
{
	JFrame jf;
	JMenuBar jmb;
	JMenuItem newMenuItem,openMenuItem,saveMenuItem,exitMenuItem,fontColorMenuItem,compileMenuItem,runMenuItem;
	JTextArea jta1,jta2;
	JScrollPane jsp1,jsp2;
	JMenu fileMenu,editMenu,toolMenu;
	String className="";
	Runtime r;
	String fname="";
	String result="";
	String result1="";
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	int screenWidth= (int)width;
	int screenHeight= (int)height;
	
	EditorO()
	{
		EditorGui();
	}
	
	public void EditorGui(){
		jf=new JFrame("EditorO for Java");
		jf.setLayout(null);
		jf.setSize(screenWidth,screenHeight);
		jmb=new JMenuBar();
		fileMenu=new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu=new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		toolMenu=new JMenu("Tools");
		toolMenu.setMnemonic(KeyEvent.VK_T);
		
		
		newMenuItem=new JMenuItem("New");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		newMenuItem.addActionListener(this);
		fileMenu.add(newMenuItem);
		fileMenu.addSeparator();
		openMenuItem=new JMenuItem("Open file");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		openMenuItem.addActionListener(this);
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		saveMenuItem=new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(this);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		exitMenuItem=new JMenuItem("Exit");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
		exitMenuItem.addActionListener(this);
		fileMenu.add(exitMenuItem);
		fontColorMenuItem=new JMenuItem("Change font color");
		fontColorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		fontColorMenuItem.addActionListener(this);
		editMenu.add(fontColorMenuItem);
		compileMenuItem=new JMenuItem("Compile");
		compileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK));
		compileMenuItem.addActionListener(this);
		toolMenu.add(compileMenuItem);
		toolMenu.addSeparator();
		runMenuItem=new JMenuItem("Run");
		runMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
		runMenuItem.addActionListener(this);
		toolMenu.add(runMenuItem);
		
		
		jmb.add(fileMenu);
		jmb.add(editMenu);
		jmb.add(toolMenu);
		jmb.setBounds(1,1,screenWidth,20);
		jta1=new JTextArea(screenHeight,screenWidth);
		jsp1=new JScrollPane(jta1);
		jsp1.setBounds(1,20,screenWidth,450);
		jta2=new JTextArea((screenHeight-450),screenWidth);
		jta2.setBackground(Color.LIGHT_GRAY);
		jsp2=new JScrollPane(jta2);
		jsp2.setBounds(1,470,screenWidth,(screenHeight-530));
		jf.add(jsp1);
		jf.add(jsp2);
		jf.add(jmb);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		r=Runtime.getRuntime();
	}
	
	public void actionPerformed(ActionEvent e)
	{
			if(e.getActionCommand().equals("New")){
				try{
					className = JOptionPane.showInputDialog("Enter the class name for your java program:");
					if(className.equals("")){
						className = JOptionPane.showInputDialog("Seems Like you didn't entered class name for your Java Program. Please do it!");
					}
					else{
						jta1.setText("public class "+className+"\n"
								+"{"+"\n"
								+" public static void main(String[] args)"+"\n"
								+" {"+"\n"
								+"               "+"\n"
								+" }"+"\n"
								+"}");
						}
					}catch(Exception e3){}
			}
			
			else if(e.getActionCommand().equals("Open file")){
				try{
						JFileChooser fileChooser = new JFileChooser();
						if (fileChooser.showOpenDialog(openMenuItem) == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						String fName[]= file.getName().split(".java",2);
						className=fName[0];
						System.out.println(fName[0]);
						
						String fileData="";
						String s="";
						FileReader fr=new FileReader(file);
						BufferedReader br=new BufferedReader(fr);
						while(s!=null){
							s=br.readLine();
							if(s!=null)
							fileData+=s;
							fileData+="\n";
						}
						jta1.setText(fileData);
					}
					}catch(Exception e4){}
				
			}
			
			
			
			else if(e.getActionCommand().equals("Exit")){
				System.exit(0);
			}
			
			else if(e.getActionCommand().equals("Change font color")){
					Color foreground = JColorChooser.showDialog(fontColorMenuItem,
            "JColorChooser Sample",Color.BLACK);
					jta1.setForeground(foreground);
			
			}
			
			else if(e.getActionCommand().equals("Compile"))
			{
				result="";
				jta2.setText("");
				if(!className.equals(""))
				{
					try{
							fname=className.trim()+".java";
							FileWriter fw=new FileWriter(fname);
							String s1=jta1.getText();
							PrintWriter pw=new PrintWriter(fw);
							pw.println(s1);
							pw.flush();
							
							Process error=r.exec("C:\\Program Files\\Java\\jdk1.8.0_51\\bin\\javac.exe -d . "+fname);
							BufferedReader err=new BufferedReader(new InputStreamReader(error.getErrorStream()));
							
							while(true)
							{
								String temp=err.readLine();
								if(temp!=null){
									result+=temp;
									result+="\n";
								}
								else
								break;
							}
							
							if(result.equals("")){
								Color DARK_GREEN=new Color(1,81,36);
								jta2.setForeground(DARK_GREEN);
								jta2.setText(fname+" compiled Successfully!");
								err.close();
							}
							else{
								jta2.setForeground(Color.RED);
								jta2.setText(result);
								}
						}catch(Exception e1){}
				}
				else{
						className = JOptionPane.showInputDialog("Seems Like you didn't entered class name for your Java Program. Please do it!");
					}
			}
			
			else if(e.getActionCommand().equals("Run"))
			{
				result="";result1="";
				try{
						String fn=className.trim();
						Process p=r.exec("C:\\Program Files\\Java\\jdk1.8.0_51\\bin\\java.exe "+fn);
						
						BufferedReader output=new BufferedReader(new InputStreamReader(p.getInputStream()));
						BufferedReader error=new BufferedReader(new InputStreamReader(p.getErrorStream()));
						
						while(true)
						{
							String temp=output.readLine();
							if(temp!=null){
								jta2.setForeground(Color.BLACK);
								result+=temp;
								result+="\n";
							}
							else
							break;
						}
						
						while(true)
						{
							String temp=error.readLine();
							if(temp!=null){
								jta2.setForeground(Color.RED);
								result1+=temp;
								result1+="\n";
							}
							else
							break;
						}
						output.close();
						error.close();
						
						if(!result.equals("") && result1.equals(""))
						jta2.setText(result);
						if(result.equals(""))
						jta2.setText(result1);
						if(!result.equals("") && !result1.equals(""))
						jta2.setText(result+"\n"+result1);
					}catch(Exception e2){System.out.println(e2);}
			}
	}
	public static void main(String args[])
	{
		new EditorO();
	}
}