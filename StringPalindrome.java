
class StringPalindrome{
    public static boolean isPalindrome (String str){
        // int left = 0;
        // int right = str.length()-1;
    //     while(left<right){
    //         if(str.charAt(left) != str.charAt(right)){
    //             return false;
    //         }
    //         left++;
    //         right--;
    //     }
    //     return true;
    // 

    for(int i=0; i<str.length(); i++){
        if(str.charAt(i) != str.charAt(str.length()-1-i)){
            return false;
        }
    }
    return true;
}
    public static void main(String args[]){
        String str = "malayalam";
        if(isPalindrome(str)){
            System.out.println("String is Palindrome");
        }
        else{
            System.out.println("String is not Palindrome");
        }    
    }
}


