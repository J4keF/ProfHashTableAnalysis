package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class GenderByKeyword extends DataAnalyzer {

    MyHashTable<String, MyHashTable<String, Integer>> genderToKeywords;
    MyHashTable<String, Integer> genderToNum;

	public GenderByKeyword(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		// ADD YOUR CODE BELOW THIS
        //Hashmap (Genders --> Times input word used)
        String word = keyword.toLowerCase().trim();
        genderToNum = new MyHashTable<>();

        if (genderToKeywords.get("M").get(word) != null) {
            genderToNum.put("M", genderToKeywords.get("M").get(word));
        }else{
            genderToNum.put("M", 0);
        }

        if (genderToKeywords.get("F").get(word) != null) {
            genderToNum.put("F", genderToKeywords.get("F").get(word));
        }else{
            genderToNum.put("F", 0);
        }

        if (genderToKeywords.get("X").get(word) != null) {
            genderToNum.put("X", genderToKeywords.get("X").get(word));
        }else{
            genderToNum.put("X", 0);
        }


        return genderToNum;
		//ADD YOUR CODE ABOVE THIS
	}

	@Override
	public void extractInformation() {
		// ADD YOUR CODE BELOW THIS
        //Chop up comments
        //Hashmap (Gender --> Hashmap (KeyWords --> Uses))

        genderToKeywords = new MyHashTable<>();
        genderToKeywords.put("M", new MyHashTable<>());
        genderToKeywords.put("F", new MyHashTable<>());
        genderToKeywords.put("X", new MyHashTable<>());


        int i = 0;
        while (i < super.parser.data.size()) {



            String gender = super.parser.data.get(i)[super.parser.fields.get("gender")];
            String com = super.parser.data.get(i)[super.parser.fields.get("comments")];

            String[] subWords = com.toLowerCase().replaceAll("[^a-z']", " ").split(" ");

            ArrayList<String> used = new ArrayList<>();

            for (String w: subWords) {
                if(used.contains(w)) {
                    continue;
                }else{
                    used.add(w);
                }
                if (genderToKeywords.get(gender).get(w) == null){
                    genderToKeywords.get(gender).put(w, 1);
                }else{
                    genderToKeywords.get(gender).put(w, genderToKeywords.get(gender).get(w) + 1);
                }
            }

            i++;
        }


		//ADD YOUR CODE ABOVE THIS
	}

}
