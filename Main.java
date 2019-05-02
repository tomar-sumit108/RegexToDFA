package com.company;

import com.company.REGEX.Regex;

public class Main {

    public static void main(String[] args) {
          Regex regex=new Regex();
            regex.solve("a+b+");
            System.out.println(regex.check("ab"));
    }
}
