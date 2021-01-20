import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Case {
    private List<Entite> entiteList;

    public Case(List<Entite> list){
        this.entiteList = list;
    }

    public Case(Entite entite){
        this.entiteList = new ArrayList<>();
        this.entiteList.add(entite);
    }

    public List<Entite> getEntiteList() {
        return entiteList;
    }

    public void setEntiteList(List<Entite> entiteList) {
        this.entiteList = entiteList;
    }

    public boolean containObjet(){
        boolean contain = false;
        for(int i=0;i<this.entiteList.size();i++){
            if(this.entiteList.get(i).getClass() == Objet.class){
                contain = true;
            }
        }
        return contain;
    }

    public boolean containAgent(){
        boolean contain = false;
        for(int i=0;i<this.entiteList.size();i++){
            if(this.entiteList.get(i).getClass() == Agent.class){
                contain = true;
            }
        }
        return contain;
    }

    public boolean containObjetA(){
        boolean contain = false;
        for(int i=0;i<this.entiteList.size();i++){
            if(this.entiteList.get(i).getType().equals("A")){
                contain = true;
            }
        }
        return contain;
    }

    public boolean containObjetB(){
        boolean contain = false;
        for(int i=0;i<this.entiteList.size();i++){
            if(this.entiteList.get(i).getType().equals("B")){
                contain = true;
            }
        }
        return contain;
    }
}
