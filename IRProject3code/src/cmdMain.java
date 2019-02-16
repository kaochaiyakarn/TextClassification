import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import opennlp.tools.ml.naivebayes.NaiveBayesModel;

public class cmdMain {
	
	public static void trainningData(List<String> corpus) {
		// TODO Auto-generated method stub
		System.out.println("!!!!!! Learning Process !!!!!!!!");
		dataTranninng test = new NaiveBayesClassify(corpus);
		String s=test.classifier("proud",false);
		System.out.println("result : "+s);
	}
	public static List<String> dataList=new ArrayList<String>();
	public static void setCorpus() {
		// TODO Auto-generated method stub
		dataList.add("/ANGERPHRASE_neg.txt");
		dataList.add("/FEARPhrases_neg.txt");
		dataList.add("/JOYPhrases_pos.txt");
		dataList.add("/LOVEPhrases_pos.txt");
		dataList.add("/SADNESSPhrases_neg.txt");
		dataList.add("/SURPRISEPhrases_pos.txt");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		StringBuilder str = new StringBuilder();
////		str.append("kao \n");
////		str.append("Chaiyakarn");
////		String a = str.toString();
////		System.out.println(a);
//		
//		setCorpus();
//		trainningData(dataList);

//		Map<String,Integer> wordCountPos = new HashMap<String,Integer>();
//		wordCountPos.put("kao", 10);
//		wordCountPos.put("kao",11);
//		System.out.println(wordCountPos);
		
		String str = "1) i must say it feels divine to let my caique walk all over it with his sharp little claws is -> Positive";
		String[] splits = str.split("->");
		System.out.println(splits[0]);
		
	}

}
