package com.appsdeveloperblog.app.ws.ui.model.request;

import javax.validation.constraints.NotBlank;

public class UpdateUserDetailsRequestModel {
    @NotBlank(message="first name cannot be null")
    private String firstName;
    @NotBlank
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
