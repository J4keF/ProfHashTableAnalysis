package finalproject;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingDistributionBySchool extends DataAnalyzer {

    private MyHashTable<String, String> namesToAve;
    private MyHashTable<String, Integer> namesToReviews;
    private MyHashTable<String, String> namesToSchool;
    private MyHashTable<String, Integer> output;

	public RatingDistributionBySchool(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		// ADD YOUR CODE BELOW THIS
        //Take prof name --> Return hashmap (Name and AVG rating to # of ratings)
        String school = keyword.toLowerCase().trim();
        output = new MyHashTable<>();

        try {
            boolean triggered = false;

            for (MyPair<String, String> pair : namesToSchool.getEntries()) {
                if (pair.getValue().toLowerCase().trim().equals(school)) {
                    output.put(pair.getKey() + "\n" + namesToAve.get(pair.getKey()), namesToReviews.get(pair.getKey()));
                    triggered = true;
                }
            }

            if (triggered == false){
                return null;
            }

        }catch(Exception e){
            return null;
        }


		return output;
		//ADD YOUR CODE ABOVE THIS
	}

	@Override
	public void extractInformation() {
		// ADD YOUR CODE BELOW THIS
        //Hashmap (Prof Name to AVG RATING)
        //Hashmap (Prof Name to number of reviews)

        namesToAve = new MyHashTable<>(super.parser.data.size());
        namesToReviews = new MyHashTable<>(super.parser.data.size());
        namesToSchool = new MyHashTable<>(super.parser.data.size());
        String curName = super.parser.data.get(0)[super.parser.fields.get("professor_name")];
        ArrayList<Double> average = new ArrayList<>(100);
        int ratingCount = 0;
        String school = "";

        ///////////////
        int i = 0;
        while (i < super.parser.data.size()) {
            while (i < super.parser.data.size() && super.parser.data.get(i)[super.parser.fields.get("professor_name")].equals(curName)) {
                ratingCount ++;
                Double rating = Double.parseDouble(super.parser.data.get(i)[super.parser.fields.get("student_star")]);
                average.add(rating);
                school = super.parser.data.get(i)[super.parser.fields.get("school_name")];

                i++;
            }
            double avg = 0;
            for (double num: average){
                avg += num;
            }
            avg = Math.round((avg/(average.size() * 1.0))*100.0)/100.0;

            average.clear();
            namesToAve.put(curName.toLowerCase().trim(), avg+"");
            namesToReviews.put(curName.toLowerCase().trim(), ratingCount);
            namesToSchool.put(curName.toLowerCase().trim(), school);

            ratingCount = 0;
            if (i < super.parser.data.size()){
                curName = super.parser.data.get(i)[super.parser.fields.get("professor_name")];
            }
        }

		//ADD YOUR CODE ABOVE THIS
	}
}
