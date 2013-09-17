package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import modele.RendezVous;

 
/**
 * Boîte de dialogue pour l'édition d'un rendez-vous.
 * @author daniel
 */
@SuppressWarnings("serial")
public class RendezVousDialog extends JDialog implements ActionListener {
	
	/**
	 * Titre de la boîte de message en cas d'erreur de saisie
	 */
	private static final String MSGD_TITLE_RDV_COMMIT_ERROR = "Impossible de valider le rendez-vous";
	
	/**
	 * Labels associés au champs de saisie
	 */
	private JLabel mLblTitle;
	private JLabel mLblBeginDay;
	private JLabel mLblBeginTime;
	private JLabel mLblEndDay;
	private JLabel mLblEndTime;
	private JLabel mLblSummary;
	private JLabel mLblColor;
	
	/**
	 * Champs de saisie
	 */
	private JTextField mTfTitle;
	private JFormattedTextField mTfBeginDay;
	private JFormattedTextField mTfBeginTime;
	private JFormattedTextField mTfEndDay;
	private JFormattedTextField mTfEndTime;
	private JTextArea mTfSummary;
	
	/**
	 * Bouttons de validation et d'annulation
	 */
	private JButton mBtColor;
	private JButton mBtSubmit;
	private JButton mBtCancel;
	
	/**
	 * Instance du rendez vous en cours d'édition.
	 * et autres variables d'édition
	 */
	private RendezVousDialogArg mRendezVousArg;
	private RendezVous mRendezVousSource;
	private Color mRendezVousColor;
	
	public RendezVousDialog(JFrame parent, RendezVousDialogArg rendezVousArg) {
		super(parent, "Rendez-vous", true);
		
		if (rendezVousArg == null || rendezVousArg.getRendezVous() == null) {
			dispose();
			return ;
		}
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// largeur des champs de saisie
		int width = 250;
		
		setSize(width*2, 500);
		setResizable(false);
		
		mRendezVousArg = rendezVousArg;
		mRendezVousSource = rendezVousArg.getRendezVous();
		mRendezVousColor = rendezVousArg.getRendezVous().getColor();
		
		creationComposants(width);
		placementComposants(width);
		
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	/**
	 * Création des composants
	 * @param width Largeur des champs
	 */
	private void creationComposants(int width) {	
		/* Text fields */
		mTfTitle = new JTextField(mRendezVousSource.getTitle());
		mTfTitle.setPreferredSize(new Dimension(width,20));
		mTfTitle.addActionListener(this);
		
		// Patterns à respecter dans les champs de date et d'horaire
		SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		mTfBeginDay = new JFormattedTextField(dayFormat);
		mTfBeginDay.setValue(mRendezVousSource.getBegin());
		mTfBeginDay.setPreferredSize(new Dimension(width,20));
		mTfBeginDay.addActionListener(this);
		
		mTfBeginTime = new JFormattedTextField(timeFormat);
		mTfBeginTime.setValue(mRendezVousSource.getBegin());
		mTfBeginTime.setPreferredSize(new Dimension(width,20));
		mTfBeginTime.addActionListener(this);
		
		mTfEndDay = new JFormattedTextField(dayFormat);
		mTfEndDay.setValue(mRendezVousSource.getEnd());
		mTfEndDay.setPreferredSize(new Dimension(width,20));
		mTfEndDay.addActionListener(this);
		
		mTfEndTime = new JFormattedTextField(timeFormat);
		mTfEndTime.setValue(mRendezVousSource.getEnd());
		mTfEndTime.setPreferredSize(new Dimension(width,20));
		mTfEndTime.addActionListener(this);
		
		mTfSummary = new JTextArea(mRendezVousSource.getSummary());
		mTfSummary.setLineWrap(true);
		mTfSummary.setWrapStyleWord(true);
		
		/* Buttons */
		mBtColor = new JButton(" ");
		mBtColor.setFocusPainted(false);
		mBtColor.setBackground(mRendezVousColor);
		mBtColor.addActionListener(this);
		
		mBtSubmit = new JButton("Valider");
		mBtSubmit.addActionListener(this);
		
		mBtCancel = new JButton("Annuler");
		mBtCancel.addActionListener(this);
		
		/* Labels */
		mLblTitle = new JLabel("Titre :");
		mLblTitle.setLabelFor(mTfTitle);
		
		mLblBeginDay =new JLabel("Date début (JJ/MM/AAAA) :");
		mLblBeginDay.setLabelFor(mTfBeginDay);
		
		mLblBeginTime = new JLabel("Heure de début (HH:MM) :");
		mLblBeginTime.setLabelFor(mTfBeginTime);
		
		mLblEndDay = new JLabel("Date fin (JJ/MM/AAAA) :");
		mLblEndDay.setLabelFor(mTfEndDay);
		
		mLblEndTime = new JLabel("Heure de fin (HH:MM) :");
		mLblEndTime.setLabelFor(mTfEndTime);
		
		mLblSummary = new JLabel("Description (facultative) :");
		mLblSummary.setLabelFor(mTfSummary);
		
		mLblColor = new JLabel("Couleur d'affichage :");
		mLblColor.setLabelFor(mBtColor);
	}
	
	/**
	 * Placement des composants
	 * @param width Largeur des champs
	 */
	private void placementComposants(int width) {
		
		JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1));
		pTitle.add(mLblTitle);
		pTitle.add(mTfTitle);
		
		JPanel pBeginDay = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1));
		pBeginDay.add(mLblBeginDay);
		pBeginDay.add(mTfBeginDay);
		
		JPanel pBeginTime = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1));
		pBeginTime.add(mLblBeginTime);
		pBeginTime.add(mTfBeginTime);
		
		JPanel pEndDay = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1));
		pEndDay.add(mLblEndDay);
		pEndDay.add(mTfEndDay);
		
		JPanel pEndTime = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1));
		pEndTime.add(mLblEndTime);
		pEndTime.add(mTfEndTime);
		
		int areaHeigth = 280;
		JPanel pSummary = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1));
		
		JPanel pSummaryLabel = new JPanel(new BorderLayout());
		pSummaryLabel.add(mLblSummary, BorderLayout.NORTH);
		pSummaryLabel.add(Box.createRigidArea(
				new Dimension(1,
						areaHeigth - mLblSummary.getPreferredSize().height - 4)));
		pSummary.add(pSummaryLabel);
		
		JScrollPane pSummaryText = new JScrollPane(mTfSummary);
		pSummaryText.setPreferredSize(new Dimension(width, areaHeigth));
		pSummary.add(pSummaryText);
		
		JPanel pColor = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1));
		JPanel pColorButton = new JPanel(new BorderLayout());
		pColorButton.setPreferredSize(new Dimension(width, mBtColor.getPreferredSize().height));
		pColorButton.add(mBtColor, BorderLayout.WEST);
		pColor.add(mLblColor);
		pColor.add(pColorButton);
		
		JPanel pButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel pButtonCancel = new JPanel(new BorderLayout());
		pButtonCancel.setPreferredSize(new Dimension(width, mBtCancel.getPreferredSize().height));
		pButtonCancel.add(mBtCancel, BorderLayout.WEST);
		pButtons.add(mBtSubmit);
		pButtons.add(pButtonCancel);
		
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(pTitle);
		contentPane.add(pBeginDay);
		contentPane.add(pBeginTime);
		contentPane.add(pEndDay);
		contentPane.add(pEndTime);
		contentPane.add(pSummary);
		contentPane.add(pColor);
		contentPane.add(pButtons);
		
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		this.setContentPane(contentPane);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mBtColor) {
			Color newColor = JColorChooser.showDialog(this, 
					"Choisissez votre couleur préférée", mRendezVousColor);
			if (newColor != null) {
				mRendezVousColor = newColor;
				mBtColor.setBackground(mRendezVousColor);
			}
		}
		/* Une validation est acceptée si les cinq premiers
		 * champs sont remplis
		 * et
		 * si la date de début est antérieure à celle de fin 
		 */
		else if (e.getSource() == mBtSubmit
				|| e.getSource() == mTfTitle
				|| e.getSource() == mTfBeginDay
				|| e.getSource() == mTfBeginTime
				|| e.getSource() == mTfEndDay
				|| e.getSource() == mTfEndTime
				) {
			final String title = mTfTitle.getText();
			final String sBeginDay = mTfBeginDay.getText();
			final String sBeginTime = mTfBeginTime.getText();
			final String sEndDay = mTfEndDay.getText();
			final String sEndTime = mTfEndTime.getText();
			
			if (title.isEmpty() || sBeginDay.isEmpty() || sBeginTime.isEmpty() 
					|| sEndDay.isEmpty() || sEndTime.isEmpty())  {
				JOptionPane.showMessageDialog(this, 
						"Les cinq premiers champs sont obligatoires.", 
						MSGD_TITLE_RDV_COMMIT_ERROR, JOptionPane.WARNING_MESSAGE);
			} else {
				final Date begin = getDateFromTextFields(mTfBeginDay, mTfBeginTime);
				final Date end = getDateFromTextFields(mTfEndDay, mTfEndTime);
				
				if (begin.after(end)) {
					JOptionPane.showMessageDialog(this, 
							"Le début n'est pas correct par rapport à la fin.", 
							MSGD_TITLE_RDV_COMMIT_ERROR, JOptionPane.ERROR_MESSAGE);
				} else {
					// mise à jour de l'instance source
					mRendezVousArg.setSubmitted();
					mRendezVousSource.setTitle(title);
					mRendezVousSource.setBegin(begin);
					mRendezVousSource.setEnd(end);
					mRendezVousSource.setSummary(mTfSummary.getText());
					mRendezVousSource.setColor(mRendezVousColor);
					dispose();
				}
			}
		} else if (e.getSource() == mBtCancel) {
			dispose();
		}
	}
	
	/**
	 * Obtenir une date complète (jour + horaire)
	 * à partir de deux champs de texte formatés
	 * @param tfDay Champ contenant la date du jour
	 * @param tfTime Champ contenant l'horaire
	 * @return Une date issue de la fusion des paramètres
	 */
	private Date getDateFromTextFields(JFormattedTextField tfDay,
			JFormattedTextField tfTime) {
		Date day = (Date) tfDay.getValue();
		Date time = (Date) tfTime.getValue();
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.clear();
		
		// obtenir les infos du jour
		cal.setTime(day);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int dayMonth = cal.get(Calendar.DAY_OF_MONTH);
		
		// obtenir les infos de l'horaire
		cal.setTime(time);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int mins = cal.get(Calendar.MINUTE);
		
		// fusionner les infos
		cal.clear();
		cal.set(year, month, dayMonth, hour, mins);
		
		return cal.getTime();
	}
	
}

