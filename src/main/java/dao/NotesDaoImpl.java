package dao;

import entity.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesDaoImpl implements NotesDao {

    private final String jdbcURL;
    private final String jdbcUsername;
    private final String jdbcPassword;
    private Connection jdbcConnection;

    public NotesDaoImpl(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    private static final String SQL_GET_ALL = "SELECT * FROM notes";
    private static final String SQL_SEARCH = "SELECT * FROM notes WHERE LOWER(title) LIKE '%s' OR LOWER(text) LIKE '%s'";
    private static final String SQL_INS_ROW = "INSERT INTO notes (title, text) values (?, ?)";
    private static final String SQL_DEL_ROW = "DELETE FROM notes WHERE id = ?";

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    private static String getSearchString(String searchString) {
        String search = "%" + searchString.toLowerCase() + "%";
        return String.format(SQL_SEARCH, search, search);
    }

//    private static final List<Note> notes = new ArrayList<>();
//
//    static {
//
//        notes.add(new Note("Title1", " text1"));
//        notes.add(new Note("Title2", " text2"));
//        notes.add(new Note(null, " text3"));
//        notes.add(new Note("Title4", " text4"));
//        notes.add(new Note(null, " text5"));
//    }


    @Override
    public void addNote(Note note) throws SQLException {
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(SQL_INS_ROW);
        statement.setString(1, note.getTitle());
        statement.setString(2, note.getText());
        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    @Override
    public List<Note> showAllNotes() throws SQLException {
        connect();
        List<Note> result = new ArrayList<>();
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_GET_ALL);
        return getNotes(result, statement, resultSet);
    }

    @Override
    public List<Note> searchBySubstring(String substring) throws SQLException {
        //TODO:
        connect();
        List<Note> searchResult = new ArrayList<>();
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(getSearchString(substring));
        return getNotes(searchResult, statement, resultSet);
    }

    private List<Note> getNotes(List<Note> searchResult, Statement statement, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String text = resultSet.getString("text");
            Note note = new Note(id, title, text);
            searchResult.add(note);
        }
        resultSet.close();
        statement.close();
        disconnect();
        return searchResult;
    }

    @Override
    public void deleteNote(long id) throws SQLException {
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(SQL_DEL_ROW);
        statement.setInt(1, Math.toIntExact(id));
        statement.executeUpdate();
        statement.close();
        disconnect();
    }
}

