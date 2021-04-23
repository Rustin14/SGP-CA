package SGP.CA.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import SGP.CA.DataAccess.Interfaces.IBookDAO;
import SGP.CA.Domain.Book;


public class BookDAO implements IBookDAO {

    @Override
    public int saveBook(Book book) throws SQLException {
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "INSERT INTO book (bookName, numberOfPages, authors, yearOfPublication, idEvidence) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, book.getBookName());
        statement.setInt(2, book.getNumberOfPages());
        statement.setString(3, book.getAuthors());
        statement.setInt(4, book.getYearOfPublication());
        statement.setInt(5, book.getIdEvidence());
        int successfulUpdate = statement.executeUpdate();
        return successfulUpdate;
    }

    @Override
    public Book searchBookByBookName(String bookName) throws SQLException {
        Book book = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM book WHERE bookName LIKE '%?%'";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, bookName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            book = new Book();
            book.setIdBook(resultSet.getInt("idBook"));
            book.setBookName(resultSet.getString("bookName"));
            book.setAuthors(resultSet.getString("authors"));
            book.setNumberOfPages(resultSet.getInt("numberOfPages"));
            book.setYearOfPublication(resultSet.getInt("yearOfPublication"));
            book.setIdEvidence(resultSet.getInt("idEvidence"));
        }
        return book;
    }

    @Override
    public ArrayList<Book> getAllBooks() throws SQLException {
        ArrayList<Book> allBooks = new ArrayList<>();
        Book book = null;
        ConnectDB databaseConnection = new ConnectDB();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM book";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            book = new Book();
            book.setIdBook(resultSet.getInt("idBook"));
            book.setBookName(resultSet.getString("bookName"));
            book.setAuthors(resultSet.getString("authors"));
            book.setNumberOfPages(resultSet.getInt("numberOfPages"));
            book.setYearOfPublication(resultSet.getInt("yearOfPublication"));
            book.setIdEvidence(resultSet.getInt("idEvidence"));
            allBooks.add(book);
        }
        return allBooks;
    }
}
