package dao;

import entity.Note;

import java.sql.SQLException;
import java.util.List;

public interface NotesDao {

    void addNote(Note note) throws SQLException;
    List<Note> showAllNotes() throws SQLException;
    List<Note> searchBySubstring(String substring) throws SQLException;
    void deleteNote(long id) throws SQLException;
}
