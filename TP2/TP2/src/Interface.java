import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JList<String> wordList;
	private JList<String> lexiconList;
	private JTextField textField;
	private JScrollPane wordListScrollPanel;
	private JScrollPane lexiconListScrollPanel;
	private JButton lexiconButton;
	private Graph graph;
	
	
	private JFrame lexiconFrame;
	private JPanel lexiconPanel;
	private JLabel lexiconLabel;
	
	
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
		lexiconButton = new JButton("Afficher les labels");
		wordList = new JList<String>();
		textField = new JTextField(23);
		wordListScrollPanel = new JScrollPane(wordList);
		
		panel.add(label, BorderLayout.WEST);
		panel.add(textField, BorderLayout.NORTH);
		textField.setAlignmentY(TOP_ALIGNMENT);
		panel.add(lexiconButton,  BorderLayout.SOUTH);
		panel.add(wordListScrollPanel);
		this.add(panel);
		this.setVisible(true);
		
		
		
		
		lexiconButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				lexiconFrame = new JFrame("Lexique");
                lexiconLabel = new JLabel("---------LEXIQUE--------");
                lexiconFrame.setVisible(true);
                lexiconFrame.setSize(400, 400);
                lexiconPanel = new JPanel();
                lexiconFrame.add(lexiconPanel);
                lexiconPanel.add(lexiconLabel);
                
                lexiconList = new JList<String>();
                lexiconListScrollPanel = new JScrollPane(lexiconList);
                lexiconPanel.add(lexiconListScrollPanel);
			}
		});
		
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
				updateList(words, wordList);
				updateList(graph.getLexiconWords(), lexiconList);
			}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public void updateList(Vector<Mot> words, JList<String> list) 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (int i = 0; i < words.size(); i++)
		{	
			String nom = words.get(i).getNom(); 
			int utilisation = words.get(i).getNbTimeUsed(); 
			int recent = words.get(i).getRecentlyUsed() == false? 0 : 1;
			String affichage = "";
			if (list.equals(lexiconList))
				 affichage = String.format("%2d %4d    %-5s", utilisation, recent, nom);
			else 
				affichage = nom;
			model.addElement(affichage);
			list.setModel(model);
		}
	} 
	
	public void wipeSelection() 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		wordList.setModel(model);
		textField.setText("");
	}
	
	public void wipeList() 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		wordList.setModel(model);
	}

}
