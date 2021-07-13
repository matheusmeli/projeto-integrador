package com.mercadolibre.projetointegrador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnore
    List<Section> sections = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse")
    List<Employee> employees;
}
