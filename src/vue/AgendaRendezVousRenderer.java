/**
 * 
 */
package vue;

import java.awt.Component;

import javax.swing.JTable;

import modele.RendezVous;


/**
 * Moteur de rendu des rendez vous
 * @author daniel
 */

@SuppressWarnings("serial")
public class AgendaRendezVousRenderer extends AgendaTableCellRenderer {

	/**
	 * Constructeur
	 */
	public AgendaRendezVousRenderer() {
		super();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		if (value == null)
			return null;	
		
		RendezVous rendezVous = (RendezVous) value;
		mLabel.setText(rendezVous.getTitle());
		mLabel.setBackground(rendezVous.getColor());
		return mLabel;
	}
	
	
}
