package com.folautech.cleanupdata.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Entity
@Table(name = "row_data")
public class RowData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String            id;

    @Column(name = "slug")
    private String            slug;

    @Column(name = "column_1")
    private Double            column1;

    @Column(name = "column_2")
    private Long              column2;

    @Column(name = "column_3")
    private Double            column3;

    @Column(name = "column_4")
    private LocalDate         column4;

    @Column(name = "column_5")
    private Long              column5;

    @Column(name = "column_6")
    private Long              column6;

    @Column(name = "column_7")
    private String            column7;

    @Column(name = "column_8")
    private String            column8;

}
