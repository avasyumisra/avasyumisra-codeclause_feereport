package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import com.itextpdf.text.DocumentException;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, SQLException, DocumentException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.println("Login as a :");
			System.out.println(" 1: Admin ");
			System.out.println(" 2: Accountant ");
			System.out.println(" 3: Application Close ");
			int opt =0;
			try {
				opt = Integer.parseInt(br.readLine());

			}
			catch(Exception e) {
				System.out.println(" Not a valid number Input.\n");
				continue;
			}

			if(opt == 1) {
				System.out.println("\tAdmin\n");
				Admin adm = new Admin();
				boolean chk = adm.admin();
				if(chk == true) {
					System.out.println("\t\t....Sucessfully Admin Login....\n");
					AdminMenu am = new AdminMenu();
					am.adminSection();
				}
				else {
					System.out.println("wrong admin credentials");
					continue;
				}
			}

			else if(opt == 2) {
				System.out.println("\tAccountant\n");
				Accountant acc = new Accountant();
				boolean chk = acc.accountant();
				if(chk == true) {
					System.out.println("\t\t....Sucessfully Accountant Login....\n");
					AccountantMenu acm = new AccountantMenu();
					acm.accountantSection();
				}
				else {
					System.out.println("\n...Wrong accountant credentials...\n");
					continue;
				}
			}

			else if(opt == 3) {
				System.out.println(" Application terminated ...");
				break;
			}
			else {
				System.out.println(" Select from above given respective inputs.\n");
				continue;
			}
		}
	}

}
 92 changes: 92 additions & 0 deletions92  
src/dao/AccountantDetailsDao.java
@@ -0,0 +1,92 @@
package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import controller.AdminMenu;
import model.*;
import utility.ConnectionManager;

public class AccountantDetailsDao {

	public void addAccountant(AccountantPojo addacc) throws ClassNotFoundException, SQLException, NumberFormatException, IOException {
		// TODO Auto-generated method stub
		int id = addacc.getId();
		String name = addacc.getName();
		String password  = AccountantPojo.getPassword();
		String email = AccountantPojo.getEmail();
		String contact = addacc.getContact();

		/*System.out.println(id);
		System.out.println(name);
		System.out.println(password);
		System.out.println(email);
		System.out.println(contact); */
		ConnectionManager cm = new ConnectionManager();

		//insert all details into the database
		String sql = "insert into Accountant (ID,NAME,PASSWORD,EMAIL,CONTACT)VALUES(?,?,?,?,?)";

		//CREATE STATEMENT OBJECT
		PreparedStatement st = cm.getConnection().prepareStatement(sql);
		st.setInt(1, id);
		st.setString(2, name);
		st.setString(3, password);
		st.setString(4, email);
		st.setString(5, contact);

		AdminMenu am =new AdminMenu();
		try {
			st.executeUpdate();
			System.out.println("\n Sucessfully Accountant Details has been Stored.\n");
			cm.getConnection().close();
		}
		catch(Exception e) {
			System.out.println("\n Unique Constraint Violated. Same ID cannot be alloted to Different Accountant.\n");
			am.adminSection();
		}


	}
	public void deleteAccountant(int id) throws ClassNotFoundException, SQLException, NumberFormatException, IOException {
		// TODO Auto-generated method stub

		int roll = 0;
		Connection con = null;
		Statement stmt = null;
		ConnectionManager cm = new ConnectionManager();
		con = cm.getConnection();

		  stmt = con.createStatement();
		    String sqlget = "SELECT * FROM Accountant";
		    ResultSet rs = stmt.executeQuery(sqlget);

		    while(rs.next()){
		    	 roll = rs.getInt("ID");
		    	 if(id == roll) {
		    		 break;
		    	 } 
		    }
		    if(id !=roll) {
		    	System.out.println("Accountant ID does not match....!");
		    	cm.getConnection().close();

		    }
		    else {
		    	String sqlDelStuDetail = "delete from Accountant where  ID=? ";

			    PreparedStatement stm = con.prepareStatement(sqlDelStuDetail);
			    stm.setInt(1,roll);
			    stm.executeUpdate();
		        System.out.println("Database updated successfully ");
		        cm.getConnection().close();
		    }

	}

}
