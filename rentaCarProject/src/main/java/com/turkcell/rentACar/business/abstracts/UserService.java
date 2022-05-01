package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.requests.creates.CreateUserRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteUserRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateUserRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

import com.turkcell.rentACar.business.dtos.UserListDto;

public interface UserService {

	Result add(CreateUserRequest createUserRequest) throws BusinessException;

	Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException;

	Result update(UpdateUserRequest updateUserRequest) throws BusinessException;

	DataResult<List<UserListDto>> getAll() throws BusinessException;
}
