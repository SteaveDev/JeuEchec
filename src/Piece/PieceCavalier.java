package Piece;

public class PieceCavalier extends Piece{
	
	/**
	 * Constructeur du Cavalier
	 * @param couleur : Couleur du Cavalier
	 * @param ligne : Ligne de la grille
	 * @param colonne : Colonne de la grille
	 */
	public PieceCavalier(boolean couleur, int l, int c){
		super(Piece_nom_constantes.CAVALIER, couleur, l, c);
	}

	/**
	 * Deplacement en L
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @return Verifie si le cavalier se deplace en L
	 */
	public boolean deplacementValide(int l, int c) {
		return (this.getLigne()-2==l && this.getColonne()+1==c) || 
				(this.getLigne()-1==l && this.getColonne()+2==c) || 
				(this.getLigne()+1==l && this.getColonne()+2==c) ||
				(this.getLigne()+2==l && this.getColonne()+1==c) ||
				(this.getLigne()+2==l && this.getColonne()-1==c) ||
				(this.getLigne()+1==l && this.getColonne()-2==c) ||
				(this.getLigne()-1==l && this.getColonne()-2==c) ||
				(this.getLigne()-2==l && this.getColonne()-1==c)				
				;
	}
}
