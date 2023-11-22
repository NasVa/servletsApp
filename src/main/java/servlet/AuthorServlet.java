package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AuthorDto;
import service.AuthorService;
import service.impl.AuthorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "authorServlet", urlPatterns = "/authors")
public class AuthorServlet extends HttpServlet {
    private final AuthorService authorService;
    public AuthorServlet() {
        this.authorService = AuthorServiceImpl.getInstance();
    }
    public AuthorServlet(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AuthorDto authorDto = authorService.getById(Integer.parseInt(req.getParameter("id")));
        writeResponse(resp, authorDto);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AuthorDto authorDto = new ObjectMapper().readValue(req.getReader(), AuthorDto.class);
        authorDto = authorService.create(authorDto);
        writeResponse(resp, authorDto);
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
        authorService.delete(Integer.parseInt(req.getParameter("id")));
        writeResponse(resp, 1);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AuthorDto authorDto = new ObjectMapper().readValue(req.getReader(), AuthorDto.class);
        authorDto = authorService.update(authorDto);
        writeResponse(resp, authorDto);
    }
}
