import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.princeton.cs.algs4.UF;


public class WaterRetention {
	int[][] soil;
	boolean exit = false;
	
	public static void main(String args[]) {
		WaterRetention retention = new WaterRetention();
		
		//Her test 1
		retention.readFile(new File("test Cases/File-1.txt"));
		retention.canDrain();
		//Her test 2
		retention.readFile(new File("test Cases/File-2.txt"));
		retention.canDrain();
		//Her test 3
		retention.readFile(new File("test Cases/File-3.txt"));
		retention.canDrain();
		//Her test 4
		retention.readFile(new File("test Cases/File-4.txt"));
		retention.canDrain();
		//null case
		retention.readFile(new File("test Cases/File-5.txt"));
		retention.canDrain();
		//Wrong file
		retention.readFile(new File("test Cases/Files.txt"));
		//2*2 Matrix
		retention.readFile(new File("test Cases/File-6.txt"));
		retention.canDrain();
		
		//1*1 (0)
		retention.readFile(new File("test Cases/File-7.txt"));
		retention.canDrain();
		//1*1 (1)
		retention.readFile(new File("test Cases/File-8.txt"));
		retention.canDrain();
	}
	
	public WaterRetention() {
	}
	
	private void readFile(File file) {
		
		List<int[]> values = new ArrayList<int[]>();
		Scanner scan;
		
		try {
			scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				
				String[] row = line.split("");
				
				int[] rowNumbers = new int[row.length];
				
				for(int i = 0; i < row.length; i++) {
					rowNumbers[i] = Integer.parseInt(row[i]);
				}
				
				values.add(rowNumbers);
			}
			
			scan.close();
			
			int size = values.size();
			
			int[][] soilMatrix = new int[size][size];
			
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					soilMatrix[i][j] = values.get(i)[j];
					System.out.print(soilMatrix[i][j] + " ");
				}
				System.out.println();
			}
			
			soil = soilMatrix;
			
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file name." + '\n');
		}
	}
	
	private void canDrain() {
		int n = soil.length;
		
		if(n > 0) {
			UF soilComposition = new UF(n*n);
			
			//for every row
			for(int i = 0; i < n; i++) {
				///for every column
				for(int j = 0; j < n; j++) {
					//If the item above is the same as it
					if(i != 0 && soil[i-1][j] == soil[i][j] && soil[i][j] == 1) {
						soilComposition.union((n*(i-1)+j), (n*i+j));
					}
					//If the item behind is the same as it
					if(j != 0 && soil[i][j-1] == soil[i][j] && soil[i][j] == 1) {
						soilComposition.union((n*i+j-1), (n*i+j));
					}
					//If the item below is the same as it
					if(i != n-1 && soil[i+1][j] == soil[i][j] && soil[i][j] == 1) {
						soilComposition.union(n*(i+1)+j, n*i+j);
					}
					//If the item in front is the same as it
					if(j != n-1 && soil[i][j+1] == soil[i][j] && soil[i][j] == 1) {
						soilComposition.union(n*i+j+1, n*i+j);
					}
				}
			}
			
			boolean CanDrain = false;
			
			if(n > 1) {
				for(int i = 0; i < n; i++) {
					for(int j = 0; j < n; j++) {
						if(soilComposition.connected(i, ((n-1)*n)+j)) {
							CanDrain = true;
							break;
						}
					}
				}
			}
			else {
				if(soil[0][0] == 1) {
					CanDrain = true;
				}
			}
			
			if(CanDrain) {
				System.out.println("Allows water to drain" + '\n');
			}
			else {
				System.out.println("Doesn't allow water to drain" + '\n');
			}
		}
		else {
			System.out.println("Soil Composition Invalid" + '\n');
		}
	}
}
