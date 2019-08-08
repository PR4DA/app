package com.gucci.mobileappws.service.impl;

import com.gucci.mobileappws.io.entity.AddressEntity;
import com.gucci.mobileappws.io.entity.UserEntity;
import com.gucci.mobileappws.io.repository.AddressRepository;
import com.gucci.mobileappws.io.repository.UserRepository;
import com.gucci.mobileappws.service.AddressService;
import com.gucci.mobileappws.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null)return  returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity:addresses){
            returnValue.add(new ModelMapper().map(addressEntity,AddressDto.class));
        }
        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {
        AddressDto returnValue = null;
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if(addressEntity!=null){
            returnValue=new ModelMapper().map(addressEntity, AddressDto.class);
        }
        return returnValue;
    }
}
