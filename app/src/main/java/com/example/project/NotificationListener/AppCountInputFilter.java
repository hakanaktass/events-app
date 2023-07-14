package com.example.project.NotificationListener;

import android.text.InputFilter;
import android.text.Spanned;

public class AppCountInputFilter implements InputFilter{
    int minValue;
    int maxValue;

    public AppCountInputFilter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        try{
            String inputString = dest.toString().substring(0, dStart) + dest.toString().substring(dEnd);
            inputString = inputString.substring(0, dStart) + source.toString() + inputString.substring(dStart);

            int userInput = Integer.parseInt(inputString);

            if (isInRange(minValue, maxValue, userInput)) {
                return null;
            }
        }
        catch(NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return "";
    }//public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd)

    private boolean isInRange(int minValue, int maxValue, int userInput) {
        if(userInput > minValue && userInput <= maxValue) {
            return true;
        }

        return false;
    }//private boolean isInRange(int minValue, int maxValue, int userInput)
}//public class AppCountInputFilter implements InputFilter
