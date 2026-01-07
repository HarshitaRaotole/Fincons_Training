import java.util.Scanner;
public class Factorial {
    public static void main(String args[]){
        int fact = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number ");
        int n = sc.nextInt();

        //using for loop

        // for(int i=1; i<=n; i++){
        //     fact = i*fact;
        // }
        // System.out.println("Factorial : "+fact);

        //using while loop

        int i = 1;
        
        while(i<=n){
            fact = i*fact;
            i++;
        }
        System.out.println("Factorial : "+ fact);

    }
}
