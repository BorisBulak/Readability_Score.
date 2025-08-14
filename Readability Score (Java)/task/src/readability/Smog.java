package readability;

public class Smog {
    int polysyllables;
    int sentence;

    public Smog(int polysyllables,int sentence){
        this.polysyllables=polysyllables;
        this.sentence=sentence;
    }

    double calculateSmog(){
        return 1.043 * Math.sqrt(polysyllables * ((double)30 / sentence)) + 3.1291;
    }
}
