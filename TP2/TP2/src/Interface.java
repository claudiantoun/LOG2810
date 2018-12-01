import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Interface extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel label;
	private JList<String> list;
	private JTextField textField;
	private JScrollPane scrollPanel;
	private JButton button;
	private Graph graph;
	
	public Interface(Graph graph) 
	{
		this.graph = graph;
		creerInterface();
	}
	
	public void  creerInterface() {
		this.setTitle("LOG2810 - TP2");
		this.setSize(600, 240);
		this.setResizable(false); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		label = new JLabel("Entrer le mot désiré :     ");
		button = new JButton("Afficher les labels");
		list = new JList<String>();
		textField = new JTextField(23);
		scrollPanel = new JScrollPane(list);
		
		panel.add(label, BorderLayout.WEST);
		panel.add(textField, BorderLayout.NORTH);
		textField.setAlignmentY(TOP_ALIGNMENT);
		panel.add(button,  BorderLayout.SOUTH);
		panel.add(scrollPanel);
		this.add(panel);
		this.setVisible(true);
		
		
		textField.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) 
			{
				String input = textField.getText();
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					graph.addToQueue(input);
					wipeSelection();
					return;
				}
				if(graph.displayWords(input) == null) 
				{
					wipeList();
					return;
				}				
				Vector<Mot> words = graph.displayWords(input);
				updateList(words);
			}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public void updateList(Vector<Mot> words) 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (int i = 0; i < words.size(); i++)
		{	
			String nom = words.get(i).getNom(); 
			int utilisation = words.get(i).getNbTimeUsed(); 
			int recent = words.get(i).getRecentlyUsed() == false? 0 : 1;
			String affichage = String.format("%2d %4d    %-5s", utilisation, recent, nom);
			model.addElement(affichage);
			list.setModel(model);
		}
	} 
	
	public void wipeSelection() 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		list.setModel(model);
		textField.setText("");
	}
	
	public void wipeList() 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		list.setModel(model);
	}

}
