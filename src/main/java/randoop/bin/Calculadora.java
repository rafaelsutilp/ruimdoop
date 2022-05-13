package randoop.bin;

import java.time.LocalDate;
import java.time.Period;

public class Calculadora {
    public int somaInt(int num1, int num2){
        return num1 + num2;
    }

    public int subInt(int num1, int num2){
        return num1 - num2;
    }

    public int mulInt(int num1, int num2){
        return num1 * num2;
    }

    public int divInt(int num1, int num2) throws Exception {
        if (num2 == 0) {
            throw new Exception("Divisão por zero");
        }
        return num1/num2;
    }

    public boolean maior(int num1, int num2){
        return num1>num2;
    }

    public boolean menor(int num1, int num2){
        return num1<num2;
    }

    public double somaDouble(double num1, double num2){
        return num1 + num2;
    }

    public double subDouble(double num1, double num2){
        return num1 - num2;
    }

    public double mulDouble(double num1, double num2){
        return num1 * num2;
    }

    public double divDouble(double num1, double num2) throws Exception {
        if (num2 == 0) {
            throw new Exception("Divisão por zero");
        }
        return num1/num2;
    }

    public boolean and(boolean num1, boolean num2){
        return num1&num2;
    }

    public boolean or(boolean num1, boolean num2){
        return num1|num2;
    }

    public String toLowCase(String text){
        return text.toLowerCase();
    }

    public String toUpCase(String text){
        return text.toUpperCase();
    }
}
