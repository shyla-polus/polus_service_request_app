package com.example.polusServiceRequest.models;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roleid")
	private Long roleId;

	@Column(name = "ROLE_NAME", nullable = false, unique = true)
	private String roleName;

	@Column(name = "roledescription", nullable = false)
	private String roleDescription;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserRoleEntity> userRoles = new HashSet<>();

}
