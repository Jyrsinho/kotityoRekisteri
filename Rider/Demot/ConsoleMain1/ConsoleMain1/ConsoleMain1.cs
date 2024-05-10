using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Text.RegularExpressions;
using System.Collections.Generic;
using System.Linq;
public class httpAsiakasLuokka
{
    public static void Main()
    {
#pragma warning disable 108, SYSLIB0014
        {
            string url = "http://users.jyu.fi/~arjuvi/opetus/itkp104/testi2.html";
            Console.WriteLine("Hienoa, aliohjelma toimii oikein! Alla vielä esimerkkituloste:");
            Console.WriteLine("Esimerkki URL: {0} palauttaa:\r\n", url);
            Console.WriteLine("-----------------------------------------------------------------");
            string sivu = httpAsiakas(url);
            Console.Write(sivu);
            Console.WriteLine("-----------------------------------------------------------------");
            
        }
    }

    // BYCODEBEGIN
    /// <summary>
    /// Funktio palauttaa HTML sivun.
    /// </summary>
    /// <param name="url">URL tekstimuotoiselle HTML-sivulle</param>
    /// <returns>string HTML-sivu tekstinä</returns>
    public static string httpAsiakas(string url)
    {
        string htmlSivu = "";
        

        // Toteuta

        Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

        if (url.Substring(0, 7) != "http://")
        {
            url = url.Insert(0, "http://");
        }
        
        Uri uri = new Uri(url);
        
        string host = uri.Host;
        string resurssi = uri.PathAndQuery;
        int port = 80;
        s.Connect(host, port);
        
        
            
        string msg = "GET " + resurssi + " HTTP/1.1\r\nHost:" + host + "\r\nConnection:Close\r\n\r\n";
        byte[] snd = Encoding.UTF8.GetBytes(msg);
        s.Send(snd);

        byte[] rec = new byte[2048];
        int bytesRead = s.Receive(rec);

        string received = Encoding.UTF8.GetString(rec, 0, bytesRead);
    
        s.Close();
        
        int index = received.IndexOf("\r\n\r\n");
        
        if (index != -1)
        {
            string content = received.Substring(index + 4); //  4 poistaa myös "\r\n\r\n"
            htmlSivu += content;
            return htmlSivu;
        }
        
        return htmlSivu;
    }
    }
    // BYCODEEND
