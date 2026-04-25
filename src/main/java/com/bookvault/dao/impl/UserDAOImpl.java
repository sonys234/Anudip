package com.bookvault.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bookvault.dao.UserDAO;
import com.bookvault.dto.UserDTO;
import com.bookvault.utility.DbConnection;

public class UserDAOImpl implements UserDAO {
	
	@Override
	public boolean insertUserToDB(UserDTO user) {
		boolean isInserted = false;
		
		try {
			Connection con = DbConnection.establishConnection();
			String sql = "INSERT INTO Users (First_Name, Last_Name, Email, Contact, Password) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName()); 
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getContact());
			ps.setString(5, user.getPassword()); 
			
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				isInserted = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isInserted;
	}
	
	@Override
	public boolean loginUser(String email, String password) {
		boolean isValidUser = false;
		
		try {
			Connection con = DbConnection.establishConnection();
			String sql = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
			    isValidUser = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isValidUser;
	}
	
	@Override
	public UserDTO getUserByEmail(String email) {
	    UserDTO user = null;
	    String sql = "SELECT * FROM Users WHERE Email = ?";
	    
	    try (Connection con = DbConnection.establishConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            user = new UserDTO();
	            // Pull data from DB columns and pack the DTO
	            user.setUserId(rs.getInt("User_id")); 
	            user.setFirstName(rs.getString("First_Name"));
	            user.setLastName(rs.getString("Last_Name"));
	            user.setEmail(rs.getString("Email"));
	            user.setContact(rs.getString("Contact"));
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return user;
	}
	
	@Override
	public boolean updateUserProfile(UserDTO user) {
	    boolean isUpdated = false;
	    String sql = "UPDATE Users SET First_Name = ?, Last_Name = ?, Contact = ? WHERE User_id = ?";
	    
	    try (Connection con = DbConnection.establishConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setString(1, user.getFirstName());
	        ps.setString(2, user.getLastName());
	        ps.setString(3, user.getContact());
	        ps.setInt(4, user.getUserId()); // Targeting the specific user
	        
	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            isUpdated = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return isUpdated;
	}
	
	@Override
	public boolean changePassword(int userId, String oldPassword, String newPassword) {
	    boolean isChanged = false;
	    // The query only succeeds if BOTH the ID and the OLD password match
	    String sql = "UPDATE Users SET Password = ? WHERE User_id = ? AND Password = ?";
	    
	    try (Connection con = DbConnection.establishConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setString(1, newPassword);
	        ps.setInt(2, userId);
	        ps.setString(3, oldPassword);
	        
	        int rows = ps.executeUpdate();
	        if (rows > 0) {
	            isChanged = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return isChanged;
	} 
	
	@Override
	public boolean deleteUserAccount(int userId) {
	    boolean isDeleted = false;
	    String sql = "DELETE FROM Users WHERE User_id = ?";
	    
	    try (Connection con = DbConnection.establishConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, userId);
	        
	        int rows = ps.executeUpdate();
	        if (rows > 0) {
	            isDeleted = true;
	        }
	    } catch (Exception e) {
	       
	        e.printStackTrace();
	    }
	    return isDeleted;
	}
}