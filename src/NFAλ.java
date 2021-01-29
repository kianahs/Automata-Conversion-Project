import com.sun.org.apache.bcel.internal.generic.ALOAD;

import java.util.*;
import java.util.stream.Collectors;

public class NFAλ {

    private String name;
    private ArrayList<Character> alphabets ;
    private ArrayList<State>  states ;
    private State firstState;
    private ArrayList<State> finalStates;
    private DFA dfa;

    public NFAλ(String name) {
        this.name = name;
        dfa=new DFA("my DFA");
        finalStates=new ArrayList<>();
        states = new ArrayList<>();
        alphabets =new ArrayList<>();
    }

    public void setFirstState(State firstState) {
        this.firstState = firstState;
    }

    public ArrayList<Character> getAlphabets() {
        return alphabets;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public State getFirstState() {
        return firstState;
    }

    public ArrayList<State> getFinalStates() {
        return finalStates;
    }

    public State findStateFromList(State s){

        int index= states.indexOf(s);
        return states.get(index);
    }


    public void linkFirstState(){

        int index = states.indexOf(firstState);
        firstState=states.get(index);
    }


    public void removeDuplicates(ArrayList<State> stateArrayList){

        List<State> list=stateArrayList;
        List<State> newList = list.stream().distinct().collect(Collectors.toList());
        stateArrayList= (ArrayList<State>) newList;

    }


    public Boolean checkValueOnHashMap(ArrayList<State> arrayList , HashMap<State,ArrayList<State>> hashMap){

        if (hashMap.containsValue(arrayList))
            return true;
        else
            return false;

    }

    public ArrayList<State> delta(ArrayList<State> mapElementValues ,Character alphabet){

        //System.out.println("lambda closure size "+arrayList1.size());
        ArrayList<State> arrayList1=new ArrayList<>();
        ArrayList<State> arrayList2=new ArrayList<>();

        for (State s: mapElementValues) {

            ArrayList<State> nextStates=s.findNextStates(alphabet);
            if(nextStates !=null )
                arrayList1.addAll(nextStates);
            else
                System.out.println("there is no next state");

        }

        removeDuplicates(arrayList1);

        for (State s: arrayList1) {
            arrayList2.addAll(s.getλClosure());

        }
        removeDuplicates(arrayList2);


        return arrayList2;
    }






    public void constructDFAStates(){

        HashMap<State,ArrayList<State>> hashMapOfStates =dfa.getHashMapOfStates();
        State first=new State("A");
        hashMapOfStates.put(first,firstState.getλClosure());
        dfa.setFirstState(first);
        int ascii=65;
        Iterator iterator=hashMapOfStates.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry mapElement= (Map.Entry) iterator.next();
            ArrayList<State> res;

            for (char c: dfa.getAlphabets()) {

                ArrayList<State> mapElementValue=(ArrayList<State>) mapElement.getValue();
                //System.out.println(mapElementValue.size());

                if (mapElementValue.isEmpty()){
                    System.out.println("map is empty");
                }

              if (delta(mapElementValue,c).size()>0) {

                   System.out.println("delta size "+delta(mapElementValue,c).size());
                   res = delta(mapElementValue, c);

                   State key= (State) mapElement.getKey();
                   key.addNextArrayOfStatesToState(c,res);

                   if(checkValueOnHashMap(res,hashMapOfStates)==false){

                       ascii++;
                       String name=Character.toString((char) ascii);
                       hashMapOfStates.put(new State(name),res);


                   }

               }

                else{

                   System.out.println("delta is empty");
                    continue;
               }

            }


        }

        dfa.setHashMapOfStates(hashMapOfStates);

    }


    public void constructFinalStatesOfDFA(){

        HashMap<State,ArrayList<State>> dfaHashMap=dfa.getHashMapOfStates();
        ArrayList<State> result=new ArrayList<>();

        for (State s: finalStates) {

            Iterator iterator=dfaHashMap.entrySet().iterator();

            while (iterator.hasNext()){

                Map.Entry mapElement= (Map.Entry) iterator.next();
                ArrayList<State> value= (ArrayList<State>) mapElement.getValue();

                if (value.contains(s)){

                    State key= (State) mapElement.getKey();
                    result.add(key);


                }


            }
        }

        dfa.setFinalStates(result);

    }




    public void convertToDFA(){


        ArrayList<Character> newAlph=new ArrayList<>();
        newAlph.addAll(alphabets);
        newAlph.remove(Character.valueOf('λ'));
        dfa.setAlphabets(newAlph);
        constructDFAStates();
        constructFinalStatesOfDFA();
        dfa.printDFA();
    }






}
