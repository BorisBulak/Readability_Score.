package readability;

public class ColemanLiau {
    double avaregeCharPer100Words;
    double avaregeSentencePer100Words;

    public ColemanLiau(double avaregeCharPer100Words,double avaregeSentencePer100Words){
        this.avaregeCharPer100Words=avaregeCharPer100Words;
        this.avaregeSentencePer100Words=avaregeSentencePer100Words;
    }

    double calculateColemanLiau(){
        return 0.0588 * avaregeCharPer100Words - 0.296 * avaregeSentencePer100Words - 15.8;
    }
}
