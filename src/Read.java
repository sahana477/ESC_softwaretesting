import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Read {
    public static void main(String[] args) throws Exception {

        // Make a CSV parser which reads from 2 CSV files and compares the differences per line
        // The first file is the master file and the second file is the file to compare against the master file
        // The output is a CSV file with the differences per line
        // The output file is named "differences.csv"
        ArrayList<String> compare = new ArrayList<String>();
        String result = "";
        // Create Scanner
        Scanner csv1 = new Scanner(new File("./src/sample_file_1.csv"));
        Scanner csv3 = new Scanner(new File("./src/sample_file_3.csv"));
        csv1.useDelimiter(",");
        csv3.useDelimiter(",");
        while (csv1.hasNextLine()) {
            String first_line_csv1 = csv1.nextLine();
            String Second_line_csv3= csv3.nextLine();
            if (!first_line_csv1.equals(Second_line_csv3)) {
                compare.add(first_line_csv1);
                compare.add(first_line_csv1);
            }
        }
        csv1.close();
        csv3.close();

        // Open output file
        FileWriter fw = new FileWriter("./src/compare.csv");
        try{
            for (int j = 0; j < compare.size(); j++) {
                result = result + compare.get(j) + "\n";
            }
        } catch (Exception ex) {
            System.out.println("cannot write to compare.csv file");
        }
        fw.write(result);
        fw.close();
    }
}

