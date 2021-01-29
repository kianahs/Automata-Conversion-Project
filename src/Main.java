import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        NFAλ nfAλ=new NFAλ("myNFAλ");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("NFA_Input_2.txt"));
        ArrayList<String> lines=new ArrayList<>();

        String row=bufferedReader.readLine();
        while (row!=null){

            lines.add(row);
            row=bufferedReader.readLine();

        }


        String[] alphabets=lines.get(0).split("\\s+");

        for (String s: alphabets) {
            nfAλ.getAlphabets().add(s.charAt(0));
        }


        String[] statesNames= lines.get(1).split("\\s+");
        for (String s:statesNames) {

            State state=new State(s);
            nfAλ.getStates().add(state);

        }

        System.out.println(" numb of states="+nfAλ.getStates().size());

        String[] firstState=lines.get(2).split("\\s+");
        for (String s: firstState) {
            State state=new State(s);
            nfAλ.setFirstState(state);
        }


        String[] finalStates= lines.get(3).split("\\s+");
        for (String s:finalStates) {

            State state=new State(s);
            nfAλ.getFinalStates().add(state);
        }


        for (int i=4 ; i<lines.size(); i++){

            String[] combination= lines.get(i).split("\\s+");
            ArrayList<State> states=nfAλ.getStates();
            State cs=new State(combination[0]);

            if(states.contains(cs)){

                int index=states.indexOf(cs);
                states.get(index).addNextStatesToState(combination[1].charAt(0),new State(combination[2]));
            }

        }

        nfAλ.linkFirstState();

        for (State s: nfAλ.getStates()) {

            s.lambdaClosure();
        }


        nfAλ.convertToDFA();












    }

}
