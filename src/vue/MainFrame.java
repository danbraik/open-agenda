package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import modele.RendezVous;
import controleur.Agenda;
import controleur.CommandAddRendezVous;
import controleur.CommandModifyRendezVous;
import controleur.CommandRemoveRendezVous;
import controleur.Main;
import controleur.TimeMachineController;
import controleur.UndoRedoManager;

/**
 * Fenêtre principale
 * @author daniel
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements MouseListener, WindowListener, ActionListener {
	/**
	 * Titre de la fenêtre par défaut
	 */
	private static final String TITLE = "Mon calendrier";
	/**
	 * Panneau contenant les composants permettant de
	 * changer de semaine.
	 */
	private TimeMachinePane mTimeMachinePane;
	/**
	 * Contrôleur associé au précédent panneau.
	 */
	private TimeMachineController mTimeMachineController;
	/**
	 * Table affichant les rendez vous
	 */
	private JTable mCalendrier;
	/**
	 * Éléments du menu
	 */
	private JMenuItem mMiNew;
	private JMenuItem mMiOpen;
	private JMenuItem mMiSave;
	private JMenuItem mMiQuit;
	private JMenuItem mMiUndo;
	private JMenuItem mMiRedo;
	/**
	 * Fichier actuellement ouvert
	 */
	private File openAgenda;
	/**
	 * Permet de gérer le déplacement de la souris
	 */
	private MouseMotion mouseDrag;
	/**
	 * Gestionnaire des actions utilisateurs
	 */
	private UndoRedoManager urManager ;
	
	/**
	 * Constructeur 
	 * @param agenda Fichier actuellement ouvert
	 */
	public MainFrame(File agenda) {
		super(TITLE);
		
		mouseDrag = new MouseMotion();
		openAgenda = agenda;
		
		setTitle((openAgenda == null) ? TITLE : openAgenda.getName());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setSize(850, 480);
		setLocationRelativeTo(null);
		
		creationComposants();
		placementComposants();
		createMenu();
		
		urManager = new UndoRedoManager(mMiUndo, mMiRedo);
		
		setVisible(true);
	}
	/**
	 * Création du menu
	 */
	private void createMenu() {
		// items
		mMiNew = new JMenuItem("Nouveau");
		mMiNew.addActionListener(this);
		mMiNew.setAccelerator(KeyStroke.getKeyStroke('N', java.awt.event.InputEvent.CTRL_DOWN_MASK));
		
		mMiOpen = new JMenuItem("Ouvrir");
		mMiOpen.addActionListener(this);
		mMiOpen.setAccelerator(KeyStroke.getKeyStroke('O', java.awt.event.InputEvent.CTRL_DOWN_MASK));
		
		mMiSave = new JMenuItem("Sauver");
		mMiSave.addActionListener(this);
		mMiSave.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.event.InputEvent.CTRL_DOWN_MASK));
		
		mMiQuit = new JMenuItem("Quitter");
		mMiQuit.addActionListener(this);
		mMiQuit.setAccelerator(KeyStroke.getKeyStroke('Q', java.awt.event.InputEvent.CTRL_DOWN_MASK));
		
		mMiUndo = new JMenuItem("Annuler");
		mMiUndo.addActionListener(this);
		mMiUndo.setAccelerator(KeyStroke.getKeyStroke('Z', java.awt.event.InputEvent.CTRL_DOWN_MASK));
		
		mMiRedo = new JMenuItem("Refaire");
		mMiRedo.addActionListener(this);
		mMiRedo.setAccelerator(KeyStroke.getKeyStroke('Y', java.awt.event.InputEvent.CTRL_DOWN_MASK));
		
		// Menus
		JMenu menuFile = new JMenu("Fichier");
		JMenu menuEdit = new JMenu("Editer");
		
		menuFile.add(mMiNew);
		menuFile.add(mMiOpen);
		menuFile.add(mMiSave);
		menuFile.add(mMiQuit);
		
		menuEdit.add(mMiUndo);
		menuEdit.add(mMiRedo);
		
		// bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		
		setJMenuBar(menuBar);
	}
	/**
	 * Création des composants
	 */
	private void creationComposants() {
		mTimeMachineController = new TimeMachineController();
		mTimeMachinePane = new TimeMachinePane(mTimeMachineController);
		mTimeMachinePane.setBorder(BorderFactory.createLoweredBevelBorder());
		
		mCalendrier = new JTable();
		mCalendrier.setRowSelectionAllowed(false);
		mCalendrier.setDoubleBuffered(true);
		mCalendrier.getTableHeader().setReorderingAllowed(false);
		
		mCalendrier.setModel(Agenda.getInstance().getTableModel());
		mCalendrier.setColumnModel(Agenda.getInstance().getTableColumnModel());
		mCalendrier.setDefaultRenderer(RendezVous.class, new AgendaRendezVousRenderer());
		mCalendrier.setDefaultRenderer(String.class, new AgendaHourRenderer());
		mCalendrier.addMouseListener(this);
	}
	/**
	 * Placement des composants
	 */
	private void placementComposants() {

		JPanel pRight = new JPanel(new BorderLayout());
		pRight.add(mCalendrier.getTableHeader(), BorderLayout.NORTH);
		pRight.add(mCalendrier);
		pRight.setBorder(BorderFactory.createLoweredBevelBorder());
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.add(mTimeMachinePane, JSplitPane.LEFT);
		splitPane.add(pRight, JSplitPane.RIGHT);
		int offset = 10;
		splitPane.setBorder(BorderFactory.createEmptyBorder(offset, offset, offset, offset));
		
		setContentPane(splitPane);
	}
	/**
	 * Lorsque le bouton vient d'être appuyé
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mouseDrag.setFirstRowCol(mCalendrier.rowAtPoint(e.getPoint()),
				mCalendrier.columnAtPoint(e.getPoint()));
	}
	/**
	 * Lorsque le bouton vient d'être relâché
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDrag.setSecondRowCol(mCalendrier.rowAtPoint(e.getPoint()),
				mCalendrier.columnAtPoint(e.getPoint()));
		
        if (mouseDrag.isValid() 
        		&& (mouseDrag.hasMoved() || e.getClickCount() == 2) 
        		&& mouseDrag.col1 > 0) {
        	
        	boolean isNew = true;
        	RendezVous rendezVous = (RendezVous) Agenda.getInstance().getTableModel()
    				.getValueAt(mouseDrag.row1, mouseDrag.col1);
        	
        	// obtenir le rendez vous de la cellule de départ
        	RendezVousDialogArg editorArg = new RendezVousDialogArg(
        			rendezVous);
        	
        	// s'il est nul, alors il faut en créer un nouveau
        	if (editorArg.getRendezVous() == null) { 
				editorArg.setRendezVous(new RendezVous(
						mTimeMachineController.getAfter(mouseDrag.col1-1, mouseDrag.row1),
						mTimeMachineController.getAfter(mouseDrag.col2-1, mouseDrag.row2+1))
				);
				// il faut qu'on ait bougé pour créer un rendez vous
				// et que la cellule de départ (ok à cause du premier if)
				// et d'arrivée soient vides
				if (mouseDrag.hasMoved() 
						&& Agenda.getInstance().getTableModel()
						.getValueAt(mouseDrag.row2, mouseDrag.col2) == null)
	        		new RendezVousDialog(this, editorArg);
        	} else if (e.getClickCount() == 2 // double clic droit pour supprimer
        			&& e.getButton() == MouseEvent.BUTTON3) {
        		urManager.execute(new CommandRemoveRendezVous(editorArg.getRendezVous()));
        		mCalendrier.repaint();
        	} else {
        		isNew = false;
        		editorArg.setRendezVous(new RendezVous(rendezVous)); // crée une copie pour l'édition
        	}
        	
        	// pour éditer(ou créer)sur une cellule, il faut avoir double cliqué
        	if (e.getClickCount() == 2 
        			&& e.getButton() == MouseEvent.BUTTON1) {
        		new RendezVousDialog(this, editorArg);
        	}
			 
			if (editorArg.isSubmitted()) {
				if (isNew)
					urManager.execute(new CommandAddRendezVous(editorArg.getRendezVous()));
				else
					urManager.execute(new CommandModifyRendezVous(rendezVous, editorArg.getRendezVous()));
				mCalendrier.repaint();
			}
        }
	}


	/**
	 * Fermeture de l'application
	 */
	private void quit() {
		saveCurrentAgenda();
		dispose();
	}

	/**
	 * L'utilisateur souhaite fermet la fenêtre
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		quit();
	}

	/**
	 * Bouton ou élément du menu actionné
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mMiNew) {
			saveCurrentAgenda();
			newAgenda();
		} else if (e.getSource() == mMiOpen) {
			saveCurrentAgenda();
			openAgendaFile();
		} else if (e.getSource() == mMiSave) {
			saveCurrentAgenda();
		} else if (e.getSource() == mMiQuit) {
			quit();
		} else if (e.getSource() == mMiUndo) {
			urManager.undo();
		} else if (e.getSource() == mMiRedo) {
			urManager.redo();
		}
		
		mCalendrier.repaint();
	}
	
	/**
	 * Réinitialiser l'ensemble des rendez-vous
	 */
	private void newAgenda() {
		Agenda.getInstance().removeAll();
		openAgenda = null;
		setTitle(TITLE);
	}
	
	/**
	 * Procédure proposant à l'utilisateur l'ouverture d'un agenda
	 */
	private void openAgendaFile() {
		JFileChooser chooser = new JFileChooser(new File("agenda.xml"));
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    	openAgenda = chooser.getSelectedFile();
	    	Main.setProperty(Main.SYSPROP_LATEST_AGENDA, openAgenda.getAbsolutePath());
	    	Agenda.getInstance().loadFrom(openAgenda);
	    	setTitle(openAgenda.getName());
	    }
	}
	
	/**
	 * Sauvegarder l'agenda courrant dans un fichier
	 * éventuellement sélectionné par l'utilisateur
	 */
	private void saveCurrentAgenda() {
		if (openAgenda == null) { // then it is a new agenda
			if (Agenda.getInstance().count() > 0) {// don't save empty agenda
				JFileChooser chooser = new JFileChooser(new File("agenda.xml"));
			    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			    	openAgenda = chooser.getSelectedFile();
			    	Main.setProperty(Main.SYSPROP_LATEST_AGENDA, openAgenda.getAbsolutePath());
			    	Agenda.getInstance().saveTo(openAgenda);
			    	setTitle(openAgenda.getName());
			    }
			}
		} else
			Agenda.getInstance().saveTo(openAgenda);		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	

	@Override
	public void mouseEntered(MouseEvent e) {	
	}
	
	@Override
	public void mouseExited(MouseEvent e) {	
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}	

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {		
	}	
	
}
