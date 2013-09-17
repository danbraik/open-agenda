/**
 * 
 */
package vue;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe proposant une base pour afficher du texte dans une cellule
 * de manière centré et sur un fond éventuellement coloré
 * @author daniel
 */
@SuppressWarnings("serial")
public abstract class AgendaTableCellRenderer extends DefaultTableCellRenderer {
	
	/**
	 * Label renvoyé pour l'affichage
	 */
	protected JLabel mLabel;
	
	/**
	 * Constructeur
	 */
	public AgendaTableCellRenderer() {
		mLabel = new JLabel();
		mLabel.setOpaque(true);
		mLabel.setForeground(Color.BLACK);
		mLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
}
