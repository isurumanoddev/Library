package tm;

public class BookTm {
    private int id ;
    private String title;
    private String author;
    private String publisher;
    private String isbn;

    public BookTm() {
    }

    public BookTm(int id, String title, String author, String publisher, String isbn) {
        this.setId(id);
        this.setTitle(title);
        this.setAuthor(author);
        this.setPublisher(publisher);
        this.setIsbn(isbn);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
