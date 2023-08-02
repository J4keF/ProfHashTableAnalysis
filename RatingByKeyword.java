package finalproject;

import java.util.ArrayList;
import java.util.HashMap;

public class RatingByKeyword extends DataAnalyzer {

    MyHashTable<String, MyHashTable<String, Integer>> keywordToReviews;
    MyHashTable<String, Integer> reviewToCount;
	
    public RatingByKeyword(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {
		// ADD YOUR CODE BELOW THIS
        //Keyword input!
        //Hashmap (Ratings --> # of reviews with keyword)
        String word = keyword.toLowerCase().trim();


        try {
            if (keywordToReviews.get(word).get("1") == null) {
                keywordToReviews.get(word).put("1", 0);
            }
            if (keywordToReviews.get(word).get("2") == null) {
                keywordToReviews.get(word).put("2", 0);
            }
            if (keywordToReviews.get(word).get("3") == null) {
                keywordToReviews.get(word).put("3", 0);
            }
            if (keywordToReviews.get(word).get("4") == null) {
                keywordToReviews.get(word).put("4", 0);
            }
            if (keywordToReviews.get(word).get("5") == null) {
                keywordToReviews.get(word).put("5", 0);
            }

            return keywordToReviews.get(word);
        } catch (Exception e){
            return null;
        }

		//ADD YOUR CODE ABOVE THIS
	}

	@Override
	public void extractInformation() {
		// ADD YOUR CODE BELOW THIS
        //Hashmap (Keyword --> Hashmap(1,2,3,4,5 --> # of reviews))

        keywordToReviews = new MyHashTable<>();

        int i = 0;
        while (i < super.parser.data.size()) {
            String rating = super.parser.data.get(i)[super.parser.fields.get("student_star")];

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

            String com = super.parser.data.get(i)[super.parser.fields.get("comments")];

            String[] subWords = com.toLowerCase().replaceAll("[^a-z']", " ").split(" ");

            ArrayList<String> used = new ArrayList<>();


            for (String w : subWords) {
                if(w.equals("")){
                    continue;
                }

                if(used.contains(w)) {
                    continue;
                }else{
                    used.add(w);
                }
                if (keywordToReviews.get(w) != null) {
                    if (keywordToReviews.get(w).get(rating) != null) {
                        keywordToReviews.get(w).put(rating, keywordToReviews.get(w).get(rating) + 1);
                    }else{
                        keywordToReviews.get(w).put(rating, 1);
                    }
                }else{
                    MyHashTable<String, Integer> tempLoad = new MyHashTable<>();
                    tempLoad.put(rating, 1);
                    keywordToReviews.put(w, tempLoad);
                }
            }

            i++;
        }



		//ADD YOUR CODE ABOVE THIS
	}
}
