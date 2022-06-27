package com.mobydigital.recruiting.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobydigital.recruiting.enums.TypeOfDni;
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
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "First Name cannot be blank")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last Name cannot be blank")
    private String lastName;

    @Column(name = "type_dni")
    @NotEmpty(message = "Type of DNI cannot be blank")
    private TypeOfDni typeOfDni;

    @Column(name = "dni_number")
    @NotEmpty(message = "DNI number cannot be blank")
    private String dniNumber;

    @NotEmpty(message = "Birthday cannot be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthday;
}
