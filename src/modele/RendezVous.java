package modele;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Décrit un rendez vous dans l'agenda
 * @author daniel
 *
 */
public class RendezVous {
	
	/**
	 * Titre du rendez vous
	 */
	private String mTitle;
	/**
	 * Jour et heure du début du rendez vous
	 */
	private Date mBegin;
	/**
	 * Jour et heure de fin du rendez vous
	 */
	private Date mEnd;
	/**
	 * Description (facultative) du rendez vous
	 */
	private String mSummary;
	/**
	 * Couleur d'affichage du rendez vous
	 */
	private Color mColor;
	
	/** 
	 * Constructeur complet
	 * @param title Titre
	 * @param begin Date de début
	 * @param end Date de fin
	 * @param summary Description
	 * @param color Couleur d'affichage
	 */
	public RendezVous(String title, Date begin, Date end, String summary, Color color) {
		setTitle(title);
		setBegin(begin);
		setEnd(end);
		setSummary(summary);
		setColor(color);
	}
	
	/** 
	 * Constructeur
	 * @param title Titre
	 * @param begin Date de début
	 * @param end Date de fin
	 * @param summary Description
	 */
	public RendezVous(String title, Date begin, Date end, String summary) {
		this(title, begin, end, summary, Color.LIGHT_GRAY);
	}

	/** 
	 * Constructeur
	 * @param title Titre
	 * @param begin Date de début
	 * @param end Date de fin
	 */
	public RendezVous(String title, Date begin, Date end) {
		this(title, begin, end, "");
	}

	/** 
	 * Constructeur
	 * @param begin Date de début
	 * @param end Date de fin
	 */
	public RendezVous(Date begin, Date end) {
		this("", begin, end, "");
	}
	

	/**
	 * Constructeur par défaut
	 */
	public RendezVous() {
		this(new Date(0), new Date(0));
	}

	/**
	 * Constructeur par copie
	 * @param rendezVous Rendez vous d'origine
	 */
	public RendezVous(RendezVous rendezVous) {
		this(rendezVous.getTitle(),
				rendezVous.getBegin(),
				rendezVous.getEnd(),
				rendezVous.getSummary(),
				rendezVous.getColor());
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		mTitle = title;
	}

	/**
	 * @return the begin
	 */
	public Date getBegin() {
		return mBegin;
	}

	/**
	 * @param begin the begin to set
	 */
	public void setBegin(Date begin) {
		mBegin = begin;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return mEnd;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		mEnd = end;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return mSummary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		mSummary = summary;
	}
	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return mColor;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		mColor = color;
	}
	
	/**
	 * Copier tous les attributs d'un autre rendez-vous
	 * @param other Rendez-vous modèle
	 */
	public void setByCopy(RendezVous other) {
		setTitle(other.getTitle());
		setBegin(other.getBegin());
		setEnd(other.getEnd());
		setSummary(other.getSummary());
		setColor(other.getColor());
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return mBegin.hashCode();
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RendezVous))
			return false;
		RendezVous other = (RendezVous) obj;
		if (mBegin == null) {
			if (other.mBegin != null)
				return false;
		} else if (!mBegin.equals(other.mBegin))
			return false;
		if (mEnd == null) {
			if (other.mEnd != null)
				return false;
		} else if (!mEnd.equals(other.mEnd))
			return false;
		if (mTitle == null) {
			if (other.mTitle != null)
				return false;
		} else if (!mTitle.equals(other.mTitle))
			return false;
		return true;
	}

	// utile au debug pour l'affichage simplifié d'un rendez vous
	static SimpleDateFormat sdf = new SimpleDateFormat("Ed MMMyyyy '('HH'h'mm')'");

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("RendezVous [").append(getTitle())
				.append(", du ").append(sdf.format(getBegin()))
				.append(" au ").append(sdf.format(getEnd()));
		if (!getSummary().isEmpty())
				builder.append(". ").append((getSummary().length() < 14 ? getSummary() : getSummary().substring(0, 9)+"..."));
		builder.append("]");
		return builder.toString();
	}
	
	

	
}
