/**
 * 
 */
package modele;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Gérer la sauvegarde et le chargement d'un agenda
 * @author daniel
 */
public class RendezVousFactory {

	/**
	 * Instance unique
	 */
	private static RendezVousFactory mInstance;

	/**
	 * Obtenir l'instance unique
	 * @return L'instance
	 */
	public static RendezVousFactory getIntance() {
		if (mInstance == null)
			mInstance = new RendezVousFactory();
		return mInstance;
	}

	/**
	 * Constructeur privé
	 */
	private RendezVousFactory() {
	}
	
	/**
	 * Obtenir un ensemble vide de rendez vous
	 * @return Ensemble vide de rendez vous
	 */
	public List<RendezVous> getEmptySet() {
		return new LinkedList<RendezVous>();
	}

	/**
	 * Sauvegarder un agenda dans un fichier
	 * @param setRendezVous Ensemble de rendez vous
	 * @param destination Fichier de destination
	 */
	public void save(List<RendezVous> setRendezVous, File destination) {
		Collections.sort(setRendezVous, RendezVousComparator.getInstance());
		try {
			XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream(destination)));
			for (RendezVous rendezVous : setRendezVous) {
				e.writeObject(rendezVous);
			}
			e.close();
		} catch (FileNotFoundException e) {
			System.err.println("When saving agenda : " + e.getMessage());
		}
	}
	
	/**
	 * Charger un agenda depuis un fichier
	 * @param source Fichier source
	 * @return Ensemble des rendez vous
	 */
	public List<RendezVous> load(File source) {
		List<RendezVous> set = getEmptySet();
		XMLDecoder xmlDecoder = null;
		try {
			xmlDecoder = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(source)));
			Object readObj;
			while ((readObj = xmlDecoder.readObject()) != null) {
				try {
					set.add((RendezVous) readObj);
				} catch (ClassCastException e) {
					System.err.println("When loading agenda : " + e.getMessage());
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// do nothing because of XMLDecoder.readObject() documentation :
			/*
			 Throws:
    			ArrayIndexOutOfBoundsException 
    			- if the stream contains no objects (or no more objects)
			 */
		} catch (FileNotFoundException e) {
			System.err.println("When loading agenda : " + e.getMessage());
		} finally {
			if (xmlDecoder != null)
				xmlDecoder.close();
		}
		Collections.sort(set, RendezVousComparator.getInstance());
		return set;
	}

	

}
