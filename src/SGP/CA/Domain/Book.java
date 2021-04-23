package SGP.CA.Domain;

public class Book extends Evidence {

    private int idBook;
    private String bookName;
    private int numberOfPages;
    private String authors;
    private int yearOfPublication;

    public Book() {}

    public Book(int idEvidence, String description, String filePath, int idBook, String bookName, int numberOfPages, String authors, int yearOfPublication) {
        super(idEvidence, description, filePath);
        this.idBook = idBook;
        this.bookName = bookName;
        this.numberOfPages = numberOfPages;
        this.authors = authors;
        this.yearOfPublication = yearOfPublication;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
}
