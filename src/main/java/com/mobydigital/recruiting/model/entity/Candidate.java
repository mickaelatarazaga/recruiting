package com.mobydigital.recruiting.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobydigital.recruiting.model.enums.TypeOfDni;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "candidates")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean deleted = Boolean.FALSE;

    @Column(name = "first_name")
    @NotNull(message = "First Name cannot be null")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last Name cannot be null")
    private String lastName;

    @Column(name = "type_dni")
    @NotNull(message = "Type of DNI cannot be null")
    private TypeOfDni typeOfDni;

    @Column(name = "dni_number")
    @NotNull(message = "DNI number cannot be null")
    private String dniNumber;

    @NotNull(message = "Birthday cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

}
