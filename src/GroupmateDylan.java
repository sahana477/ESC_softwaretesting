import java.util.*;
import java.io.*;




public class GroupmateDylan {



    static boolean checkFileFormat(String name){
        File file = new File(name);
        String fileName = file.toString();
        int tempIndex = fileName.lastIndexOf('.');
        if (tempIndex > 0){
            String fileExtension = fileName.substring(tempIndex + 1);
            if (!fileExtension.equals("csv")){
                return false;
            }
        }
        return file.exists();
    }

    static List<String> compareHashMap(HashMap<String, String> hashMapFile1, HashMap<String, String> hashMapFile2){
        List<String> differences = new ArrayList<>();
        for (String key: hashMapFile1.keySet()){
            String combine1, combine2;
            if (hashMapFile2.containsKey(key)){
                if (!hashMapFile1.get(key).equals(hashMapFile2.get(key))){
                    combine1 = key + ", " + hashMapFile1.get(key);
                    combine2 = key + ", " + hashMapFile2.get(key);
                    differences.add(combine1);
                    differences.add(combine2);
                    System.out.println("Difference for: " + key);
                    System.out.println("CSV 1: " + hashMapFile1.get(key));
                    System.out.println("CSV 2: " + hashMapFile2.get(key));
                    System.out.println("========================================");
                }
            } else {
                combine1 = key + ", " + hashMapFile1.get(key);
                differences.add(combine1);
                List<String> key_list = new ArrayList<>(Arrays.asList(key.split(",")));
                // Get key from hashmapfile2 with minor difference (e.g if key is "A, B, C" and the key in the other hashmap is "A, B, D", get the key "A, B, D")
                for (String key_2: hashMapFile2.keySet()){
                    List<String> key_2_list = new ArrayList<>(Arrays.asList(key_2.split(",")));
                    // Check if there is similarity between the two keys: (e.g A is the same for both keys)
                    if (key_list.contains(key_2_list.get(0)) || key_list.contains(key_2_list.get(1))){
                        combine2 = key_2 + ", " + hashMapFile2.get(key_2);
                        differences.add(combine2);
                        System.out.println("CSV1 Key: " + key);
                        System.out.println("CSV2 Key: " + key_2);
                        System.out.println("CSV 1: " + hashMapFile1.get(key));
                        System.out.println("CSV 2: " + hashMapFile2.get(key_2));
                        System.out.println("========================================");
                        break;
                    }
                }
            }
        }
        return differences;
    }

    static HashMap<String, String> readCSV(Scanner fileData, List<Integer> selected_headers_index) {
        HashMap<String, String> hashMapFile = new HashMap<>();
        while (fileData.hasNextLine()){
            String data = fileData.nextLine();
            Integer index;
            String pivot, non_pivot;
            List<String> temp_data = new ArrayList<>();
            List<String> pivot_list = new ArrayList<>();
            // List<String> non_pivot = new ArrayList<>();
            temp_data = Arrays.asList(data.split(","));
            List<String> actual_data = new ArrayList<>(temp_data);

            // Get the pivot values then convert them to string format
            for (int i = 0; i < selected_headers_index.size(); i++) {
                index = selected_headers_index.get(i);
                pivot_list.add(actual_data.get(index));
            }
            pivot = String.join(", ", pivot_list);

            Iterator iter = actual_data.iterator();
            String str = "";
            while (iter.hasNext()){
                str = (String) iter.next();
                for (String s: pivot_list) {
                    if (str.equals(s)){
                        iter.remove();
                    }
                }
            }
            non_pivot = String.join(", ", actual_data);
            hashMapFile.put(pivot, non_pivot);
        }
        return hashMapFile;
    }

    public static void main(String[] args) throws Exception {

        List<String> output_differences = new ArrayList<String>();
        String output = "";

        try{
            System.out.println("Location of File 1:");
            Scanner file1_scanner = new Scanner(System.in);
            System.out.println("Location of File 2:");
            Scanner file2_scanner = new Scanner(System.in);
            String Input1 = file1_scanner.nextLine();
            String Input2 = file2_scanner.nextLine();
            checkFileFormat(Input1);
            checkFileFormat(Input2);
            String temp1, temp2;
            HashMap<String, String> hashMapFile1 = new HashMap<>();
            HashMap<String, String> hashMapFile2 = new HashMap<>();
            List<String> header_file_1 = new ArrayList<>();
            List<String> header_file_2 = new ArrayList<>();
            List<String> headers = new ArrayList<>();
            List<String> comparable_headers = new ArrayList<>();
            List<String> selected_headers = new ArrayList<>();
            List<Integer> selected_headers_index = new ArrayList<>();
            Scanner file1Data = new Scanner(new File(Input1));
            Scanner file2Data = new Scanner(new File(Input2));
            file1Data.useDelimiter(",");
            file2Data.useDelimiter(",");
            // Skip the headers from the first file
            temp1 = file1Data.nextLine();
            temp2 = file2Data.nextLine();
            // Split the headers into an arraylist
            header_file_1 = Arrays.asList(temp1.split(","));
            header_file_2 = Arrays.asList(temp2.split(","));
            System.out.println("CSV 1:" + header_file_1);
            System.out.println("CSV 2:" + header_file_2);

            // Select Headers to compare:
            for (int i = 0; i < header_file_1.size(); i++) {
                if (headers.contains(header_file_1.get(i))){
                    continue;
                } else {
                    headers.add(header_file_1.get(i));
                }
            }
            for (int i = 0; i < header_file_2.size(); i++) {
                if (headers.contains(header_file_2.get(i))){
                    continue;
                } else {
                    headers.add(header_file_2.get(i));
                }
            }

            // Get the headers that can be compared
            for(int i = 0; i < header_file_1.size(); i++){
                if(header_file_1.get(i).equals(header_file_2.get(i))){
                    comparable_headers.add(header_file_1.get(i));
                }
            }
            System.out.print("List of Headers to select: ");
            System.out.println(headers);
            System.out.print("List of Comparable Headers: ");
            System.out.println(comparable_headers);

            System.out.println("Type out which headers you want to use as pivot");
            Scanner select_header_scanner = new Scanner(System.in);
            selected_headers = Arrays.asList(select_header_scanner.nextLine().split(","));
            for (int i = 0; i < selected_headers.size(); i++) {
                selected_headers_index.add(header_file_1.indexOf(selected_headers.get(i)));
            }
            System.out.println(selected_headers);

            hashMapFile1 = readCSV(file1Data, selected_headers_index);
            hashMapFile2 = readCSV(file2Data, selected_headers_index);

            // Check for each key in the hashmap if it exists in the other hashmap, if it doesn't then it is a difference
            // If they exist, check the non-pivot data, if they are different then it is a difference
            output_differences = compareHashMap(hashMapFile1, hashMapFile2);

            file1_scanner.close();
            file2_scanner.close();
            file1Data.close();
            file2Data.close();
            select_header_scanner.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        // Open output file

        try{
            FileWriter fw = new FileWriter("./src/differences.csv");
            for (int i = 0; i < output_differences.size(); i++) {
                output += output_differences.get(i) + "\n";
            }
            fw.write(output);
            fw.close();
        } catch (Exception e) {
            System.out.println("Error writing to file!");
        }
    }

}

