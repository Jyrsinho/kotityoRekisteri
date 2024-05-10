using System;
using System.Text;
using System.Linq;
using System.Collections.Generic;
using System.IO;
using System.Net.Sockets;

/// @author Omanimi
/// @version 09.05.2024
/// <summary>
/// 
/// </summary>
public class SMTPasiakas
{
    /// <summary>
    /// 
    /// </summary>
    public static void Main()
    {
        Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

        try
        {
            s.Connect("localhost", 25000);
        }
        catch (Exception e)
        {
            Console.WriteLine("Virhe "+ e.Message);
            Console.ReadKey();
            return;
            throw;
        }

        NetworkStream ns = new NetworkStream(s);

        StreamReader sr = new StreamReader(ns);
        StreamWriter sw = new StreamWriter(ns);

        String email = "testi posti";

        Boolean on = true;
        String viesti = "";
        while (on)
        {
            viesti = sr.ReadLine();
            String [] status = viesti.Split(" ");

            switch (status [0])
            {
                case "220":
                    sw.WriteLine("HELO jyu.fi");
                    break;
                case "250":
                    switch (status [1])
                    {
                        case "2.0.0": 
                            sw.WriteLine("QUIT");
                            break;
                    } //switch
                default:
                    Console.WriteLine("Virhe...");
                    sw.WriteLine("QUIT");
                    break;
            } // switch
            sw.Flush();
        } //while
        
        

        Console.ReadKey();
        
        sw.Close();
        sr.Close();
        ns.Close();
        s.Close();
    }
}