import java.util.*;
import java.io.*;


public class GroupJonah{
    public static void main(String[] args) {

        try{

            Scanner scan = new Scanner(System.in);

            System.out.println("Please enter 1st CSV file path / name (if file is in the same folder), with the extension: ");
            String file_1 = scan.nextLine();

            System.out.println("Please enter 2nd CSV file path / name (if file is in the same folder), with the extension: ");
            String file_2 = scan.nextLine();

            scan.close();

            if(file_1.length() > 24 || file_2.length() > 24) {
                System.out.println("Filename is too long! Ensure filenames are less than 20 characters.");
                throw new Exception();
            }

            else if(!file_1.substring(file_1.length()-4).equals(".csv") || !file_2.substring(file_2.length()-4).equals(".csv")){
                System.out.println("Only .csv file extensions allowed.");
                throw new Exception();
            }

            else{
                Scanner CSV1_Scanner = new Scanner(new File(file_1));
                Scanner CSV2_Scanner = new Scanner(new File(file_2));

                String row_for_file_1 = "";
                String row_for_file_2 = "";

                ArrayList<String> array_rows_for_file_1 = new ArrayList<String>();
                ArrayList<String> array_rows_for_file_2 = new ArrayList<String>();

                Boolean continueOn = true;

                int lengthAllowedForEachRow = 100;

                row_for_file_1 = CSV1_Scanner.nextLine();
                row_for_file_2 = CSV2_Scanner.nextLine();

                if(!row_for_file_1.equals("\"Customer ID#\",\"Account No.\",\"Currency\",\"Type\",\"Balance\"")
                        || !row_for_file_2.equals("\"Customer ID#\",\"Account No.\",\"Currency\",\"Type\",\"Balance\"")){

                    System.out.println("\nPlease ensure that the first row of each CSV files is \"Customer ID#\",\"Account No.\",\"Currency\",\"Type\",\"Balance\".");
                    throw new Exception();
                }

                else{
                    while (continueOn){

                        try{
                            if((!CSV1_Scanner.hasNext() && CSV2_Scanner.hasNext()) || (CSV1_Scanner.hasNext() && !CSV2_Scanner.hasNext()))
                            {
                                System.out.println("\nFiles do not contain same number of rows.");
                                throw new Exception();
                            }

                            row_for_file_1 = CSV1_Scanner.nextLine();
                            row_for_file_2 = CSV2_Scanner.nextLine();

                            if (row_for_file_1.length() > lengthAllowedForEachRow || row_for_file_2.length() > lengthAllowedForEachRow){
                                System.out.println("Length of data for a row is too long.");
                                throw new Exception();
                            }

                            Boolean openInvComma = false;
                            Boolean closeInvComma = false;
                            Boolean inBetweenComma = false;
                            Boolean nowAtLettersDigitsSpace = false;


                            int numberOfDataPerRow = 0;
                            for (int i = 0; i < row_for_file_1.length(); i++){

                                if (!Character.isLetterOrDigit(row_for_file_1.charAt(i))
                                        && !Character.isWhitespace(row_for_file_1.charAt(i))
                                        && row_for_file_1.charAt(i) != '"'
                                        && row_for_file_1.charAt(i) != ','
                                        && (String.valueOf(row_for_file_1.charAt(i)).matches(".")) )
                                    throw new IllegalArgumentException("Invalid character found in file. Only letters and digits allowed within inverted commas.");

                                else if (row_for_file_1.charAt(i) == '"'){
                                    if(i == 0 || inBetweenComma == true){
                                        openInvComma = true;
                                        inBetweenComma = false;
                                        closeInvComma = false;
                                    }
                                    else if(openInvComma == true){
                                        if(nowAtLettersDigitsSpace == false)
                                            throw new IllegalArgumentException("Missing data in one of the rows. Is there an empty \"\" ?");                            // Must have value within "", eg. "value1". "" with no value is invalid.
                                        else if (nowAtLettersDigitsSpace == true){
                                            openInvComma = false;
                                            closeInvComma = true;
                                            numberOfDataPerRow += 1;
                                        }
                                    }
                                    else if(inBetweenComma == false && i != 0 && openInvComma == false)                 // Not the open inverted comma at start of row, nor a close inverted comma. Nothing should be between commas and the open inverted comma.
                                        throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value\",\"value\",\"... Only a comma between each \"value\".");

                                }
                                else if (row_for_file_1.charAt(i) == ','){
                                    if(i == 0)
                                        throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value\",\"value\",\"... Only a comma between each \"value\".");                       // No commas at start of row.
                                    else if(i != 0 && openInvComma == false && closeInvComma == true){
                                        if(inBetweenComma == false){
                                            inBetweenComma = true;
                                        }
                                        else if (inBetweenComma == true)
                                            throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value\",\"value\",\"... Only a comma between each \"value\".");                // No more than 1 comma, eg. "value",,"value" is invalid.
                                    }
                                    else if(openInvComma == true)
                                        throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value1 value2\",\"value\",\"... No commas within each \"value\".");                  // No commas within " " is allowed. Eg. "value, value" is invalid.
                                }

                                else if (row_for_file_1.charAt(i) == '\n' && numberOfDataPerRow != 5) throw new IllegalArgumentException("Missing data in one of the rows. Is there an empty \"\" ?");

                                else{
                                    if(openInvComma == true && closeInvComma == false){
                                        nowAtLettersDigitsSpace = true;
                                    }
                                    else
                                        throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value\",\"value\",\"... Only a comma between each \"value\".");                  // Any letters and digits are only allowed within "", eg. "abc","abd". NOT "abc"a,"abd"
                                }
                            }

                            openInvComma = false;
                            closeInvComma = false;
                            inBetweenComma = false;
                            nowAtLettersDigitsSpace = false;
                            numberOfDataPerRow = 0;

                            for (int i = 0; i < row_for_file_2.length(); i++){

                                if (!Character.isLetterOrDigit(row_for_file_2.charAt(i))
                                        && !Character.isWhitespace(row_for_file_2.charAt(i))
                                        && row_for_file_2.charAt(i) != '"'
                                        && row_for_file_2.charAt(i) != ','
                                        && (String.valueOf(row_for_file_2.charAt(i)).matches(".")) )
                                    throw new IllegalArgumentException("Invalid character found in file. Only letters and digits allowed within inverted commas.");

                                else if (row_for_file_2.charAt(i) == '"'){
                                    if(i == 0 || inBetweenComma == true){
                                        openInvComma = true;
                                        inBetweenComma = false;
                                        closeInvComma = false;
                                    }
                                    else if(openInvComma == true){
                                        if(nowAtLettersDigitsSpace == false)
                                            throw new IllegalArgumentException("Missing data in one of the rows. Is there an empty \"\" ?");
                                        else if (nowAtLettersDigitsSpace == true){
                                            openInvComma = false;
                                            closeInvComma = true;
                                            numberOfDataPerRow += 1;
                                        }
                                    }
                                    else if(inBetweenComma == false && i != 0 && openInvComma == false)
                                        throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value\",\"value\",\"... Only a comma between each \"value\".");

                                }
                                else if (row_for_file_2.charAt(i) == ','){
                                    if(i == 0)
                                        throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value\",\"value\",\"... Only a comma between each \"value\"..");
                                    else if(i != 0 && openInvComma == false && closeInvComma == true){
                                        if(inBetweenComma == false){
                                            inBetweenComma = true;
                                        }
                                        else if (inBetweenComma == true)
                                            throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value\",\"value\",\"... Only a comma between each \"value\"...");
                                    }
                                    else if(openInvComma == true)
                                        throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value1 value2\",\"value\",\"... No commas within each \"value\".");
                                }

                                else if (row_for_file_2.charAt(i) == '\n' && numberOfDataPerRow != 5)
                                    throw new IllegalArgumentException("Missing data in one of the rows. Is there an empty \"\" ?");

                                else{
                                    if(openInvComma == true && closeInvComma == false){
                                        nowAtLettersDigitsSpace = true;
                                    }
                                    else
                                        throw new IllegalArgumentException("Invalid formatting found in one of the rows. Ensure format for second row onwards is eg. \"value\",\"value\",\"... Only a comma between each \"value\"....");
                                }
                            }

                            array_rows_for_file_1.add(row_for_file_1);
                            array_rows_for_file_2.add(row_for_file_2);

                            if((!CSV1_Scanner.hasNext() && !CSV2_Scanner.hasNext())){
                                continueOn = false;
                            }

                        }

                        catch(IllegalArgumentException e){
                            System.out.println();
                            System.out.println(e.getMessage());
                            throw new Exception();
                        }

                    }

                    CSV1_Scanner.close();
                    CSV2_Scanner.close();

                    // Writing Output to File: https://stackoverflow.com/questions/11496700/how-to-use-printwriter-and-file-classes-in-java

                    PrintWriter output_file = new PrintWriter ("output_file.csv");

                    for ( int i = 0; i < array_rows_for_file_1.size(); i++){
                        try{
                            boolean have_match = false;
                            if(!array_rows_for_file_1.get(i).equals(array_rows_for_file_2.get(i))){

                                int j = 0;
                                while (have_match == false && j < array_rows_for_file_2.size()){

                                    if(array_rows_for_file_1.get(i).equals(array_rows_for_file_2.get(j)))
                                    {
                                        have_match = true;
                                    }
                                    else{
                                        j++;
                                    }

                                }
                            }
                            else{
                                have_match = true;
                            }

                            if(!have_match) throw new Exception();
                        }

                        catch(Exception f){
                            output_file.println(array_rows_for_file_1.get(i));
                            output_file.println(array_rows_for_file_2.get(i));
                        }
                    }

                    output_file.close();
                }

            }

        }

        catch(FileNotFoundException e){
            //e.printStackTrace();
            System.out.println("\nFile not found. Please check if file exist, and if you included the extension.");
        }

        catch(Exception ex){
            //System.out.println(ex);
            System.out.println("Error! Execution Stopped.");
        }
    }
}
