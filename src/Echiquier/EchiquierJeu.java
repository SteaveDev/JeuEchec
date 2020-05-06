package Echiquier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import Piece.Piece;
import Piece.PieceCavalier;
import Piece.PieceDame;
import Piece.PieceFou;
import Piece.PiecePion;
import Piece.PieceRoi;
import Piece.PieceTour;

public class EchiquierJeu{
	
	private Echiquier jeu;
	private Joueur j1;
	private Joueur j2;
	private static boolean TOUR, FIN_PARTIE = false;
	private static int NB_PIECE = 32, NB_TOUR = 0;
	
	
	public EchiquierJeu() {
		this.jeu = new Echiquier();
		this.jeu.initialiseEchiquier();
		this.j1 = new Joueur();
		this.j2 = new Joueur();
		TOUR = true;
	}
	
	public boolean saisieValide(int g_l, int g_c){
		return g_l>=0 && g_l<=7 && g_c>=0 && g_c<=7;
	}
	
	public int[] saisieDepart(){
		String ch1 = new String(), reponse = new String();
		boolean valide = false, couleur = false, sortie = false;
		int[] nb = new int[2]; nb[0]=-1; nb[1]=-1;
		int changement = -1;
		Piece p = null;
		
		if(verificationEchecMat()){
			System.out.println(String_Constantes.echec_affiche);
			return nb;
		}
		else if(verificationPat()){
			System.out.println(String_Constantes.pat_affiche);
			nb[0]=-2;
			return nb;
		}
	
		Scanner s = new Scanner(System.in);
		do{
			do{
				do{
					do{
						do{
							System.out.print("\t\t  Saisir les coordonnes de depart : ");
							ch1 = s.nextLine();
						}while(ch1.length()!=2 && !ch1.equals("abandon") && !ch1.equals("sauver") && !ch1.equals("charger") && !ch1.equals("quitter") );
						if(ch1.equals("abandon")){
							nb[0]=-3; sortie = true;
						}
						else if(ch1.equals("quitter")){
							do{
								System.out.print("\tVoulez vous sauvegarder cette partie ? (oui) / (non) : ");
								reponse = s.nextLine();
							}while(!reponse.equals("oui") && !reponse.equals("non"));
							if(reponse.equals("oui")){
								sauver("reprendre");
							}sortie = true;
							nb[0]=-2;
						}
						else{
							if(ch1.equals("sauver")){
								System.out.print("\t\t\tSaisir un nom de fichier : ");
								sauver(s.nextLine());
							}
							if(ch1.equals("charger"))
								charge();
							
							changement = Math.abs(((int)ch1.charAt(1)-48)-8);
							
							if(saisieValide(changement, ch1.charAt(0)-65))
								if(jeu.getGrille(changement, ch1.charAt(0)-65).getCaseRemplie())
									valide = true;
						}
					}while(!valide && !sortie);
					if(!ch1.equals("abandon") && !sortie)
						p = jeu.getGrille(changement, ch1.charAt(0)-65).getPiece();
				}while(p==null && !sortie);
				
				if(!ch1.equals("abandon") && !sortie)
				couleur  = p.getCouleurPiece();
			}while(couleur != TOUR && !sortie);
			if(!ch1.equals("abandon") && !sortie){
				autorise(changement, ch1.charAt(0)-65);
				parerEchecRoi(changement, ch1.charAt(0)-65);
			}
		}while(nbCaseAutorise()==0 && !sortie);
		//1-CARACTERES		2-DANS LA GRILLE		3-Case non vide		4-BONNE COULEURS	5-REFAIRE SI LA PIECE NE PEUT PAS BOUGER
		if(!sortie){
			nb[0]=changement;
			nb[1]=ch1.charAt(0)-65;
		}
		return nb;
	}
	
	public void saisieArrive(int g_l, int g_c){
		String ch1 = new String();
		boolean valide = false;
		int changement = -1;
		
		Scanner s = new Scanner(System.in);
		do{
			do{
				do{
					System.out.print("\t\t  Saisir les coordonnes d'arrive : ");
					ch1 = s.nextLine();	
				}while(ch1.length()!=2);	//LONGUEUR VALIDE
				changement = Math.abs(((int)ch1.charAt(1)-48)-8);
			}while(!saisieValide(changement, ch1.charAt(0)-65));	//SI BONNE COORDONNE

			if(jeu.getGrille(changement, ch1.charAt(0)-65).getCaseAutorise()){
				deplacerPiece(g_l, g_c, changement, ch1.charAt(0)-65);
				valide = true;
			}
		}while(!valide);
	}

	public void parerEchecRoi(int l, int c){
		int[] roi = trouverPieceRoi(), echec = new int[2];
		Piece echange = null;
		Piece p = jeu.getGrille(l, c).getPiece();
		jeu.getGrille(l, c).libererCase();
		echec = deplacementRoiEchec(roi[0], roi[1]);
		
		if(echec[0]!=-1){
			for(int g_l=0; g_l<8; g_l++){
				for(int g_c=0; g_c<8; g_c++){
					if(jeu.getGrille(g_l, g_c).getCaseAutorise()){
						if(jeu.getGrille(g_l, g_c).getCaseRemplie())
							echange = jeu.getGrille(g_l, g_c).getPiece();
						jeu.setGrille(new Case(p, g_l, g_c));
						
						roi = trouverPieceRoi();
						echec = deplacementRoiEchec(roi[0], roi[1]);
						
						if(echange!=null)
							jeu.setGrille(new Case(echange, g_l, g_c));
						else
							jeu.getGrille(g_l, g_c).libererCase();
						echange = null;
						
						if(echec[0]!=-1)
							jeu.getGrille(g_l, g_c).setCaseAutorise(false);
						else
							jeu.getGrille(g_l, g_c).setCaseAutorise(true);
					}
				}
			}
		}
		jeu.setGrille(new Case(p, l, c));
	}
	
	
	
	
	public boolean deplacerPiece(int g_l, int g_c, int l, int c){
		boolean estDeplace = false;
		
		try{			
			if(jeu.getGrille(g_l, g_c).getCaseRemplie()==false)
				System.out.println("\t\t\tPas de piece dans cette case");
			else if(jeu.getGrille(g_l, g_c).getPiece() instanceof PieceRoi){
				PieceRoi p = (PieceRoi)jeu.getGrille(g_l, g_c).getPiece();
				if(p.deplacementValide(l, c) && jeu.getGrille(l, c).getCaseAutorise()){
					if(g_c-2==c){
						PieceTour t1 = (PieceTour)jeu.getGrille(g_l, g_c-4).getPiece();
						deplacePieceIf(t1, g_l, g_c-4, g_l, g_c-1);
					}
					else if(g_c+2==c){
						PieceTour t2 = (PieceTour)jeu.getGrille(g_l, g_c+3).getPiece();
						deplacePieceIf(t2, g_l, g_c+3, g_l, g_c+1);
					}
					deplacePieceIf(p, g_l, g_c, l, c);
					estDeplace = true;
				}
				else{
					System.out.println("\t\t\tMouvement impossible pour le Roi");
				}
			}
			else if(jeu.getGrille(g_l, g_c).getPiece() instanceof PiecePion){
				PiecePion p = (PiecePion)jeu.getGrille(g_l, g_c).getPiece();
				if((p.deplacementValide(l, c) || p.deplacementValideVersus(l, c)) && jeu.getGrille(l, c).getCaseAutorise()){
					if(l==0 || l==7)
						promotionPion(p, g_l, g_c, l, c);
					else if((g_l==3 || g_l==4) && g_c-1>=0 && g_c-1==c && jeu.getGrille(g_l, g_c-1).getPiece() instanceof PiecePion && jeu.getGrille(g_l, g_c-1).getPiece().getMouvement()==1 && jeu.getGrille(g_l, g_c).getPiece().getCouleurPiece()!=jeu.getGrille(g_l, g_c-1).getPiece().getCouleurPiece())
							deplacePiecePrisePassant(p, g_l, g_c, l, c);
					else if((g_l==3 || g_l==4) && g_c+1<=7 && g_c+1==c && jeu.getGrille(g_l, g_c+1).getPiece() instanceof PiecePion && jeu.getGrille(g_l, g_c+1).getPiece().getMouvement()==1 && jeu.getGrille(g_l, g_c).getPiece().getCouleurPiece()!=jeu.getGrille(g_l, g_c+1).getPiece().getCouleurPiece())
							deplacePiecePrisePassant(p, g_l, g_c, l, c);
					else
						deplacePieceIf(p, g_l, g_c, l, c);
					estDeplace = true;
				}
				else
					System.out.println("\t\t\tMouvement impossible");
			}
			else{ // CAVALIER / FOU / TOUR / DAME
				Piece p = jeu.getGrille(g_l, g_c).getPiece();
				if(p.deplacementValide(l, c))
					if(jeu.getGrille(l,c).getCaseAutorise()){
						deplacePieceIf(p, g_l, g_c, l, c);
						estDeplace = true;
					}
				else
					System.out.println("\t\t\tMouvement impossible");
			}

		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("\t\t\tVous n'etes pas dans la grille");
		}
		
		return estDeplace;
	}
	
	public void deplacePieceIf(Piece p, int g_l, int g_c, int l, int c){
		if(jeu.getGrille(l, c).getCaseRemplie()){
			NB_PIECE--;
			NB_TOUR=0;
		}
		else
			NB_TOUR++;
		jeu.setGrille(new Case(p, l, c));
		jeu.getGrille(l, c).getPiece().setLigne(l);
		jeu.getGrille(l, c).getPiece().setColonne(c);
		jeu.getGrille(l, c).getPiece().setMouvement(jeu.getGrille(l, c).getPiece().getMouvement()+1);
		StringBuilder deplacement_affiche = new StringBuilder("\t   ┌───────────────────────────────────────────────────�?").append(System.lineSeparator()) //changement = Math.abs(((int)ch1.charAt(1)-48)-8);
				.append("\t   │       Deplacement de " + jeu.getGrille(g_l, g_c).getPiece() +  " : " + ((char)(g_c+65)) + Math.abs(g_l-8) + " vers " + ((char)(c+65)) + Math.abs(l-8) + "              │").append(System.lineSeparator())
				.append("\t   └───────────────────────────────────────────────────┘").append(System.lineSeparator());
		System.out.println(deplacement_affiche);
		jeu.getGrille(g_l, g_c).libererCase();
	}
	
	public void deplacePiecePrisePassant(Piece p, int g_l, int g_c, int l, int c){
		deplacePieceIf(p, g_l, g_c, l, c);
		if(g_l-1==l || g_l+1==l){
			if(g_c-1==c)
				jeu.getGrille(g_l, g_c-1).libererCase();
			else if(g_c+1==c)
				jeu.getGrille(g_l, g_c+1).libererCase();
		}
	}
	
	public void promotionPion(Piece p, int g_l, int g_c, int l, int c){
		Scanner s = new Scanner(System.in);
		int a=0;
		System.out.println(String_Constantes.promotion);
		do{
			System.out.print("\t\t\t   Choisissez :");
			a = s.nextInt();
		}while(a<0 || a>4);
		
		Piece p1;
		if(a==1)
			p1 = new PieceDame(p.getCouleurPiece(), p.getLigne(), p.getColonne());
		else if(a==2)
			p1 = new PieceTour(p.getCouleurPiece(), p.getLigne(), p.getColonne());
		else if(a==3)
			p1 = new PieceFou(p.getCouleurPiece(), p.getLigne(), p.getColonne());
		else
			p1 = new PieceCavalier(p.getCouleurPiece(), p.getLigne(), p.getColonne());
		deplacePieceIf(p1, g_l, g_c, l, c);
	}
	
	public void roiGrandRoque(int l, int c){
		boolean estVide = true, estEchec=false;
		int[] echec = new int[2];
		echec = deplacementRoiEchec(l, c);
		
		if(echec[0]==-1){
			if(jeu.getGrille(l, c).getPiece().getMouvement()==0){
				if(jeu.getGrille(l, c-4).getPiece() instanceof PieceTour){
					if(jeu.getGrille(l, c-4).getPiece().getMouvement()==0){
						if(jeu.getGrille(l, c).getPiece().getCouleurPiece()==jeu.getGrille(l, c-4).getPiece().getCouleurPiece()){
							for(int i=1; i<=3; i++){
								if(jeu.getGrille(l, c-i).getCaseRemplie()){
									estVide = false;
								}
								else{
									echec = deplacementRoiEchec(l, c-i);
									if(i<=2 && echec[0]!=-1)
										estEchec = true;
								}
							}
							if(estVide && !estEchec)
								jeu.getGrille(l, c-2).setCaseAutorise(true);
						}
					}
				}
			}
		}
	}
	
	public void roiPetitRoque(int l, int c){
		boolean estVide = true, estEchec=false;
		int[] echec = new int[2];
		echec = deplacementRoiEchec(l, c);
		
		if(echec[0]==-1){
			if(jeu.getGrille(l, c).getPiece().getMouvement()==0){
				if(jeu.getGrille(l, c+3).getPiece() instanceof PieceTour){
					if(jeu.getGrille(l, c+3).getPiece().getMouvement()==0){
						if(jeu.getGrille(l, c).getPiece().getCouleurPiece()==jeu.getGrille(l, c+3).getPiece().getCouleurPiece()){
							for(int i=1; i<=2; i++){
								if(jeu.getGrille(l, c+i).getCaseRemplie()){
									estVide = false;
								}
								else{
									echec = deplacementRoiEchec(l, c+i);
									if(echec[0]!=-1)
										estEchec = true;
								}
							}
							if(estVide && !estEchec)
								jeu.getGrille(l, c+2).setCaseAutorise(true);
						}
					}
				}
			}
		}
	}
	
	public int[] deplacementRoiEchec(int l, int c){
		int[] coordonne = new int[2], roi = trouverPieceRoi(); coordonne[0] = -1; coordonne[1] = -1;
		Piece p = jeu.getGrille(roi[0], roi[1]).getPiece();
		jeu.getGrille(roi[0], roi[1]).libererCase();
		boolean[] vide_tour = new boolean[4], vide_fou = new boolean[4];
		for(int i=0; i<4; i++){
			vide_tour[i]=true;
			vide_fou[i]=true;
		}
		
		for(int i=1; i<8; i++){
			if(l-i>=0 && vide_tour[0] && coordonne[0]==-1){
				if(jeu.getGrille(l-i,c).getCaseRemplie()){
					vide_tour[0] = false;
					Piece test_tourH = jeu.getGrille(l-i, c).getPiece();
					if(TOUR!=jeu.getGrille(l-i,c).getPiece().getCouleurPiece())
						if(test_tourH instanceof PieceTour || test_tourH instanceof PieceDame || (i==1 && test_tourH instanceof PieceRoi)){
							coordonne[0] = l-i;
							coordonne[1] = c;
						}
				}
			}
			if(l+i<=7 && vide_tour[1] && coordonne[0]==-1){
				if(jeu.getGrille(l+i,c).getCaseRemplie()){
					vide_tour[1] = false;
					Piece test_tourB = jeu.getGrille(l+i,c).getPiece();
					if(TOUR!=jeu.getGrille(l+i,c).getPiece().getCouleurPiece())
						if(test_tourB instanceof PieceTour || test_tourB instanceof PieceDame || (i==1 && test_tourB instanceof PieceRoi)){
							coordonne[0] = l+i;
							coordonne[1] = c;
						}
				}
			}
			if(c-i>=0 && vide_tour[2] && coordonne[0]==-1){
				if(jeu.getGrille(l,c-i).getCaseRemplie()){
					vide_tour[2] = false;
					Piece test_tourG = jeu.getGrille(l,c-i).getPiece();
					if(TOUR!=jeu.getGrille(l,c-i).getPiece().getCouleurPiece())
						if(test_tourG instanceof PieceTour || test_tourG instanceof PieceDame || (i==1 && test_tourG instanceof PieceRoi)){
							coordonne[0] = l;
							coordonne[1] = c-i;
						}
				}
			}
			if(c+i<=7 && vide_tour[3] && coordonne[0]==-1){
				if(jeu.getGrille(l,c+i).getCaseRemplie()){
					vide_tour[3] = false;
					Piece test_tourD = jeu.getGrille(l,c+i).getPiece();
					if(TOUR!=jeu.getGrille(l,c+i).getPiece().getCouleurPiece())
						if(test_tourD instanceof PieceTour || test_tourD instanceof PieceDame || (i==1 && test_tourD instanceof PieceRoi)){
							coordonne[0] = l;
							coordonne[1] = c+i;
						}
				}
			}
			if(l-i>=0 && c-i>=0 && vide_fou[0] && coordonne[0]==-1){
				if(jeu.getGrille(l-i,c-i).getCaseRemplie()){
					vide_fou[0] = false;
					Piece test_fouHG = jeu.getGrille(l-i,c-i).getPiece();
					if(TOUR!=jeu.getGrille(l-i,c-i).getPiece().getCouleurPiece()){
						if(test_fouHG instanceof PieceFou || test_fouHG instanceof PieceDame || (i==1 && test_fouHG instanceof PieceRoi) || (i==1 && test_fouHG instanceof PiecePion && test_fouHG.getCouleurPiece()==false)){
							coordonne[0] = l-i;
							coordonne[1] = c-i;
						}
					}
				}
			}
			if(l-i>=0 && c+i<=7 && vide_fou[1] && coordonne[0]==-1){
				if(jeu.getGrille(l-i, c+i).getCaseRemplie()){
					vide_fou[1] = false;
					Piece test_fouHD = jeu.getGrille(l-i, c+i).getPiece();
					if(TOUR!=jeu.getGrille(l-i, c+i).getPiece().getCouleurPiece()){
						if(test_fouHD instanceof PieceFou || test_fouHD instanceof PieceDame || (i==1 && test_fouHD instanceof PieceRoi) || (i==1 && test_fouHD instanceof PiecePion && test_fouHD.getCouleurPiece()==false)){
							coordonne[0] = l-i;
							coordonne[1] = c+i;
						}
					}
				}
			}
			if(l+i<=7 && c-i>=0 && vide_fou[2] && coordonne[0]==-1){
				if(jeu.getGrille(l+i,c-i).getCaseRemplie()){
					vide_fou[2] = false;
					Piece test_fouBG = jeu.getGrille(l+i,c-i).getPiece();
					if(TOUR!=jeu.getGrille(l+i, c-i).getPiece().getCouleurPiece()){
						if(test_fouBG instanceof PieceFou || test_fouBG instanceof PieceDame || (i==1 && test_fouBG instanceof PieceRoi) || (i==1 && test_fouBG instanceof PiecePion && test_fouBG.getCouleurPiece()==true)){
							coordonne[0] = l+i;
							coordonne[1] = c-i;
						}
					}
				}
			}
			if(l+i<=7 && c+i<=7 && vide_fou[3] && coordonne[0]==-1){
				if(jeu.getGrille(l+i,c+i).getCaseRemplie()){
					vide_fou[3] = false;
					Piece test_fouBD = jeu.getGrille(l+i,c+i).getPiece();
					if(TOUR!=jeu.getGrille(l+i, c+i).getPiece().getCouleurPiece()){
						if(test_fouBD instanceof PieceFou || test_fouBD instanceof PieceDame || (i==1 && test_fouBD instanceof PieceRoi) || (i==1 && test_fouBD instanceof PiecePion && test_fouBD.getCouleurPiece()==true)){
							coordonne[0] = l+i;
							coordonne[1] = c+i;
						}
					}
				}
			}
		}
		
		
		for(int i=-2; i<=2; i++)
			for(int j=-2; j<=2; j++)
				if(Math.abs((double)i/(double)j)==0.5 || Math.abs((double)i/(double)j)==2.0)
					if(saisieValide(l+i, c+j))
						if(jeu.getGrille(l+i, c+j).getCaseRemplie())
							if(jeu.getGrille(l, c).getPiece() instanceof PieceCavalier)
								if(TOUR != jeu.getGrille(l+i, c+j).getPiece().getCouleurPiece()){
									coordonne[0] = l-1;
									coordonne[1] = c-2;
								}

		jeu.setGrille(new Case(p, roi[0], roi[1]));
		return coordonne;
	}
	
	
	
	public void autorise(int l, int c){
		if(saisieValide(l, c)){
			if(jeu.getGrille(l, c).getPiece() instanceof PieceRoi)
				autoriseRoi(l, c);
			else if(jeu.getGrille(l,c).getPiece() instanceof PieceCavalier)
				autoriseCavalier(l, c);
			else if(jeu.getGrille(l,c).getPiece() instanceof PieceFou)
				autoriseFou(l, c);
			else if(jeu.getGrille(l,c).getPiece() instanceof PieceTour)
				autoriseTour(l, c);
			else if(jeu.getGrille(l,c).getPiece() instanceof PiecePion)
				autorisePion(l, c);
			else if(jeu.getGrille(l,c).getPiece() instanceof PieceDame){
				autoriseFou(l, c);
				autoriseTour(l, c);
			}
		}
	}

	public void autoriseRoi(int l, int c){
		try{
			int[] echec = new int[2];
			
			if(jeu.getGrille(l, c).getPiece().getMouvement()==0){
				if(l==jeu.getGrille(l,c).getLigne() && c-4>=0)
					roiGrandRoque(l, c);
				if(l==jeu.getGrille(l,c).getLigne() && c+3<=7)
					roiPetitRoque(l, c);
			}
		
			Piece roi = jeu.getGrille(l, c).getPiece();
			for(int g_l=-1; g_l<=1; g_l++){
				for(int g_c=-1; g_c<=1; g_c++){
					if(saisieValide(l+g_l, c+g_c)){
						if(roi.deplacementValide(g_l+l, g_c+c)){
							echec = deplacementRoiEchec(g_l+l, g_c+c);
							if(echec[0]==-1){
								if(!jeu.getGrille(g_l+l,g_c+c).getCaseRemplie())
									jeu.getGrille(g_l+l,g_c+c).setCaseAutorise(true);
								else if(!(jeu.getGrille(g_l+l,g_c+c).getPiece() instanceof PieceRoi)){
									if(jeu.getGrille(l, c).getPiece().getCouleurPiece()!=jeu.getGrille(g_l+l, g_c+c).getPiece().getCouleurPiece())
										jeu.getGrille(g_l+l,g_c+c).setCaseAutorise(true);
								}
							}
						}
					}
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public void autoriseCavalier(int l, int c){
		try{
			Piece p = jeu.getGrille(l,c).getPiece();
			Piece cavalier = new PieceCavalier(p.getCouleurPiece(), p.getLigne(), p.getColonne());
			for(int i=0; i<8; i++){
				for(int j=0; j<8; j++){
					if(cavalier.deplacementValide(i, j)){
						if(!jeu.getGrille(i,j).getCaseRemplie())
							jeu.getGrille(i,j).setCaseAutorise(true);
						else if(!(jeu.getGrille(i,j).getPiece() instanceof PieceRoi)){
							if(p.getCouleurPiece()!=jeu.getGrille(i,j).getPiece().getCouleurPiece())
								jeu.getGrille(i,j).setCaseAutorise(true);
						}
					}
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public void autoriseFou(int l, int c){
		try{
			Piece p = jeu.getGrille(l, c).getPiece();
			boolean[] vide = new boolean[4];
			for(int i=0; i<4; i++)
				vide[i]=true;
			
			for(int i=1; i<8; i++){
				if(l-i>=0 && c-i>=0 && vide[0]){
					if(!jeu.getGrille(l-i,c-i).getCaseRemplie())
						jeu.getGrille(l-i,c-i).setCaseAutorise(true);
					else{
						if(!(jeu.getGrille(l-i,c-i).getPiece() instanceof PieceRoi))
							if(p.getCouleurPiece()!=jeu.getGrille(l-i,c-i).getPiece().getCouleurPiece())
								jeu.getGrille(l-i,c-i).setCaseAutorise(true);
						vide[0]=false;
					}
				}
				if(l-i>=0 && c+i<=7 && vide[1]){
					if(!jeu.getGrille(l-i,c+i).getCaseRemplie())
						jeu.getGrille(l-i,c+i).setCaseAutorise(true);
					else{
						if(!(jeu.getGrille(l-i,c+i).getPiece() instanceof PieceRoi))
							if(p.getCouleurPiece()!=jeu.getGrille(l-i,c+i).getPiece().getCouleurPiece())
								jeu.getGrille(l-i,c+i).setCaseAutorise(true);
						vide[1]=false;
					}
				}
				if(l+i<=7 && c-i>=0 && vide[2]){
					if(!jeu.getGrille(l+i,c-i).getCaseRemplie())
						jeu.getGrille(l+i,c-i).setCaseAutorise(true);
					else{
						if(!(jeu.getGrille(l+i,c-i).getPiece() instanceof PieceRoi))
							if(p.getCouleurPiece()!=jeu.getGrille(l+i,c-i).getPiece().getCouleurPiece())
								jeu.getGrille(l+i,c-i).setCaseAutorise(true);
						vide[2]=false;
					}
				}
				if(l+i<=7 && c+i<=7 && vide[3]){
					if(!jeu.getGrille(l+i,c+i).getCaseRemplie())
						jeu.getGrille(l+i,c+i).setCaseAutorise(true);
					else{
						if(!(jeu.getGrille(l+i,c+i).getPiece() instanceof PieceRoi))
							if(p.getCouleurPiece()!=jeu.getGrille(l+i,c+i).getPiece().getCouleurPiece())
								jeu.getGrille(l+i,c+i).setCaseAutorise(true);
						vide[3]=false;
					}
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public void autoriseTour(int l, int c){
		try{
			
			Piece p = jeu.getGrille(l, c).getPiece();
			boolean[] vide = new boolean[4];
			for(int i=0; i<4; i++)
				vide[i]=true;
		
			for(int i=1; i<8; i++){
				if(l-i>=0 && vide[0]){
					if(!jeu.getGrille(l-i,c).getCaseRemplie())
						jeu.getGrille(l-i,c).setCaseAutorise(true);
					else if(jeu.getGrille(l-i,c).getCaseRemplie()){
						if(!(jeu.getGrille(l-i,c).getPiece() instanceof PieceRoi))
							if(p.getCouleurPiece()!=jeu.getGrille(l-i,c).getPiece().getCouleurPiece())
								jeu.getGrille(l-i,c).setCaseAutorise(true);
						vide[0]=false;
					}
				}
				if(l+i<=7 && vide[1]){
					if(!jeu.getGrille(l+i,c).getCaseRemplie())
						jeu.getGrille(l+i,c).setCaseAutorise(true);
					else if(jeu.getGrille(l+i,c).getCaseRemplie()){
						if(!(jeu.getGrille(l+i,c).getPiece() instanceof PieceRoi))
							if(p.getCouleurPiece()!=jeu.getGrille(l+i, c).getPiece().getCouleurPiece())
								jeu.getGrille(l+i,c).setCaseAutorise(true);
						vide[1]=false;
					}
				}
				if(c-i>=0 && vide[2]){
					if(!jeu.getGrille(l,c-i).getCaseRemplie())
						jeu.getGrille(l,c-i).setCaseAutorise(true);
					else if(jeu.getGrille(l,c-i).getCaseRemplie()){
						if(!(jeu.getGrille(l,c-i).getPiece() instanceof PieceRoi))
							if(p.getCouleurPiece()!=jeu.getGrille(l,c-i).getPiece().getCouleurPiece())
								jeu.getGrille(l,c-i).setCaseAutorise(true);
						vide[2]=false;
					}
				}
				if(c+i<=7 && vide[3]){
					if(!jeu.getGrille(l,c+i).getCaseRemplie())
						jeu.getGrille(l,c+i).setCaseAutorise(true);
					else if(jeu.getGrille(l,c+i).getCaseRemplie()){
						if(!(jeu.getGrille(l,c+i).getPiece() instanceof PieceRoi))
							if(p.getCouleurPiece()!=jeu.getGrille(l, c+i).getPiece().getCouleurPiece())
								jeu.getGrille(l,c+i).setCaseAutorise(true);
						vide[3]=false;
					}
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public void autorisePion(int l, int c){
		try{
			Piece p = jeu.getGrille(l, c).getPiece();
			if(jeu.getGrille(l,c).getPiece().getMouvement()==0){
				if(jeu.getGrille(l,c).getPiece().getCouleurPiece()){
					if(!jeu.getGrille(l-1,c).getCaseRemplie()){
						jeu.getGrille(l-1,c).setCaseAutorise(true);
						if(!jeu.getGrille(l-2,c).getCaseRemplie())
							jeu.getGrille(l-2,c).setCaseAutorise(true);
					}
					if(c-1>=0)
						if(jeu.getGrille(l-1,c-1).getCaseRemplie())
							if(p.getCouleurPiece()!=jeu.getGrille(l-1,c-1).getPiece().getCouleurPiece())
								if(!(jeu.getGrille(l-1,c-1).getPiece() instanceof PieceRoi))
									jeu.getGrille(l-1,c-1).setCaseAutorise(true);
					if(c+1<=7)
						if(jeu.getGrille(l-1,c+1).getCaseRemplie())
							if(p.getCouleurPiece()!=jeu.getGrille(l-1,c+1).getPiece().getCouleurPiece())
								if(!(jeu.getGrille(l-1,c+1).getPiece() instanceof PieceRoi))
									jeu.getGrille(l-1,c+1).setCaseAutorise(true);
				}
				else{
					if(!jeu.getGrille(l+1,c).getCaseRemplie()){
						jeu.getGrille(l+1,c).setCaseAutorise(true);
						if(!jeu.getGrille(l+2,c).getCaseRemplie())
							jeu.getGrille(l+2,c).setCaseAutorise(true);
					}
					if(c-1>=0)
						if(jeu.getGrille(l+1,c-1).getCaseRemplie())
							if(p.getCouleurPiece()!=jeu.getGrille(l+1,c-1).getPiece().getCouleurPiece())
								if(!(jeu.getGrille(l+1,c-1).getPiece() instanceof PieceRoi))
									jeu.getGrille(l+1,c-1).setCaseAutorise(true);
					if(c+1<=7)
						if(jeu.getGrille(l+1,c+1).getCaseRemplie())
							if(p.getCouleurPiece()!=jeu.getGrille(l+1,c+1).getPiece().getCouleurPiece())
								if(!(jeu.getGrille(l+1,c+1).getPiece() instanceof PieceRoi))
									jeu.getGrille(l+1,c+1).setCaseAutorise(true);
				}
			}
			//APRES MOUVEMENT
			else{
				if(jeu.getGrille(l,c).getPiece().getCouleurPiece()==true){
					if(l-1>=0 && !jeu.getGrille(l-1,c).getCaseRemplie())
						jeu.getGrille(l-1,c).setCaseAutorise(true);
					if(l-1>=0 && c-1>=0 && jeu.getGrille(l-1,c-1).getCaseRemplie())
						if(p.getCouleurPiece()!=jeu.getGrille(l-1, c-1).getPiece().getCouleurPiece())
							if(!(jeu.getGrille(l-1,c-1).getPiece() instanceof PieceRoi))
								jeu.getGrille(l-1,c-1).setCaseAutorise(true);
					if(l-1>=0 && c+1<=7 && jeu.getGrille(l-1,c+1).getCaseRemplie())
						if(p.getCouleurPiece()!=jeu.getGrille(l-1, c+1).getPiece().getCouleurPiece())
							if(!(jeu.getGrille(l-1,c+1).getPiece() instanceof PieceRoi))
								jeu.getGrille(l-1,c+1).setCaseAutorise(true);
					if(l==3){
						if(c-1>=0 && jeu.getGrille(l, c-1).getCaseRemplie()){
							if(jeu.getGrille(l, c-1).getPiece() instanceof PiecePion)
								if(p.getCouleurPiece()!=jeu.getGrille(l, c-1).getPiece().getCouleurPiece())
									if(jeu.getGrille(l, c-1).getPiece().getMouvement()==1)
										if(!jeu.getGrille(l-1, c-1).getCaseRemplie())
											jeu.getGrille(l-1, c-1).setCaseAutorise(true);
						}
						if(c+1<=7 && jeu.getGrille(l, c+1).getCaseRemplie()){
							if(jeu.getGrille(l, c+1).getPiece() instanceof PiecePion)
								if(p.getCouleurPiece()!=jeu.getGrille(l, c+1).getPiece().getCouleurPiece())
									if(jeu.getGrille(l, c+1).getPiece().getMouvement()==1)
										if(!jeu.getGrille(l-1, c+1).getCaseRemplie())
											jeu.getGrille(l-1, c+1).setCaseAutorise(true);
						}
						
					}
				}
				else{
					if(l+1<=7 && !jeu.getGrille(l+1,c).getCaseRemplie())
						jeu.getGrille(l+1,c).setCaseAutorise(true);
					if(l+1<=7 && c-1>=0 && jeu.getGrille(l+1,c-1).getCaseRemplie())
						if(p.getCouleurPiece()!=jeu.getGrille(l+1, c-1).getPiece().getCouleurPiece())
							if(!(jeu.getGrille(l+1,c-1).getPiece() instanceof PieceRoi))
								jeu.getGrille(l+1,c-1).setCaseAutorise(true);
					if(l+1<=7 && c+1<=7 && jeu.getGrille(l+1,c+1).getCaseRemplie())
						if(p.getCouleurPiece()!=jeu.getGrille(l+1, c+1).getPiece().getCouleurPiece())
						if(!(jeu.getGrille(l+1,c+1).getPiece() instanceof PieceRoi))
							jeu.getGrille(l+1,c+1).setCaseAutorise(true);
					if(l==4){
						if(c-1>=0 && jeu.getGrille(l, c-1).getCaseRemplie()){
							if(jeu.getGrille(l, c-1).getPiece() instanceof PiecePion)
								if(p.getCouleurPiece()!=jeu.getGrille(l, c-1).getPiece().getCouleurPiece())
								if(jeu.getGrille(l, c-1).getPiece().getMouvement()==1)
									if(!jeu.getGrille(l+1, c-1).getCaseRemplie())
										jeu.getGrille(l+1, c-1).setCaseAutorise(true);
						}
						if(c+1<=7 && jeu.getGrille(l, c+1).getCaseRemplie()){
							if(jeu.getGrille(l, c+1).getPiece() instanceof PiecePion)
								if(p.getCouleurPiece()!=jeu.getGrille(l, c+1).getPiece().getCouleurPiece())
								if(jeu.getGrille(l, c+1).getPiece().getMouvement()==1)
									if(!jeu.getGrille(l+1, c+1).getCaseRemplie())
										jeu.getGrille(l+1, c+1).setCaseAutorise(true);
						}
					}
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}

	public int nbCaseAutorise(){
		int nb=0;
		for(int l=0; l<8; l++)
			for(int c=0; c<8; c++)
				if(jeu.getGrille(l, c).getCaseAutorise())
					nb++;
		return nb;
	}

	public void libererCaseAutorise(){
		for(int l=0; l<8; l++)
			for(int c=0; c<8; c++)
				jeu.getGrille(l, c).setCaseAutorise(false);
	}
	
	
	
	public int[] trouverPieceRoi(){
		int[] roi = new int[2];
		for(int l=0; l<8; l++)
			for(int c=0; c<8; c++)
				if(jeu.getGrille(l, c).getCaseRemplie())
					if(jeu.getGrille(l, c).getPiece() instanceof PieceRoi)
						if(jeu.getGrille(l, c).getPiece().getCouleurPiece()==TOUR){
							roi[0] = l;
							roi[1] = c;
						}
		return roi;
	}
	
	public boolean verificationEchecMat(){
		boolean estEchec = false;
		int[] roi = trouverPieceRoi();
		int[] echec = new int[2];
		echec = deplacementRoiEchec(roi[0], roi[1]);
		
		if(echec[0]!=-1){
			System.out.println(String_Constantes.roi_est_echec);
			estEchec = true;
			for(int l=0; l<8; l++){
				for(int c=0; c<8; c++){
					if(jeu.getGrille(l, c).getCaseRemplie()){
						if(jeu.getGrille(l, c).getPiece().getCouleurPiece()==TOUR){
							autorise(l, c);
							parerEchecRoi(l, c);
							if(nbCaseAutorise()!=0)
								estEchec = false;
							libererCaseAutorise();
						}
					}
				}
			}
		}
		return estEchec;
	}
	
	public boolean verificationPat(){
		int nb = 0;
		for(int l=0; l<8; l++){
			for(int c=0; c<8; c++){
				if(jeu.getGrille(l, c).getCaseRemplie()){
					if(jeu.getGrille(l, c).getPiece().getCouleurPiece()==TOUR){
						autorise(l, c);
					if(nbCaseAutorise()!=0)
							nb++;
						libererCaseAutorise();
					}
				}
			}
		}
		
		if(nb==0 || NB_PIECE==2 || NB_TOUR == 50)
			return true;
		return false;
	}

	
	
	
	public void sauver(String nomFichier){
		try{
			FileWriter fichier = new FileWriter(nomFichier+".txt");
			BufferedWriter ecriture = new BufferedWriter(fichier);
			String texte_grille = new String();
			for(int l=0; l<8; l++){
				for(int c=0; c<8; c++){
					if(jeu.getGrille(l, c).getCaseRemplie())
						texte_grille = texte_grille + jeu.getGrille(l, c).getPiece() + "\t" + jeu.getGrille(l, c).getPiece().getMouvement() + "\t";
					else
						texte_grille = texte_grille + "--\t--\t";
				}
			}
			texte_grille = texte_grille + NB_TOUR + "\t";
			if(TOUR)
				texte_grille = texte_grille + "blanc\t";
			else
				texte_grille = texte_grille + "noir\t";
			ecriture.write(texte_grille);
			ecriture.close();
			fichier.close();
			System.out.println("\t\t    Vous avez sauvegardé avec succès");
		}catch(IOException e){
			e.printStackTrace();
		}
	}	
	
	public void charge(){
		FileReader fr = null;
		try{
			boolean existe = false;
			String nom = new String();
			do{
				Scanner s = new Scanner(System.in);
				System.out.print("\t\t\tSaisir un fichier : ");
				nom = s.nextLine() + ".txt";
				File nomFichier = new File(nom);
				if(nomFichier.exists())
					existe = true;
			}while(!existe);
			fr = new FileReader(nom);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		try{
			line = br.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		while(line!=null){
			StringTokenizer st = new StringTokenizer(line,"\t");
			Echiquier jeu_charge = new Echiquier();
			String information = new String();
			Piece piece_tampon;
			boolean couleur_tampon;
			char type, couleur;
			int nbPiece = 0;

			for(int l=0; l<8; l++){
				for(int c=0; c<16; c++){//PREMIERE TABULATION
					information = st.nextToken();
					couleur = information.charAt(1);
					if(couleur=='b')
						couleur_tampon = true;
					else
						couleur_tampon = false;
					
					type = information.charAt(0);
					if(type=='R')
						piece_tampon = new PieceRoi(couleur_tampon, l, (c/2));
					else if(type=='D')
						piece_tampon = new PieceDame(couleur_tampon, l, (c/2));
					else if(type=='T')
						piece_tampon = new PieceTour(couleur_tampon, l, (c/2));
					else if(type=='F')
						piece_tampon = new PieceFou(couleur_tampon, l, (c/2));
					else if(type=='C')
						piece_tampon = new PieceCavalier(couleur_tampon, l, (c/2));
					else if(type=='P')
						piece_tampon = new PiecePion(couleur_tampon, l, (c/2));
					else
						piece_tampon = null;
					
					c++;//DEUXIEME TABULATION
					information = st.nextToken();
					if(piece_tampon!=null){
						piece_tampon.setMouvement((information.charAt(0)-48));
						jeu_charge.setGrille(new Case(piece_tampon, l, ((c-1)/2)));
						nbPiece++;
					}
					else
						jeu_charge.setGrille(new Case(l, ((c-1)/2)));
				}
			}
			information = st.nextToken();//NB_TOUR
			if(information.length()==2)
				NB_TOUR = ((information.charAt(0)-48)*10) + information.charAt(1)-48;
			else
				NB_TOUR = information.charAt(0)-48;
			information = st.nextToken();//COULEUR JOUEUR QUI A SAUVER
			if(information.equals("blanc") && TOUR==false){
				TOUR = true;
				if(TOUR==this.j1.getCouleur())
					System.out.println(String_Constantes.joueur1);
				else
					System.out.println(String_Constantes.joueur2);
			}
			if(information.equals("noir") && TOUR==true){
				TOUR = false;
				if(TOUR==this.j1.getCouleur())
					System.out.println(String_Constantes.joueur1);
				else
					System.out.println(String_Constantes.joueur2);
			}
			NB_PIECE = nbPiece;
			jeu = jeu_charge;
			System.out.println(jeu.toString());
			try{
				line=br.readLine();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	public void IA(){
		
		Case[] deplacements = new Case[16], dep_manger_niv1 = new Case[16], arr_manger_niv1 = new Case[16];
		int deplacements_taille = 0, coordonne_taille = 0, manger_niv1_taille = 0;
		int nb = 0;
		
		//ON CHERCHE LES PIECES NOIRES
		for(int l=0; l<8; l++){
			for(int c=0; c<8; c++){
				if(jeu.getGrille(l, c).getCaseRemplie()){
					if(jeu.getGrille(l, c).getPiece().getCouleurPiece()==TOUR){
						autorise(l, c);
						parerEchecRoi(l, c);
						if(nbCaseAutorise()!=0){
							deplacements[deplacements_taille] = new Case(jeu.getGrille(l, c).getPiece(), l, c);
							deplacements_taille++;
						}
						libererCaseAutorise();
					}
				}
			}
		}
		
		//SI PERSONNE NE PEUT BOUGER -> ON VERIFIE SI IL Y A UNE VICTOIRE OU UN MATCH NUL
		if(deplacements_taille==0){
			int[] roi = trouverPieceRoi(), echec = new int[2];
			echec = deplacementRoiEchec(roi[0], roi[1]);
			if(echec[0]!=-1)
				System.out.println("ECHEC ET MAT");
			else
				System.out.println("MATCH NUL");
			FIN_PARTIE = true;
		}
		else{
			int l_1, c_1;
			for(int i=0; i<deplacements_taille; i++){
				l_1 = deplacements[i].getLigne();
				c_1 = deplacements[i].getColonne();
				for(int l=0; l<8; l++){
					for(int c=0; c<8; c++){
						autorise(l_1, c_1);
						if(jeu.getGrille(l, c).getCaseAutorise()){
							if(jeu.getGrille(l, c).getCaseRemplie()){
								dep_manger_niv1[manger_niv1_taille] = new Case(jeu.getGrille(l_1, c_1).getPiece(), l_1, c_1);
								arr_manger_niv1[manger_niv1_taille] = new Case(jeu.getGrille(l, c).getPiece(), l, c);
								manger_niv1_taille++;
							}
						}
						libererCaseAutorise();
					}
				}
			}
			
			if(manger_niv1_taille!=0){
				manger_niv1_taille--;
				nb = (int)(Math.random() * (manger_niv1_taille - 0 + 1));
				
				Piece dep_p = dep_manger_niv1[nb].getPiece();
				Piece arr_p = arr_manger_niv1[nb].getPiece();
				int dep_ligne = dep_p.getLigne(), dep_colonne = dep_p.getColonne();
				int arr_ligne = arr_p.getLigne(), arr_colonne = arr_p.getColonne();
				autorise(dep_ligne, dep_colonne);
				deplacerPiece(dep_ligne, dep_colonne, arr_ligne, arr_colonne);
				libererCaseAutorise();
			}
			else{
				deplacements_taille--;
				nb = (int)(Math.random() * (deplacements_taille - 0 + 1));
				Piece p = deplacements[nb].getPiece();
				int ligne = p.getLigne(), colonne = p.getColonne();
				//System.out.println("Piece choisi : " + p + " en " + ligne + "," + colonne);
				//ON CHOISIT LA CASE
				autorise(ligne, colonne);
				parerEchecRoi(ligne, colonne);
				//ON SAUVE TOUT LES CHEMINS POSSIBLES
				Case[] coordonne_valide = new Case[27];
				for(int l=0; l<8; l++){
					for(int c=0; c<8; c++){
						if(jeu.getGrille(l, c).getCaseAutorise()){
							coordonne_valide[coordonne_taille] = new Case(l,c);
							coordonne_taille++;
						}
					}
				}
				//ON GENERE UN NOMBRE ALEATOIRE ENTRE 0 et NB_TOTAL_CHEMIN-1
				coordonne_taille--;
				int coordonne_aleatoire = (int)(Math.random() * (coordonne_taille - 0 + 1));
				int alea_l = coordonne_valide[coordonne_aleatoire].getLigne();
				int alea_c = coordonne_valide[coordonne_aleatoire].getColonne();
				//ON DEPLACE
				deplacerPiece(ligne, colonne, alea_l, alea_c);
				
				libererCaseAutorise();
				//System.out.println(jeu.toString());
			}
			
		}
	}
	
	
	
	public void jouerJeu(){
		System.out.println(String_Constantes.jeu_affiche);
		Scanner s = new Scanner(System.in);
		int choix = 0;
		do{
			System.out.print(String_Constantes.choix_affiche + "\t\t\t Faites votre choix : ");
			choix = s.nextInt();
			if(choix==3)
				System.out.println(String_Constantes.regle);
		}while(choix!=1 && choix!=2 && choix!=4);
		
		if(choix!=4){
			EchiquierJeu jouer = new EchiquierJeu();
			int[] tab = new int[2];
		
			if(choix==1){
				
				int couleur;
				System.out.print(String_Constantes.choix_couleur + "\n\t\t\t Faites votre choix : ");
				do{
					couleur = s.nextInt();
				}while(couleur<0 || couleur>3);
				
				boolean choix_couleur = true;
				if(couleur==2)
					choix_couleur = false;
				else if(couleur==3){
					int choix_aleatoire = (int)(Math.random() * (2));
					if(choix_aleatoire==1)
						choix_couleur = false;
				}
				jouer.j1 = new Joueur("Steave", choix_couleur);
				jouer.j2 = new Joueur("Kevin", !choix_couleur);
				
				if(choix==1)
					System.out.println(jouer);
				
				do{
					if(TOUR==jouer.j1.getCouleur())
						System.out.println(String_Constantes.joueur1);
					else
						System.out.println(String_Constantes.joueur2);
						
						tab = jouer.saisieDepart();
						
						if(tab[0]==-1 || tab[0]==-2 || tab[0]==-3){//-1 ECHEC -2 PAT.MATCH NUL -3 ABANDON
							FIN_PARTIE = true;
						}
						else{
							System.out.println(jouer);
							jouer.saisieArrive(tab[0], tab[1]);
							jouer.libererCaseAutorise();
							System.out.println(jouer);
							TOUR = !TOUR;
						}
				}while(!FIN_PARTIE);
				
				if(tab[0]!=-2){
					if(TOUR==jouer.j1.getCouleur())
						System.out.println(String_Constantes.victoire_joueur2);
					else
						System.out.println(String_Constantes.victoire_joueur1);
				}
				
			}
			else{
				do{
					if(TOUR){
						System.out.println(String_Constantes.joueur1);
						System.out.println(jouer);
						tab = jouer.saisieDepart();
						if(tab[0]==-1 || tab[0]==-2 || tab[0]==-3){//-1 ECHEC -2 PAT.MATCH NUL -3 ABANDON
							FIN_PARTIE = true;
						}
						else{
							System.out.println(jouer);
							jouer.saisieArrive(tab[0], tab[1]);
							jouer.libererCaseAutorise();
							System.out.println(jouer);
							TOUR = !TOUR;
						}
					}
					else{
						System.out.println(String_Constantes.bot);
						jouer.IA();
						TOUR = !TOUR;
					}
				}while(!FIN_PARTIE);
				
				if(tab[0]!=-2){
					if(TOUR==j1.getCouleur())
						System.out.println(String_Constantes.victoire_bot);
					else
						System.out.println(String_Constantes.victoire_joueur1);
				}
			}
		}
		
		System.out.println(String_Constantes.fin_prgm);
	}
	
	public String toString(){
		return jeu.toString();
	}
}
	