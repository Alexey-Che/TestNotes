package controller;

import dao.NotesDao;
import dao.NotesDaoImpl;
import entity.Note;
import exception.NotesException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class SelectNotesServlet extends HttpServlet {

    private NotesDao notesDao;

    @Override
    public void init() throws ServletException {
        notesDao = new NotesDaoImpl("jdbc:postgresql://127.0.0.1:5432/testConnect?currentSchema=schema_notes", "postgres", "postgres");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    addNote(req, resp);
                    break;
                case "/delete":
                    deleteNote(req, resp);
                    break;
                case "/search":
                    searchNotes(req, resp);
                    break;
                default: //no action - show list(1st page)
                    listNotes(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listNotes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        List<Note> listNotes = notesDao.showAllNotes();
        request.setAttribute("listNotes", listNotes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("NoteList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("NoteForm.jsp");
        dispatcher.forward(request, response);
    }

    private void searchNotes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String searchText = request.getParameter("searchText");
        List<Note> listNotes = null;
        if (searchText == null || searchText.trim().isEmpty()) {
            listNotes = notesDao.showAllNotes();
        } else {
            listNotes = notesDao.searchBySubstring(searchText);
        }
        request.setAttribute("listNotes", listNotes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("NoteList.jsp");
        dispatcher.forward(request, response);
    }

    private void addNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NotesException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        if (text.isEmpty()) throw new NotesException("text field cannot be empty");
        if (title.length() > 20) throw new NotesException("the title cannot contain more than 20 characters");
        Note note = new Note(title, text);
        notesDao.addNote(note);
        response.sendRedirect("list");
    }

    private void deleteNote(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        notesDao.deleteNote(id);
        response.sendRedirect("list");
    }
}
