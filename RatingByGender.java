package finalproject;

import java.util.ArrayList;
import java.util.Locale;

public class RatingByGender extends DataAnalyzer{

    MyHashTable<String, MyHashTable<String, MyHashTable<String, Integer>>> omega;

	public RatingByGender(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		// ADD YOUR CODE BELOW THIS
        //Input = "F, quality" or "M, difficulty"

        //Hashmap (1,2,3,4,5 --> # of reviews for given gender)
        String spl1 = keyword.toLowerCase().trim();
        if (spl1.charAt(3) == ' ' || (spl1.charAt(0) != 'm' && spl1.charAt(0) != 'f')){
            return null;
        }

        String[] spl = spl1.replaceAll(" ", "").split(",");
        String g = spl[0].toUpperCase();

        String qualOrDiff = spl[1];

        if (!qualOrDiff.equals("quality") && !qualOrDiff.equals("difficulty")){
            return null;
        }

        MyHashTable<String, Integer> output;

        output = omega.get(g).get(qualOrDiff);

        if (output.get("1") == null){
            output.put("1", 0);
        }
        if (output.get("2") == null){
            output.put("2", 0);
        }
        if (output.get("3") == null){
            output.put("3", 0);
        }
        if (output.get("4") == null){
            output.put("4", 0);
        }
        if (output.get("5") == null){
            output.put("5", 0);
        }


		return output;
		//ADD YOUR CODE ABOVE THIS
	}

	@Override
	public void extractInformation() {
		// ADD YOUR CODE BELOW THIS

        //Hashmap (Gender --> Hashmap (qual, diff --> Hashmap (1,2,3,4,5 --> Count)))

        omega = new MyHashTable<>();
        omega.put("M", new MyHashTable<>());
        omega.put("F", new MyHashTable<>());

        int i = 0;
        while (i < super.parser.data.size()) {

            String gender = super.parser.data.get(i)[super.parser.fields.get("gender")];

            if (gender.equals("X")){
                i++;
                continue;
            }

            String rating = super.parser.data.get(i)[super.parser.fields.get("student_star")];
            String diff = super.parser.data.get(i)[super.parser.fields.get("student_difficult")];

            Double ratingCheck = Double.parseDouble(rating);

            if (ratingCheck >= 1.0 && ratingCheck < 2.0) {
                rating = "1";
            } else if (ratingCheck >= 2.0 && ratingCheck < 3.0) {
                rating = "2";
            } else if (ratingCheck >= 3.0 && ratingCheck < 4.0) {
                rating = "3";
            } else if (ratingCheck >= 4.0 && ratingCheck < 5.0) {
                rating = "4";
            } else if (ratingCheck == 5.0) {
                rating = "5";
            }

            //Quality
            if(omega.get(gender).get("quality") != null) {
                if(omega.get(gender).get("quality").get(rating) != null) {
                    omega.get(gender).get("quality").put(rating, omega.get(gender).get("quality").get(rating) + 1);
                }else{
                    omega.get(gender).get("quality").put(rating, 1);
                }
            }else{
                MyHashTable<String, Integer> tempMap = new MyHashTable<>();
                tempMap.put(rating, 1);
                omega.get(gender).put("quality", tempMap);
            }

            //Difficulty
            if(omega.get(gender).get("difficulty") != null) {
                if(omega.get(gender).get("difficulty").get(diff) != null) {
                    omega.get(gender).get("difficulty").put(diff, omega.get(gender).get("difficulty").get(diff) + 1);
                }else{
                    omega.get(gender).get("difficulty").put(diff, 1);
                }
            }else{
                MyHashTable<String, Integer> tempMap = new MyHashTable<>();
                tempMap.put(diff, 1);
                omega.get(gender).put("difficulty", tempMap);
            }

            i++;

        }


            //ADD YOUR CODE ABOVE THIS
	}
}
