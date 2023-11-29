
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JDBCConnector {
	
	public void insertNewUser(String username, String password, String email) throws ClassNotFoundException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/finalproject?user=root&password=root");
			st = conn.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
			st.setString(1, username);
			st.setString(2, password);
			st.setString(3, email);
			st.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
		catch (ClassNotFoundException ex) {
			System.out.println("MySQL Driver not found!");
		}
		finally {
			try {
				if(st != null) {
					st.close();
				}
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
	}
	
	public boolean validUser(String username, String password) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/finalproject?user=root&password=root");
            st = conn.prepareStatement("SELECT * FROM users WHERE username = ? OR password = ?");
            st.setString(1, username);
            st.setString(2, password);
            rs = st.executeQuery();
            return rs.next();
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } 
        catch (ClassNotFoundException e) {
        	System.out.println(e.getMessage());
            return false;
		} 
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } 
            catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
            }
        }
    }
	
	public ArrayList<User> populateUserList() {
		ArrayList<User> users = new ArrayList<User>();
		
		Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/finalproject?user=root&password=root");
            st = conn.prepareStatement("SELECT username, password, email FROM users");
            rs = st.executeQuery();
            
            while(rs.next()) {
            	String username = rs.getString("username");
            	String password = rs.getString("password");
            	String email = rs.getString("email");
            	User user = new User(username, password, email);
            	users.add(user);
            }
            
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        catch (ClassNotFoundException e) {
        	System.out.println(e.getMessage());
		} 
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } 
            catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
            }
        }
		
        return users;
	}
	
	
	public int getUserID(User user) {
		Connection conn = null;
		PreparedStatement st = null;
		int user_id = -1;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/finalproject?user=root&password=root");
			st = conn.prepareStatement("SELECT user_id FROM users WHERE username = ? AND password = ?");
			st.setString(1, user.getUsername());
			st.setString(2, user.getPassword());
	
			try (ResultSet rs = st.executeQuery()) {
				if(rs.next()) {
					user_id = rs.getInt("user_id");
				}
			}
			
		}
		catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
		catch (ClassNotFoundException ex) {
			System.out.println("MySQL Driver not found!");
		}
		finally {
			try {
				if(st != null) {
					st.close();
				}
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
		return user_id;
	}
	
	
	
	
}