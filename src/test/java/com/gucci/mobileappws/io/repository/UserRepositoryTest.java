package com.gucci.mobileappws.io.repository;

import com.gucci.mobileappws.io.entity.AddressEntity;
import com.gucci.mobileappws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() {
        if(!recordsCreated) {
            createRecords();
        }
    }

    @Test
    void findAllUsersWithConfirmedEmailAddress() {
        Pageable pageable = PageRequest.of(1, 1);
        Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageable);
        assertNotNull(page);

        List<UserEntity> userEntities = page.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size()==1);
    }

    @Test
    void findUserByFirstName(){
        String firstName = "Name";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);
        assertNotNull(users);
        assertTrue(users.size()==2);

        UserEntity user = users.get(0);
        assertTrue(user.getFirstName().equalsIgnoreCase(firstName));
    }

    @Test
    void findUserByLastName(){
        String lastName = "Surname";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertTrue(users.size()==2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().equalsIgnoreCase(lastName));
    }

    @Test
    void findUserByKeyword(){
        String keyword = "am";
        List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size()==2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().contains(keyword)|| user.getFirstName().contains(keyword));
    }

    @Test
    void findUsersFirstNameAndLastNameByKeyword(){
        String keyword = "am";
        List<Object[]> users = userRepository.findUsersFirstNameAndLastNameByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size()==2);

        Object[] user = users.get(0);

        assertTrue(user.length==2);

        String userFirstName = String.valueOf(user[0]);
        String userLatName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLatName);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+ userFirstName);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+userLatName);
    }

    @Test
    void updateUserEmailVerificationStatus(){
        boolean newEmailVerificationStatus = true;
        userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "1a2bc3");

        UserEntity storedDetails = userRepository.findByUserId("1a2bc3");
        boolean storedEmailVerificationStatus = storedDetails.isEmailVerificationStatus();

        assertTrue(storedEmailVerificationStatus==newEmailVerificationStatus);
    }

    @Test
    void findUserByUserId(){
        String userId="1a2bc3";
        UserEntity userEntity = userRepository.findUserByUserId(userId);

        assertNotNull(userEntity);
        assertTrue(userEntity.getUserId().equals(userId));
    }

    @Test
    void getUserEntityFullNameById(){
        String userId="1a2bc3";
        List<Object[]> records = userRepository.getUserEntityFullNameById(userId);

        assertNotNull(records);
        assertTrue(records.size()==1);

        Object[] userDetails = records.get(0);

        String firstName = String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
    }

    @Test
    void updateUserEntityEmailVerificationStatus(){
        boolean newEmailVerificationStatus = true;
        userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "1a2bc3");

        UserEntity storedDetails = userRepository.findByUserId("1a2bc3");
        boolean storedEmailVerificationStatus = storedDetails.isEmailVerificationStatus();

        assertTrue(storedEmailVerificationStatus==newEmailVerificationStatus);
    }

    private void createRecords() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Name");
        userEntity.setLastName("Surname");
        userEntity.setUserId("1a2bc3");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationStatus(true);

        AddressEntity addressEntity=new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setAddressId("4d5e6f");
        addressEntity.setCity("Canada");
        addressEntity.setCountry("Vancouver");
        addressEntity.setPostalCode("A123");
        addressEntity.setStreetName("street");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);

        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Name");
        userEntity2.setLastName("Surname");
        userEntity2.setUserId("1a2bc34");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmail("test@test.com");
        userEntity2.setEmailVerificationStatus(true);

        AddressEntity addressEntity2=new AddressEntity();
        addressEntity2.setType("shipping");
        addressEntity2.setAddressId("4d5e6f4");
        addressEntity2.setCity("Canada");
        addressEntity2.setCountry("Vancouver");
        addressEntity2.setPostalCode("A123");
        addressEntity2.setStreetName("street");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);

        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);

        recordsCreated = true;
    }
}