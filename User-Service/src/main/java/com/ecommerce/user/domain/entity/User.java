package com.ecommerce.user.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NonNull;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDate;

@Entity
@Builder
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private Long Id;
    @ColumnTransformer(
            read =  "pgp_sym_decrypt( pwd::bytea, password_key() )",
            write = "pgp_sym_encrypt( ?, password_key() )"
    )
    @NonNull
    private String pwd;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String userId;
    private LocalDate createAt;
}
