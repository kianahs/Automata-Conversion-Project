import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DFA {

    private String name;
    private ArrayList<Character>  alphabets =new ArrayList<>() ;
    private HashMap<State , ArrayList<State> >  hashMapOfStates = new HashMap<>();
    private State firstState;
    private ArrayList<State> finalStates=new ArrayList<>();

    public void setFirstState(State firstState) {
        this.firstState = firstState;
    }

    public DFA(String name) {
        this.name=name;
    }

    public ArrayList<Character> getAlphabets() {
        return alphabets;
    }

    public HashMap<State, ArrayList<State>> getHashMapOfStates() {
        return hashMapOfStates;
    }



    public State getFirstState() {
        return firstState;
    }

    public ArrayList<State> getFinalStates() {
        return finalStates;
    }

    public void setHashMapOfStates(HashMap<State, ArrayList<State>> hashMapofStates) {
        this.hashMapOfStates = hashMapofStates;
    }

    public void setAlphabets(ArrayList<Character> alphabets) {
        this.alphabets = alphabets;
    }



    public void setFinalStates(ArrayList<State> finalStates) {
        this.finalStates = finalStates;
    }


    
    public void printDFA(){

        for (char c: alphabets) {
            
            System.out.printf("%c ",c);
        }

        System.out.println();


        Set<State> keys=hashMapOfStates.keySet();
        System.out.println("size is "+hashMapOfStates.keySet().size());

        for (State s: keys) {

            ArrayList<State> arrayList=hashMapOfStates.get(s);
            System.out.printf("%s:" ,s.getStateName());

            for (State state : arrayList ) {

                System.out.printf("%s" ,  state.getStateName());

            }

            System.out.printf(" ");

        }

        System.out.println();
        System.out.println(firstState.getStateName());

        for (State s:finalStates) {
            
            System.out.printf("%s " , s.getStateName());
        }


        
        
        
        
        
    }









}

