package Piece;

public class PieceDame extends Piece{
	
	/**
	 * Constructeur de la Dame
	 * @param couleur : Couleur de la Dame
	 * @param ligne : Ligne de la grille
	 * @param c : Colonne de la grille
	 */
	public PieceDame(boolean couleur, int l, int c){
		super(Piece_nom_constantes.DAME, couleur, l, c);
	}	
	
	/**
	 * Deplacement Diagonale -- Haut/Bas -- Gauche/Droite
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @return Verifie si la dame se deplace en diagonale, à l'horizontal ou à la verticale
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
		
		return (this.getLigne()>=0 && this.getLigne()<=7 && this.getColonne()==c) ||
				(this.getLigne()==l && this.getColonne()>=0 && this.getColonne()<=7);
	}
}
