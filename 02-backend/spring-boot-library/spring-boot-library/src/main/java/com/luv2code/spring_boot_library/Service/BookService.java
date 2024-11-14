package com.luv2code.spring_boot_library.Service;

import com.luv2code.spring_boot_library.dao.BookRepository;
import com.luv2code.spring_boot_library.dao.CheckoutRepository;
import com.luv2code.spring_boot_library.entity.Book;
import com.luv2code.spring_boot_library.entity.Checkout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Book checkoutBook (String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );

        checkoutRepository.save(checkout);

        return book.get();
    }
}




//package com.luv2code.spring_boot_library.Service;
//
//import com.luv2code.spring_boot_library.dao.BookRepository;
//import com.luv2code.spring_boot_library.dao.CheckoutRepository;
//import com.luv2code.spring_boot_library.entity.Book;
//import com.luv2code.spring_boot_library.entity.Checkout;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class BookService {
//
//    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
//
//    private BookRepository bookRepository;
//    private CheckoutRepository checkoutRepository;
//
//    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
//        this.bookRepository = bookRepository;
//        this.checkoutRepository = checkoutRepository;
//    }
//
//    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
//        logger.info("Attempting to checkout book with ID: {} for user: {}", bookId, userEmail);
//
//        Optional<Book> book = bookRepository.findById(bookId);
//        if (!book.isPresent()) {
//            logger.error("Book with ID {} not found", bookId);
//            throw new Exception("Book doesn't exist");
//        }
//
//        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
//        if (validateCheckout != null) {
//            logger.error("User {} has already checked out book with ID {}", userEmail, bookId);
//            throw new Exception("Book already checked out by user");
//        }
//
//        if (book.get().getCopiesAvailable() <= 0) {
//            logger.error("No available copies for book with ID {}", bookId);
//            throw new Exception("No available copies");
//        }
//
//        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
//        bookRepository.save(book.get());
//        logger.info("Book with ID {} checked out successfully, copies left: {}", bookId, book.get().getCopiesAvailable());
//
//        Checkout checkout = new Checkout(
//                userEmail,
//                LocalDate.now().toString(),
//                LocalDate.now().plusDays(7).toString(),
//                book.get().getId()
//        );
//        checkoutRepository.save(checkout);
//        logger.info("Checkout record created for user: {}, book ID: {}", userEmail, bookId);
//
//        return book.get();
//    }
//}
