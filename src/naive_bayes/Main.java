package naive_bayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {
	static String[][] CONTACT_LENSES = {
			{"Age","SpectaclePrescrip","Astigmatism","TearProdRate","ContactLens"},
			{"young","myope","no","reduced","none"},
			{"young","myope","no","normal","soft"},
			{"young","myope","yes","reduced","none"},
			{"young","myope","yes","normal","hard"},
			{"young","hypermetrope","no","reduced","none"},
			{"young","hypermetrope","no","normal","soft"},
			{"young","hypermetrope","yes","reduced","none"},
			{"young","hypermetrope","yes","normal","hard"},
			{"pre-presbyopic","myope","no","reduced","none"},
			{"pre-presbyopic","myope","no","normal","soft"},
			{"pre-presbyopic","myope","yes","reduced","none"},
			{"pre-presbyopic","myope","yes","normal","hard"},
			{"pre-presbyopic","hypermetrope","no","reduced","none"},
			{"pre-presbyopic","hypermetrope","no","normal","soft"},
			{"pre-presbyopic","hypermetrope","yes","reduced","none"},
			{"pre-presbyopic","hypermetrope","yes","normal","none"},
			{"presbyopic","myope","no","reduced","none"},
			{"presbyopic","myope","no","normal","none"},
			{"presbyopic","myope","yes","reduced","none"},
			{"presbyopic","myope","yes","normal","hard"},
			{"presbyopic","hypermetrope","no","reduced","none"},
			{"presbyopic","hypermetrope","no","normal","soft"},
			{"presbyopic","hypermetrope","yes","reduced","none"},
			{"presbyopic","hypermetrope","yes","normal","none"}};
	/*
	 * static String[][] WEATHER = {
	 * {"outlook","temperature","humidity","windy","play"},
	 * {"sunny","hot","high","false","no"}, {"sunny","hot","high","true","no"},
	 * {"overcast","hot","high","false","yes"},
	 * {"rainy","mild","high","false","yes"},
	 * {"rainy","cool","normal","false","yes"},
	 * {"rainy","cool","normal","true","no"},
	 * {"overcast","cool","normal","true","yes"},
	 * {"sunny","mild","high","false","no"},
	 * {"sunny","cool","normal","false","yes"},
	 * {"rainy","mild","normal","false","yes"},
	 * {"sunny","mild","normal","true","yes"},
	 * {"overcast","mild","high","true","yes"},
	 * {"overcast","hot","normal","false","yes"},
	 * {"rainy","mild","high","true","no"}};
	 */
	static Map<String, String[][]>datas = Collections.unmodifiableMap(new HashMap<String, String[][]>(){
		private static final long serialVerersoinUID = 1L;
		{
			/* put("WEATHER",WEATHER); */
			put("CONTACT_LENSES",CONTACT_LENSES);
		}
	});
	static String dataKey = datas.keySet().iterator().next();
	public static void main(String[] args) throws IOException {
		DataSet dataset = new DataSet(datas.get(dataKey));
		System.out.println("["+dataKey+" DATASET]\n"+dataset);
		BufferedReader buffread = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		while (flag) {
			System.out.println("> Calculate Probability Or exit?\n");
			String command = buffread.readLine();
			switch(command) {
			case "Calculate":
				System.out.println("> Please enter values for: ");
				for(int i=0; i < dataset.getInput()[0].length-2; i++) System.out.print(dataset.getInput()[0][i] + ", ");
				System.out.println(dataset.getInput()[0][dataset.getInput()[0].length-2] + "(separated BY Commas)");
				String[]values = (buffread.readLine().split(","));
				HashMap<String, String> instMap = new HashMap<String, String>(); 
				for (int i = 0; i < dataset.getInput()[0].length-1; i++) instMap.put(dataset.getInput()[0][i], values[i].trim());

				HashMap<String, Double> condProbs = dataset.calcCondProbs(instMap); 
				double allProbs =0;

				Iterator <Double> probsIterator = condProbs.values().iterator();

				while(probsIterator.hasNext()) allProbs+= probsIterator.next();

				Iterator<String> keyIterator = condProbs.keySet().iterator(); 
				while (keyIterator.hasNext()) {

					String next = keyIterator.next();

					System.out.println("Prob("+ next+"|"+ DataSet.GetInstanceStr(dataset, instMap)+") = "+ String.format("%.5f",condProbs.get(next))+"/"+String.format("%.5f", allProbs)+ "="+String.format("%.5f",condProbs.get(next)/allProbs));
				}
				System.out.println();
				break;
			/*
			 * case "change dataset":
			 * System.out.println("> Choose dataset ("+datas.keySet()+" ?"); String
			 * value=buffread.readLine(); if (datas.keySet().contains(value)) { dataKey=
			 * value; dataset =new DataSet (datas.get(dataKey));
			 * System.out.println(dataset); } else
			 * System.out.println("please enter valid dataset name"); break;
			 */
			case "exit":
				flag = false;
				break;
			}
		}
		System.exit(0);
	}
}
