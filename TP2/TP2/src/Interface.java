import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
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
	//�l�ments JFrame de la fen�tre principale.
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel label;
	private JList<String> wordList;
	
	private JTextField textField;
	private JScrollPane wordListScrollPanel;
	private JButton lexiconButton;
	private JButton queueButton;
	private Graph graph;
	
	//�l�ments JFrame de la fen�tre du lexique.
	private JFrame lexiconFrame;
	private JPanel lexiconPanel;
	private JLabel lexiconLabel;
	private JList<String> lexiconList;
	private JScrollPane lexiconListScrollPanel;
	
	//�l�ments JFrame de la fen�tre des mots r�cemment utilis�s.
	private JFrame queueFrame;
	private JPanel queuePanel;
	private JLabel queueLabel;
	private JList<String> queueList;
	private JScrollPane queueListScrollPanel;
	
	//Constructeur par param�tres de l'interface.
	public Interface(Graph graph) 
	{
		this.graph = graph;
		creerInterface();
	}
	//Cette m�thode cr�� l'interface et int�ragit avec les �l�ments du graphe.
	public void  creerInterface() {
		this.setTitle("LOG2810 - TP2");
		this.setSize(600, 250);
		this.setResizable(false); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Cr�ation et affichage de la fen�tre principale.
		panel = new JPanel();
		label = new JLabel("Entrer le mot d�sir� :     ");
		lexiconButton = new JButton("Afficher les labels");
		queueButton = new JButton("               Afficher les mots r�cemment utilis�s               ");
		wordList = new JList<String>();
		textField = new JTextField(23);
		wordListScrollPanel = new JScrollPane(wordList);
		
		panel.add(label, BorderLayout.WEST);
		panel.add(textField, BorderLayout.NORTH);
		textField.setAlignmentY(TOP_ALIGNMENT);
		panel.add(lexiconButton, BorderLayout.SOUTH);
		panel.add(wordListScrollPanel);
		panel.add(queueButton, BorderLayout.SOUTH);
		this.add(panel);
		this.setVisible(true);
		
		lexiconButton.addActionListener(new ActionListener()
		{
			//Cr�ation et affichage de la fen�tre du lexique lorsque le bouton est appuy�.
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				lexiconFrame = new JFrame("Lexique");
                lexiconLabel = new JLabel("- - - - - - - - - -LEXIQUE- - - - - - - - - -");
                lexiconPanel = new JPanel();
                lexiconList = new JList<String>();
                lexiconListScrollPanel = new JScrollPane(lexiconList);
                
                updateList(graph.getLexiconWords(),lexiconList);
                lexiconPanel.add(lexiconLabel, BorderLayout.NORTH);
                lexiconPanel.add(lexiconListScrollPanel);            
                lexiconFrame.add(lexiconPanel);
                
                lexiconFrame.setSize(300, 250);
                lexiconFrame.setResizable(false);
                lexiconFrame.setVisible(true);
			}
		});
		
		queueButton.addActionListener(new ActionListener()
		{
			//Cr�ation et affichage de la fen�tre des mots 
			//r�cemment utilis�s lorsque le bouton est appuy�.
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				queueFrame = new JFrame("Mots r�cemment utilis�s");
                queueLabel = new JLabel("---Mots r�cemment utilis�s---");
                queuePanel = new JPanel();
                queueList = new JList<String>();
                queueListScrollPanel = new JScrollPane(queueList);
                
                updateQueueList(graph.getFiveRecentlyUsed(),queueList);
                queuePanel.add(queueLabel, BorderLayout.NORTH); 
                queuePanel.add(queueListScrollPanel);
                queueFrame.add(queuePanel);
                
                queueFrame.setSize(300, 250);
                queueFrame.setResizable(false);
                queueFrame.setVisible(true);
			}
		});
		
		//Int�raction entre les listes et le mot saisi dans le JTextField.
		textField.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) {}
			
			//Cette m�thode est appel�e chaque fois 
			//qu'une touche est relach�e � partir de la zone de texte
			@Override
			public void keyReleased(KeyEvent e) 
			{
				String input = textField.getText();
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					graph.addToQueue(input);
					wipeSelection();
					
					if(lexiconList != null)
						updateList(graph.getLexiconWords(),lexiconList);
					if(queueList != null)
						updateQueueList(graph.getFiveRecentlyUsed(),queueList);
					return;
				}
				if(graph.displayWords(input) == null) 
				{
					wipeList();
					return;
				}				
				Vector<Mot> words = graph.displayWords(input);
				updateList(words, wordList);
			}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}
	//Cette m�thode met � jour la liste de mots qu'affiche l'interface.
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
		}
		list.setModel(model);
	} 
	
	//Cette m�thode met � jour la liste de mots r�cemment utilis�s.
	public void updateQueueList(Queue<String> words, JList<String> list) 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		Vector<String> mots = new Vector<String>();
		for (int i = 0; i < words.size(); i++)
		{	
			String nom = words.peek();
			words.add(nom);
			mots.add(nom);
			words.poll();
		}
		for (int i = mots.size()-1; i >= 0; i--)
		{	
			model.addElement(mots.get(i));
		}
		list.setModel(model);
	}
	//Cette m�thode fait dispara�tre la saisie de mot du JTextField de la fen�tre principale 
	//et la liste de mots � l'int�rieur du JScrollPane.
	public void wipeSelection() 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		wordList.setModel(model);
		textField.setText("");
	}
	//Cette m�thode fait dispara�tre la liste de mots � l'int�rieur du JScrollPane.
	public void wipeList() 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		wordList.setModel(model);
	}

}
