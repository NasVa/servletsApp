package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AuthorDto;
import dto.BookDto;
import dto.ReaderDto;
import exception.ValidationException;
import org.mockito.Mockito;
import service.AuthorService;
import service.BookService;
import service.impl.AuthorServiceImpl;
import service.impl.BookServiceImpl;
import service.impl.ReaderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.mockito.Mockito.*;

public class ServletMocks {
    static final ReaderServiceImpl readerService;
    static final AuthorService authorService;
    static final BookService bookService;
    static final ReaderServlet readerServlet;
    static final AuthorServlet authorServlet;
    static final BookServlet bookServlet;
    static HttpServletRequest request;
    static HttpServletResponse response;

    static final int BOOK_SAVE_ID = 121;
    static final int READER_SAVE_ID = 131;
    static final int AUTHOR_SAVE_ID = 141;
    static StringWriter writer;

    static void refreshRequestMock(Object object) {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        try {
            when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new ObjectMapper().writeValueAsString(object))));
            writer = new StringWriter();
            PrintWriter pr = new PrintWriter(writer);
            when(response.getWriter()).thenReturn(pr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        readerService = mock(ReaderServiceImpl.class);
        authorService = mock(AuthorServiceImpl.class);
        bookService = mock(BookServiceImpl.class);


        readerServlet = new ReaderServlet(readerService);
        authorServlet = new AuthorServlet(authorService);
        bookServlet = new BookServlet(bookService);

        when(readerService.create(Mockito.any())).then(invocationOnMock -> {
            ReaderDto readerDto = invocationOnMock.getArgument(0, ReaderDto.class);
            if (readerDto.getId() == null) {
                readerDto.setId(READER_SAVE_ID);
            }
            return readerDto;
        });

        when(readerService.update(Mockito.any())).then(invocationOnMock -> {
            var readerDto = invocationOnMock.getArgument(0, ReaderDto.class);
            if (readerDto.getId() == null) {
                throw new ValidationException();
            }
            return readerDto;
        });

        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0);
            return createTestReaderDto(id);
        }).when(readerService).getById(Mockito.anyInt());
        when(readerService.delete(Mockito.anyInt())).thenReturn(1);


        when(authorService.create(Mockito.any())).then(invocationOnMock -> {
            AuthorDto authorDto = invocationOnMock.getArgument(0, AuthorDto.class);
            if (authorDto.getId() == null) {
                authorDto.setId(AUTHOR_SAVE_ID);
            }
            return authorDto;
        });

        when(authorService.update(Mockito.any())).then(invocationOnMock -> {
            var authorDto = invocationOnMock.getArgument(0, AuthorDto.class);
            if (authorDto.getId() == null) {
                throw new ValidationException();
            }
            return authorDto;
        });

        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0);
            return createTestAuthorDto(id);
        }).when(authorService).getById(Mockito.anyInt());
        when(authorService.delete(Mockito.anyInt())).thenReturn(1);

        when(bookService.create(Mockito.any())).then(invocationOnMock -> {
            BookDto bookDto = invocationOnMock.getArgument(0, BookDto.class);
            if (bookDto.getId() == null) {
                bookDto.setId(BOOK_SAVE_ID);
            }
            return bookDto;
        });

        when(bookService.update(Mockito.any())).then(invocationOnMock -> {
            var bookDto = invocationOnMock.getArgument(0, BookDto.class);
            if (bookDto.getId() == null) {
                throw new ValidationException();
            }
            return bookDto;
        });


        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0);
            return createTestBookDto(id);
        }).when(bookService).getById(Mockito.anyInt());
        when(bookService.delete(Mockito.anyInt())).thenReturn(1);


    }

    static BookDto createTestBookDto(Integer id) {
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setName("Name");
        bookDto.setPages(111);
        bookDto.setYearOfPublishing(2001);
        bookDto.setLocationOfPublishing("M");
        return bookDto;
    }

    static ReaderDto createTestReaderDto(Integer id) {
        ReaderDto readerDto = new ReaderDto();
        readerDto.setId(id);
        readerDto.setMail("mail@mail.su");
        readerDto.setName("Bob");
        readerDto.setPhone(2224);
        return readerDto;
    }

    static AuthorDto createTestAuthorDto(Integer id) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        return authorDto;
    }
}
