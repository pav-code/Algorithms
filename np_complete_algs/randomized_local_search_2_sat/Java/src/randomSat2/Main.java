package randomSat2;

public class Main {

	private static final boolean DEBUG = false;
	private static final int TEST_CASE = 6;
	private static final int INPUT_FILE = 6;
	
	/*
	 * 2sat1: TRUE
	 * 2sat2: FALSE?
	 * 2sat3: TRUE
	 * 2sat4: TRUE
	 * 2sat5: FALSE?
	 * 2sat6: FALSE?
	 */
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		System.out.println("SAT 2: Papadimitriou Randomized Algorithm");
		
		String inputFileName;
		
		if (DEBUG) {
			inputFileName = "./src/randomSat2/data/testInput" + TEST_CASE + ".txt";
		} else {
			inputFileName = "./src/randomSat2/data/data2Sat" + INPUT_FILE + ".txt";	
		}

		PapaRandomizedSat2 PRSat2 = new PapaRandomizedSat2( inputFileName );
		boolean isSatisfied = PRSat2.isSatisfiable();
		
		System.out.println("SAT 2: Input: " + inputFileName + ". Satisfiable: " + isSatisfied);
	}
	
}
