package fr.ul.rollingball.models;


import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.SoundFactory;

public class GameState {
    private static int tempsDispo;
    public enum etat {
        enCours,
        victoire,
        perte,
        arret;
    }
    private etat etatActuel;
    private int score;
    private int tempsRestant;
    private int nbPastillesAvalees;
    private Timer.Task decompte;

    /**
     * Permets de gérer le score et le temps de jeu
     */
    public GameState(){
        tempsDispo = 60;
        score = 0;
        tempsRestant = tempsDispo;
        nbPastillesAvalees = 0;
        etatActuel = etat.enCours;
        Timer timer = new Timer();
        decompte = new Timer.Task() {
            @Override
            public void run() {
                countDown();
            }
        };
        timer.scheduleTask(decompte, 1f, 1f);
    }

    /**
     * Diminue le temps restant de 1 toutes les secondes
     */
    private void countDown(){
        if(isInProgress()){
            tempsRestant --;
            if(tempsRestant <= 10){
                SoundFactory.getInstance().playAlerte(20);
            }
        }
    }

    //////////////////////////////////////
    //////////Getters et Setters//////////
    //////////////////////////////////////

    public void setState(etat etat){
        etatActuel = etat;
    }

    public boolean isStop(){
        return etat.arret.equals(etatActuel);
    }

    public boolean isVictory(){
        return etat.victoire.equals(etatActuel);
    }

    public boolean isLost(){
        return  etat.perte.equals(etatActuel);
    }

    public boolean isInProgress(){
        return etat.enCours.equals(etatActuel);
    }

    public int getNbPastillesAvalees() {
        return nbPastillesAvalees;
    }

    public void setNbPastillesAvalees(int nbPastillesAvalees) {
        this.nbPastillesAvalees = nbPastillesAvalees;
    }

    public static int getTempsDispo() {
        return tempsDispo;
    }

    public static void setTempsDispo(int tempsDispo) {
        GameState.tempsDispo = tempsDispo;
    }

    public int getTempsRestant() {
        return tempsRestant;
    }

    public void setTempsRestant(int tempsRestant) {
        this.tempsRestant = tempsRestant;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public etat getEtatActuel() {
        return etatActuel;
    }
}
