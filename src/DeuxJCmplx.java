import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;


public class DeuxJCmplx

{

	private static final JFrame accueil = Cadres.accueil();
	private static final JLabel reponseOui = new JLabel("Oui ", JLabel.CENTER);
	private static final JLabel reponseNon = new JLabel("Non ", JLabel.CENTER);
	private static final JLabel choisissez = new JLabel("Choisissez votre question ", JLabel.CENTER);
	private static  JPanel[] panelsPersos; 
	private static final JFrame frame = Cadres.cadre(panelsPersos); 
	private static void repondreOui(){
		System.out.println("oui");
		reponseOui.setVisible(true);
		reponseNon.setVisible(false);
		choisissez.setVisible(false);
		frame.repaint();
	}
	private static void repondreNon(){
		System.out.println("non");
		reponseNon.setVisible(true);
		reponseOui.setVisible(false);
		choisissez.setVisible(false);
		frame.repaint(); 
	}
	private static <E> void assignerQuestion(JComboBox<E> box, Function<E,Boolean> question){
		box.addActionListener(new ActionListener() {     
				//	@Override
			public void actionPerformed(ActionEvent e) {

				if(question.apply((E)box.getSelectedItem()))
					{ repondreOui();

					} 
					else {
						repondreNon();
					}
				} 
			});
	}
// Fonction choix d'un nb aléatoire
	public static  int Choixalea(int nbPersos){
		Random random = new Random();
		int nb_Alea = random.nextInt(nbPersos);
		return nb_Alea;
	}
	public static void main(String[] args) 
	{ 	
	// menus 
		JMenuBar menu = new JMenuBar();						
		JMenu file = new JMenu("Fichier");	
		JMenu mode = new JMenu("Modes de jeu");				
		JMenu theme = new JMenu("Theme");					
		JMenu help = new JMenu("Aide");	
		JMenuItem newf = new JMenuItem("Nouvelle partie");
		JMenuItem load = new JMenuItem("Reprendre");
		JMenuItem quit = new JMenuItem("Sauvegarder");
		JMenuItem soloJ = new JMenuItem("1 personnage");
		JMenuItem deuxJ = new JMenuItem("2 personnages");
		JMenuItem aide = new JMenuItem("Aide");
		JMenuItem credit = new JMenuItem("Credits");
		JMenuItem readme = new JMenuItem( "Read Me");
		JMenuItem coldefaut= new JMenuItem("Par defaut");
		JMenuItem jaune= new JMenuItem("Jaune");
		JMenuItem rose= new JMenuItem("Rose");
		JMenuItem orange= new JMenuItem("Orange");
		JMenuItem vert= new JMenuItem("Vert");
		JMenuItem bleu= new JMenuItem("Bleu");

		help.add(readme);
		help.add(aide);
		help.add(credit);
		frame.add(menu);
		file.add(newf);
		file.add(load);
		file.add(quit);
		mode.add(soloJ);
		mode.add(deuxJ);
		theme.add(coldefaut);
		theme.add(jaune);
		theme.add(orange);
		theme.add(rose);
		theme.add(vert);
		theme.add(bleu);
		
		aide.addActionListener(e -> {JOptionPane.showMessageDialog(null , "BIENVENUE sur 'QUI-EST-CE'.\n Afin de deviner le sportif aléatoirement sélectionné, vous pouvez serez aidé de:\n-sa nationalité\n -son type de sport(individuel ou collectif) \n -sa catégorie d'âge \n -son genre(masculin ou féminin)\n -et sa pilosité.","menu d'aide",JOptionPane.INFORMATION_MESSAGE);});
		credit.addActionListener(e-> {JOptionPane.showMessageDialog(null," This game has been developped by: \n -Paul FONTAINE \n -Adam DAIA \n -Matthias BLANC \n -Michel BE  ","Credit",JOptionPane.INFORMATION_MESSAGE);});

		menu.add(file);
		menu.add(mode);
		menu.add(theme);
		menu.add(help);

		JPanel question = new JPanel();
		JPanel reponse =  new JPanel();
		reponse.add(reponseOui);
		reponseOui.setVisible(false);
		reponse.add(reponseNon);
		reponseNon.setVisible(false); 
		reponse.add(choisissez);
		JPanel validation = new JPanel();
		Personnage[] personnages = Jeu.getPersonnages(false,true);
		panelsPersos = BoutonPersonnage.panels(personnages);
		JLabel label = new JLabel("BIENVENUE SUR QUI-EST-CE?", JLabel.CENTER);
		//frame.add(label);
		Personnage persoChoisi1 = personnages[Choixalea(personnages.length)];
		Personnage persoChoisiEphemere = personnages[Choixalea(personnages.length)];
		Personnage persoChoisi3 = personnages[Choixalea(personnages.length)];

		if (persoChoisi1.equals(persoChoisiEphemere)) { persoChoisiEphemere =persoChoisi3 ; } 

		Personnage persoChoisi2= persoChoisiEphemere ; 

		System.out.println("les personnages choisis sont: " + persoChoisi1.getNom() +" et "+persoChoisi2.getNom());
		JPanel[] panelsPersos = BoutonPersonnage.panels(personnages);
		frame.setLayout(new GridLayout(8,1));
		for (int i = 0; i < panelsPersos.length; i++){
			frame.add(panelsPersos[i]); 
		}

		JLabel espacevide= new JLabel ("                     ") ; 
		JLabel textconnect = new JLabel ( "Choisissez le connecteur de votre choix : "  ) ; 

       // il faut stocker les opérateurs dans une varaible 
		String connect []= {"", "&&" , "||" , "!" } ;


		JComboBox connecteur = new JComboBox <>(connect);



		connecteur.addActionListener(new ActionListener() { 
 // @Override
			public void actionPerformed(ActionEvent e) {

				if(connecteur.getSelectedItem() == "||") { 

					JComboBox<Nationalite> nationalites = new JComboBox<> (Nationalite.values());
					assignerQuestion(nationalites, n ->( n == persoChoisi1.getNationalite() || n ==persoChoisi2.getNationalite() )) ;

					JComboBox<Sport> sports= new JComboBox<>(Sport.values());
					assignerQuestion(sports, s ->( s== persoChoisi1.getSport() || s==persoChoisi2.getSport() ));

					JComboBox<Age> ages = new JComboBox<>(Age.values());
					assignerQuestion(ages, a ->( a== persoChoisi1.getAge() || a==persoChoisi2.getAge() ));

					JComboBox<Genre> genres = new JComboBox<>(Genre.values());
					assignerQuestion(genres, g ->( g== persoChoisi1.getGenre() || g == persoChoisi2.getGenre () ));

					JComboBox<CouleurCheveux> couleurs = new JComboBox<>(CouleurCheveux.values());
					assignerQuestion(couleurs, c ->( c== persoChoisi1.getCouleurCheveux() || c== persoChoisi2.getCouleurCheveux() ));

					JComboBox<Pilosite> pilosites = new JComboBox<>(Pilosite.values());
					assignerQuestion(pilosites, p ->( p== persoChoisi1.getPilosite() || p==persoChoisi2.getPilosite() ));

					JComboBox<Cheveux> cheveux = new JComboBox<>(Cheveux.values());
					assignerQuestion(cheveux, c ->(c== persoChoisi1.getCheveux() || c==persoChoisi2.getCheveux() )) ; 

					question.add(nationalites);
					question.add(sports);
					question.add(ages);
					question.add(genres);
					question.add(couleurs);
					question.add(pilosites);
					question.add(cheveux);
				}

				if(connecteur.getSelectedItem() == "&&") { 

					JComboBox<Nationalite> nationalites = new JComboBox<> (Nationalite.values());
					assignerQuestion(nationalites, n ->( n == persoChoisi1.getNationalite() && n == persoChoisi2.getNationalite()   ));

					JComboBox<Sport> sports= new JComboBox<>(Sport.values());
					assignerQuestion(sports, s ->( s== persoChoisi1.getSport()  &&  s==persoChoisi2.getSport() ));

					JComboBox<Age> ages = new JComboBox<>(Age.values());
					assignerQuestion(ages, a ->( a== persoChoisi1.getAge() && a==persoChoisi2.getAge() )) ;


					JComboBox<Genre> genres = new JComboBox<>(Genre.values());
					assignerQuestion(genres, g ->( g== persoChoisi1.getGenre() && g == persoChoisi2.getGenre () )) ;


					JComboBox<CouleurCheveux> couleurs = new JComboBox<>(CouleurCheveux.values());
					assignerQuestion(couleurs, c ->( c== persoChoisi1.getCouleurCheveux()&& c== persoChoisi2.getCouleurCheveux() ));


					JComboBox<Pilosite> pilosites = new JComboBox<>(Pilosite.values());
					assignerQuestion(pilosites, p ->( p== persoChoisi1.getPilosite() && p==persoChoisi2.getPilosite() ));


					JComboBox<Cheveux> cheveux = new JComboBox<>(Cheveux.values());
					assignerQuestion(cheveux, c ->(c== persoChoisi1.getCheveux() && c==persoChoisi2.getCheveux()  )) ; 


					question.add(nationalites);
					question.add(sports);
					question.add(ages);
					question.add(genres);
					question.add(couleurs);
					question.add(pilosites);
					question.add(cheveux);
				}

				if(connecteur.getSelectedItem() == "!") { 

					JComboBox<Nationalite> nationalites = new JComboBox<> (Nationalite.values());
					assignerQuestion(nationalites, n ->( n != persoChoisi1.getNationalite() && n != persoChoisi2.getNationalite() )) ;

					JComboBox<Sport> sports= new JComboBox<>(Sport.values());
					assignerQuestion(sports, s ->( s!= persoChoisi1.getSport() && s !=persoChoisi2.getSport() ));

					JComboBox<Age> ages = new JComboBox<>(Age.values());
					assignerQuestion(ages, a ->( a!= persoChoisi1.getAge() && a!=persoChoisi2.getAge() ));

					JComboBox<Genre> genres = new JComboBox<>(Genre.values());
					assignerQuestion(genres, g ->( g!= persoChoisi1.getGenre() && g != persoChoisi2.getGenre () ));

					JComboBox<CouleurCheveux> couleurs = new JComboBox<>(CouleurCheveux.values());
					assignerQuestion(couleurs, c ->( c!= persoChoisi1.getCouleurCheveux() && c!= persoChoisi2.getCouleurCheveux() ));

					JComboBox<Pilosite> pilosites = new JComboBox<>(Pilosite.values());
					assignerQuestion(pilosites, p ->( p!= persoChoisi1.getPilosite() && p!=persoChoisi2.getPilosite() ));

					JComboBox<Cheveux> cheveux = new JComboBox<>(Cheveux.values());
					assignerQuestion(cheveux, c ->(c!= persoChoisi1.getCheveux() && c!=persoChoisi2.getCheveux() )) ; 

					question.add(nationalites);
					question.add(sports);
					question.add(ages);
					question.add(genres);
					question.add(couleurs);
					question.add(pilosites);
					question.add(cheveux); }   

					frame.setVisible(true);


				} });



JComboBox<Personnage> labelPersos = new JComboBox<>(personnages);


//Fonction recherche nom 
labelPersos.addActionListener(new ActionListener() {     
		//	@Override
	public void actionPerformed(ActionEvent e) {

		if(labelPersos.getSelectedItem()== persoChoisi1 || labelPersos.getSelectedItem() ==persoChoisi2)
		{   
			System.out.println("oui");
			choisissez.setVisible(false);
			reponseOui.setVisible(true);
			reponseNon.setVisible(false);
			frame.repaint();
			JOptionPane.showMessageDialog(null , "Félicitation vous avez trouvé le personnage !!");

		} 
		else {
			System.out.println("non");
			choisissez.setVisible(false);
			reponseNon.setVisible(true);
			reponseOui.setVisible(false);
			frame.repaint();
			JOptionPane.showMessageDialog(null , "Dommage, vous pouvez reesayer");
		}


	} 
});




		//définition des différents thèmes
coldefaut.addActionListener(e ->{
	for (int i=0;i<4;i++) panelsPersos[i].setBackground(Color.WHITE);
		menu.setBackground(Color.WHITE);
	question.setBackground(Color.WHITE);reponse.setBackground(Color.WHITE);validation.setBackground(Color.WHITE);
	frame.getContentPane().setBackground(Color.lightGray);
	frame.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
	accueil.getContentPane().setBackground(Color.lightGray);
	accueil.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
});
jaune.addActionListener(e ->{
	for (int i=0;i<4;i++) panelsPersos[i].setBackground(Color.YELLOW);
		menu.setBackground(Color.YELLOW);
	question.setBackground(Color.YELLOW);reponse.setBackground(Color.YELLOW);validation.setBackground(Color.YELLOW);
	frame.setBackground(Color.YELLOW);
	frame.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.YELLOW));
	accueil.getContentPane().setBackground(Color.YELLOW);
	accueil.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.YELLOW));
});
rose.addActionListener(e ->{
	for (int i=0;i<4;i++) panelsPersos[i].setBackground(Color.PINK);
		menu.setBackground(Color.PINK);
	question.setBackground(Color.PINK);reponse.setBackground(Color.PINK);validation.setBackground(Color.PINK);
	frame.setBackground(Color.PINK);
	frame.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.PINK));
	accueil.getContentPane().setBackground(Color.PINK);
	accueil.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.PINK));
});
orange.addActionListener(e ->{
	for (int i=0;i<4;i++) panelsPersos[i].setBackground(Color.ORANGE);
		menu.setBackground(Color.ORANGE);
	question.setBackground(Color.ORANGE);reponse.setBackground(Color.ORANGE);validation.setBackground(Color.ORANGE);
	frame.setBackground(Color.ORANGE);
	frame.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.ORANGE));
	accueil.getContentPane().setBackground(Color.ORANGE);
	accueil.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.ORANGE));
});
vert.addActionListener(e ->{
	for (int i=0;i<4;i++) panelsPersos[i].setBackground(Color.GREEN);
		menu.setBackground(Color.GREEN);
	question.setBackground(Color.GREEN);reponse.setBackground(Color.GREEN);validation.setBackground(Color.GREEN);
	frame.setBackground(Color.GREEN);
	frame.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.GREEN));
	accueil.getContentPane().setBackground(Color.GREEN);
	accueil.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.GREEN));
});
bleu.addActionListener(e ->{
	for (int i=0;i<4;i++) panelsPersos[i].setBackground(Color.CYAN);
		menu.setBackground(Color.CYAN);
	question.setBackground(Color.CYAN);reponse.setBackground(Color.CYAN);validation.setBackground(Color.CYAN);
	frame.setBackground(Color.CYAN);
	frame.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.CYAN));
	accueil.getContentPane().setBackground(Color.CYAN);
	accueil.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.CYAN));
});





		//fonctions menus
		//new game


newf.addActionListener(e ->{
	frame.dispose();
	accueil.dispose();
	try {
		Process processus = Runtime.getRuntime().exec("rm -f sauvegarde.json");
		Process processus2 = Runtime.getRuntime().exec("java -cp .:gson-2.8.2.jar DeuxJCmplx");

		StringBuilder resultat = new StringBuilder(); 

		BufferedReader lecteur = new BufferedReader(new InputStreamReader(processus.getInputStream())); 

		String ligne;

		while ((ligne = lecteur.readLine()) != null) {
			resultat.append(ligne + "\n"); 
		}

		int valeurDeSortie = processus.waitFor();
		int valeurDeSortie2 = processus.waitFor();
		if (valeurDeSortie == 0 && valeurDeSortie2==0) {
			System.out.println("Success!");
			System.out.println(resultat); 
			System.exit(0);
		} else {
			System.out.println("Quelque chose de pas normal s'est produit :( "); 
		}

	} catch (IOException t) {
		t.printStackTrace();
	} catch (InterruptedException t) {
		t.printStackTrace();
	}
	;});


load.addActionListener(e->{
	frame.dispose();
	accueil.dispose();
	try {
		Process processus = Runtime.getRuntime().exec("java -cp .:gson-2.8.2.jar DeuxJCmplx"); 

		StringBuilder resultat = new StringBuilder(); 

		BufferedReader lecteur = new BufferedReader(new InputStreamReader(processus.getInputStream())); 

		String ligne;

		while ((ligne = lecteur.readLine()) != null) {
			resultat.append(ligne + "\n"); 
		}

		int valeurDeSortie = processus.waitFor();
		if (valeurDeSortie == 0) {
			System.out.println("Success!");
			System.out.println(resultat); 
			System.exit(0);
		} else {
			System.out.println("Quelquechose de pas normal s'est produit :( "); 
		}

	} catch (IOException t) {
		t.printStackTrace();
	} catch (InterruptedException t) {
		t.printStackTrace();
	}
	;});

quit.addActionListener(e -> { 
	try{
		Jeu.sauvegarder();
	}
	catch (IOException ex){
		System.err.println("echec de sauvegarde");
	}
	finally {
		frame.setVisible(false); 
		accueil.setVisible(true);
	}
});


soloJ.addActionListener(e ->{
	frame.dispose();
	accueil.dispose();
	try {
		Process processus = Runtime.getRuntime().exec("javac -cp gson-2.8.2.jar -sourcepath . Main.java"); 
		Process processus2 =  Runtime.getRuntime().exec("java -cp .:gson-2.8.2.jar Main");

		StringBuilder resultat = new StringBuilder(); 

		BufferedReader lecteur = new BufferedReader(new InputStreamReader(processus.getInputStream())); 

		String ligne;

		while ((ligne = lecteur.readLine()) != null) {
			resultat.append(ligne + "\n"); 
		}

		int valeurDeSortie = processus.waitFor();
		int valeurDeSortie2 = processus2.waitFor();
		if (valeurDeSortie == 0 && valeurDeSortie2== 0) {
			System.out.println("Success!");
			System.out.println(resultat); 
			System.exit(0);
		} else {
			System.out.println("Quelquechose de pas normal s'est produit :( "); 
		}

	} catch (IOException t) {
		t.printStackTrace();
	} catch (InterruptedException t) {
		t.printStackTrace();
	}
	;});


deuxJ.addActionListener(e ->{
	frame.dispose();
	accueil.dispose();
	try {
		Process processus = Runtime.getRuntime().exec("javac -cp gson-2.8.2.jar -sourcepath . DeuxJ.java"); 
		Process processus2 =  Runtime.getRuntime().exec("java -cp .:gson-2.8.2.jar DeuxJ");

		StringBuilder resultat = new StringBuilder(); 

		BufferedReader lecteur = new BufferedReader(new InputStreamReader(processus.getInputStream())); 

		String ligne;

		while ((ligne = lecteur.readLine()) != null) {
			resultat.append(ligne + "\n"); 
		}

		int valeurDeSortie = processus.waitFor();
		int valeurDeSortie2 = processus2.waitFor();
		if (valeurDeSortie == 0 && valeurDeSortie2== 0) {
			System.out.println("Success!");
			System.out.println(resultat); 
			System.exit(0);
		} else {
			System.out.println("Quelquechose de pas normal s'est produit :( "); 
		}

	} catch (IOException t) {
		t.printStackTrace();
	} catch (InterruptedException t) {
		t.printStackTrace();
	}
	;});


readme.addActionListener(e ->{
	try {
		Process processus = Runtime.getRuntime().exec("display ../tableau1.jpg"); 

		StringBuilder resultat = new StringBuilder(); 

		BufferedReader lecteur = new BufferedReader(new InputStreamReader(processus.getInputStream())); 

	} catch (IOException t) {
		t.printStackTrace();
	} 

	;});








frame.add(question);
frame.add(reponse);
frame.add(validation);
question.add(textconnect) ;  
question.add(connecteur) ;
question.add(espacevide) ; 
validation.add(labelPersos);
        /*
		question.add(nationalites);
		question.add(sports);
		question.add(ages);
		question.add(genres);
		question.add(couleurs);
		question.add(pilosites);
		question.add(cheveux);*/
		
		frame.pack();
		frame.setSize(1500, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.lightGray);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
		frame.setVisible(false);



		//écran d'accueil
		JButton jouer= new JButton("");
		accueil.add(jouer);
		
	//rendre le bouton transparent
		jouer.setOpaque(false);
    //enlever la zone de contenu
		jouer.setContentAreaFilled(false);
    //enlever la bordure
		jouer.setBorderPainted(false);
   	//pour lancer le jeu
		jouer.addActionListener(new ActionListener() {     

			public void actionPerformed(ActionEvent e) {
				accueil.setVisible(false);
				frame.setVisible(true);				
			}});

		accueil.pack();
		accueil.setSize(1200, 1200);
		accueil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		accueil.setContentPane(new JLabel(new ImageIcon (OuvrirFichier.getImage("qui.jpg"))));
		accueil.getContentPane().setBackground(Color.lightGray);
		accueil.getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
		accueil.setVisible(true);
		accueil.add(jouer);

		

	}
}
