/**
 * 
 */
package controleur;

import modele.RendezVous;

/**
 * Commande représentant la modification d'un rendez vous
 * @author daniel
 *
 */
public class CommandModifyRendezVous extends Command {
	/**
	 * Instance du rendez vous modifié
	 */
	private RendezVous mRendezVousSource;
	/**
	 * Anciens attributs du rendez vous modifié
	 */
	private RendezVous mOldAttributes;
	/**
	 * Nouveaux attributs du rendez vous modifié
	 */
	private RendezVous mNewAttributes;
	
	/**
	 * Constructeur
	 * @param rendezVous Rendez vous à modifier
	 * @param newAttributes Nouveaux attributs
	 */
	public CommandModifyRendezVous(RendezVous rendezVous, RendezVous newAttributes) {
		mRendezVousSource = rendezVous;
		mNewAttributes = new RendezVous(newAttributes);
		mOldAttributes = new RendezVous(rendezVous);
	}
	
	/**
	 * Exécuter les modifications du rendez vous
	 */
	@Override
	public void execute() {
		mRendezVousSource.setByCopy(mNewAttributes);
		// indique que le rendez vous a été modifié
		Agenda.getInstance().setDirty(mRendezVousSource);
	}

	/**
	 * Annule les modifications
	 */
	@Override
	public void undo() {
		mRendezVousSource.setByCopy(mOldAttributes);
		Agenda.getInstance().setDirty(mRendezVousSource);
	}
}
