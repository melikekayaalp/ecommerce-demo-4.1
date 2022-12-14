package com.etiya.ecommercedemo4.business.concretes;

import com.etiya.ecommercedemo4.business.abstracts.IAddressService;
import com.etiya.ecommercedemo4.business.abstracts.IAddressTypeService;
import com.etiya.ecommercedemo4.business.abstracts.IStreetService;
import com.etiya.ecommercedemo4.business.abstracts.IUserService;
import com.etiya.ecommercedemo4.business.constants.Messages;
import com.etiya.ecommercedemo4.business.dtos.request.address.AddAddressRequest;
import com.etiya.ecommercedemo4.business.dtos.response.address.GetAddressDto;
import com.etiya.ecommercedemo4.core.util.exceptions.BusinessException;
import com.etiya.ecommercedemo4.core.util.mapping.ModelMapperService;
import com.etiya.ecommercedemo4.core.util.messages.IMessagesService;
import com.etiya.ecommercedemo4.core.util.results.DataResult;
import com.etiya.ecommercedemo4.core.util.results.Result;
import com.etiya.ecommercedemo4.core.util.results.SuccessDataResult;
import com.etiya.ecommercedemo4.core.util.results.SuccessResult;
import com.etiya.ecommercedemo4.entities.concretes.*;
import com.etiya.ecommercedemo4.repository.IAddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@AllArgsConstructor
@Service
public class AddressManager implements IAddressService {

    private IAddressRepository addressRepository;
    private IAddressTypeService addressTypeService;
    private IStreetService streetService;
    private IUserService userService;
    private ModelMapperService modelMapperService;
    private IMessagesService messagesService;




    @Override
    public DataResult<List<Address>> getAll() {
        List<Address> response = this.addressRepository.findAll();
        return new SuccessDataResult<List<Address>>(response, messagesService.getMessage(Messages.SuccessMessages.ListAll));

    }

    @Override
    public DataResult<Address> getById(int id) {
        Address response = this.addressRepository.findById(id).orElseThrow();
        return new SuccessDataResult<Address>(response,messagesService.getMessage( Messages.SuccessMessages.ListById));
    }

    @Override
    public DataResult<List<Address>> getByAddressType() {
        List<Address> response = this.addressRepository.findByAddressType();
        return new SuccessDataResult<List<Address>>(response, messagesService.getMessage("ADDRESSES_LISTED_BY_ADDRESSTYPE"));

    }

    @Override
    public Result add(AddAddressRequest addAddressRequest) {

        checkIfStreetExists(addAddressRequest.getStreetId());
        checkIfUserExists(addAddressRequest.getUserId());

        Address address = this.modelMapperService.forRequest().map(addAddressRequest, Address.class);

        address.setId(0);
        this.addressRepository.save(address);
        return new SuccessResult(Messages.SuccessMessages.Add);


    }

    @Override
    public Page<Address> getAllWithPagination(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    @Override
    public DataResult<GetAddressDto> getAddressDto(int id) {
        GetAddressDto response = this.addressRepository.getAddressDto(id);
        return new SuccessDataResult<GetAddressDto>(response,messagesService.getMessage( Messages.SuccessMessages.Succeeded));
    }

    private Country getCountryByCityName(String name) {
        return this.addressRepository.findCountryByCityName(name);
    }

    private City getCityByTownName(String name) {
        return this.addressRepository.findCityByTownName(name);
    }

    private Town getTownByDistrictName(String name) {
        return this.addressRepository.findTownByDistrictName(name);
    }

    private District getDistrictByStreetId(int id) {
        return this.addressRepository.findDistrictByStreetId(id);
    }

    private void checkIfStreetExists(int id) {
        Street street = this.streetService.getById(id).getData();
        if (street == null) {
            throw new
                    BusinessException(messagesService.getMessage(Messages.Address.StreetDoesNotExist));
        }
    }

    private void checkIfUserExists(int id) {
        User user = this.userService.getById(id).getData();
        if (user == null) {
            throw new
                    BusinessException(messagesService.getMessage(Messages.User.UserDoesNotExist));
        }
    }
}
