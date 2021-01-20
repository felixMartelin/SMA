public class Main {
    public static void main(String args[]){
        Tri_Collectif tri = new Tri_Collectif(10,10,10,1,10,10,10,10);
        tri.GenerationAleatoireAgent();
        tri.GenerationAleatoireObjet();
        tri.AffichageGrille();
        tri.DeplacementAgent();
        tri.AffichageGrille();
    }
}
