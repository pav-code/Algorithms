package cacheScheduling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class cacheSchedulers {
	ArrayList<Jobs> tasks;
	int numJobs;
	int scoringFunction;
	
	public static void main( String[] args ) {
		System.out.println("Begin.");
		
		// Parameters
		boolean test = false;
		int scoringFunctionSelect = 2; // 1 or 2
		
		cacheSchedulers cs = new cacheSchedulers();
		cs.importJobs( test );
		cs.setScoringFunction( scoringFunctionSelect );
		cs.calculateSchedule();
		cs.sortSchedule();
		long cost = cs.processJobs();
		
		System.out.println("Schedule cost is: " + cost);
		System.out.println("End.");
	}
	
	public void importJobs( boolean test ) {

		this.tasks = new ArrayList<Jobs>();
		
		try {
			File myObj;
			if( test == true ) {
				myObj = new File("./src/cacheScheduling/test.txt");
			} else {
				myObj = new File("./src/cacheScheduling/jobs.txt");
			}
			Scanner myReader = new Scanner(myObj);
		
			numJobs = Integer.parseInt( myReader.nextLine() );
			
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] lineSplit = data.split("\s");
				
				int weight = Integer.parseInt( lineSplit[0] );
				int length = Integer.parseInt( lineSplit[1] );
				this.tasks.add( new Jobs( weight, length ) );
				
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}

	public void setScoringFunction(int function) {
		if( function == 1 ) {
			scoringFunction = 1;
		} else if( function == 2 ) {
			scoringFunction = 2;
		}
	}
	
	public void calculateSchedule() {
		for (int job = 0; job < numJobs; job++) {
			calcScore( job );
		}
	}

	public void calcScore(int job) {
		if( scoringFunction == 1 ) {
			tasks.get(job).calcScore_f1();
		} else if( scoringFunction == 2 ) {
			tasks.get(job).calcScore_f2();
		}
	}
	
	public void sortSchedule() {
		Collections.sort(tasks);
	}

	public long processJobs() {
		long cost = 0;
		
		int time = 0;
		for (int currentTaskIdx = 0; currentTaskIdx < numJobs; currentTaskIdx++) {
			Jobs currentTask = tasks.get(currentTaskIdx);
			time += currentTask.getLength();
			cost += currentTask.getWeight() * time;			
		}
		
		return cost;
	}
	
}

/*

public static class MyObject implements Comparable<MyObject> {

  private Date dateTime;

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date datetime) {
    this.dateTime = datetime;
  }

  @Override
  public int compareTo(MyObject o) {
    return getDateTime().compareTo(o.getDateTime());
  }
}

And then you sort it by calling:

Collections.sort(myList);


// ony the fly comparator:
  Collections.sort(myList, new Comparator<MyObject>() {
  public int compare(MyObject o1, MyObject o2) {
      return o1.getDateTime().compareTo(o2.getDateTime());
  }
});
 */