package controleur;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import vue.MainFrame;

 
/**
 * Classe principale
 * @author daniel
 *
 */
public class Main {

	/**
	 * Nom du répertoire contenant la configuration
	 */
	private static final String CONFIG_FOLDER = ".open-agenda";
	/**
	 * Nom de la propriété contenant le chemin vers le dernier agenda ouvert
	 */
	public static final String SYSPROP_LATEST_AGENDA = "agenda.properties";

	/**
	 * Point d'entrée de l'application
	 * @param args Arguments
	 */
	public static void main(String[] args) {
		// récupère l'agenda à ouvrir
		File agenda = getOrRememberAgenda(args);
		
		new MainFrame(agenda);
	}

	/**
	 * Obtenir l'agenda spécifié ou récemment ouvert
	 * @param args Argument contenant éventuellement l'agenda à ouvrir
	 * @return L'agenda à ouvrir ou null s'il faut un nouvel agenda
	 */
	private static File getOrRememberAgenda(String[] args) {
		File agenda = null;
		
		// récupère le chemin sur la ligne de commande
		if (args.length > 0) {
			agenda = new File(args[0]);
			if (!agenda.exists()) {
				agenda = null;
				System.err.println("No such file : " + args[0]);
			}
		}

		// s'il n'y a rien dans les arguments, on lit la propriété
		// pour obtenir l'agenda récemment ouvert
		if (agenda == null) {
			String path = Main.getProperty(Main.SYSPROP_LATEST_AGENDA);
			if (!path.isEmpty()) {
				agenda = new File(path);
				if (!agenda.exists()) {
					agenda = null;
					Main.setProperty(Main.SYSPROP_LATEST_AGENDA, "");
					System.err.println("No such file : " + path);
				} else {
					Agenda.getInstance().loadFrom(agenda);
					// save property
					Main.setProperty(Main.SYSPROP_LATEST_AGENDA, agenda.getAbsolutePath());
				}
			}
		}
		return agenda;
	}
	
	
	/**
	 * Obtenir la valeur d'une propriété sauvegardée 
	 * @param key Nom de la propriété
	 * @return La valeur ou une chaîne vide
	 */
	public static String getProperty(String key) {
		String value = "";
		try {
			Scanner scanner = new Scanner(new FileReader(
					System.getProperty("user.home")+File.separator+CONFIG_FOLDER+File.separator+key));
			value = scanner.nextLine();
			scanner.close();
		} catch (SecurityException | IOException e) {
			System.err.println("When reading property : " + e.getMessage());
		}
		return value;
	}
	
	/**
	 * Définir ou redéfinir la valeur d'une propriété
	 * @param key Nom de la propriété
	 * @param value Valeur à affecter
	 */
	public static void setProperty(String key, String value) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(
					System.getProperty("user.home")+File.separator+CONFIG_FOLDER+File.separator+key));
			writer.println(value);
			writer.close();
		} catch (SecurityException | IOException e) {
			System.err.println("When writing property : " + e.getMessage());
		}
	}

}
