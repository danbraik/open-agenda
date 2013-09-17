/**
 * 
 */
package modele;

import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 * Modèle de JTable servant de mémoire tampon
 * en mémorisant les rendez vous filtrés
 * @author daniel
 */
@SuppressWarnings("serial")
public class AgendaTableModel extends DefaultTableModel {

	/**
	 * Nombre de jours visualisés
	 */
	private final static int NB_DAYS = 7;
	/**
	 * Heures par jours
	 */
	private final static int NB_HOURS = 24;
	/**
	 * Durée d'une heure en millisecondes
	 */
	private final static float DURATION_HOUR_MILI = 1000*3600;
	
	/**
	 * Nombre de colonnes : 1 (Heures) + Nombre de jours
	 */
	protected final static int NB_COLS = NB_DAYS+1;
	
	/**
	 * Constructeur
	 */
	public AgendaTableModel() {
		super(24, NB_COLS);
		for (int r=0; r < NB_HOURS;++r)
			setValueAt(String.format("%d h", r), r, 0);		
	}
	
	/**
	 * Mettre à jour la mémoire tampon avec les rendez-vous filtrés.
	 * @param begin Date de début de la période de filtrage
	 * @param end Date de fin de la période de filtrage
	 * @param mCollection Ensemble des rendez vous à filtrer
	 */
	public void updateData(Date begin, Date end, List<RendezVous> mCollection) {
		clear();
		for (RendezVous rendezVous : mCollection)
			if (rendezVous.getBegin().before(end) || rendezVous.getEnd().after(begin))
				printRendezVous(rendezVous, begin);
	}
	
	/**
	 * Ecrire les informations d'un rendez vous dans les bonnes cellules
	 * @param rdv Rendez-vous à traiter
	 * @param axe Date du début de la période visualisée
	 */
	private void printRendezVous(RendezVous rdv, Date axe) {
		int caseDepart = calculatePositionCell(rdv.getBegin(), axe);
		int caseArrivee = calculatePositionCell(rdv.getEnd(), axe);
		
		while (caseDepart < caseArrivee) {
			int colonne = caseDepart / NB_HOURS;
			int ligne = caseDepart - NB_HOURS*colonne;
			++colonne;
			
			if (colonne > 0 && colonne < NB_COLS && ligne >= 0 && ligne < NB_HOURS)
				if (getValueAt(ligne, colonne) == null)
					setValueAt(rdv, ligne, colonne);
			
			++caseDepart;
		}
	}

	/**
	 * Calculer la position relative d'une date par rapport à un axe
	 * en nombre de cellules (soit nombre d'heures)
	 * @param date Date à traiter
	 * @param axis Origine du temps considéré
	 * @return Position relative de la cellule
	 */
	private int calculatePositionCell(Date date, Date axis) {
		return (int) Math.round((date.getTime() - axis.getTime()) / (DURATION_HOUR_MILI));
	}

	private void clear() {
		for (int r = 0; r < NB_HOURS; ++r)
			for (int c = 1 ; c < NB_COLS; ++c)
				setValueAt(null, r, c);
	}
	
	
	/**
	 * Obtenir le type des données d'une colonne
	 * @param columnIndex Index de la colonne
	 * @return Métaclasse d'une possible donnée
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return (columnIndex > 0) ? RendezVous.class : String.class;
	}
	
}
