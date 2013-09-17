package vue;

import modele.RendezVous;

/**
 * Classe permettant de communiquer avec une RendezVousDialog
 * @author daniel
 *
 */
public class RendezVousDialogArg {

	/**
	 * Rendez vous à éditer
	 */
	private RendezVous mRendezVous;
	/**
	 * Indique si l'utilisateur a validé les modifications
	 */
	private boolean mSubmitted;
	
	/**
	 * Constructeur
	 * @param rendezVous Rendez vous à éditer
	 */
	public RendezVousDialogArg(RendezVous rendezVous) {
		this.mRendezVous = rendezVous;
		this.mSubmitted = false;
	}

	/**
	 * Constructeur
	 */
	public RendezVousDialogArg() {
		this(null);
	}

	/**
	 * Obtenir le rendez vous
	 * @return the rendezVous
	 */
	public RendezVous getRendezVous() {
		return mRendezVous;
	}
	
	/**
	 * Affecter le rendez vous à éditer
	 * @param rendezVous
	 */
	public void setRendezVous(RendezVous rendezVous) {
		this.mRendezVous = rendezVous;
	}

	/**
	 * Indique si l'utilisateur a validé la saisie
	 * @return the submitted
	 */
	public boolean isSubmitted() {
		return mSubmitted;
	}

	/**
	 * Spécifier que l'utilisateut a validé
	 */
	public void setSubmitted() {
		this.mSubmitted = true;
	}

	
	
	
	
}
