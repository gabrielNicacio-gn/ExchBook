package dev.nicacio.Exchbook.services;

import dev.nicacio.Exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.Exchbook.dtos.response.CreateBookResponseDto;
import dev.nicacio.Exchbook.enums.Condition;
import dev.nicacio.Exchbook.models.Author;
import dev.nicacio.Exchbook.models.Book;
import dev.nicacio.Exchbook.models.CopyBook;
import dev.nicacio.Exchbook.models.EditionBook;
import dev.nicacio.Exchbook.repository.AuthorRepository;
import dev.nicacio.Exchbook.repository.BookRepository;
import dev.nicacio.Exchbook.repository.CopyBookRepository;
import dev.nicacio.Exchbook.repository.EditionBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServices {
    private final BookRepository bookRepository;
    private final CopyBookRepository copyBookRepository;
    private final AuthorRepository authorRepository;
    private final EditionBookRepository editionBookRepository;

    @Autowired
    public BookServices(
            BookRepository bookRepository,
            CopyBookRepository copyBookRepository,
            AuthorRepository authorRepository,
            EditionBookRepository editionBookRepository
            ){
        this.bookRepository = bookRepository;
        this.copyBookRepository = copyBookRepository;
        this.authorRepository = authorRepository;
        this.editionBookRepository = editionBookRepository;
    }

    public CreateBookResponseDto RegisterBook(CreateBookRequestDto createBook){

        var author = getOrCreateAuthor(createBook.authorName());
        var book = getOrCreateBook(createBook,author);
        var newCopy = createCopyBook(createBook.condition(),book);

        return new CreateBookResponseDto(newCopy.getIdCopy(),book.getTitle(),newCopy.getCondition());
    }

    private Book getOrCreateBook(CreateBookRequestDto createBook, Author author) {

        var bookExist = bookRepository.findByTitleAndAuthorAndEdition("", author.getName(), "");

        if (bookExist != null) {
            return bookExist;
        }

        var book = new Book();
        book.setTitle("");
        book.addAuthors(author);

        var edition = new EditionBook();
        edition.setBook(book);
        edition.setNumberEdition(createBook.numberEdition());
        edition.setYearOfPublication(createBook.yearOfPublication());
        edition.setFormat(createBook.format());

        book.addEditions(edition);
        editionBookRepository.save(edition);
        bookRepository.save(book);

        return book;
    }
    private Author getOrCreateAuthor(String authorName){

        var author = authorRepository.findByName(authorName);
        if(author == null){
            var newAuthor = new Author();
            newAuthor.setName(authorName);
            authorRepository.save(newAuthor);
            return  newAuthor;
        }
        return author;
    }
    private CopyBook createCopyBook(Condition condition, Book book){
        var newCopy = new CopyBook();
        newCopy.setBook(book);
        newCopy.setCondition(condition);
        copyBookRepository.save(newCopy);
        return newCopy;
    }
}
