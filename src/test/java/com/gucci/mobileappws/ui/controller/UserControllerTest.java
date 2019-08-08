package com.gucci.mobileappws.ui.controller;

import com.gucci.mobileappws.service.impl.UserServiceImpl;
import com.gucci.mobileappws.shared.dto.AddressDto;
import com.gucci.mobileappws.shared.dto.UserDto;
import com.gucci.mobileappws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;

    final String USER_ID = "blabla123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = new UserDto();
        userDto.setFirstName("name");
        userDto.setLastName("surname");
        userDto.setEmail("test@test.com");
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationToken(null);
        userDto.setUserId(USER_ID);
        userDto.setAddresses(getAddressesDto());
        userDto.setEncryptedPassword("blablabla");
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertTrue(userDto.getAddresses().size()==userRest.getAddresses().size());

    }

    private List<AddressDto> getAddressesDto(){
        AddressDto addressDto=new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("Canada");
        addressDto.setCountry("Vancouver");
        addressDto.setPostalCode("A123");
        addressDto.setStreetName("street");

        AddressDto addressDtoBilling=new AddressDto();
        addressDtoBilling.setType("billing");
        addressDtoBilling.setCity("Canada");
        addressDtoBilling.setCountry("Vancouver");
        addressDtoBilling.setPostalCode("A123");
        addressDtoBilling.setStreetName("street");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(addressDto);
        addresses.add(addressDtoBilling);

        return addresses;
    }
}