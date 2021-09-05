package naive_bayes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.IntStream;

public class Feature {
	private String name = null;
	private String[][] input = null;
	private HashSet<FeatureValue>featureValues = new HashSet<FeatureValue>();
	private double probability;
	public Feature(String[][] input, int column) {
		// TODO Auto-generated constructor stub
		System.out.println(column);
		this.name = input[0][column];
		this.input = input;
		IntStream.range(1, input.length).forEach(row -> featureValues.add(new FeatureValue(input[row][column])));
		featureValues.stream().forEach(featureValue -> {
			int counter = 0;
			for(int row =1;row < input.length; row++)
				if(featureValue.getName() == input[row][column]) featureValue.setOccurences(++counter);
		});
	}
	public Feature calculateProbability(String featureValueName,HashMap<String,String> logMap) {
		if(getFeatureValue(featureValueName) != null) {
			probability = (((double)getFeatureValue(featureValueName).getOccurences())/(input.length-1));
			logMap.put(this.name, getFeatureValue(featureValueName).getOccurences() + "/" +(input.length-1));
		}else {
			probability = 0;
			logMap.put(this.name, "0/"+ (input.length-1));
		}
		return this;
	}
	public FeatureValue getFeatureValue(String featureValueName) {
		FeatureValue returnValue = null;
		Iterator<FeatureValue> iterator = featureValues.iterator();
		while(iterator.hasNext()) {
			FeatureValue featureValue = iterator.next();
			if(featureValue.getName().equals(featureValueName)) {
				returnValue = featureValue;
				break;
			}
		}
		return returnValue;	
	}

	public double getProbability() {
		return probability;
	}
	public String getName() {
		return name;
	}
	public HashSet<FeatureValue> getFeaturesValues(){
		return featureValues;
	}
	public String toString() {
		return name;
	}
}
