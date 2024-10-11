package ru.sber.backend.services.client;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.sber.backend.entities.client.ClientPhone;
import ru.sber.backend.repositories.client.ClientPhoneRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Disabled
public class ClientTelephoneServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private ClientPhoneRepository clientPhoneRepository;

    @InjectMocks
    private ClientTelephoneServiceImp clientTelephoneService;

    @Test
    public void testAddClientPhoneSuccess() {
        when(clientService.getIdClient()).thenReturn("1234");
        when(clientPhoneRepository.existsByPhone(anyString())).thenReturn(false);

        assertTrue(clientTelephoneService.addClientPhone("123456789"));

        verify(clientPhoneRepository).save(any(ClientPhone.class));
    }

    @Test
    public void testAddClientPhoneFailureExistingPhone() {
        when(clientPhoneRepository.existsByPhone(anyString())).thenReturn(true);

        assertFalse(clientTelephoneService.addClientPhone("123456789"));

        verify(clientPhoneRepository, never()).save(any(ClientPhone.class));
    }

    @Test
    public void testAddClientPhoneExceptionOnSave() {
        when(clientService.getIdClient()).thenReturn("1234");
        when(clientPhoneRepository.existsByPhone(anyString())).thenReturn(false);
        when(clientPhoneRepository.save(any(ClientPhone.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> clientTelephoneService.addClientPhone("123456789"));
    }


    @Test
    public void testAddClientPhoneEmptyPhone() {
        when(clientPhoneRepository.save(any(ClientPhone.class))).thenAnswer(invocation -> {
            // Извлекаем переданный объект ClientPhone из аргументов вызова
            ClientPhone clientPhone = invocation.getArgument(0);

            // Проверяем, пустой ли номер телефона в переданном объекте
            if (clientPhone.getPhone().isEmpty()) {
                // Если номер пустой, выбрасываем исключение RuntimeException
                throw new RuntimeException();
            }

            // Возвращаем заглушку для сохранения объекта
            return null;
        });

        assertThrows(RuntimeException.class, () -> clientTelephoneService.addClientPhone(""));
    }

    @Test
    public void testAddClientPhoneNoClientId() {
        when(clientService.getIdClient()).thenReturn(null);

        when(clientPhoneRepository.save(any(ClientPhone.class))).thenAnswer(invocation -> {
            // Извлекаем переданный объект ClientPhone из аргументов вызова
            ClientPhone clientPhone = invocation.getArgument(0);

            if (clientPhone.getIdClient() == null) {
                throw new RuntimeException();
            }

            // Возвращаем заглушку для сохранения объекта
            return null;
        });

        assertThrows(RuntimeException.class, () -> clientTelephoneService.addClientPhone("123456789"));

        // Проверка, что метод сохранения не вызывался
    }


    @Test
    public void testGetAllClientPhonesByClientIdSuccess() {
        // Устанавливаем id клиента
        when(clientService.getIdClient()).thenReturn("123");

        // Создаем список телефонов для возвращения
        List<ClientPhone> clientPhones = new ArrayList<>();
        clientPhones.add(new ClientPhone(133L,"123456789", "123"));
        clientPhones.add(new ClientPhone(134L,"987654321", "123"));

        // Мокируем репозиторий для возвращения списка телефонов при поиске
        when(clientPhoneRepository.findAllClientPhonesByIdClient(anyString())).thenReturn(clientPhones);

        // Вызываем метод и проверяем результат
        List<String> result = clientTelephoneService.getAllClientPhonesByClientId();
        assertEquals(2, result.size());
        assertEquals("123456789", result.get(0));
        assertEquals("987654321", result.get(1));
    }

    @Test
    public void testGetAllClientPhonesByClientIdEmptyList() {
        // Устанавливаем id клиента
        when(clientService.getIdClient()).thenReturn("123");

        // Мокируем репозиторий для возвращения пустого списка телефонов при поиске
        when(clientPhoneRepository.findAllClientPhonesByIdClient(anyString())).thenReturn(new ArrayList<>());

        // Вызываем метод и проверяем, что результат пустой список
        List<String> result = clientTelephoneService.getAllClientPhonesByClientId();
        assertEquals(0, result.size());
    }


    @Test
    public void testDeleteClientPhoneSuccess() {
        // Устанавливаем id клиента
        when(clientService.getIdClient()).thenReturn("123");

        // Мокируем репозиторий для возвращения телефона при поиске
        ClientPhone clientPhone = new ClientPhone();
        clientPhone.setId(1L);
        when(clientPhoneRepository.findClientPhoneByPhoneAndIdClient(anyString(), anyString())).thenReturn(Optional.of(clientPhone));

        // Вызываем метод и проверяем, что телефон был удален
        assertTrue(clientTelephoneService.deleteClientPhone("123456789"));

        // Проверяем, что метод delete был вызван один раз с аргументом clientPhone
        verify(clientPhoneRepository, times(1)).delete(clientPhone);
    }

    @Test
    public void testDeleteClientPhoneNotFound() {
        // Устанавливаем id клиента
        when(clientService.getIdClient()).thenReturn("123");

        // Мокируем репозиторий для возвращения Optional.empty при поиске
        when(clientPhoneRepository.findClientPhoneByPhoneAndIdClient(anyString(), anyString())).thenReturn(Optional.empty());

        // Вызываем метод и проверяем, что телефон не был удален
        assertFalse(clientTelephoneService.deleteClientPhone("123456789"));

        // Проверяем, что метод delete не был вызван
        verify(clientPhoneRepository, never()).delete(any(ClientPhone.class));
    }

    @Test
    public void testDeleteClientPhoneException() {
        // Устанавливаем id клиента
        when(clientService.getIdClient()).thenReturn("123");

        // Мокируем репозиторий для выброса исключения при поиске
        when(clientPhoneRepository.findClientPhoneByPhoneAndIdClient(anyString(), anyString())).thenThrow(new RuntimeException());

        // Проверяем, что метод выбросил исключение
        assertThrows(RuntimeException.class, () -> clientTelephoneService.deleteClientPhone("123456789"));

        // Проверяем, что метод delete не был вызван
        verify(clientPhoneRepository, never()).delete(any(ClientPhone.class));
    }

}
