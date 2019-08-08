package com.gucci.mobileappws.io.repository;

import com.gucci.mobileappws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);

    UserEntity findUserByEmailVerificationToken(String token);

    //native query
    //provide count cus of pageable
    @Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            countQuery = "select count (*) from Users u where u.EMAIL_VERIFICATION_STATUS='true'",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageable);

    //NATIVE SQL QUERIES

    //1
    @Query(value = "select * from Users u where u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    //2 no matter order of arguments
    @Query(value = "select * from Users u where u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName")String lastName);

    @Query(value = "select * from Users u where first_name LIKE %:keyword% or last_name LIKE %:keyword%", nativeQuery = true)
    List<UserEntity> findUsersByKeyword(@Param("keyword") String keyword);

    @Query(value = "select u.first_name, u.last_name from Users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%",nativeQuery = true)
    List<Object[]> findUsersFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);

    //update query
    //if error transactional will role back
    @Transactional
    //need for all update delete actions
    @Modifying
    @Query(value = "update Users u set u.EMAIL_VERIFICATION_STATUS = :emailVerificationStatus where u.user_id = :userId" ,nativeQuery = true)
    void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);

    //JAVA PERSISTENCE QUERY LANG(JPQL)

    //need select usable entity with annotation @Table(name=users)
    @Query("select user from UserEntity user where user.userId = :userId")
    UserEntity findUserByUserId(@Param("userId") String userId);

    @Query("select user.firstName, user.lastName from UserEntity user where user.userId = :userId")
    List<Object[]> getUserEntityFullNameById(@Param("userId") String userId);

    //for modify data in database
    @Modifying
    //rollback changes if called error
    @Transactional
    @Query("update UserEntity u set u.emailVerificationStatus = :emailVerificationStatus where u.userId = :userId")
    void updateUserEntityEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);
}
