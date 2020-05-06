package Piece;

public class PieceRoi extends Piece{
	
	/**
	 * Constructeur du Roi
	 * @param couleur : Couleur du Fou
	 * @param ligne : Ligne de la grille
	 * @param colonne : Colonne de la grille
	 */
	public PieceRoi(boolean couleur, int ligne, int colonne){
		super(Piece_nom_constantes.ROI, couleur, ligne, colonne);
	}

	/**
	 * Deplacement autour de lui
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @return Verifie si le roi se deplace a une ligne et/ou colonne de lui
	 */
	public boolean deplacementValide(int l, int c){
		if(this.getMouvement() == 0)
			return (this.getLigne()==l && (this.getColonne()-2==c || this.getColonne()+2==c)) ||
					((this.getLigne()-1==l || this.getLigne()+1==l || this.getLigne()==l) && (this.getColonne()==c || this.getColonne()-1==c || this.getColonne()+1==c));
		else
			return (this.getLigne()-1==l || this.getLigne()+1==l || this.getLigne()==l) && (this.getColonne()==c || this.getColonne()-1==c || this.getColonne()+1==c);
	}
}