package com.example.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "userinfo")
@Data

@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String email;
	private String profileImageUrl;
	private boolean loggedIn=true;


	public User(@NotBlank String username, 
            @NotBlank String password) {
    this.username = username;
    this.password = password;
    this.loggedIn = false;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User user = (User) o;
		return Objects.equals(email, user.email) && Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, password, loggedIn);

	}
	  @Override
	    public String toString() {
	        return "User{" +
	                "id=" + id +
	                ", username='" + email + '\'' +
	                ", password='" + password + '\'' +
	                ", loggedIn=" + loggedIn +
	                '}';
}
}
