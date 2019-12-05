import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

class SequenceGenerator {
	public static void main(String[] args) throws IOException {
		int seqLength=Integer.parseInt(args[0]);
		int probA=Integer.parseInt(args[1]);
		int probC=Integer.parseInt(args[2]);
		int probG=Integer.parseInt(args[3]);
		int probT=Integer.parseInt(args[4]);
		int numSequences=Integer.parseInt(args[5]);
		double mutationProb=Double.parseDouble(args[6]);

		generatesequence(seqLength, probA, probC, probG, probT,numSequences, mutationProb);
	}

	private static void generatesequence(int seqLength, int probA, int probC, int probG, int probT,int numSequences, double mutationProb) throws IOException {
		// TODO Auto-generated method stub
		double sum=probA + probC + probG + probT;
		double minA=0; double maxA=probA/sum;
		double minC=probA/sum; double maxC=probC/sum + probA/sum;
		double minG=maxC; double maxG=probG/sum + probG/sum;
		double minT=maxG; double maxT=1;
		
		//generate first sequence
		StringBuilder sequence = new StringBuilder();
		for(int i=0;i<seqLength; i++) {
			double random= Math.random();
			if(random> minA && random < maxA) {
				sequence.append('A');
			}
			if(random> minC && random < maxC) {
				sequence.append('C');
			}
			if(random> minG && random < maxG) {
				sequence.append('G');
			}
			if(random> minT && random < maxT) {
				sequence.append('T');
			}
		}
		String firstSequence = sequence.toString();
		FileWriter fw=new FileWriter("output1.txt");
		fw.write(">Sequence1");
		fw.write(System.getProperty( "line.separator" ));
		fw.write(firstSequence);
//		char[] seqchar = firstSequence.toString().toCharArray();
//		int k=0;
//		while(k<seqchar.length) {
//			if(k%80 == 0) {
//				fw.write(System.getProperty( "line.separator" ));
//			}
//			fw.write(seqchar[k]);
//			k++;
//		}
		
		//generate 2 to numSequence
		for(int i=0;i<numSequences-1; i++) {
			StringBuilder seq = new StringBuilder();
			for(int j=0;j<seqLength; j++) {
				double random= Math.random();
				if(random < mutationProb) {
					double decideReplaceDelete=Math.random();
					if(decideReplaceDelete<0.5) {
						if(firstSequence.charAt(j)=='A') {
							ArrayList<Character> list = new ArrayList<Character>();
							list.add('C'); list.add('G'); list.add('T'); Collections.shuffle(list);
							seq.append(list.get(0));
						}
						if(firstSequence.charAt(j)=='C') {
							ArrayList<Character> list = new ArrayList<Character>();
							list.add('A'); list.add('G'); list.add('T'); Collections.shuffle(list);
							seq.append(list.get(0));
						}
						if(firstSequence.charAt(j)=='G') {
							ArrayList<Character> list = new ArrayList<Character>();
							list.add('A'); list.add('C'); list.add('T'); Collections.shuffle(list);
							seq.append(list.get(0));
	
						}
						if(firstSequence.charAt(j)=='T') {
							ArrayList<Character> list = new ArrayList<Character>();
							list.add('A'); list.add('C'); list.add('G'); Collections.shuffle(list);
							seq.append(list.get(0));
						}
					}
				}
				else {
					seq.append(firstSequence.charAt(j));
				}
			}
			fw.write(System.getProperty( "line.separator" ));
			fw.write(">Sequence"+(i+2));
			fw.write(System.getProperty( "line.separator" ));
			fw.write(seq.toString());
//			seqchar = seq.toString().toCharArray();
//			k=0;
//			while(k<seqchar.length) {
//				if(k%80 == 0) {
//					fw.write(System.getProperty( "line.separator" ));
//				}
//				fw.write(seqchar[k]);
//				k++;
//			}
		}
		fw.close();
		System.out.println("Generated file output1.txt");
	}
}