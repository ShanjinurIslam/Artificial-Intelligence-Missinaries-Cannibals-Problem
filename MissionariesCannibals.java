import java.util.*;


public class MissionariesCannibals {
    private Queue<State> closedSet ;
    private ArrayList<State> openSet ;
    private ArrayList<State> visited ;
    private Stack<State> closedSetStack ;
    private State initial_state ;
    private State final_state ;

    MissionariesCannibals(State initial_state,State final_state){
        closedSet = new LinkedList<>() ;
        openSet = new ArrayList<>() ;
        closedSetStack = new Stack<>() ;
        this.initial_state = initial_state ;
        this.final_state = final_state ;
        visited = new ArrayList<>() ;
    }

    ArrayList<State> processState(State state,int boatsize){
        ArrayList<State> outputstates = new ArrayList<>() ;

        for (int i=0;i<=boatsize;i++){
            for(int j=boatsize-i;j>=0;j--){

                if(!(i+j==0)){
                    if(!state.getSide()){
                        State t = new State(state.getM(),state.getC(),state.getSide(),state.getMaxm(),state.getMaxc()) ;
                        t.setState(t.getM()-i,t.getC()-j);
                        if(t.getM()>0 & t.getC()>=0 & (t.getM()>=t.getC()) & (t.getMaxm()-t.getM()>=t.getMaxc()-t.getC())){
                            t.setParent(state);
                            outputstates.add(t) ;
                        }
                        if(t.getM()==0 & t.getC()>=0 & (t.getMaxm()-t.getM()>=t.getMaxc()-t.getC())){
                            t.setParent(state);
                            outputstates.add(t) ;
                        }
                        if(t.getMaxm()-t.getM()==0 & (t.getM()>=t.getC()) & t.getC()>=0){
                            t.setParent(state);
                            outputstates.add(t) ;
                        }
                    }
                    else if(state.getSide()){

                        State t = new State(state.getM(),state.getC(),state.getSide(),state.getMaxm(),state.getMaxc()) ;
                        t.setState(t.getM()+i,t.getC()+j);
                        if((t.getMaxm()>=t.getM()) & (t.getMaxc()>=t.getC()) & (t.getM()>=t.getC()|t.getM()==0) & (t.getMaxm()-t.getM()>=t.getMaxc()-t.getC() | t.getMaxm()-t.getM()==0)){
                            t.setParent(state);
                            outputstates.add(t);
                        }

                    }
                }
            }
        }

        return outputstates;
    }

    void StartKBoatsBFS(int boat){
        closedSet.add(initial_state) ;
        State result=null;
        while (!closedSet.isEmpty()){
            State tem_state = closedSet.remove() ;
            visited.add(tem_state) ;
            result = tem_state ;
            if(result.equals(final_state)){
                break;
            }
            openSet.addAll(processState(tem_state,boat)) ;
            for(int i=0;i<visited.size();i++){
                for(int j=0;j<openSet.size();j++){
                    if(visited.get(i).equals(openSet.get(j))){
                        if(!openSet.get(j).equals(final_state)){
                            openSet.remove(j);
                        }
                    }
                }
            }
            closedSet.addAll(openSet) ;
            openSet.clear();

        }


        System.out.println("BFS:\n") ;
        result.showDetails();
        result=result.getParent() ;
        while (result!=null)
        {
            result.showDetails();
            result = result.getParent() ;
        }

    }

    void StartKBoatsDFS(int boat){
        closedSetStack.clear();
        openSet.clear();
        visited.clear() ;
        closedSetStack.push(initial_state);
        State result = null ;
        while (!closedSetStack.empty()){
            State tem_state = closedSetStack.pop() ;
            visited.add(tem_state) ;
            result = tem_state ;
            if(result.equals(final_state)){
                break;
            }
            openSet.addAll(processState(tem_state,boat)) ;
            for(int i=0;i<visited.size();i++){
                for(int j=0;j<openSet.size();j++){
                    if(visited.get(i).equals(openSet.get(j))){
                        if(!openSet.get(j).equals(final_state)){
                            openSet.remove(j);
                        }
                    }
                }
            }
            closedSetStack.addAll(openSet) ;
            openSet.clear();
        }
        

        System.out.println("\nDFS:\n") ;
        result.showDetails();
        result=result.getParent() ;
        while (result!=null)
        {
            result.showDetails();
            result = result.getParent() ;
        }
    }

    public static void main(String args[]) throws Exception {
        State initialState = new State(4,3,false,4,3) ;
        State finalState = new State(0,0,true) ;
        MissionariesCannibals instance = new MissionariesCannibals(initialState,finalState) ;
        instance.StartKBoatsBFS(2);
        System.out.println();
        instance.StartKBoatsDFS(2);
    }

    public static class State {
        private int m ;
        private int c ;
        private int maxm ;
        private int maxc ;
        boolean side ;
        int root ;
        State parent ;

        State(int maxm,int maxc,boolean side){
            this.side = side ;
            this.maxc = maxc ;
            this.maxm = maxm ;
            this.m =maxm ;
            this.c =maxc ;
            root = 0 ;
            parent = null ;
        }

        public State(int m, int c, boolean side, int maxm, int maxc) {
            this.side = side ;
            this.m = m ;
            this.c = c ;
            this.maxm =maxm ;
            this.maxc =maxc ;
        }


        void setState(int m,int c){
            this.m = m ;
            this.c = c ;
            this.side = !side ;
        }

        int getM(){
            return m;
        }

        int getC(){
            return c;
        }

        public int getMaxm() {
            return maxm;
        }

        public int getMaxc() {
            return maxc;
        }

        public boolean getSide() {
            return side;
        }

        boolean equals(State e){
            if(e.getM()==this.getM() & e.getC()==this.getC() & e.getSide()==this.getSide()){
                return true ;
            }
            else return false ;
        }

        public void showDetails() {
            System.out.println(m+" "+c+" "+side);
        }

        public State getParent() {
            return parent;
        }

        public void setParent(State parent) {
            this.parent = parent;
        }
    }
}
