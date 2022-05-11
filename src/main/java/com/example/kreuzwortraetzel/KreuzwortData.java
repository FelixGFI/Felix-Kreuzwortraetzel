package com.example.kreuzwortraetzel;

public class KreuzwortData {
    String word;
    int xCord;
    int yCord;
    char horizontalOrVertical;

    public KreuzwortData(String word, int xCord, int yCord) {
        this.word = word.toUpperCase();
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public KreuzwortData(String word, int xCord, int yCord, char horizontalOrVertical) {
        this.word = word;
        this.xCord = xCord;
        this.yCord = yCord;
        this.horizontalOrVertical = horizontalOrVertical;
    }

    public int getXCord() {
        return xCord;
    }

    public void setXCord(int xCord) {
        this.xCord = xCord;
    }

    public int getYCord() {
        return yCord;
    }

    public void setYCord(int yCord) {
        this.yCord = yCord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public char getHorizontalOrVertical() {
        return horizontalOrVertical;
    }

    public void setHorizontalOrVertical(char horizontalOrVertical) {
        this.horizontalOrVertical = horizontalOrVertical;
    }

    @Override
    public String toString() {
        return "KreuzwortData{" +
                "word='" + word + '\'' +
                ", xCord=" + xCord +
                ", yCord=" + yCord +
                ", horizontalOrVertical=" + horizontalOrVertical +
                '}';
    }
}
