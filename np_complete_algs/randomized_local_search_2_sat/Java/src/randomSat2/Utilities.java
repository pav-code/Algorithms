package randomSat2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	private static final int TWO_SAT_CLAUSE = 2;
	protected static final int LITERAL_A = 0;
	protected static final int LITERAL_B = 1;

	protected int importVariables( String inputFileName ) {
		int numOfVariables = 0;
		try {
			File fileHandler = new File( inputFileName );
			Scanner myReader = new Scanner( fileHandler );
			String header = myReader.nextLine();
			
			numOfVariables = Integer.parseInt( header );
			
		    myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		return numOfVariables;
	}
	
	protected int[][] importClauses( String inputFileName ) {
		int[][] clauses = null;
		
		try {
			File fileHandler = new File( inputFileName );
			Scanner myReader = new Scanner( fileHandler );
		
			String header = myReader.nextLine();
			
			int numOfClauses = Integer.parseInt( header );
			
			clauses = new int [ numOfClauses ][ TWO_SAT_CLAUSE ];
			
			int clauseCounter = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				int literalA = 0, literalB = 0;
			    try {
			    	Matcher matches = Pattern.compile("[-0-9]+").matcher(data);
			    	matches.find();
			    	literalA = Integer.parseInt( matches.group(0) );
			    	matches.find();
			    	literalB = Integer.parseInt( matches.group(0) );
			    } catch( Exception e ) {
			    	System.out.println("Failed to parse input file. Format must be: " + 
			    			"123.456 789.012. " + e);
			    }
			    clauses[ clauseCounter ][ LITERAL_A ] = literalA;
			    clauses[ clauseCounter ][ LITERAL_B ] = literalB;
			    clauseCounter++;
		    }
		    myReader.close();
		    
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		return clauses;
		
		
	}
	
}
