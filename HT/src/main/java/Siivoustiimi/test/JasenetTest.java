package Siivoustiimi.test;
import Siivoustiimi.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.sql.SQLException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class JasenetTest {

    Siivoustiimi siivoustiimi1 = new Siivoustiimi();
    private Jasenet jasenet;
    private String tiedNimi;
    private File ftied;
    Jasen timo1;
    Jasen timo2;
    Jasen timo3;
    Jasen timo4;
    Jasen timo5;

    @BeforeEach
    public void alusta() throws SailoException, SQLException {
        siivoustiimi1 = new Siivoustiimi();
        tiedNimi = "testi";
        ftied = new File(tiedNimi + ".db");
        ftied.delete();
        jasenet = new Jasenet(tiedNimi);
        siivoustiimi1.lueTiedostosta(tiedNimi);

        timo1 = new Jasen();
        timo2 = new Jasen();
        timo3 = new Jasen();
        timo4 = new Jasen();
        timo5 = new Jasen();
        timo1.taytaJasen();
        timo2.taytaJasen();
        timo3.taytaJasen();
        timo4.taytaJasen();
        timo5.taytaJasen();
    }


    @AfterEach
    public void siivoa() {
        ftied.delete();
    }


    @Test
    public void shouldAddMembersToTable() throws SailoException {

        Collection<Jasen> loytyneet = jasenet.etsi("", 1);
        assertEquals(0, loytyneet.size());

        jasenet.lisaa(timo1);
        jasenet.lisaa(timo2);

        loytyneet = jasenet.etsi("", 1);
        assertEquals(2, loytyneet.size());

        jasenet.lisaa(timo3);
        jasenet.lisaa(timo4);
        jasenet.lisaa(timo5);
        loytyneet = jasenet.etsi("", 1);

        assertEquals(5, loytyneet.size());
    }

    @Test
    public void testShouldRemoveMembersFromTable() throws SailoException {
        Collection<Jasen> loytyneet = jasenet.etsi("", 1);
        assertEquals(0, loytyneet.size());

        jasenet.lisaa(timo1);
        jasenet.lisaa(timo2);
        loytyneet = jasenet.etsi("", 1);
        assertEquals(2, loytyneet.size());

        jasenet.poistaJasen(timo1);
        loytyneet = jasenet.etsi("", 1);
        assertEquals(1, loytyneet.size());

    }

}