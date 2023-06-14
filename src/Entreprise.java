import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Entreprise {
    Connection con;
    String url;
    String user;
    String pwd;
    Entreprise()
    {
    	url="jdbc:mysql://localhost/entreprisedb";
    	user="root";
    	pwd="";
    	this.connexion();
    }
    public void connexion()
    {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		System.out.println("--driver chargé---");
    	}
    	catch(ClassNotFoundException e)
    	{
    		System.out.println("erreur "+e.getMessage());
    	}
    	try {
			this.con=DriverManager.getConnection(url,user,pwd);
			System.out.println("Connexion ok !");
		} catch (SQLException e) {
			System.out.println(" la connexion à la base de données n'a pas pu \r\n" + "être établie");
			e.printStackTrace();
		}
    }
    public void deconnexion()
    {
    	try {
			this.con.close();
			System.out.println("Deconnexion ok !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public void interrogation() {
    	try {
			Statement st=con.createStatement();
			String select="SELECT nom FROM emp";
			ResultSet rs=st.executeQuery(select);
			while(rs.next())
			{
				String resultat=rs.getString("nom");
				System.out.println("le nom de est  :"+resultat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public boolean createTableAffaire()
    {
    	boolean test=false;
    	try {
        	String CrTable="CREATE TABLE affaire (NOAFF numeric(3),NOM varchar(10),BUDGET numeric(8,2))";
        	Statement st=con.createStatement();
        	@SuppressWarnings("unused")
			int result=st.executeUpdate(CrTable);
        	System.out.println("table cree \n");
            test=true;
    	}
    	catch(SQLException e)
    	{
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    	return test;
    }
    public boolean dropTable(String nom) {
    	boolean test=false;
    	try 
    	{
    		String DrTable="DROP table "+nom;
    		Statement st=con.createStatement();
    		int rs=st.executeUpdate(DrTable);
    		System.out.println("table supprimer \n");
    		test=true;
    	}
    	catch(SQLException e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    	return test;
    	
    }
    public void insertionDansAffaire(int NOAFF,String NOM,int BUDGET) 
    {
    	try {
//    	String inserer="INSERT INTO affaire SET values("+NOAFF+","+NOM+","+BUDGET+")";
//    	Statement st=con.createStatement();
            String inserer = "INSERT INTO affaire VALUES (?, ?, ?)";
    		PreparedStatement pstmt=con.prepareStatement(inserer);
    		pstmt.setInt(1, NOAFF);
    		pstmt.setString(2, NOM);
    		pstmt.setInt(3, BUDGET);
    		int res=pstmt.executeUpdate();
//    	int res=st.executeUpdate(inserer);
    	System.out.println("donneés inserées \n");
    	}
    	catch(SQLException e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
    public void afficheAffaire()
    {
    	try {
        	String affiche="SELECT * FROM affaire";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(affiche);
			while(rs.next())
			{
				int budget=rs.getInt("BUDGET");
				String result=rs.getString("NOM");
				int num_aff=rs.getInt("NOAFF");
				System.out.println("le numéro d affaire " +num_aff+ " le nom d'affaire : "+result+" le budget : "+budget);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    }
    public void afficheAffaireBigData()
    {
    	String affiche="SELECT * FROM affaire";
    	try {
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(affiche);
			ResultSetMetaData rsmd=rs.getMetaData();
			int count_nbr_column=rsmd.getColumnCount();
			for(int i=1;i<=count_nbr_column;i++)
			{
				System.out.println(rsmd.getColumnLabel(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    
    
}
