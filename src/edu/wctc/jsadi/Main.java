package edu.wctc.jsadi;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        sortFile("places.csv");
        FileInput places = new FileInput("places.csv");
        FileInput stuff = new FileInput("stuff.csv");
        String countryLine, stuffLine;
        String[] countryLines, stuffLines;
        ArrayList<String> allCountries = new ArrayList<>();
        ArrayList<String[]> allStuff = new ArrayList<>();

        while ((countryLine = places.fileReadLine()) != null) {
            countryLines = countryLine.split(",");
            allCountries.add(countryLines[0]);

            while((stuffLine = stuff.fileReadLine()) != null) {
                stuffLines = stuffLine.split(",");
                allStuff.add(stuffLines);
            }
        }
        places.fileClose();
        stuff.fileClose();


        System.out.format("%-21s    %6s     %5s\n", "Country", "Cities", "Stuff");

        String[] countryArr = new String[allCountries.size()];

        for (int i = 0; i < countryArr.length; i++) {
            countryArr[i] = allCountries.get(i);
        }

        String[] uniqueCountries = Arrays.stream(countryArr).distinct().toArray(String[]::new);

        for (String s : uniqueCountries) {
            int cityCount = 0, stuffCount = 0, addCount = 0;
            String[] stuffPerCountry = new String[allStuff.size()];

            for (String c : countryArr) {
                if (s.equals(c)) {
                    cityCount++;
                }
            }

            for (int a = 0; a < allStuff.size(); a++) {
                if (allStuff.get(a)[0].equals(s)) {
                    stuffPerCountry[addCount] = allStuff.get(a)[1];
                    addCount++;
                }
            }

            String[] uniqueStuff = Arrays.stream(stuffPerCountry).distinct().toArray(String[]::new);
            for (String u : uniqueStuff) {
                if (u != null)
                    stuffCount++;
            }

            System.out.format("%-21s    %6s     %5s\n", s, cityCount, stuffCount);
        }
    }

    public static void sortFile(String fileName) {
        FileInput inFile = new FileInput(fileName);

        String line;
        String[] lines;
        ArrayList<String[]> toSort = new ArrayList<>();

        while ((line = inFile.fileReadLine()) != null) {
            lines = line.split(",");
            toSort.add(lines);

            int n = toSort.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (toSort.get(j)[0].compareTo(toSort.get(j + 1)[0]) > 0) {
                        String[] temp = toSort.get(j);
                        toSort.set(j, toSort.get(j + 1));
                        toSort.set(j + 1, temp);
                    }
                }
            }
        }

        inFile.fileClose();

        FileOutput sorted = new FileOutput(fileName);

        for (String[] s : toSort) {
            sorted.fileWrite(s[0] + "," + s[1] + "," + s[2] + "," + s[3]);
        }

        sorted.fileClose();
    }
}