package com.etiya.ecommercedemo4.business.concretes;

import com.etiya.ecommercedemo4.business.abstracts.IUserService;
import com.etiya.ecommercedemo4.business.constants.Messages;
import com.etiya.ecommercedemo4.business.dtos.request.user.AddUserRequest;
import com.etiya.ecommercedemo4.business.dtos.response.user.AddUserResponse;
import com.etiya.ecommercedemo4.core.util.mapping.ModelMapperService;
import com.etiya.ecommercedemo4.core.util.messages.IMessagesService;
import com.etiya.ecommercedemo4.core.util.results.DataResult;
import com.etiya.ecommercedemo4.core.util.results.Result;
import com.etiya.ecommercedemo4.core.util.results.SuccessDataResult;
import com.etiya.ecommercedemo4.core.util.results.SuccessResult;
import com.etiya.ecommercedemo4.entities.concretes.User;
import com.etiya.ecommercedemo4.repository.IUserRepository;
import org.aspectj.bridge.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager implements IUserService {

    private IUserRepository userRepository;
    private ModelMapperService modelMapperService;

    private IMessagesService messagesService;

    public UserManager(IUserRepository userRepository,ModelMapperService modelMapperService) {
        this.userRepository = userRepository;
        this.modelMapperService=modelMapperService;
    }

    @Override
    public DataResult<List<User>> getAll() {
        List<User> response = this.userRepository.findAll();
        return new SuccessDataResult<List<User>>(response,messagesService.getMessage( Messages.SuccessMessages.ListAll));
    }

    @Override
    public Page<User> getAllWithPagination(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public Result add(AddUserRequest addUserRequest) {

        User user = this.modelMapperService.forRequest().map(addUserRequest,User.class);
        user.setId(0);
        this.userRepository.save(user);

        return new SuccessResult(messagesService.getMessage(Messages.SuccessMessages.Add));
    }

    @Override
    public DataResult<User> getById(int id) {
        User response = this.userRepository.findById(id).orElseThrow();
        return new SuccessDataResult<User>(response,messagesService.getMessage(Messages.SuccessMessages.ListById));
    }
}
