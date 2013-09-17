package controleur;

import modele.RendezVous;

/**
 * Commande représentant la suppression d'un rendez vous
 * @author daniel
 */
public class CommandRemoveRendezVous extends Command {

	/**
	 * Rendez vous supprimé
	 */
	private RendezVous mRendezVous;
	
	/**
	 * Constructeur
	 * @param rendezVous Rendez vous à supprimer
	 */
	public CommandRemoveRendezVous(RendezVous rendezVous) {
		mRendezVous = rendezVous;
	}
	
	/**
	 * Exécute la suppression du rendez vous
	 */
	@Override
	public void execute() {
		Agenda.getInstance().remove(mRendezVous);
	}
	
	/**
	 * Annule la suppression, soit réajoute le rendez vous
	 */
	@Override
	public void undo() {
		Agenda.getInstance().add(mRendezVous);
	}

}
