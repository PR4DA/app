package com.gucci.mobileappws.ui.controller;

import com.gucci.mobileappws.ui.model.request.UserLoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @ApiOperation("User LogIn")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
            message = "Response Headers",
            responseHeaders = {
                    @ResponseHeader(name = "authorization",
                    description = "PR4DA <JWT value here>",
                    response = String.class),
                    @ResponseHeader(name = "userId",
                    description = "<Public User Id value here>",
                    response = String.class)
            })
    })
    @PostMapping("/users/login")
    public void deFakeLogin(@RequestBody UserLoginRequestModel loginRequestModel){
        throw new IllegalStateException("This method should not be called. Its implemented by springSecurity");
    }
}
