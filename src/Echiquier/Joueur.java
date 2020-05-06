package Echiquier;

public class Joueur {
	private String nom;
	private boolean couleur;
	
	public Joueur(){
		this.setNom("defaut");
		this.setCouleur(true);
	}
	
	/**
	 * Constructeur Champ a champ
	 * @param n Nom du joueur
	 * @param c Couleur du joueur
	 */
	public Joueur(String n, boolean c){
		this.setNom(n);
		this.setCouleur(c);
	}
	
	/**
	 * 
	 * @return Le nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Affecte un nom au joueur
	 * @param nom Le nom du joueur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * 
	 * @return true -> joueur blanc / false -> joueur noir
	 */
	public boolean getCouleur() {
		return couleur;
	}

	/**
	 * Affecte une couleur a un joueur
	 * @param couleur true -> blanc / false -> noir
	 */
	public void setCouleur(boolean couleur) {
		this.couleur = couleur;
	}
}