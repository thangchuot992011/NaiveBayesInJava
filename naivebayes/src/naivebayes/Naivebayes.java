/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebayes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class Naivebayes {
    // tap hop tui tu thuoc loai positive
    static Set<String> posBagOfWords = new HashSet<>();
    // tap hop tui tu thuoc loai negative
    static Set<String> negBagOfWords = new HashSet<>();
    
    // ArrayList tat ca cac tu trong file positive
    static ArrayList<String> posArrayListBag = new ArrayList<>();
    // ArrayList tat ca cac tu trong file negative
    static ArrayList<String> negArrayListBag = new ArrayList<>();
    
    // ham dem so lan xuat hien cua str trong file positive
    public static int wordCountInPos(String str) {
        int count = 0;
        int pos_size = posArrayListBag.size();
        for (int i = 0; i < pos_size; i++) {
            if (str.equals(posArrayListBag.get(i))) count++;
        }
        return count;
    }
    // tuong tu cho file negative
    public static int wordCountInNeg(String str) {
        int count = 0;
        int neg_size = negArrayListBag.size();
        for (int i = 0; i < neg_size; i++) {
            if (str.equals(negArrayListBag.get(i))) count++;
        }
        return count;
    }
    
    // ham tinh xac suat P(x_i|class=positive)
    public static double calPPos(String str) {
        // bien dem so lan xuat hien cua str trong tap thuoc loai positive
        double count = 0;
        int posBagSize = posBagOfWords.size();
        int posArrlSize = posArrayListBag.size();
        for (int i = 0; i < posBagSize; i++) {
            if (posBagOfWords.contains(str)) {
                count = wordCountInPos(str);
            }
        }
        
        // Laplace Smoothing
        return (double)(count + 1) / (posArrlSize + posBagSize);
    }
    
    // thuc hien tuong tu
    public static double calPNeg(String str) {
        double count = 0;
        int negBagSize = negBagOfWords.size();
        int negArrlSize = negArrayListBag.size();
        for (int i = 0; i < negBagSize; i++) {
            if (negBagOfWords.contains(str)) {
                count = wordCountInPos(str);
            }
        }
        
        // Laplace Smoothing
        return (double)(count + 1) / (negArrlSize + negBagSize);
    }
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        System.out.println("Tai du lieu tu tap huan luyen...");
        // doc du lieu cac Bag Of Words tu file training_result.dat
        ObjectInputStream inpSet = new ObjectInputStream(
                                    new FileInputStream(
                                    new File("data/training_result/training_result.dat")));
        posBagOfWords = (HashSet<String>) inpSet.readObject();
        negBagOfWords = (HashSet<String>) inpSet.readObject();
        inpSet.close();
        
        // doc du lieu tu file all_word.
        ObjectInputStream inpArrl = new ObjectInputStream(
                                    new FileInputStream(
                                    new File("data/training_result/all_words.dat")));
        posArrayListBag = (ArrayList<String>) inpArrl.readObject();
        negArrayListBag = (ArrayList<String>) inpArrl.readObject();
        inpArrl.close();
        
        System.out.println("Ban hay nhap mot xau can kiem tra: ");
        Scanner scanner = new Scanner(System.in);
        String testedString = scanner.nextLine();
        System.out.println("Dang kiem tra...\n");
        
        // ta chi co 1 file positive va 1 file negative
        // nen xac suat ban dau deu la 50 %
        double pPos = 0.5, pNeg = 0.5;
        ArrayList<String> listTestedString = TrainingData.cvttArrayList(testedString);
        for (String testedStr : listTestedString) {
            pPos *= calPPos(testedStr);
            pNeg *= calPNeg(testedStr);
        }
        
        if (pNeg < pPos) {
            System.out.println("Cau van thuoc loai positive");
        }
        else System.out.println("Cau van thuoc loai negative");
    }
    
}
