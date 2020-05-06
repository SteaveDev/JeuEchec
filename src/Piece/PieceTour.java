package Piece;

public class PieceTour extends Piece{
	
	/**
	 * Constructeur de la Tour
	 * @param couleur : Couleur de la Tour
	 * @param ligne : Ligne de la grille
	 * @param c : Colonne de la grille
	 */
	public PieceTour(boolean couleur, int ligne, int colonne){
		super(Piece_nom_constantes.TOUR, couleur, ligne, colonne);
	}

	/**
	 * Deplacement Haut/Bas -- Gauche/Droit
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @return Verifie si la tour se deplace à l'horizontale et à la verticale
	 */
	public boolean deplacementValide(int l, int c){
		return (this.getLigne()>=0 && this.getLigne()<=7 && this.getColonne()==c) ||
				(this.getLigne()==l && this.getColonne()>=0 && this.getColonne()<=7);
	}
}