package com.example.polusServiceRequest.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ROLE")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME", nullable = false, unique = true)
    private String roleName;

    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;

    @ManyToOne
    @JoinColumn(name = "UPDATE_USER")
    private PersonEntity updateUser;

    @Column(name = "UPDATE_TIMESTAMP")
    private Timestamp updateTimestamp;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonRoleEntity> userRoles = new HashSet<>();

}
