import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


//I implemented a random fuzzer which takes inputs of different forms such as .pdf, .xlsx , string and an empty string""


public class Fuzzer {
    public Fuzzer() {
    }

    //This test takes in csv files which are converted to pdf as the input
    //The contents are not equal in the file generated and expected csv converted to pdf
    @Test
    public void test1() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/sample_file_1.pdf"));
        Scanner csv3 = new Scanner(new File("./src/sample_file_3.pdf"));
        Read.get_differences(csv1, csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/compare_saved.pdf");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
    }
    //This test takes in csv files which are converted to pdf as the input
    //The contents are not equal in the file generated and an empty test4_result csv file
    // Even an empty file cannot be generated when we compare pdf based files
    // The program fails because pdf files are not seperated by comma which is used for seperation in the read class files
    // only csv files seperated by comma pdf files are not seperated by comma
    @Test
    public void test2() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/sample_file_1.pdf"));
        Scanner csv3 = new Scanner(new File("./src/sample_file_3.pdf"));
        Read.get_differences(csv1, csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test4_result");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
    }


    //This test takes in csv files which are converted to excel as the input
    //The contents are not equal in the file generated and expected csv file converted to xlsx
    @Test
    public void test3() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/sample_file_1.xlsx"));
        Scanner csv3 = new Scanner(new File("./src/sample_file_3.xlsx"));
        Read.get_differences(csv1, csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/compare_saved.xlsx");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
    }

    //This test takes in csv files which are converted to excel files as the input
    //The contents are equal in the file generated and an empty test4_result csv file
    // An empty test file is generated
    // The program runs sucessfully generating only an empty test file  because excel files are seperated by commas


    @Test
    public void test4() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/sample_file_1.xlsx"));
        Scanner csv3 = new Scanner(new File("./src/sample_file_3.xlsx"));
        Read.get_differences(csv1, csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test4_result.csv");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), true);
    }
    @Test

    //An string containing "Hello" is given
    //Even an empty csv file cannot be generated

    public void test5() throws IOException {
        try {
            Scanner csv1 = new Scanner(new File("Hello"));
            Scanner csv3 = new Scanner(new File("Hello"));
            Read.get_differences(csv1, csv3);
            File file1 = new File("./src/compare.csv");
            File file2 = new File("./src/test4_result.csv");
            Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
        } catch (FileNotFoundException var5) {
            System.out.println("An string is given");
        }
    }
    //An empty string is given
    //Even an empty csv file cannot be generated
    @Test
    public void test6() throws IOException {
        try {
            Scanner csv1 = new Scanner(new File(""));
            Scanner csv3 = new Scanner(new File(""));
            Read.get_differences(csv1, csv3);
            File file1 = new File("./src/compare.csv");
            File file2 = new File("./src/test4_result.csv");
            Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
        } catch ( FileNotFoundException var6) {
            System.out.println("A empty string is given");
        }
    }


}
