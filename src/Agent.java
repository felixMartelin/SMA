import java.util.*;

public class Agent extends Entite{
    private int taille;
    private double e;
    private Queue<String> Memoire;
    private int i;
    private Objet objet;
    private boolean haveObject;
    private double probaDepot;
    private double probaPrise;

    public Agent(int deplacement, int taille, double error){
       this.Memoire = new LinkedList<>();
       this.i = deplacement;
       this.haveObject = false;
       this.e = error;
    }

    public String getType(){
        return "X";
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public boolean getHaveObject() {
        return haveObject;
    }

    public void setHaveObject(boolean haveObject) {
        this.haveObject = haveObject;
    }

    public int getTaille(){
        return this.taille;
    }

    public Objet getObjet() {
        return this.objet;
    }

    public void setObjet(Objet objet) {
        this.objet = objet;
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

    public double calculProbaDepot(double f, double k){
        return Math.pow((f/(k+f)),2);
    }

    public double calculProbaPrise(double f, double k){
        return Math.pow((k/(k+f)),2);
    }

    public void manageMemory(String type){
        this.Memoire.add(type);
        if(this.Memoire.size()>this.taille){
            this.Memoire.remove();
        }
    }

    public void calculAndSetProba(int nb, double k1, double k2){
        double probaP = this.calculProbaPrise(nb,k1);
        double probaD = this.calculProbaDepot(nb,k2);
        this.probaDepot = probaD;
        this.probaPrise = probaP;
    }

    public void calculAndSetProbaWithError(double k1, double k2, String obj){
        if(this.objet.getType().equals("A") || obj.equals("A")){
            double probaP = this.calculProbaPrise(this.defineFA(),k1);
            double probaD = this.calculProbaDepot(this.defineFA(),k2);
            this.probaDepot = probaD;
            this.probaPrise = probaP;
        }else {
            double probaP = this.calculProbaPrise(this.defineFB(), k1);
            double probaD = this.calculProbaDepot(this.defineFB(), k2);
            this.probaDepot = probaD;
            this.probaPrise = probaP;
        }
    }

    public void possibleDepot(Case c){
        double randomNumber = Math.random();
        if(randomNumber<this.probaDepot){
            this.haveObject = false;
            c.getEntiteList().add(this.objet);
            this.objet = null;
        }
    }

    public void possiblePrise(Case c){
        double randomNumber = Math.random();
        if(randomNumber>this.probaPrise){
           this.haveObject = true;
            for(int i=0;i<c.getEntiteList().size();i++){
                if(c.getEntiteList().get(i).getClass() == Objet.class){
                    this.objet = ((Objet) c.getEntiteList().get(i));
                    c.getEntiteList().remove(i);
                }
            }
        }
    }

    public double defineFA(){
        int comptA=0;
        int comptB=0;
        Iterator it = this.Memoire.iterator();
        while(it.hasNext()){
            if(it.next().equals("A")){
                comptA++;
            }else if(it.next().equals("B")){
                comptB++;
            }
        }
        return ((comptA + this.e*comptB) / this.Memoire.size());
    }

    public double defineFB(){
        int comptA=0;
        int comptB=0;
        Iterator it = this.Memoire.iterator();
        while(it.hasNext()){
            if(it.next().equals("A")){
                comptA++;
            }else if(it.next().equals("B")){
                comptB++;
            }
        }
        return ((this.e*comptA + comptB) / this.Memoire.size());
    }
}
