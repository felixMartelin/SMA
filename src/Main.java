public class Main {
    public static void main(String args[]){
        int a = 0;
        Tri_Collectif tri = new Tri_Collectif(30,30,10,1,20,20,0.1,0.3);
        tri.GenerationAleatoireAgent();
        tri.GenerationAleatoireObjet();
        tri.AffichageGrille();
        while(a<1000000) {
            tri.DeplacementAgent();
            a++;
        }
        tri.AffichageGrille();
    }
}
