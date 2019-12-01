/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebayes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
// Apache Common IO
import org.apache.commons.io.FileUtils;
/**
 *
 * @author DELL
 */
public class TrainingData {
    
    // chuyen mot string rar lon thanh tap hop (set) cac tu nho hon khong lap
    public static Set<String> cvttBagOfWords(String str) {
        HashSet<String> bag = new HashSet<>();
        // tach cac tu phan cach boi cac dau ,.!*"'()
        StringTokenizer str_token = new StringTokenizer(str, " ,.!*\"\'()");
        while (str_token.hasMoreTokens()) {
                bag.add(str_token.nextToken());
        }
        return bag;
    }
    
    // chuyen mot string rat lon thanh mot Arraylist
    public static ArrayList<String> cvttArrayList(String str) {
        ArrayList<String> arrl_bag = new ArrayList<>();
        StringTokenizer str_token = new StringTokenizer(str, " ,.!*\"\'()");
        while (str_token.hasMoreTokens()) {
                arrl_bag.add(str_token.nextToken());
        }
        return arrl_bag;
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println("Bat dau qua trinh huan luyen...\n");
        
        // tien hanh doc file rt_polarity_pos.txt
        // chuyen thanh tui tu (bag of words)
        // va chuyen thanh ArrayList cac tu co trong file do
        File posFile = new File("data/positive/rt_polarity_pos.txt");
        // su dung FileUtils cua Apache Common IO
        // doc file thanh string
        String posFileData = FileUtils.readFileToString(posFile, "UTF-8");
        Set<String> posBagOfWords = cvttBagOfWords(posFileData);
        ArrayList<String> posArrayListBag = cvttArrayList(posFileData);
        
        // thuc hien tuong tu cho file rt_polarity_neg.txt
        File negFile = new File("data/negative/rt_polarity_neg.txt");
        String negFileData = FileUtils.readFileToString(negFile, "UTF-8");
        Set<String> negBagOfWords = cvttBagOfWords(negFileData);
        ArrayList<String> negArrayListBag = cvttArrayList(negFileData);
        
        // ghi cac Set vao file training_result.dat
        ObjectOutputStream outSet = new ObjectOutputStream(
				new FileOutputStream(
                                 new File("data/training_result/training_result.dat")));
        outSet.writeObject(posBagOfWords);
        outSet.writeObject(negBagOfWords);
        outSet.close();
        
        // ghi cac ArrayList vao file all_words.dat
        ObjectOutputStream outArrl = new ObjectOutputStream(
				new FileOutputStream(
                                 new File("data/training_result/all_words.dat")));
        outArrl.writeObject(posArrayListBag);
        outArrl.writeObject(negArrayListBag);
        outArrl.close();
        
        System.out.println("Hoan tat qua trinh huan luyen!");
    }
}
