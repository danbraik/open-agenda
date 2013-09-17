package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controleur.TimeMachineController;
import controleur.Utils;


/**
 * Panneau graphique proposant de modifier l'année et la semaine visualisée
 * et affichant le mois en cours.
 * @author daniel
 *
 */
@SuppressWarnings("serial")
public class TimeMachinePane extends JPanel implements ChangeListener {

	/**
	 * Composants
	 */
	private JSpinner mSpYear;
	private JSpinner mSpWeek;
	private JLabel mLblYear;
	private JLabel mLblWeek;
	private JLabel mLblMonth;
	
	/**
	 * Contrôleur de la période temporelle à afficher
	 */
	private TimeMachineController mController;
	
	/**
	 * Quelques méthodes utiles
	 */
	public Utils utils;
	
	/**
	 * Constructeur
	 * @param controller Contrôleur de la période visualisée dans la table
	 */
	public TimeMachinePane(TimeMachineController controller) {
		mController = controller;
		utils = Utils.getInstance();
		
		creationComposants();
		placementComposants();
	}
	
	/**
	 * Méthode appelée lorsque que les valeurs des spinner ont changées
	 * @param event Argument d'évenement
	 */
	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() == mSpYear ||
				event.getSource() == mSpWeek) {
			
			mController.setView((int)mSpYear.getValue(), (int)mSpWeek.getValue());
			
			if (event.getSource() == mSpYear) 
				// si l'année a changée, met à jour les valeurs possibles des semaines
				mSpWeek.setModel(getWeekSpinnerModel((int)mSpWeek.getValue()));
			
			mLblMonth.setText(getLabelMonth());
		}
	}
	
	/**
	 * Créer les composants
	 */
	private void creationComposants() {
		Calendar cal = mController.getCal();
		
		mSpYear = new JSpinner();
		mSpYear.setPreferredSize(new Dimension(70,20));
		mSpYear.setValue(cal.get(Calendar.YEAR));
		mSpYear.addChangeListener(this);
		
		mSpWeek = new JSpinner();
		mSpWeek.setFont(mSpWeek.getFont().deriveFont(Font.PLAIN));
		mSpWeek.setModel(getWeekSpinnerModel(cal.get(Calendar.WEEK_OF_YEAR)));
		mSpWeek.addChangeListener(this);
				
		mLblYear = new JLabel("Année :");
		mLblYear.setLabelFor(mSpYear);
		
		mLblWeek = new JLabel("Semaine :");
		mLblWeek.setLabelFor(mSpWeek);
		
		mLblMonth = new JLabel(getLabelMonth());
	}
	
	/**
	 * Placer les composants dans le panneau
	 */
	private void placementComposants() {
		JPanel pYear = new JPanel(new FlowLayout());
		pYear.add(mLblYear);
		pYear.add(mSpYear);
		
		JPanel pWeek = new JPanel(new FlowLayout());
		pWeek.add(mLblWeek);
		pWeek.add(mSpWeek);
		
		JPanel pMonth = new JPanel(new FlowLayout());
		pMonth.add(mLblMonth);
		
		JPanel pContentPane = new JPanel(new GridLayout(3,1));
		pContentPane.add(pYear);
		pContentPane.add(pWeek);
		pContentPane.add(pMonth);
		
		this.setLayout(new BorderLayout());
		this.add(pContentPane, BorderLayout.NORTH);
	}
	
	/**
	 * Obtenir un modèle de spinner pour la semaine
	 * @param week Valeur actuelle de la semaine
	 * @return Un modèle dont les min et max sont adaptés
	 */
	private SpinnerNumberModel getWeekSpinnerModel(int week) {
		Calendar cal = mController.getCal();
		cal.set(cal.get(Calendar.YEAR), Calendar.DECEMBER, 28);
		
		int min = cal.getMinimum(Calendar.WEEK_OF_YEAR);
		int max = cal.get(Calendar.WEEK_OF_YEAR);
		
		week = (week < min) ? min : week;
		week = (week > max) ? max : week;
		
		return new SpinnerNumberModel((Number) week, min, max, 1);
	}
	
	/**
	 * Obtenir le nom du mois
	 * @param timeMachinePane TODO
	 * @return Nom du mois captialisé
	 */
	public String getLabelMonth() {
		StringBuilder sb = new StringBuilder("Mois de ");
		sb.append(utils.capitalize(
				mController.getMonth()
				));
		return sb.toString();
	}
	
}


