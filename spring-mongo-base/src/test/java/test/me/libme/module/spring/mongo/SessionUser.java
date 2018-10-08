/**
 * 
 */
package test.me.libme.module.spring.mongo;

import me.libme.kernel._c._m.JModel;

/**
 * @author J
 *
 */
public class SessionUser implements JModel {

	/**
	 * the primary key of the login user , uuid
	 */
	private String id;
	
	/**
	 * user name to loginin 
	 */
	private String userName;

	/**
	 * user name to be display 
	 */
	private String natureName;

	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public static final SessionUser DEFUALT=new SessionUser();
	static{
		DEFUALT.setId("SYS-ID");
		DEFUALT.setNatureName("SYS");
		DEFUALT.setUserName("SYS");
	}
	
	
}
