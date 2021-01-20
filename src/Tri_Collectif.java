import java.util.*;

public class Tri_Collectif {
    private int nA,nB,nbAgent,Deplacement,taille1,taille2;
    private float k,k2;
    private Entite[][] Grille;
    private Map<Agent,Position> PositionAgent;
    enum Direction {
        Nord,
        Sud,
        Est,
        Ouest
    }

    public Tri_Collectif(int nbA,int nbB,int nbAg,int dep,int t1,int t2,float const1, float const2){
        this.nA = nbA;
        this.nB = nbB;
        this.nbAgent = nbAg;
        this.Deplacement = dep;
        this.taille1 = t1;
        this.taille2 = t2;
        this.k = const1;
        this.k2 = const2;
        this.Grille = new Entite[this.taille1][this.taille2];
        for(int i=0;i<this.taille1;i++){
            for(int j=0;j<this.taille2;j++){
                this.Grille[i][j] = new EntiteVide();
            }
        }
        this.PositionAgent = new HashMap<>();
    }

    public void GenerationAleatoireAgent(){
        int rand1,rand2;
        for(int i=0;i<this.nbAgent;i++){
            Agent a = new Agent(this.Deplacement, 10);
            a.manageMemory("0");
            do {
                rand1 = (int)(Math.random() * this.taille1);
                rand2 = (int)(Math.random() * this.taille2);
            }while(this.Grille[rand1][rand2].getClass() != EntiteVide.class);
            this.Grille[rand1][rand2] = a;
            PositionAgent.put(a,new Position(rand1,rand2));
        }
    }

    public void GenerationAleatoireObjet(){
        int rand1,rand2;
        for(int i=0;i<this.nA;i++){
            Objet o = new Objet("A");
            do {
                rand1 = (int)(Math.random() * this.taille1);
                rand2 = (int)(Math.random() * this.taille2);
            }while(this.Grille[rand1][rand2].getClass() != EntiteVide.class);
            this.Grille[rand1][rand2] = o;
        }
        for(int i=0;i<this.nB;i++){
            Objet o = new Objet("B");
            do {
                rand1 = (int)(Math.random() * this.taille1);
                rand2 = (int)(Math.random() * this.taille2);
            }while(this.Grille[rand1][rand2].getClass() != EntiteVide.class);
            this.Grille[rand1][rand2] = o;
        }
    }

    public void AffichageGrille(){
        System.out.println();
        System.out.println();
        for(int i=0;i<this.taille1;i++){
            for(int j=0;j<this.taille2;j++){
                System.out.print(this.Grille[i][j].getType() + "  ");
            }
            System.out.println();
        }
    }

    public void DeplacementAgent(){
        Random rand = new Random();
        boolean estDep = false;
        int n=0;
        for(Map.Entry<Agent,Position> map : PositionAgent.entrySet()){
            while(!estDep && n<4) {
                Direction d = Direction.values()[rand.nextInt(Direction.values().length)];
                switch (d) {
                    case Nord:
                        if (map.getValue().getY() != 0){
                            if(this.Grille[map.getValue().getX()][map.getValue().getY()-1].getClass() != Agent.class) {
                                map.getKey().manageMemory(this.Grille[map.getValue().getX()][map.getValue().getY() - 1].getType());
                                map.getValue().setY(map.getValue().getY() - 1);
                                calculAndSetProba(map.getKey(), Math.max(nbObjetAAdjacent(map.getValue().getX(), map.getValue().getY()), nbObjetBAdjacent(map.getValue().getX(), map.getValue().getY())), this.k, this.k2);
                                this.Grille[map.getValue().getX()][map.getValue().getY()] = map.getKey();
                                this.Grille[map.getValue().getX()][map.getValue().getY() + 1] = new EntiteVide();
                                estDep = true;
                            }
                        }
                        break;
                    case Sud:
                        if (map.getValue().getY() != this.taille2-1){
                            if(this.Grille[map.getValue().getX()][map.getValue().getY()+1].getClass() != Agent.class) {
                                map.getKey().manageMemory(this.Grille[map.getValue().getX()][map.getValue().getY() + 1].getType());
                                map.getValue().setY(map.getValue().getY() + 1);
                                calculAndSetProba(map.getKey(), Math.max(nbObjetAAdjacent(map.getValue().getX(), map.getValue().getY()), nbObjetBAdjacent(map.getValue().getX(), map.getValue().getY())), this.k, this.k2);
                                this.Grille[map.getValue().getX()][map.getValue().getY()] = map.getKey();
                                this.Grille[map.getValue().getX()][map.getValue().getY() - 1] = new EntiteVide();
                                estDep = true;
                            }
                        }
                        break;
                    case Est:
                        if (map.getValue().getX() != this.taille1-1){
                            if(this.Grille[map.getValue().getX()+1][map.getValue().getY()].getClass() != Agent.class) {
                                map.getKey().manageMemory(this.Grille[map.getValue().getX() + 1][map.getValue().getY()].getType());
                                map.getValue().setX(map.getValue().getX() + 1);
                                calculAndSetProba(map.getKey(), Math.max(nbObjetAAdjacent(map.getValue().getX(), map.getValue().getY()), nbObjetBAdjacent(map.getValue().getX(), map.getValue().getY())), this.k, this.k2);
                                this.Grille[map.getValue().getX()][map.getValue().getY()] = map.getKey();
                                this.Grille[map.getValue().getX() - 1][map.getValue().getY()] = new EntiteVide();
                                estDep = true;
                            }
                        }
                        break;
                    case Ouest:
                        if (map.getValue().getX() != 0){
                            if(this.Grille[map.getValue().getX()-1][map.getValue().getY()].getClass() != Agent.class) {
                                map.getKey().manageMemory(this.Grille[map.getValue().getX() - 1][map.getValue().getY()].getType());
                                map.getValue().setX(map.getValue().getX() - 1);
                                calculAndSetProba(map.getKey(), Math.max(nbObjetAAdjacent(map.getValue().getX(), map.getValue().getY()), nbObjetBAdjacent(map.getValue().getX(), map.getValue().getY())), this.k, this.k2);
                                this.Grille[map.getValue().getX()][map.getValue().getY()] = map.getKey();
                                this.Grille[map.getValue().getX() + 1][map.getValue().getY()] = new EntiteVide();
                                estDep = true;
                            }
                        }
                        break;
                }
                n++;
            }
            n=0;
            estDep = false;
        }
    }

    public int nbObjetAAdjacent(int x, int y){
        int compt = 0;
        if(x+1<this.taille1){
            if(Grille[x+1][y].getClass() == Objet.class && Grille[x+1][y].getType() == "A"){
                compt++;
            }
        }
        if(x-1>=0){
            if(Grille[x-1][y].getClass() == Objet.class && Grille[x-1][y].getType() == "A"){
                compt++;
            }
        }
        if(y+1<this.taille2) {
            if (Grille[x][y + 1].getClass() == Objet.class && Grille[x][y + 1].getType() == "A") {
                compt++;
            }
        }
        if(y-1>=0) {
            if (Grille[x][y - 1].getClass() == Objet.class && Grille[x][y - 1].getType() == "A") {
                compt++;
            }
        }
        return compt;
    }

    public int nbObjetBAdjacent(int x,int y){
        int compt = 0;
        if(x+1<this.taille1) {
            if (Grille[x + 1][y].getClass() == Objet.class && Grille[x + 1][y].getType() == "B") {
                compt++;
            }
        }
        if(x-1>=0) {
            if (Grille[x - 1][y].getClass() == Objet.class && Grille[x - 1][y].getType() == "B") {
                compt++;
            }
        }
        if(y+1<this.taille2) {
            if (Grille[x][y + 1].getClass() == Objet.class && Grille[x][y + 1].getType() == "B") {
                compt++;
            }
        }
        if(y-1>=0) {
            if (Grille[x][y - 1].getClass() == Objet.class && Grille[x][y - 1].getType() == "B") {
                compt++;
            }
        }
        return compt;
    }

    public void calculAndSetProba(Agent a, int nb, float k1, float k2){
        double probaPrise = a.calculProbaPrise(nb,k1);
        double probaDepot = a.calculProbaDepot(nb,k2);
        a.setProbaDepot(probaDepot);
        a.setProbaPrise(probaPrise);
    }
}
