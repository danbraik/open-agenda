package vue;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

/**
 * Moteur de rendu des heures
 * @author daniel
 */
@SuppressWarnings("serial")
public class AgendaHourRenderer extends AgendaTableCellRenderer {

	/**
	 * Constructeur
	 */
	public AgendaHourRenderer() {
		super();
		mLabel.setBackground(Color.WHITE);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		mLabel.setText(value.toString());
		return mLabel;
	}
}
