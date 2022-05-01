package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constans.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.UserListDto;
import com.turkcell.rentACar.business.requests.creates.CreateUserRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteUserRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateUserRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.UserDao;
import com.turkcell.rentACar.entities.concretes.User;

@Service
public class UserManager implements UserService{

	private UserDao userDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public UserManager(UserDao userDao, ModelMapperService modelMapperService) {
		this.userDao = userDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) throws BusinessException {
		User user = this.modelMapperService.forRequest().map(createUserRequest, User.class);
		this.userDao.save(user);

		return new SuccessResult(BusinessMessages.USER_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException {
		checkIfExistByUserId(deleteUserRequest.getUserId());
		
		User user = this.modelMapperService.forRequest().map(deleteUserRequest, User.class);
		this.userDao.delete(user);

		return new SuccessResult(BusinessMessages.USER_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateUserRequest updateUserRequest) throws BusinessException {
		 checkIfExistByUserId(updateUserRequest.getUserId());
		
		 User user = userDao.getById(updateUserRequest.getUserId());
		 user = this.modelMapperService.forRequest().map(updateUserRequest, User.class);
		 this.userDao.save(user);

			return new SuccessResult(BusinessMessages.USER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<UserListDto>> getAll() throws BusinessException {
		List<User> result = this.userDao.findAll();
		List<UserListDto> response = result.stream()
				.map(user -> this.modelMapperService.forDto().map(user, UserListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<UserListDto>>(response, BusinessMessages.USER_LISTED_SUCCESSFULLY);
	}


	private void checkIfExistByUserId(int userId) throws BusinessException {
		if (!this.userDao.existsById(userId)) {
			throw new BusinessException(BusinessMessages.USER_NOT_FOUND);
		}
	}
}
