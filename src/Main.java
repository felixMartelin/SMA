public class Main {
    public static void main(String args[]){
        int a = 0;
        Tri_Collectif tri = new Tri_Collectif(10,10,10,1,10,10,0.1,0.3);
        tri.GenerationAleatoireAgent();
        tri.GenerationAleatoireObjet();
        tri.AffichageGrille();
        while(a<150) {
            tri.DeplacementAgent();
            tri.AffichageGrille();
            a++;
        }
    }
}
