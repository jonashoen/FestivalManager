/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package festivalmanager.controller;

import festivalmanager.staff.*;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class WelcomeController {


	@Autowired
	private MessageManager MessageManager;

	@Autowired
	private final AccountManager accountManager;

	private static final Logger LOG = LoggerFactory.getLogger(WelcomeController.class);

	WelcomeController(AccountManager accountManager){

		this.accountManager = accountManager;

	}

	@RequestMapping("/")
	public String index(Model model, @LoggedIn Optional<UserAccount> userAccount) {
		Assert.notNull(MessageManager, "MessageManagement must not be null");

		if(userAccount.isPresent()) {
			Account account = accountManager.findByUserAccount(userAccount.get()).get();
			model.addAttribute("Account", account);
		}

		model.addAttribute("messageManagement", MessageManager);
		model.addAttribute("messageList", MessageManager.findAll());

		return "welcome";
	}
}
