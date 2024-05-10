using System;
using System.Text;
using System.Linq;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;

/// @author JyriHuhtala
/// @version 08.05.2024
/// <summary>
///  Kaikuasiakas, joka lähettää yhden merkkijonon palvelimelle. Saatuaan vastauksen, pitää asiakkaan erottaa vastauksesta palvelimen nimi ja kaiutettu teksti.
/// Lopuksi asiakkaan pitää tulostaa palvelimen nimi ja kaiutettu teksti, alla olevassa muodossa
/// Palvelin: [esimerkkipalvelimen nimi]
/// Teksti: [esimerkkiteksti]
/// </summary>


public class KaikuAsiakas
{
    public static void Main()
    {
        
        string host = "127.0.0.1"; // Vaihda palvelimen osoite tarvittaessa
        int port = 80;
        
        Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

        s.Connect(host, port);
            
        // Lähetetään viesti palvelimelle
        string viesti = "Terve, palvelin!";
        byte[] viestiData = Encoding.UTF8.GetBytes(viesti);
        s.Send(viestiData);
        
        // Vastaanotetaan palvelimen vastaus
        byte[] vastausData = new byte[1024];
        int bytesRead = s.Receive(vastausData);
        string vastaus = Encoding.UTF8.GetString(vastausData, 0, bytesRead);
    
        s.Close();
        
        string[] osat = vastaus.Split(';');

        if (osat.Length == 2)
        {
            string palvelimenNimi = osat[0];
            string vastausTeksti = osat[1];

            Console.WriteLine("Palvelin: "+palvelimenNimi);
            Console.WriteLine("Teksti: "+vastausTeksti);
        }
        else
        {
            Console.WriteLine("Merkkijonoa ei voitu pilkkoa kahteen osaan.");
        }
      
    }
    }