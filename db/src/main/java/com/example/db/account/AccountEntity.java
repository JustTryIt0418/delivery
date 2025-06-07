package com.example.db.account;

import com.example.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "account")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AccountEntity extends BaseEntity {


}
