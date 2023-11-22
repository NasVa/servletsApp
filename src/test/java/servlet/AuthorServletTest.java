package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static servlet.ServletMocks.*;

public class AuthorServletTest {
    @Test
    void createAuthorTest() throws IOException {
        refreshRequestMock(createTestAuthorDto(null));
        authorServlet.doPost(request, response);
        String expected = new ObjectMapper().writeValueAsString(createTestAuthorDto(AUTHOR_SAVE_ID));
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void getAuthorTest() throws IOException {
        refreshRequestMock(null);
        when(request.getParameter("id")).thenReturn("23");
        var authorDto = createTestAuthorDto(23);
        authorServlet.doGet(request, response);
        String actual = new ObjectMapper().writeValueAsString(authorDto);
        Assertions.assertEquals(actual, writer.toString());
    }

    @Test
    void deleteAuthorTest() throws IOException {
        refreshRequestMock(null);
        when(request.getParameter("id")).thenReturn("23");
        authorServlet.doDelete(request, response);
        Assertions.assertEquals(1, Integer.parseInt(writer.toString()));
    }

    @Test
    void updateAuthorTest() throws IOException {
        refreshRequestMock(createTestAuthorDto(AUTHOR_SAVE_ID));
        authorServlet.doPatch(request, response);
        String actual = new ObjectMapper().writeValueAsString(createTestAuthorDto(AUTHOR_SAVE_ID));
        Assertions.assertEquals(writer.toString(),actual);
    }
}
