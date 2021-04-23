package SGP.CA.DataAccess.Interfaces;

import SGP.CA.Domain.Book;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IBookDAO {

    public int saveBook(Book book) throws SQLException;
    public Book searchBookByBookName (String bookName) throws SQLException;
    public ArrayList<Book> getAllBooks () throws SQLException;
}
