package Echiquier;

import Piece.*;

public class Case {
	private Piece piece;
	private int ligne;
	private int colonne;
	private boolean caseRemplie;
	private boolean caseAutorise;

	/**
	 * Constructeur champ a champ - Initialisation d'une case sans piece
	 * @param l Ligne de case
	 * @param c Colonne de la case
	 */
	public Case(int l, int c){
		this.setLigne(l);
		this.setColonne(c);
		this.setCaseRemplie(false);
		this.setPiece(null);
		this.setCaseAutorise(false);
	}
	
	/**
	 * Constructeur champ a champ - Initialisation d'une case avec une piece
	 * @param p Piece dans la case
	 * @param l Ligne de case
	 * @param c Colonne de la case
	 */
	public Case(Piece p, int l, int c/*, int couleur*/){
		this.setLigne(l);
		this.setColonne(c);
		this.setCaseRemplie(true);
		this.setPiece(p);
		this.setCaseAutorise(false);
	}
	
	/**
	 * Libere la case apres un mouvement
	 */
	public void libererCase(){
		this.setPiece(null);
		this.setCaseRemplie(false);
		this.setCaseAutorise(false);
	}
	
	/**
	 * 
	 * @return le type de la piece qui est dans la case
	 */
	public Piece getPiece() {
		return this.piece;
	}

	/**
	 * Mettre une case dans une case
	 * @param piece Piece a inserer dans la case
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	
	/**
	 * 
	 * @return La ligne de la case actuelle
	 */
	public int getLigne() {
		return this.ligne;
	}
	/**
	 * Ligne de la case
	 * @param ligne
	 */
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	/**
	 * 
	 * @return La colonne de la case actuelle
	 */
	public int getColonne() {
		return this.colonne;
	}

	/**
	 * Colonne de la case
	 * @param colonne
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	/**
	 * Verifie si il y a une piece dans la case
	 * @return true -> case remplie / else -> case non remplie
	 */
	public boolean getCaseRemplie() {
		return this.caseRemplie;
	}

	/**
	 * On insere ou on enleve une piece de la case
	 * @param b true -> on insere un piece / false -> on enleve la piece
	 */
	public void setCaseRemplie(boolean b) {
		this.caseRemplie = b;
	}
	
	/**
	 * 
	 * @return On peut se deplacer ici
	 */
	public boolean getCaseAutorise() {
		return caseAutorise;
	}

	/**
	 * On autorise l'acces ou non l'acces a cette case
	 * @param caseAutorise true -> on peut venir / false -> on ne peut pas venir
	 */
	public void setCaseAutorise(boolean caseAutorise) {
		this.caseAutorise = caseAutorise;
	}

	/**
	 * On affiche le nom de la piece + la couleur de la piece
	 */
	public String toString(){
		if(this.piece instanceof Piece) //Object
			return piece.toString();
		return "  ";
	}
}
