package com.qbit.Assignment;

import java.util.Scanner;
public class Solution {
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
       Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        scan.nextLine(); //go to next line
        int K = scan.nextInt();
        int L = scan.nextInt();
        int M = scan.nextInt();
        scan.nextLine(); // go to next line
        String line = scan.next();
        int firstOccur,secondOccur,thirdOccur;
        firstOccur = secondOccur = thirdOccur = 0;
        if(line.length() > K)
          firstOccur = findOccurence(line.substring(0,K),line);
        if(line.length() > L)
          secondOccur = findOccurence(line.substring(0,L),line);
        if(line.length() > M)
          thirdOccur = findOccurence(line.substring(0,M),line);
        
        if(firstOccur > secondOccur) {
            if(firstOccur > thirdOccur)
                System.out.println("Output = "+firstOccur);
            else
                System.out.println("Output = "+thirdOccur);
        }else{
            if(secondOccur > thirdOccur)
                System.out.println("Output = "+secondOccur);
            else
                System.out.println("Output = "+thirdOccur);
        }
        System.out.println("  "+firstOccur+"  "+secondOccur+ "   "+thirdOccur);   
    }
    
    public static int findOccurence(String str,String whole ){
    	System.out.println("  "+str+"  "+whole);
        int count = 0;
        int position = 0;
        int endPosition = str.length();
        while(endPosition <= whole.length()){
            if(whole.substring(position, endPosition).equals(str))
               ++count;
            System.out.println("Posi"+position+"ENd"+endPosition);
            ++endPosition;
            ++position;
            
       }
        System.out.println("Count"+count);
        return count;
    }
}
