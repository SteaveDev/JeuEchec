package Piece;

public class PiecePion extends Piece{

	/**
	 * Constructeur du Pion
	 * @param couleur : Couleur du Pion
	 * @param ligne : Ligne de la grille
	 * @param colonne : Colonne de la grille
	 */	
	public PiecePion(boolean couleur, int ligne, int colonne){
		super(Piece_nom_constantes.PION, couleur, ligne, colonne);
	}

	/**
	 * Deplacement en avant pour les blancs et en arrière pour les noirs
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @return Verifie si le pion peut avancer d'une ou deux cases en meme temps
	 */
	public boolean deplacementValide(int l, int c){
		if(this.getMouvement() == 0)
			if(this.getCouleurPiece()==true)
				return ((this.getLigne()-2==l || this.getLigne()-1==l) && this.getColonne()==c);
			else
				return (this.getLigne()+2==l || this.getLigne()+1==l) && this.getColonne()==c;
		else
			if(this.getCouleurPiece()==true)
				return this.getLigne()-1==l && this.getColonne()==c;
			else
				return this.getLigne()+1==l && this.getColonne()==c;
	}
	
	/**
	 * Deplacement en diagonale pour les blancs/noirs
	 * @param l : Ligne de la piece
	 * @param c : Colonne de la piece
	 * @return Verifie si le pion peut avancer d'une ou deux cases en meme temps
	 */
	public boolean deplacementValideVersus(int l, int c){
		if(this.getCouleurPiece()==true){
			if(this.getMouvement() == 0)
				return this.getLigne()-1==l && (this.getColonne()-1==c || this.getColonne()+1==c);
			return this.getLigne()-1==l && (this.getColonne()==c || this.getColonne()-1==c || this.getColonne()+1==c);
		}
		else{
			if(this.getMouvement() == 0)
				return this.getLigne()+1==l && (this.getColonne()-1==c || this.getColonne()+1==c);
			return this.getLigne()+1==l && (this.getColonne()==c || this.getColonne()-1==c || this.getColonne()+1==c) ;
		}
	}
}
