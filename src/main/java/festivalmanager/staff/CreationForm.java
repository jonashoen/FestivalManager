package festivalmanager.staff;

import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalManager;
import org.h2.api.UserToRolesMapper;
import org.javamoney.moneta.Money;

import javax.validation.constraints.NotEmpty;

/**
 * this is a form for creating a new {@link Account}
 */
public class CreationForm {

		@NotEmpty(message = "{CreationForm.name.NotEmpty}")
		private final String usrName;

		@NotEmpty(message = "{CreationForm.password.NotEmpty}")
		private final String password;

		@NotEmpty(message = "{CreationForm.firstName.NotEmpty}")
		private final String firstName;

		@NotEmpty(message = "{CreationForm.lastName.NotEmpty")
		private final String lastName;

		private Boolean catering;

		private Boolean security;

		private Boolean festivalManager;

		private Boolean ticketSalesman;

		private Float workedHours;

		private Float hourlyWage;

		private String festival;

	/**
	 * the form provides following data
	 * @param usrName the username
	 * @param password the password
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param catering a boolean if true the account has the {@link org.salespointframework.useraccount.Role} 'CATERING'
	 * @param security a boolean if true the account has the {@link org.salespointframework.useraccount.Role} 'SECURITY'
	 * @param festivalManager a boolean if true the account has the {@link org.salespointframework.useraccount.Role} 'FESTIVALMANAGER'
	 * @param ticketSalesman a boolean if true the account has the {@link org.salespointframework.useraccount.Role} 'TICKET_SALESMAN'
	 * @param workedHours the hours worked by the person represented by the account, can be null
	 * @param hourlyWage the hourly wage of the represented person, can be null
	 * @param festival the festival to which the account belongs, can be null
	 */
		public CreationForm(String usrName, String password, String firstName, String lastName,
							Boolean catering, Boolean security, Boolean festivalManager, Boolean ticketSalesman, Float workedHours, Float hourlyWage, String festival) {
			this.usrName = usrName;
			this.password = password;
			this.firstName = firstName;
			this.lastName = lastName;
			this.catering = catering;
			this.security = security;
			this.festivalManager = festivalManager;
			this.ticketSalesman = ticketSalesman;
			this.workedHours = workedHours;
			this.hourlyWage = hourlyWage;
			this.festival = festival;
		}

		public String getUsrName() {
			return usrName;
		}

		public String getPassword() {
			return password;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public Boolean getCatering() {
			return catering;
		}

		public Boolean getSecurity() {
			return security;
		}

		public Boolean getFestivalManager() {
			return festivalManager;
		}

		public Boolean getTicketSalesman() {
			return ticketSalesman;
		}

		public Float getHourlyWage() {return hourlyWage;}

		public Float getWorkedHours() {return workedHours;}

		public String getFestival() {return festival;}
}

