package naive_bayes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;
public class DataSet {
	private String[][]input = null;
	private Feature classFeature = null;
	private HashMap<String, Double>priorprobability = new HashMap<String, Double>();
	public DataSet(String[][]input) {
		this.input = input;
	}
	private DataSet calculatepriorprobability() {
		classFeature = new Feature(input,input[0].length-1);
		classFeature.getFeaturesValues().stream().forEach(featureValue -> priorprobability.put(featureValue.getName(), (double)featureValue.getOccurences() /(input.length-1)));
		return this;
	}
	public HashMap<String, Double> calcCondProbs (HashMap<String, String> instance) {

		calculatepriorprobability();
		HashMap<String, Double> condProbs = new HashMap<String, Double>();
		classFeature.getFeaturesValues().forEach(featureValue -> {
			HashMap<String, String>logMap = new HashMap<String, String>();
			logMap.put(featureValue.getName(), featureValue.getOccurences() +"/"+ (input.length-1)); 
			DataSet newDataSet = createDataSet(classFeature.getFeatureValue(featureValue.getName()));
			double condProb = calcCondProb(newDataSet, featureValue.getName(), instance, logMap); 
			condProbs.put(featureValue.getName(), condProb);
			System.out.println(getOutputStr(newDataSet, instance, logMap, condProb, featureValue.getName()));

		}); 
		return condProbs;
	}
	private double calcCondProb(DataSet newDataSet, String classFeatureValue, HashMap<String, String> instanceMap, HashMap<String, String> logMap) {

		ArrayList<Feature> features = new ArrayList<Feature>();

		instanceMap.keySet().stream().forEach(featureName ->
		features.add(new Feature(newDataSet.input, getColumnNumber(featureName)).calculateProbability(instanceMap.get(featureName), logMap))); 
		double condProb = priorprobability.get(classFeatureValue); 
		for (int i = 0; i < features.size(); i++) 
			condProb = features.get(i).getProbability(); 
		return condProb;
	}
	private static String getOutputStr(DataSet dataset,HashMap<String, String>instanceMap,HashMap<String,String>logMap,double probability,String featureValue) {
		StringBuffer outputStrBuff = new StringBuffer(dataset+"\n");
		String instanceStr = GetInstanceStr(dataset,instanceMap);
		outputStrBuff.append("Prob("+featureValue+"|"+instanceStr+") = (Prob("+featureValue+")");
		IntStream.range(0, dataset.input[0].length-1).forEach(i -> outputStrBuff.append("Prob("+instanceMap.get(dataset.input[0][i])+"|"+featureValue+")"));
		outputStrBuff.append(")/"+"Prob("+instanceStr+")\n");
		outputStrBuff.append("Prob("+featureValue+"|"+instanceStr+") ="+ "(("+logMap.get(featureValue)+")");
		IntStream.range(0, dataset.input[0].length-1).forEach(i -> outputStrBuff.append("*("+logMap.get(dataset.input[0][i])+")"));
		outputStrBuff.append(")/"+"Prob("+instanceStr+")\n");
		outputStrBuff.append("Prob("+featureValue+"|"+instanceStr+") = "+ String.format("%.5f", probability)+"/"+"Prob("+instanceStr +")\n");
		return outputStrBuff.toString();
	}
	private DataSet createDataSet(FeatureValue classFeatureValue) {
		String[][] returnInput = new String[classFeatureValue.getOccurences()+1][input[0].length];
		returnInput[0] = input[0];
		int counter =1;
		for(int row = 1; row < input.length;row++)
			if(input[row][input[0].length-1].equals(classFeatureValue.getName())) returnInput[counter++] = input[row];
		return new DataSet(returnInput);
	}
	private int getColumnNumber(String ColumnName) {
		int returnvalue = 0;
		for(int column =1; column < input[0].length; column++)
			if(input[0][column] == ColumnName) {
				returnvalue = column;
				break;
			}
		return returnvalue;
	}
	public static String GetInstanceStr(DataSet dataset, HashMap<String, String> instanceMap) {
		StringBuffer instanceStrBuff = new StringBuffer("<");
		IntStream.range(0, dataset.input[0].length-2).forEach(i -> instanceStrBuff.append(instanceMap.get(dataset.input[0][i])+","));
		// TODO Auto-generated method stub
		return (instanceStrBuff.append(instanceMap.get(dataset.input[0][dataset.input[0].length-2])+">")).toString();
	}
	public String[][]getInput(){
		return input;
	}
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		IntStream.range(0, input.length).forEach(row ->{
			IntStream.range(0, input[row].length).forEach(column ->{
				strBuff.append(input[row][column]);
				IntStream.range(0, 15-input[row][column].length()).forEach(i -> strBuff.append(" "));
			});
			strBuff.append("\n");
			if(row ==0) {
				IntStream.range(0, 70).forEach(i -> strBuff.append("-"));
				strBuff.append("\n");
			}
		});
		return strBuff.toString();
	}
}
