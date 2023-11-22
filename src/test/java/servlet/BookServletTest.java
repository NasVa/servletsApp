package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static servlet.ServletMocks.*;

public class BookServletTest {
    @Test
    void createBookTest() throws IOException {
        refreshRequestMock(createTestBookDto(null));
        bookServlet.doPost(request, response);
        String actual = new ObjectMapper().writeValueAsString(createTestBookDto(BOOK_SAVE_ID));
        Assertions.assertEquals(writer.toString(),actual);
    }

    @Test
    void getBookTest() throws IOException {
        refreshRequestMock(null);
        when(request.getParameter("id")).thenReturn("23");
        var bookDto = createTestBookDto(23);
        bookServlet.doGet(request, response);
        String expected = new ObjectMapper().writeValueAsString(bookDto);
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void deleteBookTest() throws IOException {
        refreshRequestMock(null);
        when(request.getParameter("id")).thenReturn("23");
        bookServlet.doDelete(request, response);
        Assertions.assertEquals(1, Integer.parseInt(writer.toString()));
    }

    @Test
    void updateBookTest() throws IOException {
        refreshRequestMock(createTestBookDto(BOOK_SAVE_ID));
        bookServlet.doPatch(request, response);
        String expected = new ObjectMapper().writeValueAsString(createTestBookDto(BOOK_SAVE_ID));
        Assertions.assertEquals(expected, writer.toString());
    }
}
