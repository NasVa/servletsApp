package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BookDto;
import service.BookService;
import service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "bookServlet", urlPatterns = "/books")
public class BookServlet extends HttpServlet {
    private final BookService bookService;
    public BookServlet() {
        this.bookService = BookServiceImpl.getInstance();
    }
    public BookServlet(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BookDto bookDto = bookService.getById(Integer.parseInt(req.getParameter("id")));
        writeResponse(resp, bookDto);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BookDto bookDto = new ObjectMapper().readValue(req.getReader(), BookDto.class);
        bookDto = bookService.create(bookDto);
        writeResponse(resp, bookDto);
    }
    private static void writeResponse(HttpServletResponse resp, Object object) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(new ObjectMapper().writeValueAsString(object));
        resp.getWriter().flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.delete(Integer.parseInt(req.getParameter("id")));
        writeResponse(resp, (Integer) 1);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BookDto bookDto = new ObjectMapper().readValue(req.getReader(), BookDto.class);
        bookDto = bookService.update(bookDto);
        writeResponse(resp, bookDto);
    }
}
