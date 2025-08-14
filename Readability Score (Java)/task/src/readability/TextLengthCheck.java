package readability;

import java.util.Scanner;

public class TextLengthCheck {
    private String text;

    TextLengthCheck(String text) {
        this.text = text;
    }

    void textControl() {
        System.out.println("The text is:\n" + text + "\n");

        String[] sentences = text.split("(?<=[.!?])");
        int totalWords = 0;
        int sentenceCount = 0;
        int totalCharacters = text.replaceAll("\\s", "").length();
        int syllables=0;
        int polysyllables=0;
        double L=0;
        double S=0;

        for (String sentence : sentences) {

            sentence = sentence.trim();


            if (!sentence.isEmpty()) {

                String[] words = sentence.split("\\s+");
                totalWords += words.length;
                sentenceCount++;


                for (String word :words){
                    word=word.replaceAll("[^a-zA-Z]", "");
                    int wordSyllabes = countSyllabes(word);
                    syllables+=wordSyllabes;
                    if (wordSyllabes >= 3){
                        polysyllables++;
                    }
                }
            }
        }

        double charsPerWord= (double) totalCharacters / totalWords;
        double wordsPerSentence= (double) totalWords / sentenceCount;
        double score = 4.71 * charsPerWord + 0.5 * wordsPerSentence - 21.43;
        double scoreWith2Numbers=(double) Math.round(score * 100) / 100;

        int level=(int) Math.ceil(score);


        System.out.println("Words: " + totalWords);
        System.out.println("Sentences: " + sentenceCount);
        System.out.println("Characters: " + totalCharacters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);



        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        Scanner scanner=new Scanner(System.in);
        String whatScore=scanner.nextLine();


        switch (whatScore){
            case "ARI":
                String readingLevel=readingIndex(level);
                System.out.println("Automated Readability Index: " + scoreWith2Numbers +" " +  "("+readingLevel+").");
                break;
            case "FK":
                FleschKincaid fleschKincaid=new FleschKincaid(totalWords,sentenceCount,syllables);
                double fleschKincaindResult=(double) Math.round(fleschKincaid.calculateScore() * 100) /100;
                int levelFK=(int) Math.ceil(fleschKincaindResult);
                System.out.println("Flesch–Kincaid readability tests: " + fleschKincaindResult +" "+ "("+readingIndex(levelFK)+").");
                break;
            case "SMOG":
                Smog smog=new Smog(polysyllables,sentenceCount);
                double smogResult=(double) Math.round(smog.calculateSmog() * 100) /100;
                int levelSmog=(int) Math.ceil(smogResult);
                System.out.println("Simple Measure of Gobbledygook: " + smogResult + " "+ "("+readingIndex(levelSmog)+").");
                break;
            case "CL":
                L=countAvarageL(totalCharacters,totalWords);
                S=countAvaregeS(sentenceCount,totalWords);
                ColemanLiau colemanLiau=new ColemanLiau(L,S);
                double colemanLiauResult=(double) Math.round(colemanLiau.calculateColemanLiau() * 100) / 100;
                int levelCl=(int) Math.ceil(colemanLiauResult);
                System.out.println("Coleman–Liau index: "+ colemanLiauResult+ " "+ "("+readingIndex(levelCl)+").");
                break;
            case "all":
                String readingLevel2=readingIndex(level);
                System.out.println("Automated Readability Index: " + scoreWith2Numbers +" " +  "("+readingLevel2+").");


                FleschKincaid fleschKincaid2=new FleschKincaid(totalWords,sentenceCount,syllables);
                double fleschKincaindResult2=(double) Math.round(fleschKincaid2.calculateScore() * 100) /100;
                int levelFK2=(int) Math.ceil(fleschKincaindResult2);
                System.out.println("Flesch–Kincaid readability tests: " + fleschKincaindResult2 + " "+ "("+readingIndex(levelFK2)+").");


                Smog smog2=new Smog(polysyllables,sentenceCount);
                double smogResult2=(double) Math.round(smog2.calculateSmog() * 100) /100;
                int levelSmog2=(int) Math.ceil(smogResult2);
                System.out.println("Simple Measure of Gobbledygook: " + smogResult2 + " "+ "("+readingIndex(levelSmog2)+").");


                L=countAvarageL(totalCharacters,totalWords);
                S=countAvaregeS(sentenceCount,totalWords);
                ColemanLiau colemanLiau2=new ColemanLiau(L,S);
                double colemanLiauResult2=(double) Math.round(colemanLiau2.calculateColemanLiau() * 100) / 100;
                int levelCl2=(int) Math.ceil(colemanLiauResult2);
                System.out.println("Coleman–Liau index: "+ colemanLiauResult2+" "+ "("+readingIndex(levelCl2)+").");

                System.out.println();

                double age=avarageAge(score,fleschKincaindResult2,smogResult2,colemanLiauResult2);


                System.out.println("This text should be understood in average by " +age+"-year-olds.");

        }


    }

    private int countSyllabes(String word){

        word = word.toLowerCase();
        word = word.replaceAll("[^a-z]", "");

        if (word.endsWith("e") && word.length() > 1) {
            word = word.substring(0, word.length() - 1);
        }

        String vowels="aeiouy";
        int syllables=0;
        boolean prevCharWasVowel=false;

        word=word.toLowerCase();
        if (word.endsWith("e") && word.length() > 1 && !vowels.contains("" + word.charAt(word.length() - 2))){
            word=word.substring(0,word.length()-1);
        }

        for (int i=0;i<word.length();i++){
            char c=word.charAt(i);

            if (vowels.indexOf(c) >= 0) {
                if (!prevCharWasVowel) {
                    syllables++;
                    prevCharWasVowel = true;
                }
            } else {
                    prevCharWasVowel=false;
                }
            }
        return (syllables == 0) ? 1 : syllables;
        }
        private double countAvarageL(int totalCharacters, int totalWords){
        return  ((double)totalCharacters / totalWords) * 100;
        }

        private double countAvaregeS(int totalSentence, int totalWords){
        return  ((double)totalSentence / totalWords ) * 100;
        }

        private static String readingIndex(int score){
            String result= switch (score){
                case 1 -> "about 6-year-olds";
                case 2 -> "about 7-year-olds";
                case 3 -> "about 8-year-olds";
                case 4 -> "about 9-year-olds";
                case 5 -> "about 10-year-olds";
                case 6 -> "about 11-year-olds";
                case 7 -> "about 12-year-olds";
                case 8 -> "about 13-year-olds";
                case 9 -> "about 14-year-olds";
                case 10 -> "about 15-year-olds";
                case 11 -> "about 16-year-olds";
                case 12 -> "about 17-year-olds";
                case 13 -> "about 18-year-olds";
                default -> "about 19=year-olds";
            };
            return result;
        }

        private static double avarageAge(double ariScore,double fkscore,double smogScore,double colemanScore){
            double ariAge=ariScore + 6;
            double fleschAge=fkscore + 6;
            double smogAge=smogScore + 6;
            double colemanAge=colemanScore + 6;

            double avarageAge= (ariAge + fleschAge + smogAge + colemanAge) / 4.0;

            avarageAge=Math.round(avarageAge * 100 ) / 100.0;

            return avarageAge;
        }
    }
