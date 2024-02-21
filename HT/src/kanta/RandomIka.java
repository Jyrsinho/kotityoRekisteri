package kanta;

public class RandomIka {

    /**
     * Arvotaan satunnainen kokonaisluku välille [ala,yla]
     *
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala,yla]
     */
    public static int arvoIka(int ala, int yla) {
        double n = (yla - ala) * Math.random() + ala;
        return (int) Math.round(n);
    }

}