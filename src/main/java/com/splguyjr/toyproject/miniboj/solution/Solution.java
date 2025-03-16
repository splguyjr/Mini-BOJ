import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int n = 0;
        while(t-- > 0) {
           n = Math.max(n, sc.nextInt());
        }
        System.out.println(n);
    }
}

