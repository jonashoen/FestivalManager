package festivalmanager.staff;



import javax.validation.Valid;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

@Controller
class 	AccountController {

		private final AccountManager accountManager;
		public final UserAccountManager userAccounts;
		private final MessageManager messageManagement;
		private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
		@Autowired
		private ActiveAccountsStore activeAccountsStore;


		AccountController(AccountManager accountManager, UserAccountManager userAccounts, MessageManager messageManagement){
			Assert.notNull(accountManager, "kickstart.account.AccountManager must be not null");
			this.accountManager = accountManager;
			this.userAccounts = userAccounts;
			this.messageManagement = messageManagement;
		}

		@PostMapping("/createAccount")
		String createAccountNew(@Valid @ModelAttribute("form") CreationForm form, Model model, Errors result){
			accountManager.createAccount(form, result);
			if(result.hasErrors()){
				return createAccount(model,form,result);
			}
			return "redirect:/";
		}

		@PreAuthorize("hasRole('MANAGER') or hasAuthority('MANAGER')" )
		@GetMapping("/createAccount")
		String createAccount(Model model, CreationForm form, Errors result) {
			model.addAttribute("form", form);
			model.addAttribute("error", result);
			return "createAccount";
		}

		@PreAuthorize("hasRole('MANAGER') or hasAuthority('MANAGER')" )
		@GetMapping("/allAccounts")
		String allAccounts(Model model){
			model.addAttribute("accountList", accountManager.findAll());
			model.addAttribute("accountManager", accountManager);
			return "allAccounts";
		}


		@GetMapping(value = "/loggedaccounts")
		public String getLoggedAccounts(Locale locale, Model model){
			model.addAttribute( "accounts", activeAccountsStore.getAccounts());

			return "accounts";
		}

		@GetMapping("/myProfile")
		String profile(@LoggedIn UserAccount account, Model model){

			model.addAttribute("account", account);
			return "myProfile";
		}

		@GetMapping("/changePassword")
		String changePassword(@LoggedIn UserAccount account, Model model, changePasswordForm form){
			model.addAttribute("form", form);
			model.addAttribute("account", account);

			return "changePassword";
		}

		@PostMapping("/changePassword")
		String changePasswordPost( @LoggedIn UserAccount account, @Valid changePasswordForm form, Model model){
			accountManager.changePassword(account, form);

			return "redirect:/#staff";
		}

		@PreAuthorize("hasRole('MANAGER') or hasAuthority('MANAGER')")
		@GetMapping("/changePassword/{name}")
		String changePasswordByUsername(Model model, changePasswordForm form, @PathVariable String name){
			model.addAttribute("form", form);
			model.addAttribute("name", name);
			return "changePassword";
		}


		@PostMapping("changePassword/{name}")
		String changePasswordByUsernamePost(Model model, changePasswordForm form, @PathVariable String name){

			accountManager.changePassword(userAccounts.findByUsername(name).get(), form);
			LOG.info("changing password for " + name);
			return "redirect:/#staff";
		}


		@PreAuthorize("hasRole('MANAGER') or hasAuthority('MANAGER')" )
		@GetMapping("/deleteAccount/{name}")
		String deleteAccount(Model model, @PathVariable String name){
			accountManager.deleteAccount(accountManager.findByUserAccount(userAccounts.findByUsername(name).get()).get());
			return "redirect:/#staff";
		}

		@GetMapping("/sendMessage/{name}")
		String sendMessage(Model model, MessageForm form, @PathVariable String name, @RequestParam(required = false) Boolean multimessage){

			boolean multiMessage = Boolean.TRUE.equals(multimessage);

			model.addAttribute("multimessage", multiMessage);

			model.addAttribute("form", form);
			model.addAttribute("sender", name);
			model.addAttribute("accountList", accountManager.findAll());
			model.addAttribute("accountManager", accountManager);

			return "/sendMessage"  ;

		}

		@PostMapping("/sendMessage")
		String sendMessagePost(Model model, @Valid @ModelAttribute("form") MessageForm form){
			accountManager.sendMessage(form);
			return "redirect:/#staff";
		}

}

