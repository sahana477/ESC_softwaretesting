import java.util.HashMap;
import java.util.Scanner;

import java.io.File;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;




public class GroupmateDylanTest {
    public GroupmateDylanTest() {
    }

    // pass in empty hashmaps into function as the inputs
    //A an empty csv file is created which is same as empty csv file
    @Test
    public void test1() throws IOException {

        HashMap<String, String> Hash_csv1= new HashMap<>();
        HashMap<String, String> Hash_csv3= new HashMap<>();

        GroupmateDylan.compareHashMap(Hash_csv1, Hash_csv3);
        File file1 = new File("./src/compare.csv");
        File file2 = new File("./src/test1_result.csv");
        Assert.assertEquals(FileUtils.contentEquals(file1, file2), true);
    }
    // pass invalid path name "documents/differences.csv"
    @Test
    public void testCorrectFileFormat(){
        String file = "documents/differences.csv";
        assertFalse(GroupmateDylan.checkFileFormat(file));
    }

    // pass in excel files
    @Test
    public void testInvalidFileFormat(){
        String file = "./sample_file_3.xlsx";
        assertFalse(GroupmateDylan.checkFileFormat(file));
    }

    // pass in pdf files
    @Test
    public void testInvalidFileFormat2(){
        String file = "./sample_file_3.pdf";
        assertFalse(GroupmateDylan.checkFileFormat(file));
    }


}
