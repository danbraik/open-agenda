/**
 * 
 */
package controleur;

/**
 * Commande abstraite représentant une action dans le logiciel
 * @author daniel
 */
public abstract class Command {
	/**
	 * Exécuter la commande
	 */
	public abstract void execute();
	/**
	 * Annuler les effets de l'exécution
	 */
	public abstract void undo();
}
