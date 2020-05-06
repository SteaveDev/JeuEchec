package Piece;

public abstract class Piece {
	
	private String nom;
	private int ligne;
	private int colonne;
	private boolean couleurPiece;
	private int mouvement;
	
	/**
	 * 
	 * Constructeur champ à champ
	 * @param nomPiece : Nom de la piece
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @param couleur : Couleur de la piece
	 */
	public Piece(String nomPiece, boolean couleur, int l, int c){
		this.setNom(nomPiece);
		this.setCouleurPiece(couleur);
		this.setLigne(l);
		this.setColonne(c);
		this.setMouvement(0);
	}
	
	/**
	 * 
	 * Constructeur par copie
	 * @param p : Piece à copier
	 */
	public Piece(Piece p){
		this.setNom(new String(p.getNom()));
		this.setCouleurPiece(p.getCouleurPiece());
		this.setLigne(p.getLigne());
		this.setColonne(p.getColonne());
		this.mouvement=p.getMouvement();
	}
	
	/**
	 * 
	 * @return Nom de la piece
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * 
	 * @return Ligne de la piece
	 */
	public int getLigne(){
		return this.ligne;
	}
	
	/**
	 * 
	 * @return Colonne de la piece
	 */
	public int getColonne(){
		return this.colonne;
	}
	
	/**
	 * 
	 * @return Couleur de la piece (blanc ou noir)
	 */
	public boolean getCouleurPiece() {
		return this.couleurPiece;
	}
	
	/**
	 * 
	 * @return Si la piece s'est déplacée
	 */
	public int getMouvement(){
		return this.mouvement;
	}
	
	/**
	 * 
	 * @param nomPiece : Nom de la piece
	 */
	public void setNom(String nomPiece) {
		this.nom = nomPiece;
	}
	
	/**
	 * 
	 * @param ligne : Ligne de la piece
	 */
	public void setLigne(int ligne){
		this.ligne = ligne;
	}

	/**
	 * 
	 * @param colonne : Colonne de la piece
	 */
	public void setColonne(int colonne){
		this.colonne = colonne;
	}
	
	/**
	 * 
	 * @param couleur : Couleur de la piece
	 */
	public void setCouleurPiece(boolean couleur) {
		this.couleurPiece = couleur;
	}
	
	/**
	 * La piece a bougé
	 */
	public void setMouvement(int mouvement){
		this.mouvement = mouvement;
	}
	
	/**
	 * 
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @return Verifie la piece concerne peut faire ce deplacement
	 */
	public abstract boolean deplacementValide(int l, int c);	

	/**
	 * On affiche le nom de la piece + la couleur de la piece
	 */
	public String toString(){
		if(this.couleurPiece == true)
			return this.getNom() + Piece_couleur_constantes.BLANC;
		return this.getNom() + Piece_couleur_constantes.NOIR;
	}
}
