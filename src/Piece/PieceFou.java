package Piece;

public class PieceFou extends Piece{
	
	/**
	 * Constructeur du Fou
	 * @param couleur : Couleur du Fou
	 * @param ligne : Ligne de la grille
	 * @param colonne : Colonne de la grille
	 */
	public PieceFou(boolean couleur, int ligne, int colonne){
		super(Piece_nom_constantes.FOU, couleur, ligne, colonne);
	}
	
	/**
	 * Deplacement Diagonale
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @return Verifie si le fou se deplace en diagonale
	 */	
	public boolean deplacementValide(int l, int c){
		if(this.getLigne()>l && this.getColonne()>c)
			return (this.getLigne() - l) == (this.getColonne() - c);
		else if(this.getLigne()>l && this.getColonne()<c)
			return (this.getLigne() - l) == (c - this.getColonne());
		else if(this.getLigne()<l && this.getColonne()>c)
			return (l - this.getLigne()) == (this.getColonne() - c);
		else if(this.getLigne()<l && this.getColonne()<c)
			return (l - this.getLigne()) == (c - this.getColonne());
		return false;
	}	
}

