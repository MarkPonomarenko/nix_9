package nix.homework.functional;

import nix.homework.RunManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class SecondTask{

    public static Character getKey(Map<Character, Integer> map, Integer value) {
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                Character c = entry.getKey();
                map.remove(c, value); //removing map element to avoid duplicates
                return c;
            }
        }
        return null;
    }

    public static void run(BufferedReader input) throws IOException{
        System.out.println("Выбрано второе задание!");
        System.out.println("Введите строку:");
        String text = input.readLine();

        Map<Character, Integer> charCounterMap = new HashMap<Character, Integer>();
        int counter = 0;
        char[] textArray = text.toCharArray();
        for(char i : textArray){
            if (Character.isLetter(i) && !charCounterMap.containsKey(i)){
                counter++;
                for (char j :textArray){
                    if(j == i){
                        counter++;
                    }
                }
                counter--;
                charCounterMap.put(i,counter);
            }
            counter = 0;
        }
        Collection<Integer> countSet = charCounterMap.values(); //can't sort using Collections.sort ?error
        List<Integer> buffList = new ArrayList<Integer>();      //converting into list to sort in descending order
        for(Integer i : countSet){
            buffList.add(i);
        }
        Collections.sort(buffList, Collections.reverseOrder());
        List<String> finalList = new ArrayList<String>();       //final list with results
        for (Integer i : buffList){
            finalList.add(getKey(charCounterMap, i) + " - " + i); //seeking for keys and place them
        }
        int i = 0;
        for (String s : finalList){
            i++;
            System.out.println(i + ". " + s);
        }
        System.out.println("Нажмите Enter для продолжения...");
        System.in.read();
        new RunManager().run();
    }
}