package hiber.controller;

import hiber.model.Role;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/")
	public String printStart(ModelMap model) {
		List<User> userList = userService.listUsers();

		model.addAttribute("users", userList);
		return "user-list";
	}

   	@PostMapping(value = "/add")
	public String addUser2(@RequestParam String username,
						   @RequestParam String email,
						   @RequestParam String password,
						   @RequestParam(defaultValue = "false") boolean isAdmin, ModelMap model) {
		User newUser = new User(username, email, password);
		Set<Role> roles = new HashSet<>();
		roles.add(Role.getUserRole());
		if(isAdmin) roles.add(Role.getAdminRole());

		newUser.setRoles(roles);

		userService.add(newUser);

		List<User> userList = userService.listUsers();
		model.addAttribute("users", userList);
		return "user-list";
	}

	@PostMapping(value = "/update")
	public String updateUser(@ModelAttribute(value="user") User user, ModelMap model) {
		userService.update(user);
		List<User> userList = userService.listUsers();

		model.addAttribute("users", userList);
		return "user-list";
	}

	@PostMapping(value = "/delete")
	public String deleteUser(@ModelAttribute(value="user") User user, ModelMap model) {
        System.out.println(user);
		userService.delete(user);
		List<User> userList = userService.listUsers();

		model.addAttribute("users", userList);
		return "user-list";
	}
}
