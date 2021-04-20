/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbdemo;

import java.util.*;
import java.sql.*;

public class demoDb {
    
    public static void main(String[] args)throws Exception{
       Scanner sc=new Scanner(System.in);
       String b="";
        do
        {
            
       System.out.println("Enter your choice\n1.Register\n2.Login");
       
       int a=sc.nextInt();
    
       switch(a)
       {
           case 1:
           {
               System.out.println("Registration");
               Register rr=new Register();
               rr.reg();
             
               break;
           }
           case 2:
           {
               System.out.println("Login");
               login ll=new login();
               ll.log();
               break;
           }
       }
       System.out.println("Do you want to continue y or n");
       b=sc.next();
        }while(b.equals("y"));
    }
      
}
class Connect
{  
   
    static Connection con()throws Exception
{  
     Class.forName("org.apache.derby.jdbc.ClientDriver");
      Connection cc = DriverManager.getConnection("jdbc:derby://localhost:1527/pubg","niit","niit");
     
    return cc;
    
}    
}

class Register
{  
    void reg()throws Exception{
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your details:");
        System.out.println("Enter your name:");
        String name = sc.next();
        System.out.println("Enter your ph.no:");
        String phno = sc.next();
         System.out.println("Enter your Address:");
        
         String addr = sc.next ();
        
        System.out.println("Enter your D.O.B");      
        String dob = sc.next();
        
     rand rn=new rand();
              
    String accno=rn.acco();
    String pin=rn.pin();
    
    long bal=500;
    
    PreparedStatement ps=Connect.con().prepareStatement("insert into customer values(?,?,?,?,?,?,?)");
    
    ps.setString(1, name);
    ps.setString(2, phno);
    ps.setString(3,dob);
    ps.setString(4,addr);
    ps.setString(5, accno);
    ps.setString(6, pin);
    ps.setLong(7, bal);
    
    ps.executeUpdate();
        System.out.println("your account number:"+accno);
        System.out.println("your account pin:"+pin);
        
        System.out.println("*Note:- dont share your pin number!");
    }
}

class rand
{
    String acco()
    {
        Random rn=new Random();
         String val1="";
for(int i=0;i<6;i++)
{
        int val=rn.nextInt(9);
        
       
     val1+=String.valueOf(val);
}
        return val1;
    }
    
       String pin()
    {
        Random rn=new Random();
         String val1="";
for(int i=0;i<4;i++)
{
        int val=rn.nextInt(9);
        
       
     val1+=String.valueOf(val);
}
        return val1;
    }
}


class login 
{
void log()throws Exception{
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your account number:");
        String accno = sc.next();
         System.out.println("Enter yout pin number:");
        
         String pin = sc.next ();
        PreparedStatement ps=Connect.con().prepareStatement("select * from customer where accno=? and pin=?");
    ps.setString(1, accno);
    ps.setString(2, pin);
   ResultSet rs= ps.executeQuery();
   
   if(rs.next())
   {
       System.out.println("welcome "+rs.getString(1));
       System.out.println("Enter your choise: \n1. Pin change\t2. Withdraw\n3. Deposit\t4. Balance enquiry\n5. Transaction");
       int z=sc.nextInt();
       switch(z) 
       {
           case 1:
           { 
                   PreparedStatement us=Connect.con().prepareStatement("update customer set pin=? where accno=?");
                   System.out.println("Enter your new Pin: ");
                   String pi=sc.next();
                   System.out.println("Enter your PIN again: ");
                   String ap=sc.next();
                   if(pi.equals(ap))
                   {
                       System.out.println("PIN change completed successfully");
                       us.setString(1, ap);
                       us.setString(2,accno);
                       us.executeUpdate();
                   }
                   else
                   {    
                       System.out.println("PIN mismatch");
                   }
                   break;
               }
       
           case 2:
           {
               System.out.println("withdraw");
               long bal=rs.getInt(7);
               System.out.println("enter  the amount to withdraw");
               long debi=sc.nextLong();
               if(bal>=debi)
               {
                   bal=bal-debi;
                    PreparedStatement us=Connect.con().prepareStatement("update customer set balance=? where accno=?");
                   us.setLong(1, bal);
                   us.setString(2, accno);
                   us.executeUpdate();
                   
                   System.out.println("your current balance:"+bal);
               }
               else
                       {
                           System.out.println("insufficent fund");
                       }
               break;
                 
           }
           case 3:
           {
               System.out.println("Deposit");
               long bal=rs.getInt(7);
               
               System.out.println("enter  the amount to deposit");
               long depo=sc.nextLong();
                    bal=bal+depo;
                    PreparedStatement us=Connect.con().prepareStatement("update customer set balance=? where accno=?");
                   us.setLong(1, bal);
                   us.setString(2, accno);
                   us.executeUpdate();
                   
                   System.out.println("your current balance:"+bal);
                   break;
               }
           
           case 4:
           {
               System.out.println("Balance Enquiry");
           long bal=rs.getInt(7);
           System.out.println("Your current Balance:" +bal);
           break;
           }
   
       }
   }
        
}
}
