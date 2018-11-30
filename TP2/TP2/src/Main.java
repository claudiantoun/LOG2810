import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Main extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	
	private static Graph tree;

	public static void main(String[] args) throws IOException {
		
		tree = new Graph();
		tree.readFromFile("./src/lexique1.txt");
		//TODO : interface avec le traitement des inputs du user
		new Main().setVisible(true);
	}	
	
	JPanel jp = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel jl = new JLabel("sghedfshdgfhghdfghjgfhsgfhjsg");
	JList<String> list = new JList<String>();
	JTextField jt = new JTextField(18);
	JScrollPane jsp = new JScrollPane(jp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private Main() {
		super("Recherche de mots:");
		setSize(666, 666); 
		this.setResizable(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		jp.add(jt, BorderLayout.NORTH);
		jp.add(jl, BorderLayout.SOUTH);
		jt.setAlignmentY(TOP_ALIGNMENT);
		jp.add(list);
		
		jt.addKeyListener(this);
		jt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){}
		});
		
		add(jsp); 	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		String input = jt.getText();
		//String texte = "";
		Vector<Mot> words = tree.displayWords(input);
		for (int i = 0; i < words.size(); i++)
		{	
			DefaultListModel<String> model = new DefaultListModel<String>();
			model.addElement(words.get(i).getNom());
			list.setVisible(true);
			//texte += words.get(i).getNom() + "<br>";
		}
		//jl.setText("<html>" + texte + "</html>");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}
}
