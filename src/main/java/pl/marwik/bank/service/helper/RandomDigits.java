package pl.marwik.bank.service.helper;

public class RandomDigits {
    private static char digits[] = {'0','1','2','3','4','5','6','7','8','9'};

    private static char randomDecimalDigit(){
        return digits[(int)Math.floor(Math.random() * 10)];
    }

    public static String randomDecimalString(int ndigits){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < ndigits; i++){
            result.append(randomDecimalDigit());
        }
        return result.toString();
    }
}
