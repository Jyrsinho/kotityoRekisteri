package Siivoustiimi.test;
import Siivoustiimi.*;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

/*
Pitää osata lisätä jäseniä tietokantaan
TODO Pitää osata poistaa jäseniä tietokannasta.
 */


public class JasenetTest {

    Siivoustiimi siivoustiimi1 = new Siivoustiimi();
    private Jasenet jasenet;
    private String tiedNimi;
    private File ftied;


    @Before
    public void alusta() throws SailoException, SQLException {
        siivoustiimi1 = new Siivoustiimi();
        tiedNimi = "testi";
        ftied = new File(tiedNimi + ".db");
        ftied.delete();
        jasenet = new Jasenet(tiedNimi);
        siivoustiimi1.lueTiedostosta(tiedNimi);
    }


    @After
    public void siivoa() {
        ftied.delete();
    }


    @Test
    public void shouldAddMembersToTable() throws SailoException {

        Collection<Jasen> loytyneet = jasenet.etsi("", 1);
        assertEquals(0, loytyneet.size());

        Jasen timo1 = new Jasen(), timo2 = new Jasen();
        timo1.taytaJasen();
        timo2.taytaJasen();
        jasenet.lisaa(timo1);
        jasenet.lisaa(timo2);

        loytyneet = jasenet.etsi("", 1);
        assertEquals(2, loytyneet.size());

        Jasen timo3 = new Jasen();
        timo3.taytaJasen();
        Jasen timo4 = new Jasen();
        timo4.taytaJasen();
        Jasen timo5 = new Jasen();
        timo5.taytaJasen();
        jasenet.lisaa(timo3);

        jasenet.lisaa(timo4);
        jasenet.lisaa(timo5);
        loytyneet = jasenet.etsi("", 1);

        assertEquals(5, loytyneet.size());
    }

}