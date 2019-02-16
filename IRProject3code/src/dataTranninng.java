import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;

import opennlp.tools.stemmer.PorterStemmer;

public abstract class dataTranninng{
	//score 1=positive 
	//score 0=negative
	private static List<String> dataPos= new ArrayList<String>();
	private static List<String> rawPos = new ArrayList<String>();
	private static List<String> dataNeg= new ArrayList<String>();
	private static List<String> rawNeg = new ArrayList<String>();
	private static HashSet<String> vocabulary = new HashSet<String>();
	public  double getMostPos() {
		return mostPos;
	}

	public  void setMostPos(double mostPos) {
		this.mostPos = mostPos;
	}

	public  double getMostNeg() {
		return this.mostNeg;
	}

	public  void setMostNeg(double mostNeg) {
		this.mostNeg = mostNeg;
	}

	private static double mostPos = Double.NEGATIVE_INFINITY;
	private static double mostNeg = Double.NEGATIVE_INFINITY;
	public static Map<String, Integer> getWordCountPosdt() {
		return wordCountPosdt;
	}

	public static Map<String, Integer> getWordCountNegdt() {
		return wordCountNegdt;
	}

	private static Map<String,Integer> wordCountPosdt = new HashMap<String,Integer>();
	private static Map<String,Integer> wordCountNegdt = new HashMap<String,Integer>();
	
	private static final String folder = "./DataTrainninng";
	public dataTranninng(List<String> corpus) {
		// TODO Auto-generated constructor stub
		int i=0;
		System.out.println("!!!!!!!!!!!Start Trainning!!!!!!!!!!!!");
		for(String string : corpus){
			System.out.println("Read documents : "+(i+1));
			i++;
			String type = null;
			String typeCheck = string;
			String[] seperate = string.split("_");
			//System.out.println("Chech seperate");
			if(seperate[1].equalsIgnoreCase("pos.txt")){
				type = "+";
			}
			else if(seperate[1].equalsIgnoreCase("neg.txt")){
				type ="-";
			}
			List<String> read = null;
			try {
				read = FileUtils.readLines(new File(folder+string), "UTF-8");

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Read Error");
				e.printStackTrace();
			}
			if(type == "+"){
				for(String str:read){
//					System.out.println(str);
					if(str!=null){
//						String[] seperate = str.split("\\t");
//						System.out.println(str);
						rawPos.add(str);
						List<String> token = tokenizes(str);
						for(String str1: token){
							this.dataPos.add(str1);
							if(!vocabulary.contains(str1)){ // make sure it don't have morethan 2
								vocabulary.add(str1);
							}
							if(!wordCountPosdt.containsKey(str1)){
								wordCountPosdt.put(str1, 1);
							}
							else{
								wordCountPosdt.replace(str1,wordCountPosdt.get(str1)+1);
							}
						}
					}
				}
			}
			else if(type == "-"){
				for(String str:read){
//					System.out.println(str);
					if(str!=null){
//						String[] seperate = str.split("\\t");
						rawNeg.add(str);
						List<String> token = tokenizes(str);
						for(String str1: token){
							this.dataNeg.add(str1);
							if(!vocabulary.contains(str1)){ // make sure it don't have morethan 2
								vocabulary.add(str1);
							}
							if(!wordCountNegdt.containsKey(str1)){
								wordCountNegdt.put(str1, 1);
							}
							else{
								wordCountNegdt.replace(str1,wordCountNegdt.get(str1)+1);
							}
						}
					}
				}
			}
			else{ // normal
				System.out.println("Error type");
			}
			
		}
		System.out.println("!!!!!!Finish Read File Thank you for knowledge!!!!!!!!");
	}
	
	public static List<String> getDataPos() {
		return dataPos;
	}

	public static List<String> getDataNeg() {
		return dataNeg;
	}
	
	public static List<String> getRawPos() {
		return rawPos;
	}
	
	public static List<String> getRawNeg() {
		return rawNeg;
	}

	public static void setRawNeg(List<String> rawNeg) {
		dataTranninng.rawNeg = rawNeg;
	}
	
	public static HashSet<String> getVocabulary() {
		return vocabulary;
	}
	public static void addVocabulary(String str){
		
		vocabulary.add(str);
		
	}
	public static PorterStemmer porterStemmer = new PorterStemmer();
	public static final Set<String> stopWords = Stream.of("a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours","ourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves").collect(Collectors.toSet());

	public static List<String> tokenizes(String rawText)
	{
		//lower casing
		String text = rawText.toLowerCase();
		
		//remove noise
		text = text.replaceAll("[^a-zA-Z0-9]", " ");
		
		//tokenizing
		String[] tokenArray = text.split("\\s+");
		
		//stemming, cleaning individual characters, and removing stop words
		List<String> tokens = new Vector<String>();
		for(String t: tokenArray)
		{
			if(t.length() <= 1) continue;
			if(stopWords.contains(t)) continue;
			 
			t = porterStemmer.stem(t);
			tokens.add(t);
		}
//		for(String s : tokens){
//			System.out.println(s);
//		}
		//return
		return tokens;
	}

	abstract public String classifier(String sentense,boolean type);

	abstract public void improvement(List<String> newTrain);



}
