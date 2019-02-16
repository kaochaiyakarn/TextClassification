import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class NaiveBayesClassify extends dataTranninng{
	
	private static List<String> rawPos;
	private static List<String> rawNeg;
	private static List<String> tokenPos;
	private static List<String> tokenNeg;
	
	public NaiveBayesClassify(List<String> corpus) {
		super(corpus);
		// TODO Auto-generated constructor stub
		//=============raw Data==========//
		rawPos = super.getRawPos();
		rawNeg = super.getRawPos();
		//==============================//
		//============Token data========//
		tokenPos = super.getDataPos();
		tokenNeg = super.getDataNeg();
		//=============================//
		wordCountPos = super.getWordCountPosdt();
		wordCountNeg = super.getWordCountNegdt();
		//========calpositive=========//
//		System.out.println("!!!");
		NBPositive();
		//============================//
		//========calNegative=========//
		NBNegative();
		//============================//

	}	
	private static Map<String,Integer> wordCountPos = new HashMap<String,Integer>();
	private static Map<String,Integer> wordCountNeg = new HashMap<String,Integer>();
	private static Map<String,Double> probabilityPos=new HashMap<String,Double>();
	private static Map<String,Double> probabilityNeg=new HashMap<String,Double>();
	private static double probabilityPositive =0;
	private static double probabilitynegative =0;
//========================learnning part==========================//
	public static void NBPositive(){
		System.out.println("NBPositive preparing");
		for(String str : tokenPos){
			probabilityPos.put(str, positiveProCal(str));
//			System.out.println("finnish");
		}
		for(String str1 : tokenNeg){
			if(!probabilityPos.containsKey(str1)){
				probabilityPos.put(str1, positiveProCal(str1));
			}
		}
		System.out.println("End NBPositive prepared");
	}
	public static void NBNegative(){
		System.out.println("NBNegative preparing");

		for(String str : tokenNeg){
			probabilityNeg.put(str, negativeProCal(str));
		}
		for(String str1 : tokenPos){
			if(!probabilityNeg.containsKey(str1)){
				probabilityNeg.put(str1, negativeProCal(str1));
			}
		}
		System.out.println("Finish NBNegative prepared");

	}

	public static double positiveProCal(String str){
		double pro=0;
		if(wordCountPos.containsKey(str)){
			double fraction = (1.0*getVocabulary().size())+(1.0*tokenPos.size())!=0 ? (1.0*getVocabulary().size())+(1.0*tokenPos.size()):0; 

			pro = 1.0*(wordCountPos.get(str)+1)/fraction;
		}
		else{
			wordCountPos.put(str, 0);	//don't have in data set
			double fraction = (1.0*getVocabulary().size())+(1.0*tokenPos.size())!=0 ? (1.0*getVocabulary().size())+(1.0*tokenPos.size()):0; 
			pro = 1.0*(wordCountPos.get(str)+1)/fraction;
		}
		return pro;
	}
	public static double negativeProCal(String str){
		double pro=0;
		if(wordCountNeg.containsKey(str)){
			double fraction = (1.0*getVocabulary().size())+(1.0*tokenNeg.size())!=0 ? (1.0*getVocabulary().size())+(1.0*tokenNeg.size()):0; 
			//===check error
			pro = 1.0*(wordCountNeg.get(str)+1)/fraction;
		}
		else{
			wordCountNeg.put(str,0);	//don't have in data set
			double fraction = (1.0*getVocabulary().size())+(1.0*tokenNeg.size())!=0 ? (1.0*getVocabulary().size())+(1.0*tokenNeg.size()):0; 
			pro = 1.0*(wordCountNeg.get(str)+1)/fraction;
		}
		return pro;
	}
//================================================================//

//=========================classify part=========================//


	public  String classifier(String sentense,boolean type){
		
		List<String> tokenSentennse = tokenizes(sentense);
		//cal positivae case
		double vPos = 0,vNeg=0,maxVP=0,maxVN=0;
		probabilityPositive = rawPos.size()/rawPos.size()+ rawNeg.size();
		probabilitynegative = rawNeg.size()/rawPos.size()+ rawNeg.size();
		vPos+=probabilityPositive;
		vNeg+=probabilitynegative;
		for(String str : tokenSentennse){
			double pStr =0;
			double nStr=0;
			// this part can implement word check for sentense which have new words
			
			// cal with out check new words
			if(probabilityPos.containsKey(str)){
				pStr = 1.0*probabilityPos.get(str);
			}
			else{
				//check word emotion future;
			}
			if(probabilityNeg.containsKey(str)){
				nStr = 1.0* probabilityNeg.get(str);
			}
			else{
				//check word emotion future;
			}
			vPos*=pStr;
			vNeg*=nStr;
		}
		//fuzzy
		maxVP = vPos/(1.0*tokenSentennse.size());
		maxVN = vNeg/(1.0*tokenSentennse.size());
		System.out.println("VPOS "+vPos);
		System.out.println("VNNEG "+vNeg);
		// fuzzy
		if(!type){
			String ans = vPos>vNeg? "+":"-";
			return ans;
		}else{
			if(Math.abs(vPos-vNeg) <= (((vPos+vNeg)*20)/200)){
				return "N";
			}
			String ans = vPos>vNeg? "+":"-";
			return ans;
		}
		
//		return null;
		
	}
	
	@Override
	public void improvement(List<String> newTrain) {
		// TODO Auto-generated method stub
		for(String str : newTrain){
			String[] parts = str.split("\\t");
			if(parts[0].equalsIgnoreCase("1")){
				//positive case
				rawPos.add(parts[1]);
				List<String> token = tokenizes(parts[1]);
				for(String str1 : token){
					tokenPos.add(str1);
					if(!getVocabulary().contains(str1)){
						addVocabulary(str1);
					}
					if(!wordCountPos.containsKey(str1)){
						wordCountPos.put(str1, 1);
					}
					else{
						wordCountPos.replace(str1, wordCountPos.get(str1)+1);
					}
				}
			}
			else if(parts[0].equalsIgnoreCase("0")){
				//negative case
				rawNeg.add(str);
				List<String> token2 = tokenizes(parts[1]);
				for(String str2 : token2){
					tokenNeg.add(str2);
					if(!getVocabulary().contains(str2)){
						addVocabulary(str2);
					}
					if(!wordCountNeg.containsKey(str2)){
						wordCountNeg.put(str2, 1);
					}
					else{
						wordCountNeg.replace(str2, wordCountNeg.get(str2)+1);
					}
				}
			}
		}
		//use to change prob
		NBPositive();
		//============================//
		//========calNegative=========//
		NBNegative();
		//============================//
	}

}
