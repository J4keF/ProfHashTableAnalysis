package finalproject;

import java.util.Locale;

public class RatingDistributionByProf extends DataAnalyzer {

    private MyHashTable<String, MyHashTable<String, Integer>> namesToRatings;
	
    public RatingDistributionByProf(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		// ADD YOUR CODE BELOW THIS
        String name = keyword.toLowerCase().trim();

        return namesToRatings.get(name);
		//ADD YOUR CODE ABOVE THIS
	}

	@Override
	public void extractInformation() {
		// ADD YOUR CODE BELOW THIS
        namesToRatings = new MyHashTable<>(super.parser.data.size());
        String curName = super.parser.data.get(0)[super.parser.fields.get("professor_name")];
        MyHashTable<String, Integer> ratingsToCount = new MyHashTable<>();
        ratingsToCount.put("1", 0);
        ratingsToCount.put("2", 0);
        ratingsToCount.put("3", 0);
        ratingsToCount.put("4", 0);
        ratingsToCount.put("5", 0);

        //Loop through each review and create a hashmap mapping professor name
        // to second hashmap mapping 1,2,3,4,5 to number of corresponding ratings
        int i = 0;
        while (i < super.parser.data.size()) {
            while (i < super.parser.data.size() && super.parser.data.get(i)[super.parser.fields.get("professor_name")].equals(curName)) {
                Double rating = Double.parseDouble(super.parser.data.get(i)[super.parser.fields.get("student_star")]);
                if (rating >= 1.0 && rating < 2.0) {
                    ratingsToCount.put("1", ratingsToCount.get("1") + 1);
                } else if (rating >= 2.0 && rating < 3.0) {
                    ratingsToCount.put("2", ratingsToCount.get("2") + 1);
                } else if (rating >= 3.0 && rating < 4.0) {
                    ratingsToCount.put("3", ratingsToCount.get("3") + 1);
                } else if (rating >= 4.0 && rating < 5.0) {
                    ratingsToCount.put("4", ratingsToCount.get("4") + 1);
                } else if (rating == 5.0) {
                    ratingsToCount.put("5", ratingsToCount.get("5") + 1);
                }

                i++;
            }
            this.namesToRatings.put(curName.toLowerCase().trim(), ratingsToCount);
            ratingsToCount = new MyHashTable<>();
            ratingsToCount.put("1", 0);
            ratingsToCount.put("2", 0);
            ratingsToCount.put("3", 0);
            ratingsToCount.put("4", 0);
            ratingsToCount.put("5", 0);
            if (i < super.parser.data.size()){
                curName = super.parser.data.get(i)[super.parser.fields.get("professor_name")];
            }
        }
		//ADD YOUR CODE ABOVE THIS
	}

}
