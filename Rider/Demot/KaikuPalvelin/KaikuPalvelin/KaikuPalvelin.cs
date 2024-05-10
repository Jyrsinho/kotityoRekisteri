using System;
using System.Text;
using System.Linq;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;

/// @author Omanimi
/// @version 09.05.2024
/// <summary>
/// 
/// </summary>
public class KaikuPalvelin
{
    /// <summary>
    /// 
    /// </summary>
    public static void Main()
    {
        Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        IPEndPoint iep = new IPEndPoint(IPAddress.Loopback,  8888);
        s.Bind(iep);
        s.Listen(3);
        
        Socket asiakas = s.Accept();
        byte[] rec = new byte[2048];
        asiakas.Receive(rec);

        string a = "Jyrin palvelin; " + Encoding.UTF8.GetString(rec);

        Console.Write(a);

        asiakas.Send(rec);
        asiakas.Close();
        Console.ReadKey();
        s.Close();
    }
}