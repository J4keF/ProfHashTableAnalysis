package finalproject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;




public class MyHashTable<K,V> implements Iterable<MyPair<K,V>>{
	// num of entries to the table
	private int size;
	// num of buckets 
	private int capacity = 16;
	// load factor needed to check for rehashing 
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<MyPair<K,V>>> buckets; 


	// constructors
	public MyHashTable() {
		// ADD YOUR CODE BELOW THIS
        this.size = 0;
        this.capacity = 16;
        this.buckets = new ArrayList<>();
        for (int i = 0; i < 16; i++){
            this.buckets.add(new LinkedList<>());
        }
		//ADD YOUR CODE ABOVE THIS
	}

	public MyHashTable(int initialCapacity) {
		// ADD YOUR CODE BELOW THIS
        this.size = 0;
        this.capacity = initialCapacity;
        this.buckets = new ArrayList<>(initialCapacity);
        for (int i = 0; i < this.capacity; i++){
            this.buckets.add(new LinkedList<>());
        }
		//ADD YOUR CODE ABOVE THIS
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int numBuckets() {
		return this.capacity;
	}

	/**
	 * Returns the buckets variable. Useful for testing  purposes.
	 */
	public ArrayList<LinkedList<MyPair<K,V> > > getBuckets(){
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode())%this.capacity;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair
	 * to this HashTable. Expected average run time  O(1)
	 */
	public V put(K key, V value) {
		//  ADD YOUR CODE BELOW HERE
        int index = hashFunction(key);
        LinkedList<MyPair<K,V>> curList = this.buckets.get(index);

        if (curList.size() != 0){
            for(MyPair<K,V> pair: curList){
                if(pair.getKey().equals(key)){
                    V val = pair.getValue();
                    pair.setValue(value);
                    return val;
                }
            }
        }

        //TRIGGERS REHASH IF LOAD FACTOR IS EXCEEDED
        if(((double) this.size + 1.0)/this.capacity > MAX_LOAD_FACTOR){
            this.rehash();
            index = hashFunction(key);
        }
        this.buckets.get(index).add(new MyPair(key, value));
        this.size++;

		return null;
		//  ADD YOUR CODE ABOVE HERE
	}


	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
        //ADD YOUR CODE BELOW HERE
        int index = hashFunction(key);

        for (MyPair<K,V> pair : this.buckets.get(index)){
            if (pair.getKey().equals(key)){
                return pair.getValue();
            }
        }

        return null;
		//ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1) 
	 */
	public V remove(K key) {
		//ADD YOUR CODE BELOW HERE
        int index = hashFunction(key);
        int pairCount = 0;
        MyPair<K, V> output;

        for (MyPair<K,V> pair : this.buckets.get(index)){
            if (pair.getKey() == key){
                output = this.buckets.get(index).remove(pairCount);
                size --;

                return output.getValue();
            }
            pairCount++;
        }
		return null;

		//ADD YOUR CODE ABOVE HERE
	}


	/** 
	 * Method to double the size of the hashtable if load factor increases
	 * beyond MAX_LOAD_FACTOR.
	 * Made public for ease of testing.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */
	public void rehash() {
		//ADD YOUR CODE BELOW HERE
        //generate new arraylist
        //Double capcity
        //Copy elements O(m/2)
        //Fill remaining half

        this.capacity = this.capacity*2;

        ArrayList<LinkedList<MyPair<K,V>>> newList = new ArrayList<>(this.capacity);
        ArrayList<MyPair<K,V>> entries = this.getEntries();

        //Fill in
        for (int i = 0; i < this.capacity; i++){
            newList.add(new LinkedList<>());
        }

        this.buckets = newList;

        this.size = 0;

        //Existing indices transfer
        for (MyPair<K,V> pair: entries){
            this.put(pair.getKey(), pair.getValue());
        }

		//ADD YOUR CODE ABOVE HERE
	}


	/**
	 * Return a list of all the keys present in this hashtable.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> getKeySet() {
		//ADD YOUR CODE BELOW HERE

        ArrayList<K> output = new ArrayList<>(this.size);
        for (LinkedList<MyPair<K,V>> bucket: this.buckets){
            for (MyPair<K,V> pair: bucket){
                output.add(pair.getKey());
            }
        }
		
		return output;

		//ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> getValueSet() {
		//ADD CODE BELOW HERE
        ArrayList<V> output = new ArrayList<>(this.size);
        for (LinkedList<MyPair<K,V>> bucket: this.buckets){
            for (MyPair<K,V> pair: bucket){
                if (!output.contains(pair.getValue())) {
                    output.add(pair.getValue());
                }
            }
        }


		return output;

		//ADD CODE ABOVE HERE
	}


	/**
	 * Returns an ArrayList of all the key-value pairs present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<MyPair<K, V>> getEntries() {
		//ADD CODE BELOW HERE

        ArrayList<MyPair<K,V>> output = new ArrayList<>(this.size);
        for (LinkedList<MyPair<K,V>> bucket: this.buckets){
            output.addAll(bucket);
        }

        return output;

		//ADD CODE ABOVE HERE
	}

	
	
	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}   

	
	private class MyHashIterator implements Iterator<MyPair<K,V>> {

        ArrayList<MyPair<K,V>> mainList;
        int index;

		private MyHashIterator() {
			//ADD YOUR CODE BELOW HERE
            mainList = getEntries();
            this.index = 0;
			//ADD YOUR CODE ABOVE HERE
		}

		@Override
		public boolean hasNext() {
			//ADD YOUR CODE BELOW HERE
			if (this.index < mainList.size() && mainList.get(this.index) != null){
                return true;
            }

            return false;
			//ADD YOUR CODE ABOVE HERE
		}

		@Override
		public MyPair<K,V> next() {
			//ADD YOUR CODE BELOW HERE
            if (hasNext()){
                this.index++;
                return mainList.get(this.index - 1);
            }

			return null;
			
			//ADD YOUR CODE ABOVE HERE
		}

	}
	
}
