import java.awt.BorderLayout;
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
	//Éléments JFrame de la fenêtre principale.
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel label;
	private JList<String> wordList;
	
	private JTextField textField;
	private JScrollPane wordListScrollPanel;
	private JScrollPane lexiconListScrollPanel;
	private JButton lexiconButton;
	private Graph graph;
	
	//Éléments JFrame de la fenêtre du lexique.
	private JFrame lexiconFrame;
	private JPanel lexiconPanel;
	private JLabel lexiconLabel;
	private JList<String> lexiconList;
	
	//Constructeur par paramètres de l'interface.
	public Interface(Graph graph) 
	{
		this.graph = graph;
		creerInterface();
	}
	//Cette méthode créé l'interface et intéragit avec les éléments du graphe.
	public void  creerInterface() {
		this.setTitle("LOG2810 - TP2");
		this.setSize(600, 240);
		this.setResizable(false); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Création et affichage de la fenêtre principale.
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
			//Création et affichage de la fenêtre du lexique lorsque le bouton est appuyé.
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
		
		//Intéraction entre les listes et le mot saisi dans le JTextField.
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
					updateList(graph.getLexiconWords(),lexiconList);
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
	//Cette méthode met à jour la liste de mots qu'affiche l'interface.
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
	//Cette méthode fait disparaître la saisie de mot du JTextField de la fenêtre principale.
	public void wipeSelection() 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		wordList.setModel(model);
		textField.setText("");
	}
	//Cette méthode fait disparaître la liste de mots à l'intérieur du JScrollPane.
	public void wipeList() 
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		wordList.setModel(model);
	}

}
