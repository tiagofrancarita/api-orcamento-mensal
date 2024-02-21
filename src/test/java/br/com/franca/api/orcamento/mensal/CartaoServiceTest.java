package br.com.franca.api.orcamento.mensal;

import br.com.franca.api.orcamento.mensal.core.entities.CartaoCreditoEntity;
import br.com.franca.api.orcamento.mensal.core.enums.StatusCartaoCredito;
import br.com.franca.api.orcamento.mensal.core.exceptions.CartaoNotFoundException;
import br.com.franca.api.orcamento.mensal.core.repositorys.CartaoCreditoRepository;
import br.com.franca.api.orcamento.mensal.core.service.CartaoCreditoServiceImpl;
import br.com.franca.api.orcamento.mensal.core.service.CartaoValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartaoServiceTest {

    @Mock
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Mock
    CartaoValidator cartaoValidator;

    @InjectMocks
    private CartaoCreditoServiceImpl cartaoService;

    private CartaoCreditoEntity cartaoValido;

    @Before
    public void setUp() {
        // Configurar um cartão válido para os testes
        cartaoValido = new CartaoCreditoEntity();
        cartaoValido.setNomeTitular("Titular Válido");
        cartaoValido.setNumeroCartao("1234567890123456");
        cartaoValido.setDataVencimento(LocalDate.now().plusMonths(1));
        cartaoValido.setAtivo(true);
        cartaoValido.setBandeira("VISA");
        cartaoValido.setStatus(StatusCartaoCredito.VALIDO);
    }

    @Test
    public void testSalvarCartaoComSucesso() {
        // Configurar o mock do repositório para retornar o cartão salvo
        when(cartaoCreditoRepository.save(cartaoValido)).thenReturn(cartaoValido);

        // Executar o método que está sendo testado
        CartaoCreditoEntity cartaoSalvo = cartaoService.salvarCartao(cartaoValido);

        // Verificar se a validação de dados foi chamada
        verify(cartaoValidator).validarCartao(cartaoValido);

        // Verificar se o repositório foi chamado corretamente
        verify(cartaoCreditoRepository).save(cartaoValido);

        // Verificar se o cartão retornado é o mesmo que foi salvo
        assertEquals(cartaoValido, cartaoSalvo);
    }

    @Test
    public void testSalvarCartaoComFalhaNaValidacao() {
        // Configurar um cartão inválido para falhar na validação
        CartaoCreditoEntity cartaoInvalido = new CartaoCreditoEntity();

        // Configurar o comportamento esperado para o validador lançar uma exceção
        doThrow(new RuntimeException("Nome do titular do cartão de crédito não informado."))
                .when(cartaoValidator)
                .validarCartao(cartaoInvalido);

        // Executar o método que está sendo testado e verificar a exceção lançada
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            cartaoService.salvarCartao(cartaoInvalido);
        });

        // Verificar a mensagem da exceção
        assertEquals("Nome do titular do cartão de crédito não informado.", exception.getMessage());

        // Verificar se o validador foi chamado corretamente
        verify(cartaoValidator).validarCartao(cartaoInvalido);

        // Verificar se o repositório não foi chamado
        verify(cartaoCreditoRepository, never()).save(cartaoInvalido);
    }

    @Test
    public void testDeletarPorIdComSucesso() {
        // Configurar o comportamento esperado do repositório ao deletar por ID
        Long idExistente = 1L;
        doNothing().when(cartaoCreditoRepository).deleteById(idExistente);

        // Executar o método que está sendo testado
        ResponseEntity<CartaoCreditoEntity> resposta = cartaoService.deletarPorId(idExistente);

        // Verificar se o repositório foi chamado corretamente
        verify(cartaoCreditoRepository).deleteById(idExistente);

        // Verificar se a resposta é HttpStatus.OK
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    public void testDeletarPorIdComFalha() {
        // Configurar o comportamento esperado do repositório ao lançar uma exceção ao deletar por ID
        Long idInexistente = 2L;
        doThrow(EmptyResultDataAccessException.class).when(cartaoCreditoRepository).deleteById(idInexistente);

        // Executar o método que está sendo testado e verificar a resposta da entidade
        ResponseEntity<CartaoCreditoEntity> response = cartaoService.deletarPorId(idInexistente);

        // Verificar se o repositório foi chamado corretamente
        verify(cartaoCreditoRepository).deleteById(idInexistente);

        // Verificar se a resposta da entidade é NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testListarCartaoComCartoesAtivos() {
        // Simular o comportamento do repositório
        List<CartaoCreditoEntity> cartoesAtivos = Arrays.asList(
                new CartaoCreditoEntity(1L, "5358580222151652","VISA","TIAGO JUNIOR", LocalDate.of(2027, 12, 31), true,StatusCartaoCredito.VALIDO.name()),
                new CartaoCreditoEntity(2L, "5358809674771455","MASTERCARD","JUBILEU JUNIOR", LocalDate.of(2026, 12, 31), true,StatusCartaoCredito.VALIDO.name()));

        when(cartaoCreditoRepository.findByStatusAtivoTrue()).thenReturn(cartoesAtivos);

        // Chamar o método do serviço
        List<CartaoCreditoEntity> result = cartaoService.listarCartao();

        // Verificar se o resultado é o esperado
        assertEquals(cartoesAtivos, result);

        // Verificar se o repositório foi chamado corretamente
        verify(cartaoCreditoRepository, times(1)).findByStatusAtivoTrue();
    }

    @Test
    public void testListarCartaoComCartoesAtivosRepositorioRetornaNull() {
        // Simular o comportamento do repositório retornando null
        when(cartaoCreditoRepository.findByStatusAtivoTrue()).thenReturn(null);

        // Chamar o método do serviço e esperar uma exceção
        CartaoNotFoundException exception = assertThrows(CartaoNotFoundException.class, () -> {
            cartaoService.listarCartao();
        });

        // Verificar a mensagem da exceção
        assertEquals("Não há cartões de crédito ativos/valido cadastrados.", exception.getMessage());

        // Verificar se o repositório foi chamado corretamente
        verify(cartaoCreditoRepository, times(1)).findByStatusAtivoTrue();

    }

    @Test
    public void testListarCartaoComCartoesAtivosRepositorioRetornaListaVazia() {
        // Configurar o mock para retornar uma lista vazia
        when(cartaoCreditoRepository.findByStatusAtivoTrue()).thenReturn(Collections.emptyList());

        // Executar o método que está sendo testado
        Throwable exception = assertThrows(CartaoNotFoundException.class, () -> {
            cartaoService.listarCartao();
        });

        // Verificar se a exceção esperada foi lançada
        assertEquals("Não há cartões de crédito ativos/valido cadastrados.", exception.getMessage());
    }
}
