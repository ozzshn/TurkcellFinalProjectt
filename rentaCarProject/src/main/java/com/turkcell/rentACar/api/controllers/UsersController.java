package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.dtos.UserListDto;
import com.turkcell.rentACar.business.requests.creates.CreateUserRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteUserRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateUserRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	private UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateUserRequest createUserRequest) throws BusinessException {
		return this.userService.add(createUserRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteUserRequest deleteUserRequest) throws BusinessException {
		return this.userService.delete(deleteUserRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateUserRequest updateUserRequest) throws BusinessException {
		return this.userService.update(updateUserRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<UserListDto>> getAll() throws BusinessException {
		return this.userService.getAll();
	}
}
