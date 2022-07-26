
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ReadTest {
    public ReadTest() {
    }
    // When both files are empty, test1() checks current output
    @Test
    public void test1() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/test1_file1.csv"));
        Scanner csv3 = new Scanner(new File("./src/test1_file2.csv"));
        Read.get_differences(csv1, csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test1_result.csv");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), true);
        }
    // When either of the files are empty, test2() and test 3() checks current output
    @Test
    public void test2() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/test2,3_file1.csv"));
        Scanner csv3 = new Scanner(new File("./src/test2,3_file2.csv"));
        Read.get_differences(csv1, csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test2,3_result.csv");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), true);
    }


    @Test
    public void test3() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/test2,3_file2.csv"));
        Scanner csv3 = new Scanner(new File("./src/test2,3_file1.csv"));
        Read.get_differences(csv1, csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test2,3_result.csv");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), true);
    }
    // if both files are identical, test4() checks the output
    @Test
    public void test4() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/test4_file1.csv"));
        Scanner csv3 = new Scanner(new File("./src/test4_file2.csv"));
        Read.get_differences(csv1,csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test4_result.csv");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), true);
    }
    // System Testing

    @Test

    // Equipartition 1: The inputs are valid path names
    // middle value: The middle value is the shortened path of csv file.
    // when the middle value is given as  input ,test5() checks if correct output produced
    public void test5() throws IOException {
        Scanner csv1 = new Scanner(new File("./src/sample_file_1.csv"));
        Scanner csv3 = new Scanner(new File("./src/sample_file_3.csv"));
        Read.get_differences(csv1,csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test5,6,7,8_result.csv");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
    }

    @Test
    // Equipartition 1 : The inputs are valid path names
    // boundary value: maximum length & full path of the path of the csv file
    // when the boundary value is given as  input ,test5() checks if correct output produced
    public void test6() throws IOException {
        Scanner csv1 = new Scanner(new File("C:/Users/sahana/IdeaProjects/ESC_sofwaretesting/src/sample_file_1.csv"));
        Scanner csv3 = new Scanner(new File("C:/Users/sahana/IdeaProjects/ESC_sofwaretesting/src/sample_file_3.csv"));
        Read.get_differences(csv1, csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test5,6,7,8_result.csv");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
    }

    @Test
    // Equipartition  2: The inputs are invalid path names
    // middle value The middle value contains the wrong path .
    // when middle value is given as input test7 to check if correct output is returned
    public void test7() throws IOException {
        try {
            Scanner csv1 = new Scanner(new File("./.idea/sample_file_1.csv"));
            Scanner csv3 = new Scanner(new File("./.idea/sample_file_3.csv"));
            Read.get_differences(csv1, csv3);
            File file1 = new File("./src/compare.csv");
            File file2 = new File("./src/test5,6,7,8_result.csv");
            Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
        } catch (FileNotFoundException var5) {
            System.out.println("The samples files are given input path in wrong directory");
        }


    }

    @Test
    // Equipartition  2:  when input values are invalid path names
    // Boundary value: The boundary value is which is the name of csv file
    // when boundary value given test8() checks for correct output
    public void test8() throws IOException {
        try {
            Scanner csv1 = new Scanner(new File("sample_file_1.csv"));
            Scanner csv3 = new Scanner(new File("sample_file_3.csv"));
            Read.get_differences(csv1, csv3);
            File file1 = new File("./src/compare.csv");
            File file2 = new File("./src/test5,6,7,8_result.csv");
            Assert.assertEquals(FileUtils.contentEquals(file1, file2), false);
        } catch (FileNotFoundException var5) {
            System.out.println("only the names of the file are given with incomplete path");
        }

    }
    // testing the main function for exceptions
    @Test
    public void testingExceptions() {

        try {
            Read.main(new String[] {});
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }

    }




}



