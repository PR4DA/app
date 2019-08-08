package com.gucci.mobileappws.service.impl;

import com.gucci.mobileappws.exceptions.UserServiceException;
import com.gucci.mobileappws.io.entity.AddressEntity;
import com.gucci.mobileappws.io.entity.UserEntity;
import com.gucci.mobileappws.io.repository.UserRepository;
import com.gucci.mobileappws.shared.AmazonSES;
import com.gucci.mobileappws.shared.Utils;
import com.gucci.mobileappws.shared.dto.AddressDto;
import com.gucci.mobileappws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    AmazonSES amazonSES;

    String userId="abc123efh";
    String encryptedPassword="abc123efh456";




    UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userEntity= new UserEntity();
        userEntity.setId(666L);
        userEntity.setFirstName("Name");
        userEntity.setLastName("Surname");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(encryptedPassword);

        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationToken("gagaga");

        userEntity.setAddresses(getAddressesEntity());
    }

    @Test
    void getUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto u = userService.getUser("test@test.com");

        assertNotNull(u);
        assertEquals("Name", u.getFirstName());
    }

    @Test
    void TestGetUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> {
                    userService.getUser("test@test.com");
                });
    }

    @Test
    void testCreateUser (){
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto=new UserDto();

        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Name");
        userDto.setLastName("Surname");
        userDto.setPassword("123");
        userDto.setEmail("test@test.com");

        assertThrows(UserServiceException.class,
                () -> {
                    userService.createUser(userDto);
                });
    }

    @Test
    void createUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("abcdef123456");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto userDto=new UserDto();

        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Name");
        userDto.setLastName("Surname");
        userDto.setPassword("123");
        userDto.setEmail("test@test.com");

        UserDto storedUserDetails = userService.createUser(userDto);

        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());

        assertNotNull(storedUserDetails.getUserId());

        assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());

        verify(utils,times(2)).generateAddressId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("123");
        verify(userRepository, times(1)).save(any(UserEntity.class));
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

    private List<AddressEntity> getAddressesEntity(){
        List<AddressDto> addressDtos = getAddressesDto();

        Type listType = new TypeToken<List<AddressEntity>>(){}.getType();
        return new ModelMapper().map(addressDtos, listType);
    }

}
