package Echiquier;

public interface String_Constantes {
	
	public static StringBuilder jeu_affiche = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                            JEU D'ECHEC                              │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	
	public static StringBuilder choix_affiche = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                       1 - Joueur vs Joueur                          │").append(System.lineSeparator())
			.append("  │                       2 - Joueur vs IA                              │").append(System.lineSeparator())
			.append("  │                       3 - Règles du jeu                             │").append(System.lineSeparator())
			.append("  │                       4 - Quitter                                   │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘").append(System.lineSeparator());
	
	
	public static StringBuilder regle = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                             Nos règles                              │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │               Avant de saisir votre coup, vous pouvez :             │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                 <sauver>  : Sauvegarder une partie                  │").append(System.lineSeparator())
			.append("  │                 <charger> : Charger une partie                      │").append(System.lineSeparator())
			.append("  │                 <abandon> : Abandonner une partie                   │").append(System.lineSeparator())
			.append("  │                 <quitter> : Quitter une partie                      │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │               ───────────────────────────────────────               │").append(System.lineSeparator())
			.append("  │                 Pour charger votre dernière partie :                │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                 <charger> : Puis taper <reprendre>                  │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │               ───────────────────────────────────────               │").append(System.lineSeparator())
			.append("  │               Déplacements des pièces sur le plateau :              │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                  <A-H><1-8> : Saisie des coordonnées                │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │             ROI : Il peut aller a une case autour de lui            │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                DAME : Mouvements de la tour et du fou               │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │              TOUR : Mouvements verticaux et horizontaux             │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                    FOU : Mouvements diagonaux                       │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                     CAVALIER : Mouvements en L                      │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │             PION : Avance d'une case en ligne droite , il           │").append(System.lineSeparator())
			.append("  │                peut d'une ou deux cases au premier tour             │").append(System.lineSeparator())
			.append("  │                  Il peut aussi capturer en diagonale                │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │               ───────────────────────────────────────               │").append(System.lineSeparator())
			.append("  │                            BUT DU JEU :                             │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │            Le but du jeu est de faire un ECHEC ET MAT :             │").append(System.lineSeparator())
			.append("  │             - Le roi ne peut plus se déplacer                       │").append(System.lineSeparator())
			.append("  │             - Aucune piece allies ne peut parer l'echec             │").append(System.lineSeparator())
			.append("  │             - On ne peut manger la piece qui met en echec           │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │               ───────────────────────────────────────               │").append(System.lineSeparator())
			.append("  │                            MATCH NUL :                              │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │             PAT : Le roi n'est pas en echec, et aucune              │").append(System.lineSeparator())
			.append("  │                      piece ne peut se déplacer                      │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │            Lorsqu'il n'y a pas assez de piece pour mater            │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │            50 tours se sont joués sans manger de piece              │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘").append(System.lineSeparator());
	
	
	public static StringBuilder fin_prgm = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                          FIN DU PROGRAMME                           │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘").append(System.lineSeparator());

	public static StringBuilder echec_affiche = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                            ECHEC ET MAT                             │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder pat_affiche = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  │                             MATCH NUL                               │").append(System.lineSeparator())
			.append("  │                                                                     │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder roi_est_echec = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                            ECHEC DU ROI                             │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder joueur1 = new StringBuilder("       *************************************************************").append(System.lineSeparator())
			.append("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                          TOUR DE : STEAVE                           │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder joueur2 = new StringBuilder("       *************************************************************").append(System.lineSeparator())
			.append("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                          TOUR DE : KEVIN                            │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder bot = new StringBuilder("       *************************************************************").append(System.lineSeparator())
			.append("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                           TOUR DE : IA                              │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder victoire_joueur1 = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                         VICTOIRE DE STEAVE                          │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder victoire_joueur2 = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                          VICTOIRE DE KEVIN                          │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder victoire_bot = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                          VICTOIRE DE IA                             │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder promotion = new StringBuilder("  ┌─────────────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("  │                       PROMOTION DE VOTRE PION                       │").append(System.lineSeparator())
			.append("  │                          1 - Dame                                   │").append(System.lineSeparator())
			.append("  │                          2 - Tour                                   │").append(System.lineSeparator())
			.append("  │                          3 - Fou                                    │").append(System.lineSeparator())
			.append("  │                          4 - Cavalier                               │").append(System.lineSeparator())
			.append("  └─────────────────────────────────────────────────────────────────────┘");
	
	public static StringBuilder choix_couleur = new StringBuilder("      ┌─────────────────────────────────────────────────────────────┐").append(System.lineSeparator())
			.append("      │                   CHOIX DE VOTRE COULEUR                    │").append(System.lineSeparator())
			.append("      │                      1 - Blanc                              │").append(System.lineSeparator())
			.append("      │                      2 - Noir                               │").append(System.lineSeparator())
			.append("      │                      3 - Aleatoire                          │").append(System.lineSeparator())
			.append("      └─────────────────────────────────────────────────────────────┘");
	
}
