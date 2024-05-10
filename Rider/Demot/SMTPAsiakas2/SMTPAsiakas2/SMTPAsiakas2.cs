using System;
using System.Text;
using System.Linq;
using System.Collections.Generic;

/// @author Omanimi
/// @version 09.05.2024
/// <summary>
/// 
/// </summary>
public class SMTPAsiakas2
{
    /// <summary>
    /// 
    /// </summary>
    public static void Main()
    {
   
    }
    
    public static string smtpAsiakas(string vastaanotettu)
    {
        string lahetettava = "";
        Console.Write(vastaanotettu);
        // Toteuta
        
        String [] status = vastaanotettu.Split(" ");

        switch (status [0])
        {
            case "220":
                lahetettava = "HELO jyu.fi \r\n";
                break;
            case "250":
                switch (status [1])
                {
                    case "2.0.0": 
                        lahetettava = "QUIT \r\n";
                        break;
                    default: 
                        lahetettava = "MAIL FROM: <jyri@jyu.fi> \r\n";
                        break;
                } //switch
                break;
            default:
                Console.WriteLine("Virhe...");
                lahetettava ="QUIT \r\n";
                break;
        } // switch
        
        Console.Write(lahetettava);
        return lahetettava;
    }
}