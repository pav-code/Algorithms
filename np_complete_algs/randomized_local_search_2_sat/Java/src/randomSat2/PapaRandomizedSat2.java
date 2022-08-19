package randomSat2;

import java.util.Random;

/**
 * TODO: Check the boolean variables numbering: need to index literal1 as [1] and go from
 * there.
 * @author pav
 *
 */
public class PapaRandomizedSat2 extends Utilities {
	private static int START_AT_ONE = 1;
	
	private int numOfVariables;
	private int numOfClauses;
	private boolean variables[];
	private int [][] clauses;
	private boolean satisfiable;
	private Random random = new Random();
	
	public PapaRandomizedSat2( String inputFile ) {
		String inputFileName = inputFile;
		
		numOfVariables = importVariables( inputFileName );
		clauses = importClauses( inputFileName );
		numOfClauses = numOfVariables;
		
		satisfiable = algorithm();
	}
	
	private void initVariables() {
		variables = new boolean [ numOfVariables + START_AT_ONE ];
		
		for( int varIdx = START_AT_ONE; varIdx < variables.length; varIdx++ ) {
			variables[ varIdx ] = random.nextBoolean();
		} 
	}
	
	private boolean algorithm() {
		int outerLoopLimit = (int) Math.ceil( Math.log10( numOfVariables ) / Math.log10( 2 ));
		
		int optimizationLimit = numOfVariables / 5;
		
		System.out.println( "The OuterLoopLimit: " + outerLoopLimit );
		
		while( outerLoopLimit-- >= 0 ) {
			initVariables();
			
			int innerLoopLimit = optimizationLimit; //(int) Math.ceil( 2 * Math.pow( numOfVariables, 2 ));
			while( innerLoopLimit-- >= 0 ) {
				if( satisfied() ) {
					return true;
				}
				if( innerLoopLimit % 1000 == 0) {
					System.out.println( "inner: " + innerLoopLimit );
				}
			}
			System.out.println( "Done outerLoopLimit: " + outerLoopLimit );
		}
		return false;
	}
	
	private boolean satisfied() {
		boolean satisfied = true;
		
		int maxFix = 10;
		int fixCount = 0;
		
		for( int clauseIdx = 0; clauseIdx < numOfClauses; clauseIdx++ ) {
			boolean literalA = getLiteralFromClause( clauseIdx, LITERAL_A );
			boolean literalB = getLiteralFromClause( clauseIdx, LITERAL_B );
			
			if( literalA == false && literalB == false ) {
				localSearch( clauseIdx );
				satisfied = false;
				fixCount++;
			}
			if( fixCount > maxFix ) {
				return satisfied;
			}
		}
		
		return satisfied;
	}
	
	private void localSearch( int clauseIdx ) {
		
		boolean literalA = getLiteral( clauseIdx, LITERAL_A );
		boolean literalB = getLiteral( clauseIdx, LITERAL_B );
		
		if( random.nextBoolean() ) {
			setLiteral( clauseIdx, LITERAL_A, !literalA );
		} else {
			setLiteral( clauseIdx, LITERAL_B, !literalB );
		}
	}
	
	private boolean getLiteral( int clauseIdx, int literalPosition ) {
		
		int whichBoolean =  clauses[ clauseIdx ][ literalPosition ];
		boolean literal = variables[ Math.abs( whichBoolean ) ];
		return literal;
	}
	
	private boolean getLiteralFromClause( int clauseIdx, int literalPosition ) {
		
		int whichBoolean =  clauses[ clauseIdx ][ literalPosition ];
		boolean literal = variables[ Math.abs( whichBoolean ) ];
		if( whichBoolean < 0 ) {
			literal = !literal;
		}
		return literal;
	}
	
	private void setLiteral( int clauseIdx, int literalPosition, boolean literalValue ) {
		int whichBoolean =  clauses[ clauseIdx ][ literalPosition ];
		variables[ Math.abs( whichBoolean ) ] = literalValue;
	}
	
	public boolean isSatisfiable() {
		return satisfiable;
	}
	
}
