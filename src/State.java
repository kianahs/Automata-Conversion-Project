import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class State {

    private String stateName;
    private HashMap<Character, ArrayList<State>> hashMapNextStates=new HashMap<Character, ArrayList<State>>();
    private ArrayList<State> λClosure=new ArrayList<>();

    public State(String stateName) {
        this.stateName = stateName;
    }



    public  void addNextStatesToState(Character alphabet,State state){

        ArrayList<State> itemsList = hashMapNextStates.get(alphabet);

        if(itemsList == null) {

            itemsList = new ArrayList<State>();
            itemsList.add(state);
            hashMapNextStates.put(alphabet, itemsList);

            //System.out.println("itemslist size"+ arrayList.size());
        }
        else {

            if(!itemsList.contains(state))

                itemsList.add(state);
        }

        System.out.printf("%s %c ", stateName ,alphabet);
        for (State s: hashMapNextStates.get(alphabet)) {

            System.out.printf("%s ", s.getStateName());

        }
        System.out.println();

    }

    public HashMap<Character, ArrayList<State>> getHashMapNextStates() {
        return hashMapNextStates;
    }

    public ArrayList<State> findNextStates(Character alphabet){

        ArrayList<State> arrayList;
        arrayList=getHashMapNextStates().get(alphabet);


        if (arrayList==null)
            System.out.println("arraylist of next states is null");

        else if (arrayList.isEmpty())
            System.out.println("arraylist of next states is empty");


        return arrayList;
    }


    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return stateName.equals(state.stateName);
    }


    public void lambdaClosure(){

        ArrayList<State> mainList=new ArrayList<>();
        mainList.add(this);
        ArrayList<State> permanentList=new ArrayList<>();

        if (findNextStates('λ')!=null ) {
            permanentList.addAll(findNextStates('λ')) ;
            mainList.addAll(permanentList);
        }

        if (!(permanentList.isEmpty())){

            Iterator iterator=permanentList.iterator();


            while (iterator.hasNext()) {

                State s= (State) iterator.next();

                if (s.findNextStates('λ') != null) {

                    mainList.addAll(s.findNextStates('λ'));
                    permanentList.addAll(s.findNextStates('λ'));
                    removeDuplicates(permanentList);
                }
                else{

                    continue;
                }

            }

        }

        removeDuplicates(mainList);
        λClosure=mainList;



    }




    public ArrayList<State> getλClosure() {
        return λClosure;
    }


    public void removeDuplicates(ArrayList<State> stateArrayList){

        List<State> list=stateArrayList;
        List<State> newList = list.stream().distinct().collect(Collectors.toList());
        stateArrayList= (ArrayList<State>) newList;

    }

    public void addNextArrayOfStatesToState(Character character, ArrayList<State> arrayList){

        hashMapNextStates.put(character,arrayList);


    }





}
