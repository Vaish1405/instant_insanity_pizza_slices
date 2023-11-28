
import java.util.*;
import java.lang.*;
import java.io.*;

/*
 * inputStr, represents the given string for the puzzle
 * We have to find the longest substring in inputStr that is a palindrome
 */
public class temp
{
	public static void  funcSubstring(String inputStr)
	{
		// Write your code here
		String res=""; String substring ="";
		// string that read same forward and backwards is Palindrome
		for (int i = 0; i < inputStr.length(); i++) {
			for (int j = i+1; j <= inputStr.length(); j++) {
				substring = inputStr.substring(i, j);
				if (isPalindrome(substring)) {
					// compare to the length of the previosly found palindrome to ensure we get the longest substring
					System.out.printf("Length of substring: %d\n", substring.length());
				}
			}
		}
		
	}
	public static boolean isPalindrome(String str) {
		int start = 0; int end = str.length() - 1;
		while (start < end) {
			if (str.charAt(start) != str.charAt(end)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		// input for inputStr
		String inputStr = in.nextLine();	
		funcSubstring(inputStr);
	}
}