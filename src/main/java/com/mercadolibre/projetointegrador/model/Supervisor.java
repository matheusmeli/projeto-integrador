package com.mercadolibre.projetointegrador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Supervisor extends Employee{

    @OneToMany(mappedBy = "supervisor")
    @JsonIgnore
    private List<InboundOrder> inboundOrders = new ArrayList<>();
}
