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
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


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

    private Book createdValidBook() {
        return Book.builder().id(1L).author("Ailton").title("E que haja guerra").isbn("001").build();
    }

}
