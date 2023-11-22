package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.when;
import static servlet.ServletMocks.*;

public class ReaderServletTest {

    @Test
    void createReaderTest() throws IOException {
        refreshRequestMock(createTestReaderDto(null));
        readerServlet.doPost(request, response);
        String expected = new ObjectMapper().writeValueAsString(createTestReaderDto(READER_SAVE_ID));
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void getReaderTest() throws IOException {
        refreshRequestMock(null);
        when(request.getParameter("id")).thenReturn("23");
        var readerDto = createTestReaderDto(23);
        readerServlet.doGet(request, response);
        String actual = new ObjectMapper().writeValueAsString(readerDto);
        Assertions.assertEquals(actual, writer.toString());
    }

    @Test
    void deleteReaderTest() throws ServletException, IOException {
        refreshRequestMock(null);
        when(request.getParameter("id")).thenReturn("23");
        readerServlet.doDelete(request, response);
        Assertions.assertEquals(1, Integer.parseInt(writer.toString()));
    }

    @Test
    void updateReaderTest() throws ServletException, IOException {
        refreshRequestMock(createTestReaderDto(READER_SAVE_ID));
        readerServlet.doPatch(request, response);
        String actual = new ObjectMapper().writeValueAsString(createTestReaderDto(READER_SAVE_ID));
        Assertions.assertEquals(writer.toString(),actual);
    }
}
