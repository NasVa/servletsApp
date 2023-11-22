package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ReaderDto;
import service.ReaderService;
import service.impl.ReaderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlet", urlPatterns = {"/readers"})
public class ReaderServlet extends HttpServlet {
    private final ReaderService readerService;

    public ReaderServlet() {
        this.readerService = ReaderServiceImpl.getInstance();
    }

    public ReaderServlet(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ReaderDto readerDto = readerService.getById(Integer.parseInt(req.getParameter("id")));
        writeResponse(resp, readerDto);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ReaderDto readerDto = new ObjectMapper().readValue(req.getReader(), ReaderDto.class);
        readerDto = readerService.create(readerDto);
        writeResponse(resp, readerDto);
    }

    private static void writeResponse(HttpServletResponse resp, Object object) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(new ObjectMapper().writeValueAsString(object));
        resp.getWriter().flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        readerService.delete(Integer.parseInt(req.getParameter("id")));
        writeResponse(resp, (Integer) 1);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReaderDto readerDto = new ObjectMapper().readValue(req.getReader(), ReaderDto.class);
        readerDto = readerService.update(readerDto);
        writeResponse(resp, readerDto);
    }

}
