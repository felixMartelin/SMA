import java.util.*;

public class Tri_Collectif {
    private int nA,nB,nbAgent,Deplacement,taille1,taille2;
    private double k,k2;
    private Case[][] Grille;
    private Map<Agent,Position> PositionAgent;
    enum Direction {
        Nord,
        Sud,
        Est,
        Ouest
    }

    public Tri_Collectif(int nbA,int nbB,int nbAg,int dep,int t1,int t2,double const1, double const2){
        this.nA = nbA;
        this.nB = nbB;
        this.nbAgent = nbAg;
        this.Deplacement = dep;
        this.taille1 = t1;
        this.taille2 = t2;
        this.k = const1;
        this.k2 = const2;
        this.Grille = new Case[this.taille1][this.taille2];
        for(int i=0;i<this.taille1;i++){
            for(int j=0;j<this.taille2;j++){
                this.Grille[i][j] = new Case(new EntiteVide());
            }
        }
        this.PositionAgent = new HashMap<>();
    }

    public void GenerationAleatoireAgent(){
        int rand1,rand2;
        for(int i=0;i<this.nbAgent;i++){
            Agent a = new Agent(this.Deplacement, 10,0.1);
            a.manageMemory("0");
            do {
                rand1 = (int)(Math.random() * this.taille1);
                rand2 = (int)(Math.random() * this.taille2);
            }while(!this.Grille[rand1][rand2].getEntiteList().get(0).getType().equals("0"));
            this.Grille[rand1][rand2].getEntiteList().remove(0);
            this.Grille[rand1][rand2].getEntiteList().add(a);
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
            }while(!this.Grille[rand1][rand2].getEntiteList().get(0).getType().equals("0"));
            this.Grille[rand1][rand2].getEntiteList().remove(0);
            this.Grille[rand1][rand2].getEntiteList().add(o);
        }
        for(int i=0;i<this.nB;i++){
            Objet o = new Objet("B");
            do {
                rand1 = (int)(Math.random() * this.taille1);
                rand2 = (int)(Math.random() * this.taille2);
            }while(!this.Grille[rand1][rand2].getEntiteList().get(0).getType().equals("0"));
            this.Grille[rand1][rand2].getEntiteList().remove(0);
            this.Grille[rand1][rand2].getEntiteList().add(o);
        }
    }

    public void AffichageGrille(){
        System.out.println();
        System.out.println();
        for(int i=0;i<this.taille1;i++){
            for(int j=0;j<this.taille2;j++){
                System.out.print("[");
                for(int k=0;k<this.Grille[i][j].getEntiteList().size();k++){
                    if(this.Grille[i][j].getEntiteList().get(k).getType().equals("0")){
                        System.out.print(" ");
                    }else {
                        System.out.print(this.Grille[i][j].getEntiteList().get(k).getType());
                    }
                }
                System.out.print("]");
            }
            System.out.println();
        }
    }

    public void removeAgentFromCase(Case c){
        for(int i=0;i<c.getEntiteList().size();i++){
            if(c.getEntiteList().get(i).getClass() == Agent.class){
                c.getEntiteList().remove(i);
            }
        }
        if(c.getEntiteList().size()==0){
            c.getEntiteList().add(new EntiteVide());
        }
    }

    public void addAgentToCase(Case c, Agent a){
        if(c.containObjet()){
            c.getEntiteList().add(a);
        }else{
            c.getEntiteList().remove(0);
            c.getEntiteList().add(a);
        }
    }

    public void DeplacementAgent(){
        Random rand = new Random();
        boolean estDep = false;
        double randomNumber;
        int n=0;
        for(Map.Entry<Agent,Position> map : PositionAgent.entrySet()){
            while(!estDep && n<4) {
                Direction d = Direction.values()[rand.nextInt(Direction.values().length)];
                switch (d) {
                    case Nord:
                        if (map.getValue().getY() != 0){
                            if(!this.Grille[map.getValue().getX()][map.getValue().getY()-1].containAgent()) {
                                map.getKey().manageMemory(this.Grille[map.getValue().getX()][map.getValue().getY() - 1].getEntiteList().get(0).getType());
                                map.getValue().setY(map.getValue().getY() - 1);
                                removeAgentFromCase(this.Grille[map.getValue().getX()][map.getValue().getY() + 1]);
                                if(map.getKey().getHaveObject()){
                                    if(map.getKey().getObjet().getType().equals("A")){
                                        map.getKey().calculAndSetProba(nbObjetAAdjacent(map.getValue().getX(), map.getValue().getY()) , this.k, this.k2);
                                        //map.getKey().calculAndSetProbaWithError(this.k, this.k2,"A");
                                    }else if(map.getKey().getObjet().getType().equals("B")){
                                        map.getKey().calculAndSetProba(nbObjetBAdjacent(map.getValue().getX(), map.getValue().getY()), this.k, this.k2);
                                        //map.getKey().calculAndSetProbaWithError(this.k, this.k2,"B");
                                    }
                                }
                                addAgentToCase(this.Grille[map.getValue().getX()][map.getValue().getY()],map.getKey());
                                if(this.Grille[map.getValue().getX()][map.getValue().getY()].containObjet() && !map.getKey().getHaveObject()){
                                    map.getKey().possiblePrise(this.Grille[map.getValue().getX()][map.getValue().getY()]);
                                }else if(!this.Grille[map.getValue().getX()][map.getValue().getY()].containObjet() && map.getKey().getHaveObject()){
                                    map.getKey().possibleDepot(this.Grille[map.getValue().getX()][map.getValue().getY()]);
                                }
                                if(this.Grille[map.getValue().getX()][map.getValue().getY() + 1].getEntiteList().size()==0){
                                    this.Grille[map.getValue().getX()][map.getValue().getY() + 1].getEntiteList().add(new EntiteVide());
                                }
                                estDep = true;
                            }
                        }
                        break;
                    case Sud:
                        if (map.getValue().getY() != this.taille2-1){
                            if(!this.Grille[map.getValue().getX()][map.getValue().getY()+1].containAgent()) {
                                map.getKey().manageMemory(this.Grille[map.getValue().getX()][map.getValue().getY() + 1].getEntiteList().get(0).getType());
                                map.getValue().setY(map.getValue().getY() + 1);
                                removeAgentFromCase(this.Grille[map.getValue().getX()][map.getValue().getY() - 1]);
                                if(map.getKey().getHaveObject()){
                                    if(map.getKey().getObjet().getType().equals("A")){
                                        map.getKey().calculAndSetProba(nbObjetAAdjacent(map.getValue().getX(), map.getValue().getY()) , this.k, this.k2);
                                        //map.getKey().calculAndSetProbaWithError(this.k, this.k2,"A");
                                    }else if(map.getKey().getObjet().getType().equals("B")){
                                        map.getKey().calculAndSetProba(nbObjetBAdjacent(map.getValue().getX(), map.getValue().getY()), this.k, this.k2);
                                        //map.getKey().calculAndSetProbaWithError(this.k, this.k2,"B");
                                    }
                                }
                                addAgentToCase(this.Grille[map.getValue().getX()][map.getValue().getY()],map.getKey());
                                if(this.Grille[map.getValue().getX()][map.getValue().getY()].containObjet() && !map.getKey().getHaveObject()){
                                    map.getKey().possiblePrise(this.Grille[map.getValue().getX()][map.getValue().getY()]);
                                }else if(!this.Grille[map.getValue().getX()][map.getValue().getY()].containObjet() && map.getKey().getHaveObject()){
                                    map.getKey().possibleDepot(this.Grille[map.getValue().getX()][map.getValue().getY()]);
                                }
                                if(this.Grille[map.getValue().getX()][map.getValue().getY() - 1].getEntiteList().size()==0){
                                    this.Grille[map.getValue().getX()][map.getValue().getY() - 1].getEntiteList().add(new EntiteVide());
                                }
                                estDep = true;
                            }
                        }
                        break;
                    case Est:
                        if (map.getValue().getX() != this.taille1-1){
                            if(!this.Grille[map.getValue().getX()+1][map.getValue().getY()].containAgent()) {
                                map.getKey().manageMemory(this.Grille[map.getValue().getX() + 1][map.getValue().getY()].getEntiteList().get(0).getType());
                                map.getValue().setX(map.getValue().getX() + 1);
                                removeAgentFromCase(this.Grille[map.getValue().getX()-1][map.getValue().getY()]);
                                if(map.getKey().getHaveObject()){
                                    if(map.getKey().getObjet().getType().equals("A")){
                                        map.getKey().calculAndSetProba(nbObjetAAdjacent(map.getValue().getX(), map.getValue().getY()) , this.k, this.k2);
                                        //map.getKey().calculAndSetProbaWithError(this.k, this.k2,"A");
                                    }else if(map.getKey().getObjet().getType().equals("B")){
                                        map.getKey().calculAndSetProba(nbObjetBAdjacent(map.getValue().getX(), map.getValue().getY()), this.k, this.k2);
                                        //map.getKey().calculAndSetProbaWithError(this.k, this.k2,"B");
                                    }
                                }
                                addAgentToCase(this.Grille[map.getValue().getX()][map.getValue().getY()],map.getKey());
                                if(this.Grille[map.getValue().getX()][map.getValue().getY()].containObjet() && !map.getKey().getHaveObject()){
                                    map.getKey().possiblePrise(this.Grille[map.getValue().getX()][map.getValue().getY()]);
                                }else if(!this.Grille[map.getValue().getX()][map.getValue().getY()].containObjet() && map.getKey().getHaveObject()){
                                    map.getKey().possibleDepot(this.Grille[map.getValue().getX()][map.getValue().getY()]);
                                }
                                if(this.Grille[map.getValue().getX()-1][map.getValue().getY()].getEntiteList().size()==0){
                                    this.Grille[map.getValue().getX()-1][map.getValue().getY()].getEntiteList().add(new EntiteVide());
                                }
                                estDep = true;
                            }
                        }
                        break;
                    case Ouest:
                        if (map.getValue().getX() != 0){
                            if(!this.Grille[map.getValue().getX()-1][map.getValue().getY()].containAgent()) {
                                map.getKey().manageMemory(this.Grille[map.getValue().getX() - 1][map.getValue().getY()].getEntiteList().get(0).getType());
                                map.getValue().setX(map.getValue().getX() - 1);
                                removeAgentFromCase(this.Grille[map.getValue().getX()+1][map.getValue().getY()]);
                                if(map.getKey().getHaveObject()){
                                    if(map.getKey().getObjet().getType().equals("A")){
                                        map.getKey().calculAndSetProba(nbObjetAAdjacent(map.getValue().getX(), map.getValue().getY()) , this.k, this.k2);
                                        //map.getKey().calculAndSetProbaWithError(this.k, this.k2,"A");
                                    }else if(map.getKey().getObjet().getType().equals("B")){
                                        map.getKey().calculAndSetProba(nbObjetBAdjacent(map.getValue().getX(), map.getValue().getY()), this.k, this.k2);
                                        //map.getKey().calculAndSetProbaWithError(this.k, this.k2,"B");
                                    }
                                }
                                addAgentToCase(this.Grille[map.getValue().getX()][map.getValue().getY()],map.getKey());
                                if(this.Grille[map.getValue().getX()][map.getValue().getY()].containObjet() && !map.getKey().getHaveObject()){
                                    map.getKey().possiblePrise(this.Grille[map.getValue().getX()][map.getValue().getY()]);
                                }else if(!this.Grille[map.getValue().getX()][map.getValue().getY()].containObjet() && map.getKey().getHaveObject()){
                                    map.getKey().possibleDepot(this.Grille[map.getValue().getX()][map.getValue().getY()]);
                                }
                                if(this.Grille[map.getValue().getX()+1][map.getValue().getY()].getEntiteList().size()==0){
                                    this.Grille[map.getValue().getX()+1][map.getValue().getY()].getEntiteList().add(new EntiteVide());
                                }
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

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public int nbObjetAAdjacent(int x, int y){
        int compt = 0;
        if(x+1<this.taille1){
            if(Grille[x+1][y].containObjetA()){
                compt++;
            }
        }
        if(x-1>=0){
            if(Grille[x-1][y].containObjetA()){
                compt++;
            }
        }
        if(y+1<this.taille2) {
            if (Grille[x][y + 1].containObjetA()) {
                compt++;
            }
        }
        if(y-1>=0) {
            if (Grille[x][y - 1].containObjetA()) {
                compt++;
            }
        }
        return compt;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public int nbObjetBAdjacent(int x,int y){
        int compt = 0;
        if(x+1<this.taille1) {
            if (Grille[x+1][y].containObjetB()) {
                compt++;
            }
        }
        if(x-1>=0) {
            if (Grille[x-1][y].containObjetB()) {
                compt++;
            }
        }
        if(y+1<this.taille2) {
            if (Grille[x][y+1].containObjetB()) {
                compt++;
            }
        }
        if(y-1>=0) {
            if (Grille[x][y-1].containObjetB()) {
                compt++;
            }
        }
        return compt;
    }
}
