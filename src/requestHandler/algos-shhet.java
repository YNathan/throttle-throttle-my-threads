import java.util.HashMap;

import java.util.Map;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        int[] nums1 =  putRandomsNums(15);
        int[] nums2 =  putRandomsNums(15);

        printArray(nums1);
        printArray(nums2);

        findTripleNumber(nums1);
        findTripleNumber(nums2);

        int[] distinct = findRelatedNumbersInTwoArraysAndFillNegativeForPlaceHolder(nums1, nums2);
        printArray(distinct);

        int amount = substructIntFromArray(distinct);
        System.out.println(amount);

    }

    static int substructIntFromArray(int[] array) {
        int returnedValue = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != -1) {
                returnedValue = (array[i] * (i == 0 ? 1 : getMultipleBy(returnedValue))) + returnedValue;
            } else {
                break;
            }
        }

        return returnedValue;
    }

    static int getMultipleBy(int number) {
        int multipleBy = 10;
        while(number / 10 > 0){
            number /= 10;
            multipleBy *= 10;    
        }
      
        return multipleBy;
    }

    static int[] findRelatedNumbersInTwoArraysAndFillNegativeForPlaceHolder(int[] arr1, int[] arr2) {
        int[] returnedArr = new int[10];

        HashMap<Integer, Integer> combinersMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < 10; i++) {
            combinersMap.put(i, 0);
        }

        // iterate over first array
        for (int i = 0; i < arr1.length; i++) {
            int currentAmount = combinersMap.get(arr1[i]);
            if (currentAmount == 0) {
                combinersMap.remove(arr1[i]);
                combinersMap.put(arr1[i], 1);
            }
        }

        // iterate over second array
        for (int i = 0; i < arr2.length; i++) {
            int currentAmount = combinersMap.get(arr2[i]);
            if (currentAmount == 1) {
                combinersMap.remove(arr2[i]);
                combinersMap.put(arr2[i], 2);
            }
        }

        // iterate over combiner array
        int returnedIndex = 0;
        for (Map.Entry<Integer, Integer> entry : combinersMap.entrySet()) {
            int k = entry.getKey();
            int v = entry.getValue();
            if (v == 2) {
                returnedArr[returnedIndex] = k;
                returnedIndex++;
            }
        }

        // we already have an index so we dont intiate it
        for (; returnedIndex < returnedArr.length; returnedIndex++) {
            returnedArr[returnedIndex] = -1;
        }
        return returnedArr;

    }

    static int[] fillNegativeValue(int length) {
        int[] returnedArr = new int[length];
        for (int i = 0; i < length; i++) {
            returnedArr[i] = -1;
        }
        return returnedArr;
    }

    static void findTripleNumber(int... array) {
        int lowestNumber = 10;
        int tripleNumber = Integer.MAX_VALUE;
        // itrerat over array
        for (int i = 0; i < array.length - 2; i++) {
            // if currentNum if smaller its mean that now he is the smaller
            if (array[i] <= lowestNumber) {
                lowestNumber = array[i];
                // calculating the triple
                int newTriple = calculateTriple(array[i], array[i + 1], array[i + 2]);
                // now recheck if the new triple is smaller
                // imagin you have 065 and after you have 023 we want the second
                if (newTriple < tripleNumber) {
                    tripleNumber = newTriple;
                }
            }
        }

        System.out.println("triple is: " + tripleNumber + "\n");
    }

    private static int calculateTriple(int i, int j, int k) {
        int triple = (i * 100) + (j * 10) + k;
        return triple;
    }

    static int[] putRandomsNums(int length) {
        int[] returnedArr = new int[length];
        for (int i = 0; i < length; i++) {
            returnedArr[i] = randBetween(0, 9);
        }
        return returnedArr;
    }

    static void printArray(int[] arr) {
        System.out.println("array values:");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("\n--------------\n");
    }

    static int randBetween(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
