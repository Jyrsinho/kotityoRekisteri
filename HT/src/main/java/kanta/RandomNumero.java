package kanta;

public class RandomNumero {


    /**
     * Arvotaan satunnainen kokonaisluku välille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala,yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }


    /**
     * Arvotaan satunnainen matkapuhelinnumero, jossa on satunnainen suuntanumero ja 7 satunnaista numeroa.
     * @return satunnainen laillinen henkilötunnus
     * </pre>
     */

    public static String arvoNumero() {
        int arvottuSuuntanumero = rand(40, 50);

        StringBuffer arvottuNumero = new StringBuffer();
        arvottuNumero.append("0");
        arvottuNumero.append(arvottuSuuntanumero);

                for (int i= 0; i <8; i++) {
                   arvottuNumero.append(rand(0,9));
                }

        return arvottuNumero.toString();
    }

    public static void main (String[]args) {
        System.out.println(arvoNumero());
    }
}
