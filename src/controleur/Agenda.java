/**
 * 
 */
package controleur;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import modele.AgendaTableColumnModel;
import modele.AgendaTableModel;
import modele.RendezVous;
import modele.RendezVousFactory;


/**
 * Gère l'ensemble des rendez vous
 * ainsi que leur filtrage pour l'affichage
 * @author daniel
 */
public class Agenda {

	/**
	 * Instance unique
	 */
	private static Agenda mInstance ;
	/**
	 * Obtenir l'instance
	 * @return L'instance unique
	 */
	public static Agenda getInstance() {
		if (mInstance == null)
			mInstance = new Agenda();
		return mInstance;
	}
	
	/**
	 * Ensemble de tous les rendez vous
	 */
	private List<RendezVous> mSetRendezVous;
	/**
	 * Tableau contenant les rendez vous filtrés à afficher
	 */
	private AgendaTableModel tableModel;
	/**
	 * En-tête du tableau
	 */
	private AgendaTableColumnModel tableColumnModel;
	/**
	 * Date du début de la période à afficher
	 */
	private Date mFirstDate;
	/**
	 * Date de la fin (exclusive) de la période
	 */
	private Date mEndDate;
	
	/**
	 * Constructeur
	 */
	private Agenda() {
		mSetRendezVous = RendezVousFactory.getIntance().getEmptySet();
		
		tableModel = new AgendaTableModel();
		tableColumnModel = new AgendaTableColumnModel();
		
		mFirstDate = new Date();
		mEndDate = new Date();
	}

	/**
	 * Obtenir le modèle du tableau d'affichage
	 * @return the tableModel
	 */
	public TableModel getTableModel() {
		return tableModel;
	}

	/**
	 * Obtenir le modèle d'en têtes
	 * @return the tableColumnModel
	 */
	public TableColumnModel getTableColumnModel() {
		return tableColumnModel;
	}
	
	/**
	 * Ajouter un rendez vous
	 * @param rendezVous Nouveau rendez vous
	 */
	public void add(RendezVous rendezVous) {
		mSetRendezVous.add(rendezVous);
		update();
	}
	
	/**
	 * Spécifier qu'un rendez vous a été modifié
	 * @param rendezVous Rendez vous mis à jour
	 */
	public void setDirty(RendezVous rendezVous) {
		if (rendezVous.getBegin().before(mEndDate) 
				|| rendezVous.getEnd().after(mFirstDate))
			update();
	}
	
	/**
	 * Supprimer un rendez vous
	 * @param rendezVous Rendez vous à supprimer
	 */
	public void remove(RendezVous rendezVous) {
		mSetRendezVous.remove(rendezVous);
		update();
	}
	
	/**
	 * Supprimer tous les rendez vous
	 */
	public void removeAll() {
		mSetRendezVous.clear();
		update();
	}
	
	/**
	 * Fixer les dates de la période à filter
	 * @param firstDate Début de la période
	 * @param endDate Fin (exclusive) de la période
	 */
	public void setFirstDate(Date firstDate, Date endDate) {
		// se souvenir des critères pour pouvoir updater
		mFirstDate = firstDate;
		mEndDate = endDate;
		
		tableColumnModel.updateHeader(mFirstDate);
		update();
	}
	
	/**
	 * Obtenir le nombre de rendez vous
	 * @return Nombre de rendez vous
	 */
	public int count() {
		return mSetRendezVous.size();
	}
	
	/**
	 * Sauver l'agenda dans un fichier
	 * @param dest Fichier de destination
	 */
	public void saveTo(File dest) {
		RendezVousFactory.getIntance().save(mSetRendezVous, dest);		
	}

	/**
	 * Charger un agenda depuis un fichier
	 * @param source Fichier source
	 */
	public void loadFrom(File source) {
		mSetRendezVous = RendezVousFactory.getIntance().load(source);
		update();
	}
	
	/**
	 * Mettre à jour le tableau d'affichage
	 */
	private void update() {
		tableModel.updateData(mFirstDate, mEndDate, mSetRendezVous);
	}
}
