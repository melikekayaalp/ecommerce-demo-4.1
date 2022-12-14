package com.etiya.ecommercedemo4.business.concretes;

import com.etiya.ecommercedemo4.business.abstracts.IAddressTypeService;
import com.etiya.ecommercedemo4.business.constants.Messages;
import com.etiya.ecommercedemo4.business.dtos.request.addressType.AddAddressTypeRequest;
import com.etiya.ecommercedemo4.core.util.mapping.ModelMapperService;
import com.etiya.ecommercedemo4.core.util.messages.IMessagesService;
import com.etiya.ecommercedemo4.core.util.results.DataResult;
import com.etiya.ecommercedemo4.core.util.results.Result;
import com.etiya.ecommercedemo4.core.util.results.SuccessDataResult;
import com.etiya.ecommercedemo4.core.util.results.SuccessResult;
import com.etiya.ecommercedemo4.entities.concretes.AddressType;
import com.etiya.ecommercedemo4.repository.IAddressTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AddressTypeManager implements IAddressTypeService {

    private IAddressTypeRepository addressTypeRepository;
    private ModelMapperService modelMapperService;
    private IMessagesService messagesService;


    @Override
    public DataResult<List<AddressType>> getAll() {
        List<AddressType> response = this.addressTypeRepository.findAll();
        return new SuccessDataResult<List<AddressType>>(response, messagesService.getMessage(Messages.SuccessMessages.ListAll));
    }

    @Override
    public DataResult<AddressType> getById(int id) {
        AddressType response = this.addressTypeRepository.findById(id).orElseThrow();
        return new SuccessDataResult<AddressType>(response, messagesService.getMessage(Messages.SuccessMessages.ListById));
    }

    @Override
    public Page<AddressType> getAllWithPagination(Pageable pageable) {
        return this.addressTypeRepository.findAll(pageable);
    }

    @Override
    public Result add(AddAddressTypeRequest addAddressTypeRequest) {

        AddressType addressType = this.modelMapperService.forRequest().map(addAddressTypeRequest,AddressType.class);
        addressType.setId(0);
        this.addressTypeRepository.save(addressType);
        return new SuccessResult(messagesService.getMessage(Messages.SuccessMessages.Add));


    }
}
