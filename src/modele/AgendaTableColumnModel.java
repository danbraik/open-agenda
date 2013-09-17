package modele;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import controleur.Utils;

/**
 * Modèle de colonne de table. Personnalise l'affichage des en-têtes.
 * @author daniel
 */
@SuppressWarnings("serial")
public class AgendaTableColumnModel extends DefaultTableColumnModel {
	
	/**
	 * Calendrier utilisé pour la mise à jour des en-têtes
	 */
	private Calendar mCal;
	/**
	 * Quelques outils
	 */
	private Utils utils;
	
	/**
	 * Constructeur
	 * Initialise les en-têtes
	 */
	public AgendaTableColumnModel() {
		utils = Utils.getInstance();
		mCal = new GregorianCalendar();
		for (int i = 0; i < AgendaTableModel.NB_COLS ;++i) {
			TableColumn tc = new TableColumn(i);
			tc.setHeaderValue(" ");
			addColumn(tc);
		}
		getColumn(0).setPreferredWidth(20);
		getColumn(0).setResizable(false);
	}
	
	/**
	 * Mettre à jour les en-têtes des colonnes
	 * @param begin Date du début de la semaine
	 */
	public void updateHeader(Date begin) {
		mCal.setTime(begin);
		for (int i=1; i < AgendaTableModel.NB_COLS; ++i) {
			getColumn(i).setHeaderValue(formatHeader(mCal));
			fireChange(i); 
			mCal.add(Calendar.DATE, 1); // increment
		}
	}
	
	/**
	 * Obtenir un texte mis en forme de la date
	 * @param cal Calendrier contenant la date
	 * @return Texte représentant la date (ex:Lundi 11)
	 */
	private String formatHeader(Calendar cal) {
		return String.format("%s %s",
				utils.capitalize(cal.getDisplayName(Calendar.DAY_OF_WEEK,
						Calendar.LONG, Locale.getDefault())),
				cal.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * Notifier de la mise à jour d'une colonne
	 * @param i Index de la colonne
	 */
	private void fireChange(int i) {
		super.fireColumnMoved(new TableColumnModelEvent(this, i, i));
	}
}
