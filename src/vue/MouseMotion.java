package vue;

/**
 * Classe permettant de gérer le déplacement de la souris
 * @author daniel
 *
 */
public class MouseMotion {
	/**
	 * Lignes et colonnes de départ
	 * et d'arrivée de la souris
	 */
	public int row1, col1, row2, col2;
	/**
	 * Indique si le déplacement est valide.
	 */
	private boolean mValid;

	/**
	 * Constructeur
	 */
	public MouseMotion() {
		mValid = false;
	}
	
	/**
	 * Indique si la souris a changé de cellule
	 * @return True si elle a bougé, False sinon
	 */
	public boolean hasMoved() {
		return (row1 != row2) || (col1 != col2);
	}
	
	/**
	 * Indique si le déplacement est valide
	 * càd s'il on a un départ et une arrivée
	 * @return True si c'est valide, False sinon
	 */
	public boolean isValid() {
		return mValid;
	}
	
	/**
	 * Spécifier les coordonnées de départ
	 * @param row Ligne de départ
	 * @param col Colonne de départ
	 */
	public void setFirstRowCol(int row, int col) {
		row1 = row;
		col1 = col;
		mValid = false;
	}
	
	/**
	 * Spécifier les coordonnées d'arrivée
	 * @param row Ligne d'arrivée
	 * @param col Colonne d'arrivée
	 */
	public void setSecondRowCol(int row, int col) {
		if (col < col1 || (col == col1 && row < row1)) {
			// permutation
			col2 = col1;
			col1 = col;
			row2 = row1;
			row1 = row;
		} else {
			row2 = row;
			col2 = col;
		}
		mValid = true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"TableSelection [%s, %s, %s, %s]",
				row1, col1, row2, col2);
	}
}
