package festivalmanager.staff;

import festivalmanager.festival.Festival;
import festivalmanager.festival.FestivalManager;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.salespointframework.useraccount.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.salespointframework.core.Currencies.EURO;

/**
 * Implementation of business logic related to {@link Account}s
 */
@Service
@Transactional
public class AccountManager {

	public static final Role CATERING_ROLE = Role.of("CATERING");
	public static final Role MANAGER_ROLE = Role.of("MANAGER");
	public static final Role SECURITY_ROLE = Role.of("SECURITY");
	public static final Role TICKET_SALESMAN_ROLE = Role.of("TICKET_SALESMAN");
	public static final Role FESTIVAL_MANAGER_ROLE = Role.of("FESTIVAL_MANAGER");


	public final AccountRepository accounts;
	public final UserAccountManager userAccounts;
	private final ApplicationEventPublisher publisher;
	private final FestivalManager festivalManager;

	@Autowired
	private SessionRegistry sessionRegistry;

	private static final Logger LOG = LoggerFactory.getLogger(AccountManager.class);

	/**
	 * 	Creates a new {@link #AccountManager} with given {@link AccountRepository}, {@link UserAccountManager}, {@link FestivalManager} and {@link ApplicationEventPublisher}
	 * @param accounts Repository for all existing accounts, must not be null
	 * @param userAccounts must not be null
	 * @param publisher publisher for the MessageEvent
	 * @param festivalManager must not be null
	 */
	AccountManager(AccountRepository accounts, UserAccountManager userAccounts, ApplicationEventPublisher publisher, FestivalManager festivalManager) {

		Assert.notNull(accounts, "kickstart.account.AccountRepository must not be null");
		Assert.notNull(userAccounts, "UserAccountManager must not be null");

		this.accounts = accounts;
		this.userAccounts = userAccounts;
		this.publisher = publisher;
		this.festivalManager = festivalManager;
	}

	/**
	 * this method creates a new {@link Account}
	 * @param form the {@link CreationForm} with the necessary data, must not be null
	 * @param result errors created by submitting the form
	 * @return return new {@link Account} with the data of the form
	 */
	public Account createAccount(CreationForm form, Errors result) {

		Assert.notNull(form, "Registration form must not be null!");

		var password = UnencryptedPassword.of(form.getPassword());
		if (userAccounts.findByUsername(form.getUsrName()).isPresent()) {
			System.out.println("username already used");
			result.rejectValue("name", "Duplicate.creationForm.username");
			return null;
		} else {
			var userAccount = userAccounts.create(form.getUsrName(), password);
			if (Boolean.TRUE.equals(form.getCatering())) {
				userAccount.add(CATERING_ROLE);
			}
			if (Boolean.TRUE.equals(form.getSecurity())) {
				userAccount.add(SECURITY_ROLE);
			}
			/*if (Boolean.TRUE.equals(form.getManager())){

				userAccount.add(MANAGER_ROLE);
			}*/
			if (Boolean.TRUE.equals(form.getTicketSalesman())) {
				userAccount.add(TICKET_SALESMAN_ROLE);
			}

			if (Boolean.TRUE.equals(form.getFestivalManager())){
				userAccount.add(FESTIVAL_MANAGER_ROLE);
			}

			//float workedHours = form.getWorkedHours();
			Money hourlyWage = null;
			if(form.getHourlyWage() != null) {
				hourlyWage = Money.of(form.getHourlyWage(), EURO);
			}

			Festival festival = null;
			LOG.info("adding Accoung to " + form.getFestival());
			if(form.getFestival() != null){
				festival = festivalManager.findByName(form.getFestival());
			}
			return accounts.save(new Account(userAccount, form.getFirstName(), form.getLastName(), form.getWorkedHours(), hourlyWage, festival));
		}
	}

	/**
	 * this method changes the password for an {@link Account}
	 * @param account the account whose password is to be changed
	 * @param form the {@link changePasswordForm} providing the new password
	 */
	public void changePassword(UserAccount account, changePasswordForm form) {

		var password = UnencryptedPassword.of(form.getNewPassword());
		userAccounts.changePassword(account, UnencryptedPassword.of(form.getNewPassword()));
	}

	/**
	 * method for finding all existing {@link Account} in the {@link AccountRepository}
	 * @return returns all existing accounts
	 */
	public Streamable<Account> findAll() {
		return accounts.findAll();
	}

	/**
	 * this method finds all {@link Account}s connected to a {@link Festival}
	 * @param festival the festival whose accounts are to be found
	 * @return returns a stream of all connected accounts
	 */
	public Stream<Account> findAllByFestival(Festival festival) {
		LOG.info(festival.toString());
		return findAll().filter(a -> a.getFestival() != null).filter(a -> a.getFestival().equals(festival)).stream();
	}

	/**
	 * this method sends a {@link Message} defined by the data in the {@link MessageForm}
	 * @param form form providing all the data for the message
	 */
	public void sendMessage(MessageForm form){

		LOG.info(form.getRole());

		UserAccount sender = userAccounts.findByUsername(form.getSender()).get();
		if(!(form.getReceiver() == null)) {
			UserAccount receiver = userAccounts.findByUsername(form.getReceiver()).get();;
			MessageEvent messageEvent = new MessageEvent(this, accounts.findByUserAccount(sender).get(),
					accounts.findByUserAccount(receiver).get(), form.getMessage());
			publisher.publishEvent(messageEvent);

		} else {
			Role role = Role.of(form.getRole());


			findByRole(role).forEach(acc -> publisher.publishEvent(new MessageEvent(this, accounts.findByUserAccount(sender).get(),acc
					, form.getMessage())));

		}
	}

	/**
	 * this methods deletes an {@link Account}
	 * @param account account to be deleted
	 */
	public void deleteAccount(Account account){
		userAccounts.delete(account.getUserAccount());
		accounts.delete(account);
		LOG.info("deleting " + account.getUserAccount().getUsername());
	}

	/**
	 * this methods finds the {@link Account} connected to an provided {@link UserAccount}
	 * @param userAccount provides the UserAccount
	 * @return return the connected Account intance
	 */
	public Optional<Account> findByUserAccount(UserAccount userAccount) {
		return accounts.findByUserAccount(userAccount);
	}

	/**
	 * this methods finds all {@link Account}s with a specified {@link Role}
	 * @param role the role to be searched
	 * @return returns a stream of all accounts with he specified role
	 */
	public Stream<Account> findByRole(Role role){
		return findAll().stream().filter(acc -> acc.getUserAccount().getRoles().toSet().contains(role));
	}

	/**
	 * this method is for finding all currently logged in users
	 * @return returns the username of all currently active {@link Account}s
	 */
	public List<String> getUsersFromSessionRegistry() {
		return sessionRegistry.getAllPrincipals().stream()
				.filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
				.map(u -> ((UserDetails) u).getUsername())
				.collect(Collectors.toList());
	}
}

