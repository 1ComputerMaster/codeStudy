package com.ecommerce.user.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDate;

//Builder 패턴을 사용할 때 JPA는 생성자가 없는 것으로 인지해서 new User로 가져오지 못한다. 따라서, 생성자를 만들어주는 NoArgs를 붙이고 짆애
//Setter를 통해서 등록 가능
@Builder
@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
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
