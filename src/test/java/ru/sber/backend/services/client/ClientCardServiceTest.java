package ru.sber.backend.services.client;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.backend.entities.client.ClientCard;
import ru.sber.backend.models.client.AddClientCardRequest;
import ru.sber.backend.repositories.client.ClientCardRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Disabled
public class ClientCardServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private ClientCardRepository clientCardRepository;

    @InjectMocks
    private ClientCardServiceImp clientCardService;

    @Test
    public void testAddClientCardSuccess() {
        when(clientService.getIdClient()).thenReturn("123");

        when(clientCardRepository.existsByClientCardNumber(anyString())).thenReturn(false);

        AddClientCardRequest request = new AddClientCardRequest("1234567890123456", 123, LocalDate.of(2002, 12, 25), "John Doe");
        assertTrue(clientCardService.addClientCard(request));
    }

    @Test
    public void testAddClientCardAlreadyExists() {

        when(clientCardRepository.existsByClientCardNumber(anyString())).thenReturn(true);

        AddClientCardRequest request = new AddClientCardRequest("1234567890123456", 123, LocalDate.of(2002, 12, 25), "John Doe");
        assertFalse(clientCardService.addClientCard(request));
    }

    @Test
    public void testAddClientCardNullCardNumber() {
        when(clientService.getIdClient()).thenReturn("123");

        AddClientCardRequest request = new AddClientCardRequest(null, 123, LocalDate.of(2002, 12, 25), "John Doe");

        when(clientCardRepository.save(any(ClientCard.class))).thenAnswer(invocation -> {
            ClientCard clientCard = invocation.getArgument(0);

            if (clientCard.getClientCardNumber() == null) {
                throw new RuntimeException();
            }

            // Возвращаем заглушку для сохранения объекта
            return null;
        });


        assertThrows(RuntimeException.class, () -> clientCardService.addClientCard(request));
    }

    @Test
    public void testAddClientCardNullExpirationDate() {
        when(clientService.getIdClient()).thenReturn("123");

        AddClientCardRequest request = new AddClientCardRequest("324", 123, null, "John Doe");

        when(clientCardRepository.save(any(ClientCard.class))).thenAnswer(invocation -> {
            ClientCard clientCard = invocation.getArgument(0);

            if (clientCard.getClientCardExpirationDate() == null) {
                throw new RuntimeException();
            }

            // Возвращаем заглушку для сохранения объекта
            return null;
        });


        assertThrows(RuntimeException.class, () -> clientCardService.addClientCard(request));
    }

    @Test
    public void testAddClientCardNullCardOwner() {
        when(clientService.getIdClient()).thenReturn("123");

        AddClientCardRequest request = new AddClientCardRequest("324", 123, LocalDate.of(2002, 12, 25), null);

        when(clientCardRepository.save(any(ClientCard.class))).thenAnswer(invocation -> {
            ClientCard clientCard = invocation.getArgument(0);

            if (clientCard.getClientCardOwner() == null) {
                throw new RuntimeException();
            }

            // Возвращаем заглушку для сохранения объекта
            return null;
        });


        assertThrows(RuntimeException.class, () -> clientCardService.addClientCard(request));
    }

    @Test
    public void testGetClientCardFound() {
        Long clientCardId = 123L;
        ClientCard clientCard = new ClientCard();
        clientCard.setId(clientCardId);
        when(clientCardRepository.findById(clientCardId)).thenReturn(Optional.of(clientCard));

        Optional<ClientCard> result = clientCardService.getClientCard(clientCardId);

        assertTrue(result.isPresent());
        assertEquals(clientCardId, result.get().getId());
    }

    @Test
    public void testGetClientCardNotFound() {
        Long clientCardId = 123L;
        when(clientCardRepository.findById(clientCardId)).thenReturn(Optional.empty());

        Optional<ClientCard> result = clientCardService.getClientCard(clientCardId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetAllClientCardsByClientId() {
        when(clientService.getIdClient()).thenReturn("123");

        List<ClientCard> clientCards = Arrays.asList(
                new ClientCard(1L, "1234567890123456", 123, LocalDate.of(2022, 12, 25), "John Doe", "123"),
                new ClientCard(2L, "9876543210987654", 456, LocalDate.of(2023, 12, 25), "Jane Doe", "123")
        );

        when(clientCardRepository.findAllClientCardsByIdClient("123")).thenReturn(clientCards);


        List<String> result = clientCardService.getAllClientCardsByClientId();

        assertEquals(2, result.size());
        assertEquals("1234567890123456", result.get(0));
        assertEquals("9876543210987654", result.get(1));
    }

    @Test
    public void testGetAllClientCardsByClientIdEmptyList() {
        when(clientService.getIdClient()).thenReturn("123");

        when(clientCardRepository.findAllClientCardsByIdClient("123")).thenReturn(Collections.emptyList());

        List<String> result = clientCardService.getAllClientCardsByClientId();

        assertTrue(result.isEmpty());
    }


}
