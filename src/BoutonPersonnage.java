import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoutonPersonnage extends JButton {
    
    public static final JPanel [] panels (Personnage [] personnages){
        JPanel[] panelsPersos = {new JPanel(), new JPanel(), new JPanel(), new JPanel()};

//Creation des ICON de personnages 
		for (int i = 0; i<personnages.length;i++){
			BoutonPersonnage bouton = new BoutonPersonnage(personnages[i]);
			panelsPersos[i/4].add(bouton); 

		}
        return panelsPersos; 

    }
    private static final ImageIcon croix= 
        new ImageIcon(new ImageIcon(OuvrirFichier.imageCroix)
        .getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
    private ImageIcon iconePerso;
    private Personnage personnage;
    private BoutonPersonnage (Personnage perso){
        iconePerso = new ImageIcon(new ImageIcon(OuvrirFichier.getImage(perso.getPhoto())).getImage()
        .getScaledInstance(200,200,Image.SCALE_DEFAULT));
        setIcon(perso.estCoche() ? croix : iconePerso);

        personnage = perso; 
        addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e){
                if (personnage.cocherOuDecocher()){
                    setIcon(croix); 
                }
                else {
                    setIcon(iconePerso); 
                    }
                }
            });

    }


}
