package ldap;

import java.util.Scanner;

public class LDAPTester {
	
	public static void main(String [] args) {
		NoWebLDAP ldap = new NoWebLDAP();
		
		Scanner kbdIn = new Scanner(System.in);
		//System.out.print("Enter username: ");
		//String username = kbdIn.nextLine();
		//while (!username.equals("exit")) {
			System.out.println(ldap.search("chad.pilkey"));
			
			/*if (ldap.getAuthenticated())
				System.out.println(ldap.getTitle());
			else
				System.out.println("Username not found");*/
			
			//System.out.print("Enter username: ");
			//username = kbdIn.nextLine();
		//};
	}
}