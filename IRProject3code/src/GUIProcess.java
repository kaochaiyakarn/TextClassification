import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class GUIProcess {
	private static dataTranninng classify;
	GUIProcess(){
		setCorpus();
		classify = new NaiveBayesClassify(dataList);
	}
	
	public static String classifyPhase(String phase){
		
		if(classify.classifier(phase,false).equalsIgnoreCase("+")){
			return ""+ phase + " ->is Positive\n";
		}
		else if(classify.classifier(phase,false).equalsIgnoreCase("-")){
			return ""+ phase + " ->is Negative\n";
		}
		else{
			return "Natural";
		}
//		return "Error";
	}
	private static List<String> parseFiletoList(String filename){
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(new File(filename), "UTF-8");
		} catch (IOException e) {
			System.out.println("### Error reading file 2"+filename);
			e.printStackTrace();
		}
		return lines;
		
	}
	private static StringBuilder parseFileToString(String fileName){
		StringBuilder strBuilder = new StringBuilder();
//		System.out.println(fileName);
		List<String> lines = parseFiletoList(fileName);
		
		int i=1;
		for(String str : lines){
			if(classify.classifier(str,false).equalsIgnoreCase("+")){
				strBuilder.append(i+") "+str+" is -> Positive"+"\n");
			}
			else if(classify.classifier(str,false).equalsIgnoreCase("-")){
				strBuilder.append(i+") "+str+" is -> Negative"+"\n");
			}else strBuilder.append(i+") "+str+" is -> Natural"+"\n");
			i++;
		}
//		System.out.println("Max Positive = ");
		return strBuilder;
	}
	public static String classifyFile(String fileName){
		String directory = "./dataClassify/";
		
		return parseFileToString(directory+fileName).toString();
	}
	public static int improve =0;
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
	public static void addDataLearnning(String corpus){
		dataList.add(corpus);
	}
	private static DecimalFormat df5 = new DecimalFormat(".#####");
	public static StringBuilder Evaluate(String fileName){
		StringBuilder strCheck = new StringBuilder();
		List<String> lines = parseFiletoList("./Evaluate/"+fileName);
		StringBuilder strB = new StringBuilder();
		int PtruePositive = 0, PfalseNegative =0,PfalsePositive =0,PtrueNegative =0; //P means positive , // Psotive parts
//		int
		int special =0;
		for(String str:lines){
			String[] parts = str.split("\\t");
			//parts[0]==1  -> positive
			if(classify.classifier(parts[1],false).equalsIgnoreCase("+")){
				if(parts[0].equalsIgnoreCase("1")){
					PtruePositive++;
				}
				else if(parts[0].equalsIgnoreCase("0")){
					PfalseNegative++;
//					strCheck.append(parts[1]+"\n");
					special++;
				}
			}
			else if(classify.classifier(parts[1],false).equalsIgnoreCase("-")){
				if(parts[0].equalsIgnoreCase("1")){
					PfalsePositive++;
//					strCheck.append(parts[1]+"\n");
					special++;
				}
				else if(parts[0].equalsIgnoreCase("0")){
					PtrueNegative++;
				}
			}	
		}
		System.out.println("TruePositive : "+PtruePositive);
		System.out.println("True Negative : "+PtrueNegative);
		System.out.println("FalsePositive : "+PfalsePositive);
		System.out.println("False Negative : "+PfalseNegative);
//		System.out.println(lines.size());
		strB.append("~~~~~~~~~Positive part~~~~~~~~~~"+"\n");
		double precisionPositive =  ((double)PtruePositive/(double)(PtruePositive+PfalsePositive));
		strB.append("Precision Positive : "+precisionPositive+"\n");
		double recallPositive =  ((double)PtruePositive/(double)(PtruePositive+PfalseNegative));
		strB.append("Recall Positive : "+ recallPositive+"\n");
		double f1Positive = (2*precisionPositive*recallPositive)/(precisionPositive+recallPositive);
		strB.append("Positive F1 : "+(f1Positive)+"\n");
		double Pacc = (1.0*(PtruePositive+PtrueNegative))/(1.0*(PtruePositive+PfalsePositive+PfalseNegative+PtrueNegative));
		strB.append("Accurancy Positive : "+Pacc+"\n");
		
		// negative part//
		strB.append("~~~~~~~~~Negative Part~~~~~~~~~~"+"\n");
		int NTp = PtrueNegative, NFp = PfalseNegative , NFn = PfalsePositive , NTn = PtruePositive;
		double NegP = 1.0*NTp/(1.0*(NTp+NFp));
		strB.append("Precision Negative : "+NegP+"\n");
		double NegR = 1.0*NTp/(1.0*(NTp+NFn));
		strB.append("Recall Negative : "+NegR+"\n");
		double f1Neg = (1.0*((2*NegP*NegR))/(NegP+NegR));
		strB.append("F1 Negative : "+(f1Neg)+"\n");
		double Nacc = (1.0*(NTp+NTn))/(1.0*(NTp+NFp+NFn+NTn));
		strB.append("Negative accurancy : "+Nacc+"\n");
		if(improve>0){
			strB.append("Improve Program Success");
		}
		if(improve==0){
			strB.append("Start improve");
		}
		classify.improvement(lines); // add all new train try to add only mistake or add only true classify
		improve++;
//		System.out.println(strCheck.toString());
		System.out.println(special);
		//test fuzzy
		System.out.println("Fuzzy");
		System.out.println(classify.getMostPos()+"Most positive");
		System.out.println(classify.getMostNeg()+"Most Negative");
		
		return strB;
	}

	public static String removeSentense(String str , String type){
		
		StringBuilder strB = new StringBuilder();
		if(type == "+"){
			String[] splits = str.split("\\n");
			for(int i=0;i<splits.length;i++){
//				System.out.println(splits[i]);
				String[] pnSplits = splits[i].split(">");  //positive negative splits
				if(pnSplits[1].equalsIgnoreCase(" Negative")){
					strB.append(pnSplits[0]+"> Negative"+"\n");
				}
				
			}
			return strB.toString();
		}
		else if(type == "-"){
			String[] splits = str.split("\\n");
			for(int i=0;i<splits.length;i++){
//				System.out.println(splits[i]);
				String[] pnSplits = splits[i].split(">");  //positive negative splits
				if(pnSplits[1].equalsIgnoreCase(" Positive")){
					strB.append(pnSplits[0]+"> Positive"+"\n");
				}
				
			}
			return strB.toString();
		}
		else{
			// netural
		}
		
		return "";
	}
	public static String neturalClassify(String str){
		StringBuilder strBuilder = new StringBuilder();
		String[] part = str.split("\\n");
		for(int i=0;i<part.length;i++){
			String[] sentense = part[i].split("->");
//			System.out.println(sentense[0]);
			if(classify.classifier(sentense[0],true).equalsIgnoreCase("+")){
				strBuilder.append(sentense[0]+"-> Positive"+"\n");
			}
			else if(classify.classifier(sentense[0],true).equalsIgnoreCase("-")){
				strBuilder.append(sentense[0]+"-> Negative"+"\n");
			}else strBuilder.append(sentense[0]+"-> Natural"+"\n");
		}
		return strBuilder.toString();
	}
}
