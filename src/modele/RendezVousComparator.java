package modele;

import java.util.Comparator;

/**
 * Comparateur de rendez vous externe
 * @author daniel
 */
public class RendezVousComparator implements Comparator<RendezVous> {

	/**
	 * Instance unique
	 */
	private static RendezVousComparator mInstance;

	/**
	 * Obtenir l'instance unique
	 * @return L'instance
	 */
	public static RendezVousComparator getInstance() {
		if (mInstance == null)
			mInstance = new RendezVousComparator();
		return mInstance;
	}
	
	/**
	 * Constructeur privé
	 */
	private RendezVousComparator() {
	}
	
	/**
	 * Comparer deux rendez vous.
	 * Ordre : date de début, puis date de fin
	 * @param rdv1 Premier rendez vous
	 * @param rdv2 Second rendez vous
	 * @return Différence de position entre les deux rendez vous
	 */
	@Override
	public int compare(RendezVous rdv1, RendezVous rdv2) {
		// divise pour avoir une précision à la minute seulement
		int r = (int)((rdv1.getBegin().getTime() - rdv2.getBegin().getTime()) / (1000*60));
		if (r == 0)
			r = (int)((rdv1.getEnd().getTime() - rdv2.getEnd().getTime()) / (1000*60));
		return r;
	}

}
