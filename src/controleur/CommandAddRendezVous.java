package controleur;

import modele.RendezVous;

/**
 * Commande représentant l'ajout d'un rendez vous
 * @author daniel
 */
public class CommandAddRendezVous extends Command {
	/**
	 * Rendez vous créé
	 */
	private RendezVous mRendezVous;
	
	/**
	 * Constructeur
	 * @param rendezVous Nouveau rendez vous à ajouter
	 */
	public CommandAddRendezVous(RendezVous rendezVous) {
		mRendezVous = new RendezVous(rendezVous);
	}
	
	/**
	 * Exécute l'ajout du rendez vous
	 */
	@Override
	public void execute() {
		Agenda.getInstance().add(mRendezVous);
	}

	/**
	 * Annule l'ajout, soit supprime le rendez vous
	 */
	@Override
	public void undo() {
		Agenda.getInstance().remove(mRendezVous);
	}
}
