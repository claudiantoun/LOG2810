import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws IOException {
		
		Graph graph = new Graph();
		graph.readFromFile("./src/lexique1.txt");
		//TODO : interface avec le traitement des inputs du user
		new Main().setVisible(true);
	}	
	
	JPanel jp = new JPanel();
	JLabel jl = new JLabel("Entrez le mot desire");
	JTextField jt = new JTextField(39);
	
	private Main(){
		super("Whoopsy doo, here comes the goo!");
		jt.addKeyListener(this);
		setSize(666, 666); 
		setResizable(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		add(jl, BorderLayout.NORTH);
		add(jt, BorderLayout.CENTER);
		jp.add(jt);
		
		jt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String input = jt.getText();
				jl.setText(input);
			}
		});
		jp.add(jl);
		add(jp); 
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		String input = jt.getText();
		jl.setText(input.substring(0)); //FIGURE OUT HOW TO TAKE OUT FIRST CHAR OF INPUT ( = null for some reason)
	}
}
