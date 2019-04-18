package model;

import app.auth.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.Principal;


@Entity
@Table(name = "user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class User implements Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId", updatable = false, nullable = false)
    private Integer userId;

    @Column(name = "postcode")
    @NotEmpty
    @Length(min = 6, max = 7)
    @JsonView(View.Public.class)
    private String postcode;

    @Column(name = "streetnumber")
    @NotEmpty
    @Length(min = 1, max = 10)
    @JsonView(View.Public.class)
    private String streetnumber;

    @Column(name = "emailAddress")
    @NotEmpty
    @Email
    @JsonView(View.Public.class)
    private String emailAddress;

    @Column(name = "fullname")
    @Length(min = 3, max = 100)
    @JsonView(View.Public.class)
    @NotNull
    private String fullName;

    @JsonView(View.Protected.class)
    @Column(name = "password")
    @NotNull
    private String password;

    @JsonView(View.Private.class)
    @Column(name = "roles")
    private String roles;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean hasRole(String roleName)
    {
        if (roles != null)
        {
            if(roleName.equals(roles))
                {
                    return true;
                }
        }
        return false;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean equals(User user)
    {
        return emailAddress.equals(user.getEmailAddress());
    }

}
