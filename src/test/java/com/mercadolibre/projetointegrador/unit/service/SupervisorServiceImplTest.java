package com.mercadolibre.projetointegrador.unit.service;

import com.mercadolibre.projetointegrador.model.Supervisor;
import com.mercadolibre.projetointegrador.service.crud.impl.SupervisorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SupervisorServiceImplTest {

    @Mock
    private SupervisorServiceImpl supervisorService;

    @Test
    public void findByIdTest() {
        Supervisor supervisor = mockSupervisor();
        when(supervisorService.findById(1L)).thenReturn(supervisor);
        assertEquals(supervisor, supervisorService.findById(1L));
    }

    private static Supervisor mockSupervisor(){
        Supervisor supervisor = new Supervisor();
        supervisor.setId(1L);
        supervisor.setEmail("randomsupervisor@meli.com");
        supervisor.setName("random supervisor");
        supervisor.setInboundOrders(new ArrayList<>());
        return supervisor;
    }
}
