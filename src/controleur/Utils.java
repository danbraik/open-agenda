package controleur;

/**
 * Méthodes communes
 * @author daniel
 */
public class Utils {

	private static Utils mInstance;
	
	public static Utils getInstance() {
		if (mInstance == null)
			mInstance = new Utils();
		return mInstance;
	}
	
	private Utils() {
	}
	
	/**
	 * Mettre la première lettre en majuscule
	 * @param input Texte à traiter
	 * @return Texte dont la première lettre est en majuscule
	 */
	public String capitalize(String input) {
		if (input != null && input.length() > 0)
			input = input.substring(0, 1).toUpperCase() + input.substring(1);
		return input;
	}
	
}
