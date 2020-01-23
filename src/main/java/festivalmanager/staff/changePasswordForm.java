package festivalmanager.staff;

import javax.validation.constraints.NotEmpty;

/**
 * this is a form for changing the password of an {@link Account}
 */
public class changePasswordForm {


	@NotEmpty
	public String newPassword;

	/**
	 *
	 * @param newPassword the new password to be used
	 */
	public changePasswordForm( String newPassword){
		this.newPassword = newPassword;


		//this.newPassword_validate = newPassword_validate;
	}



	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


}
