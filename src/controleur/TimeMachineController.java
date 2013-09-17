/**
 * 
 */
package controleur;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Contrôleur associé au TimeMachinePane.
 * Permet de convertir une cellule en date et inversement
 * @author daniel
 */
public class TimeMachineController  {

	/**
	 * Année et semaine visualisés
	 */
	private int mYear, mWeek;
	/**
	 * Calendrier utile aux calculs
	 */
	private Calendar mCal;
	/**
	 * Date de début de la période visualisée
	 */
	private Date mFirstDate;
	/**
	 * Date de fin (exclusive) de la période visualisée
	 */
	private Date mEndDate;
	
	/**
	 * Constructeur
	 */
	public TimeMachineController() {
		mCal = new GregorianCalendar();
		setView(mCal.get(Calendar.YEAR), 
				mCal.get(Calendar.WEEK_OF_YEAR));
	}
	
	/**
	 * Définir l'année et la semaine de la période visualisée
	 * @param year Année
	 * @param week Semaine
	 */
	public void setView(int year, int week) {
		mYear = year;
		mWeek = week;
		upDates();
		
		Agenda.getInstance().setFirstDate(getFirstDate(), getEndDate());
	}
	
	/**
	 * Obtenir la date de début de la période visualisée
	 * @return Date de début
	 */
	public Date getFirstDate() {
		return mFirstDate;
	}
	
	/**
	 * Obtenir la date de fin de la période visualisée
	 * @return Date de fin
	 */
	public Date getEndDate() {
		return mEndDate;
	}
	
	/**
	 * Obtenir la date après tant de jours et d'heures
	 * après le début de la période visualisée
	 * @param nbDays Nombre de jours
	 * @param nbHours Nombre d'heures
	 * @return Date calculée
	 */
	public Date getAfter(int nbDays, int nbHours) {
		mCal.clear();
		mCal.setTime(getFirstDate());
		
		mCal.add(Calendar.DAY_OF_MONTH, nbDays);
		mCal.add(Calendar.HOUR_OF_DAY, nbHours);
		return mCal.getTime();
	}
	
	
	/**
	 * Obtenir un calendrier pointant sur le début
	 * de la période visualisée
	 * @return Calendrier pointant sur la période
	 */
	public Calendar getCal() {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.setTime(getFirstDate());
		return cal;
	}
	
	
	
	/**
	 * Recalculer les dates de début et de fin
	 */
	private void upDates() {
		mCal.clear();
		
		mCal.set(Calendar.YEAR, mYear);
		mCal.set(Calendar.WEEK_OF_YEAR, mWeek);
		mCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		mFirstDate = mCal.getTime();
		
		mCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		mCal.add(Calendar.HOUR_OF_DAY, 24); // end is actually the sunday's end
		
		mEndDate = mCal.getTime();
	}
	
	/**
	 * Obtenir le mois de la période visualisée
	 * @return Nom du mois
	 */
	public String getMonth() {
		mCal.setTime(getFirstDate());
		
		if (mWeek == 1) {
			mCal.set(Calendar.MONTH, Calendar.JANUARY);
		}
		return mCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
	}
}
