package com.ailtonpratajr.libraryapi.service;

import com.ailtonpratajr.libraryapi.exception.BusinessException;
import com.ailtonpratajr.libraryapi.model.entity.Book;
import com.ailtonpratajr.libraryapi.model.entity.repository.BookRepository;
import com.ailtonpratajr.libraryapi.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUp(){
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest(){
        //cenário
        Book book = createdValidBook();

        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(book)).thenReturn(
                Book.builder()
                        .id(1l).isbn("123")
                        .author("Fulano")
                        .title("As aventuras")
                        .build());

        //execucão
        Book savedBook = service.save(book);

        //verificação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
        assertThat(savedBook.getTitle()).isEqualTo("As aventuras");

    }

    @Test
    @DisplayName("Deve lançar um erro de negócio ao tentar salvar um livro com isbn duplicado")
    public void shouldNotSaveABookWithDuplicatedISBN(){
        //cenario
        Book book = createdValidBook();
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);

        //execucação
        Throwable exception = Assertions.catchThrowable(() -> service.save(book));

        //verificações
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn já cadastrado.");

        Mockito.verify(repository, Mockito.never()).save(book);

    }

    @Test
    @DisplayName("Deve obter um livro por Id")
    public void getIdTest(){
        //cenario
        Long id  = 1L;
        Book book = createdValidBook();
        book.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));

        //execucação
        Optional<Book> foundBook = service.getId(id);

        //verificação
        assertThat(foundBook.isPresent()).isTrue();
        assertThat(foundBook.get().getId()).isEqualTo(id);
        assertThat(foundBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(foundBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(foundBook.get().getIsbn()).isEqualTo(book.getIsbn());

    }

    @Test
    @DisplayName("Deve retornar vazio ao obter um livro por Id quando ele não existe na base")
    public void bookNotFoundIdTest(){
        //cenario
        Long id  = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //execucação
        Optional<Book> book = service.getId(id);

        //verificação
        assertThat(book.isPresent()).isFalse();

    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest(){
        //cenário
        Long id = 1L;
        Book book = createdValidBook();

        //execucação
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> service.delete(book));

        //verificação
        Mockito.verify(repository, Mockito.times(1)).delete(book);

    }

    @Test
    @DisplayName("Deve ocorrer um erro ao deletar um livro inexistente.")
    public void deleteInvalidBookTest(){
        //cenário
        Book book = new Book();

        //execucação
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(book));

        //verificação
        Mockito.verify(repository, Mockito.never()).delete(book);

    }




    private Book createdValidBook() {
        return Book.builder().id(1L).author("Ailton").title("E que haja guerra").isbn("001").build();
    }

}
