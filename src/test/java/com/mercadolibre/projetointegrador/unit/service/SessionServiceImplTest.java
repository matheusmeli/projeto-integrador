package com.mercadolibre.projetointegrador.unit.service;

import com.mercadolibre.projetointegrador.dtos.response.UserResponseDTO;
import com.mercadolibre.projetointegrador.model.User;
import com.mercadolibre.projetointegrador.service.crud.impl.UserServiceImpl;
import com.mercadolibre.projetointegrador.service.impl.SessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SessionServiceImplTest {

    UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
    SessionServiceImpl service;

    @BeforeEach
    void setUp(){
        this.service = new SessionServiceImpl(userService);
    }


    @Test
    void loginFail() {
        when(userService.findByUsernameAndPassword("user", "invalid")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.login("user", "invalid"),
                "");
    }

    @Test
    void loginOk(){
        User employee = new User();
        employee.setUsername("User");
        employee.setPassword("Pass");
        when(userService.findByUsernameAndPassword("User", "Pass")).thenReturn(employee);
        UserResponseDTO accountDTO = service.login("User","Pass");
        assertEquals("User", accountDTO.getUsername());
        assertTrue(accountDTO.getToken().startsWith("Bearer"));
    }
}
