package com.example.kreuzwortraetzel;

import java.util.ArrayList;
import java.util.Arrays;

public class Algorithmus {

    static int width = 30;
    static int height = 30;
    static char[][] raster = new char[width][height];

    public static void main(String[] args) {

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                raster[x][y] = ' ';
            }
        }

        ArrayList<String> worte = new ArrayList<>(Arrays.asList("Sherlock", "Watson", "Gustav", "Gandalf", "Wanderweg", "Gnu", "Gangnamstyle", "Erhard", "Frank", "Theodor",
                "Ramses", "Eberhard", "Equador", "Walter", "Syleman","Dachschindel", "Troll"// "Watson", "Gnu", "Equador", "Dachschindel"
        ));
        setAllWords(worte);

        printField();
    }

    public static void setAllWords(ArrayList<String> wordList) {

        KreuzwortData dataPrevious = new KreuzwortData("", -1, -1);
        ArrayList<KreuzwortData> eingetrageneList = new ArrayList<>();
        ArrayList<KreuzwortData> nichtEingetragenList = new ArrayList<>();

        for(String sEinzutragen : wordList) {

            boolean akktuellerStringWurdeEingetragen = false;

            if(sEinzutragen.length() <= width || sEinzutragen.length() <= height) {

                KreuzwortData dataEinzutragen = new KreuzwortData(sEinzutragen, -1, -1);

                if(eingetrageneList.isEmpty() || dataPrevious.getWord().equals("")) {

                    dataEinzutragen.setXCord((width/2) - (sEinzutragen.length()/2));
                    dataEinzutragen.setYCord(height/2);
                    dataEinzutragen.setHorizontalOrVertical('h');
                    akktuellerStringWurdeEingetragen = setWordHorizontaly(dataEinzutragen);

                } else {

                    dataEinzutragen = getCommonLetter(dataEinzutragen, dataPrevious);


                    if(dataEinzutragen.getHorizontalOrVertical() == 'v') {
                        akktuellerStringWurdeEingetragen = setWordVerticaly(dataEinzutragen);
                    } else if(dataEinzutragen.getHorizontalOrVertical() == 'h') {
                        akktuellerStringWurdeEingetragen = setWordHorizontaly(dataEinzutragen);
                    }



                }

                if(akktuellerStringWurdeEingetragen) {
                    eingetrageneList.add(dataEinzutragen);
                    dataPrevious = dataEinzutragen;
                } else {
                    nichtEingetragenList.add(dataEinzutragen);
                }

            } else {
                //KEIN PLATZ
                break;
            }
        }

        ArrayList<KreuzwortData> ausEinzutragenZuEntfernenList = new ArrayList<>();

        for(KreuzwortData dataEinzutragenNeuerVersuch: nichtEingetragenList) {
            boolean erfolgreichEingetragen = false;
            for(KreuzwortData dataEingetragen : eingetrageneList){
                KreuzwortData dataTryToSet = getCommonLetter(dataEinzutragenNeuerVersuch, dataEingetragen);

                if(dataTryToSet != null) {
                    if(dataTryToSet.getHorizontalOrVertical() =='h') {
                        erfolgreichEingetragen = setWordHorizontaly(dataTryToSet);
                    } else if (dataTryToSet.getHorizontalOrVertical() == 'v') {
                        erfolgreichEingetragen = setWordVerticaly(dataTryToSet);
                    }
                    if(erfolgreichEingetragen) {
                        eingetrageneList.add(dataTryToSet);
                        ausEinzutragenZuEntfernenList.add(dataEinzutragenNeuerVersuch);
                        break;
                    }
                }
            }
        }

        for (KreuzwortData entfernen : ausEinzutragenZuEntfernenList) {
            nichtEingetragenList.remove(entfernen);
        }


    }

    /**
     *
     * @param dataWanted
     * @param dataGiven
     * @return if Match was found, returns updated dataWanted with correct coordinates, otherwise returns null
     */

    private static KreuzwortData getCommonLetter(KreuzwortData dataWanted, KreuzwortData dataGiven) {
        KreuzwortData kreuzwortData = null;
        int iGiven = 0;
        int iWanted = 0;
        boolean matchFound = false;

        for(char cGiven : dataGiven.getWord().toCharArray()){

            for(char cWanted : dataWanted.getWord().toCharArray()) {

                if(cWanted == cGiven) {
                    matchFound = true;
                    break;
                }
                iWanted++;
            }
            if(matchFound) {break;}
            iWanted = 0;
            iGiven++;
        }
        if(matchFound != false) {
            int xCord = -1;
            int yCord = - 1;
            char horizontalOrVertical = ' ';

            if(dataGiven.getHorizontalOrVertical() == 'h') {
                xCord = dataGiven.getXCord() + iGiven;
                yCord = dataGiven.getYCord() - iWanted;
                horizontalOrVertical = 'v';
            } else if(dataGiven.getHorizontalOrVertical() == 'v') {
                xCord = dataGiven.xCord - iWanted;
                yCord = dataGiven.yCord + iGiven;
                horizontalOrVertical = 'h';
            }



            kreuzwortData = new KreuzwortData(dataWanted.getWord(), xCord, yCord, horizontalOrVertical);
        }

        if(dataWanted.getWord().equalsIgnoreCase("SHERLOCK") || dataWanted.getWord().equalsIgnoreCase("FRANK")) {
            System.out.println(dataWanted.getWord() + " " + iWanted + " " + dataGiven.getWord() + " " + iGiven + " " + dataWanted.getHorizontalOrVertical() + " " + dataGiven.getHorizontalOrVertical());
        }

        return kreuzwortData;
    }


    public static boolean setWordHorizontaly(KreuzwortData kreuzwortData) {
        boolean result = checkWordHorizontaly(kreuzwortData);
        try{
            if(result) {
                int offset = 0;
                for(char c : kreuzwortData.getWord().toCharArray()) {
                    raster[kreuzwortData.getXCord() + offset][kreuzwortData.getYCord()] = c;
                    offset ++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            result = false;
        }

        return result;
    }

    public static boolean checkWordHorizontaly(KreuzwortData kreuzwortData) {
        boolean result = true;
        try{
            int offset = 0;
            for(char c : kreuzwortData.getWord().toCharArray()) {
                if(raster[kreuzwortData.getXCord() + offset][kreuzwortData.getYCord()] != ' ') {
                    if(raster[kreuzwortData.getXCord() + offset][kreuzwortData.getYCord()] != c) {
                        result = false;
                    }
                }
                offset ++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            result = false;
        }

        return result;
    }

    public static boolean setWordVerticaly(KreuzwortData kreuzwortData) {
        boolean result = checkWordVerticaly(kreuzwortData);
        try{
            if(result) {
                int offset = 0;
                for(char c : kreuzwortData.getWord().toCharArray()) {
                    raster[kreuzwortData.getXCord()][kreuzwortData.getYCord() + offset] = c;
                    offset ++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            result = false;
        }

        return result;
    }

    public static boolean checkWordVerticaly(KreuzwortData kreuzwortData) {
        boolean result = true;
        try{
            int offset = 0;
            for(char c : kreuzwortData.getWord().toCharArray()) {
                if(raster[kreuzwortData.getXCord()][kreuzwortData.getYCord() + offset] != ' ') {
                    if(raster[kreuzwortData.getXCord()][kreuzwortData.getYCord() + offset] != c) {
                        result = false;
                    }
                }
                offset ++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            result = false;
        }

        return result;
    }

    public static void printField() {

        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++) {
                System.out.print("|" + raster[x][y]);
            }
            System.out.println();
        }
    }

}
