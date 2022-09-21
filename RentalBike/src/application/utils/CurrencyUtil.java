package application.utils;

public class CurrencyUtil {
	public static String currencyFormat(String n) {
		n = n.replace("-", "");
        String ans = "";
        int count = 0;
        for (int i = n.length() - 1; i >= 0; i--) {
            count++;
            ans = n.charAt(i) + ans;
            if (count == 3 && i != 0) {
                ans = '.' + ans;
                count = 0;
            }
        }
        ans += " VND";
        return ans;
    }
}
