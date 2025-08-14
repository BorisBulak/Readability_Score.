package readability;

public class FleschKincaid {
    int words;
    int sentences;
    int syllables;

    public FleschKincaid(int words,int sentences,int syllables){
        this.words=words;
        this.sentences=sentences;
        this.syllables=syllables;
    }

     double calculateScore(){
         return 0.39 * ((double) words / sentences) + 11.8 * ((double) syllables / words) - 15.59;
     }
}
