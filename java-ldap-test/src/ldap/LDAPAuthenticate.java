package ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.NamingException;

import java.util.ArrayList;
import java.util.Hashtable;

public class LDAPAuthenticate {
	private String username;
	private boolean authenticated;
	private Hashtable<Object, Object> env;
	private DirContext ldapContext;
	private SearchControls searchCtrl; 
	private String o;
	private String title;
	private String ou;
	
	public LDAPAuthenticate() {
		env = new Hashtable<Object, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		
		// specify where the ldap server is running
		env.put(Context.PROVIDER_URL, "ldap://dssy.senecac.on.ca/");
		env.put(Context.SECURITY_AUTHENTICATION, "none");
		
		// Create the initial directory context
		try {
			ldapContext = new InitialDirContext(env);
		} catch (NamingException e) {
			System.out.println("LDAP server not found");
		}
		
		searchCtrl = new SearchControls();
		searchCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		username = "";
		authenticated = false;
	}
	
	public boolean search(String u) {
		username = u;
		u = "(&(uid="+u+"))"; // format the username
		
		try {
			NamingEnumeration<SearchResult> results = ldapContext.search("o=sene.ca", u, searchCtrl);
			
			if (!results.hasMore()) // search failed
				throw new Exception();
			
			SearchResult sr = results.next();
			
			Attributes at = sr.getAttributes();
			
			ou = ((sr.getName().split(","))[1].split("="))[1]; // grab the ou (either Student or Employee)
			
			if (ou.equals("Employee"))
				title = (String) at.get("title").get(0);
			else
				title = ou;
				
			
			/*NamingEnumeration<String> ids = at.getIDs(); // outputs all the possible attribute names
			while (ids.hasMore()) {
				System.out.println(ids.next());
			}*/
			
			authenticated = true;
			return true;
		} catch (NamingException e) {
			System.out.println("search for username on ldap failed.");
		} catch (Exception e) {
			
		}
		
		authenticated = false;
		
		return false;
	}
	
	public boolean getAuthenticated() {
		return authenticated;
	}
	
	public String getTitle() {
		return title;
	}
}
