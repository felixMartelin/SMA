import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Agent extends Entite{
    private int taille;
    private Queue<String> Memoire;
    private int i;
    private double probaDepot;
    private double probaPrise;

    public Agent(int deplacement, int taille){
       this.Memoire = new LinkedList<>();
       this.i = deplacement;
    }

    public String getType(){
        return "X";
    }

    public int getTaille(){
        return this.taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void setMemoire(Queue<String> memoire) {
        Memoire = memoire;
    }

    public Queue<String> getMemoire() {
        return Memoire;
    }

    public double ProbaPrise(float k, float f){
        return Math.pow((k/(k+f)),2);
    }

    public double ProbaDepot(float k, float f){
        return Math.pow((f/(k+f)),2);
    }

    public int getI() {
        return i;
    }


    public void setI(int i) {
        this.i = i;
    }


    public double getProbaDepot() {
        return probaDepot;
    }

    public double getProbaPrise() {
        return probaPrise;
    }

    public void setProbaDepot(double probaDepot) {
        this.probaDepot = probaDepot;
    }

    public void setProbaPrise(double probaPrise) {
        this.probaPrise = probaPrise;
    }

    public double calculProbaDepot(int f, float k){
        return Math.pow((f/(k+f)),2);
    }

    public double calculProbaPrise(int f, float k){
        return Math.pow((k/(k+f)),2);
    }

    public void manageMemory(String type){
        this.Memoire.add(type);
        if(this.Memoire.size()>this.taille){
            this.Memoire.remove();
        }
    }
}
